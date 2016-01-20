package qrcodescanner.agungmanuaba.com.qrcodescanner.helpers;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Singleton RestClient
 *
 * Created by Ari_S on 6/15/2015.
 */
public class HttpClient {
    private static HttpClient mInstance = null;
    private AsyncHttpClient aSyncClient;
    private JSONObject details;
    private JSONArray licensed;
    private JSONArray roles;
    private static boolean loggedIn = false;

    public static HttpClient getInstance() {
        if(mInstance == null) {
            mInstance = new HttpClient();
        }

        return mInstance;
    }

    private HttpClient() {
        aSyncClient = new AsyncHttpClient();
        aSyncClient.setTimeout(60 * 1000);
        aSyncClient.setMaxRetriesAndTimeout(5, 1000);
    }

    public AsyncHttpClient getClient(){
        return aSyncClient;
    }


    /**
     * @param context
     * @param url
     * @param entity
     * @param contentType
     * @param responseHandler
     */
    public void post(final Context context, final String url, final HttpEntity entity, final String contentType, final ResponseHandlerInterface responseHandler) throws JSONException, UnsupportedEncodingException {
        aSyncClient.post(context, url, entity, contentType, responseHandler);
    }

    /**
     * @param context
     * @param url
     * @param responseHandler
     */
    public void get(final Context context, String url, final ResponseHandlerInterface responseHandler) throws JSONException, UnsupportedEncodingException {
        aSyncClient.get(context, url, responseHandler);
    }

    /**
     * @param context
     * @param url
     * @param params
     * @param responseHandler
     */
    public void get(final Context context, final String url, final RequestParams params, final ResponseHandlerInterface responseHandler) throws JSONException, UnsupportedEncodingException {
        aSyncClient.get(context, url, params, responseHandler);
    }


    /**
     * @param context
     */
    public void cancelRequest(Context context) {
        aSyncClient.cancelRequests(context, true);
        Log.i("request canceled", context.getClass().getSimpleName());
    }
}
