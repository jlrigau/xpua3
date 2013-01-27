package com.android.xpua.gcm;

import android.content.Context;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import static com.android.xpua.common.Constants.SERVER_ROOT_URL;


/**
 * Helper class used to communicate with the demo server.
 */
public final class ServerUtilities {

    private static final int MAX_ATTEMPTS = 2;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    private static final String TAG = ServerUtilities.class.getSimpleName();

    private static final String REGISTRATION_URL = SERVER_ROOT_URL + "/registration";

    /**
     * Register this account/device pair within the server.
     *
     * @return whether the registration succeeded or not.
     */
    static boolean register(final Context context, final String registrationId) {
        Log.i(TAG, "registering device (registrationId = " + registrationId + ")");

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register it in the
        // demo server. As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                doHttpPut(REGISTRATION_URL, buildRegistationIdRequest(registrationId));
                GCMRegistrar.setRegisteredOnServer(context, true);
                return true;
            } catch (Exception e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i, e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return false;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        return false;
    }

    private static JSONObject buildRegistationIdRequest(String registrationId) throws JSONException {
        JSONObject regObject = new JSONObject();
        regObject.put("registrationId", registrationId);
        return regObject;
    }

    /**
     * Unregister this account/device pair within the server.
     */
    static void unregister(final Context context, final String registrationId) {
        Log.i(TAG, "unregistering device (registrationId = " + registrationId + ")");
        try {
            doHttpDelete(REGISTRATION_URL + "/" + registrationId);
            GCMRegistrar.setRegisteredOnServer(context, false);
        } catch (IOException e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            Log.i(TAG, "Fail to unregister from server");
        }
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param json     object to send.
     * @throws java.io.IOException propagated from PUT.
     */
    private static HttpResponse doHttpPut(String endpoint, JSONObject json)
            throws IOException {
        HttpPut putRequest = new HttpPut(endpoint);
        putRequest.setEntity(new StringEntity(json.toString()));
        putRequest.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        return executeRequest(putRequest);
    }


    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @throws java.io.IOException propagated from DELETE.
     */
    private static HttpResponse doHttpDelete(String endpoint)
            throws IOException {
        return executeRequest(new HttpDelete(endpoint));
    }

    private static HttpResponse executeRequest(HttpRequestBase request) throws IOException {
        return new DefaultHttpClient().execute(request);
    }
}
