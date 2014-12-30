1) XMl de layout

<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <!-- Conteúdo -->
    <FrameLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


    <!-- Lista Menu Lateral -->
    <fragment
        android:id="@+id/navigation_drawer"
        android:layout_width="@dimen/navigation_drawer_width"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:name="livroandroid.fragment.NavigationDrawerFragment"
        tools:layout="@layout/navigation_drawer" />

</android.support.v4.widget.DrawerLayout>

2) ListView

/res/layout/navigation_drawer.xml

<ListView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:choiceMode="singleChoice"
    android:divider="@android:color/transparent"
    android:dividerHeight="0dp"
    android:background="#cccc"
    tools:context=".NavigationDrawerFragment" />

2) Activity

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Título da tela para mostrar no NavDrawer
     * Os fragments se quiserem podem alterar este título
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Configura o drawer
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            // Menu aberto, seta o título do app e mostra menu global
            // getMenuInflater().inflate(R.menu.global, menu);
            mNavigationDrawerFragment.setActionBarTitle(R.string.app_name);
            return true;
        } else {
            // Menu fechado, seta o título do menu selecionado
            // getMenuInflater().inflate(R.menu.main, menu);
            mNavigationDrawerFragment.setActionBarTitle(mTitle);
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public ListAdapter getNavDrawerListAdapter() {
        ActionBar a = getSupportActionBar();
        Log.d("livroandroid", "action bar: " + a);

        return new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        "Menu 1",
                        "Menu 2",
                        "Menu 3",
                });
    }

    @Override
    public ListView getNavDrawerListView(LayoutInflater inflater, ViewGroup container) {
        ListView listView = (ListView) inflater.inflate(R.layout.navigation_drawer, container, false);

        return listView;
    }

    @Override
    public void onNavDrawerItemSelected(int position) {
        Log.d("livroandroid", "Clicou: " + position);
        Toast.makeText(this,"Clicou: " + position,Toast.LENGTH_SHORT).show();
    }
}