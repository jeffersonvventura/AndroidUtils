package livroandroid.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 */
public abstract class BaseFragment extends DebugFragment {
    private Map<String,Task> tasks = new HashMap<String,Task>();
    private ProgressDialog progress;
    // Este é o fragment base do projeto.
    // Útil se for necessário inserir algum método e lógica para todos os fragments

    public Context getContext() {
        return getActivity();
    }

    public void startTask(String cod,TaskListener listener) {
        startTask(cod,listener, 0);
    }

    public void cancellTask(String cod) {
        Task task = tasks.get(cod);
        if(task != null) {
            task.cancel(true);
            tasks.remove(cod);
        }
    }

    public void startTask(String cod,TaskListener listener, int progressId) {
        Log.d("livroandroid","startTask: " + cod);
        View view = getView();
        if(view == null) {
            throw new RuntimeException("Somente pode iniciar a task se a view do fragment foi criada.\nChame o startTask depois do onCreateView");
        }

        Task task = this.tasks.get(cod);
        if(task == null) {
            // Somente executa se já não está executando
            task = new Task(cod, listener, progressId);
            this.tasks.put(cod, task);
            task.execute();
        }
    }

    private class TaskResult<T> {
        private T response;
        private Exception exception;
    }

    public interface TaskListener<T> {
        // Executa em background e retorna o objeto
        T execute() throws Exception;
        // Atualiza a view na UI Thread
        void updateView(T response);
        // Chamado caso o método execute() lance uma exception
        void onError(Exception exception);
        // Chamado caso a task tenha sido cancelada
        void onCancelled(String cod);
    }

    private class Task extends AsyncTask<Void,Void,TaskResult> {

        private String cod;
        private TaskListener listener;
        private int progressId;

        private Task(String cod,TaskListener listener, int progressId) {
            this.cod = cod;
            this.listener = listener;
            this.progressId = progressId;
        }

        @Override
        protected void onPreExecute() {
            Log.d("livroandroid", "task onPreExecute()");
            showProgress(this,progressId);
        }

        @Override
        protected TaskResult doInBackground(Void... params) {
            TaskResult r = new TaskResult();
            try {
                 r.response = listener.execute();
            } catch (Exception e) {
                Log.e("livroandroid",e.getMessage(),e);
                r.exception = e;
            }
            return r;
        }

        protected void onPostExecute(TaskResult result) {
            Log.d("livroandroid","task onPostExecute(): " + result);
            try {
                if(result != null) {
                    if(result.exception != null) {
                        listener.onError(result.exception);
                    } else {
                        listener.updateView(result.response);
                    }
                }
            } finally {
                tasks.remove(cod);
                closeProgress(progressId);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            tasks.remove(cod);
            listener.onCancelled(cod);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        stopTasks();
    }

    private void stopTasks() {
        if(tasks != null) {
            for (String key : tasks.keySet()) {
                Task task = tasks.get(key);
                if(task != null) {
                    boolean running = task.getStatus().equals(AsyncTask.Status.RUNNING);
                    if(running) {
                        task.cancel(true);
                        closeProgress(0);
                    }
                }
            }
            tasks.clear();
        }
    }

    private void closeProgress(int progressId) {
        if(progressId > 0 && getView() != null) {
            View view = getView().findViewById(progressId);
            if(view != null) {
                view.setVisibility(View.GONE);
                return;
            }
        }

        Log.d("livroandroid","closeProgress()");
        if(progress != null && progress.isShowing()) {
            progress.dismiss();
            progress = null;
        }
    }

    protected void showProgress(final Task task, int progressId) {
        if(progressId > 0 && getView() != null) {
            View view = getView().findViewById(progressId);
            if(view != null) {
                view.setVisibility(View.VISIBLE);
                return;
            }
        }

        // Mostra o dialog e permite cancelar
        if(progress == null) {
            progress = ProgressDialog.show(getActivity(), "Aguarde", "Por favor aguarde...");
            progress.setCancelable(true);
            progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // Cancela a AsyncTask
                    task.cancel(true);
                }
            });
        }
    }

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void alert(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
