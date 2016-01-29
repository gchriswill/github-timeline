package me.gchriswill.githubtimeline;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gchriswill on 1/27/16.
 */
public class HttpRequest {

    static String response = null;

    public static final String NETWORK_MESSAGE = "check internet connection!";

    public static boolean hasConnection(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();

    }

    public String makeServiceCall(String stringUrl) {

        URL url;
        InputStream in;
        HttpURLConnection httpClient;

        try {

            url = new URL(stringUrl);
            httpClient = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream( httpClient.getInputStream() );
            response = IOUtils.toString(in, "UTF-8");

        } catch ( IOException e) {

            e.printStackTrace();

        }

        return response;

    }

}
