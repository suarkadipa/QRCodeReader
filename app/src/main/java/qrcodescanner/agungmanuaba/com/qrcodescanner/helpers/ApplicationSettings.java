package qrcodescanner.agungmanuaba.com.qrcodescanner.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ari_S on 7/27/2015.
 */
public class ApplicationSettings {
    private static String _serviceUrl = Constants.SERVICE_URL;

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(Metadata.PreferenceName,
                Context.MODE_PRIVATE);
    }

    public static String getServiceUrl(Context context) {
        return getPrefs(context).getString(Metadata.serviceURL, _serviceUrl);
    }

    public static void setServiceUrl(Context context, String value) {
        getPrefs(context).edit().putString(Metadata.serviceURL, value).commit();
    }

    public static class Metadata {
        public static final String PreferenceName = "sharedPreferences";

        public static final String serviceURL = "serviceURL";
    }
}
