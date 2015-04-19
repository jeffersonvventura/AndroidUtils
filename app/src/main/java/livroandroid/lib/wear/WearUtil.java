package livroandroid.lib.wear;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

/**
 * Classe utilitária para Google Wear
 *
 * - Conecta no Google Play Services
 * - Facilita utilizar a Node API, Data API e Message API
 */
public class WearUtil {
    private static final String TAG = "wear";
    private final GoogleApiClient mGoogleApiClient;
    private DataApi.DataListener dataListener;
    private MessageApi.MessageListener messageListener;
    private NodeApi.NodeListener nodeListener;
    // id do outro device (mobile ou wear)
    private String nodeId;

    public WearUtil(Context context) {
        this.dataListener = dataListener;
        this.messageListener = messageListener;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        findDeviceNodeId();
                        // Ao conectar, liga a DataAPI e MessageAPI
                        Log.d(TAG, "Play Services onConnected!");
                        if(dataListener != null) {
                            Log.d(TAG, "addListener DataApi");
                            Wearable.DataApi.addListener(mGoogleApiClient, dataListener);
                        }
                        if(messageListener != null) {
                            Log.d(TAG, "addListener MessageApi");
                            Wearable.MessageApi.addListener(mGoogleApiClient, messageListener);
                        }
                        if(nodeListener != null) {
                            Log.d(TAG, "addListener NodeApi");
                            Wearable.NodeApi.addListener(mGoogleApiClient, nodeListener);
                        }
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();
    }

    public void setDataListener(DataApi.DataListener dataListener) {
        this.dataListener = dataListener;
    }

    public void setMessageListener(MessageApi.MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setNodeListener(NodeApi.NodeListener nodeListener) {
        this.nodeListener = nodeListener;
    }

    // Conecta no Google Play Services
    public void connect() {
        Log.d(TAG, "connect()");
        mGoogleApiClient.connect();
    }

    // Disconecta do Google Play Services
    public void disconnect() {
        Log.d(TAG, "disconnect()");
        if(mGoogleApiClient.isConnected()) {
            // Desliga as APIs do wear
            Wearable.DataApi.removeListener(mGoogleApiClient, this.dataListener);
            Wearable.MessageApi.removeListener(mGoogleApiClient, this.messageListener);
        }
        mGoogleApiClient.disconnect();
    }

    // Envia dados com a Data API
    public void sendData(String path,Bundle bundle) {
        Log.d(TAG, ">> sendData() " + path);
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(path);
        DataMap dataMap = DataMap.fromBundle(bundle);
        putDataMapReq.getDataMap().putAll(dataMap);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
    }

    // Envia uma mensagem com a Message API
    public void sendMessage(String path,byte[] msg) {
        Log.d(TAG, ">> sendMessage() " + path + ", to: " + nodeId);
        if(nodeId != null) {
            Wearable.MessageApi.sendMessage(mGoogleApiClient, nodeId, path, msg);
        }
    }

    // Descobre o id do outro device (mobile ou wear)
    private void findDeviceNodeId() {
        // Chamada assíncrona, informa o callback
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).setResultCallback(
                new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        List<Node> nodes = getConnectedNodesResult.getNodes();
                        if (nodes != null && !nodes.isEmpty()) {
                            Node node = nodes.get(0);
                            nodeId = node.getId();
                            Log.d(TAG, "findDeviceNodeId nodeId: " + nodeId);
                        }
                    }
                }
        );
    }
}
