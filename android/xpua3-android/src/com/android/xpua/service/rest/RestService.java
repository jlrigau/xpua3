package com.android.xpua.service.rest;


import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import com.android.xpua.service.rest.builder.HttpRestVerb;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public abstract class RestService extends IntentService {

    private static final String TAG = RestService.class.getName();

    public static final String EXTRA_HTTP_VERB = "com.android.xpua.EXTRA_HTTP_VERB";
    public static final String EXTRA_PARAMS = "com.android.xpua.EXTRA_PARAMS";
    public static final String EXTRA_RESULT_RECEIVER = "com.android.xpua.EXTRA_RESULT_RECEIVER";
    public static final String REST_RESULT = "com.android.xpua.REST_RESULT";

    public RestService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Uri uri = intent.getData();
        Bundle extras = intent.getExtras();

        if (!isValidIntent(uri, extras)) return;

        try {
            HttpRestVerb verb = HttpRestVerb.valueOf(extras.getString(EXTRA_HTTP_VERB, HttpRestVerb.GET.name()));
            HttpRequestBase request = verb.getHttpRequest(uri, (Bundle) extras.getParcelable(EXTRA_PARAMS));
            notifyReceiver(executeRequest(request), extras);
        } catch (IOException e) {
            Log.e(TAG, "There was a problem when sending the request.", e);
            ResultReceiver receiver = extras.getParcelable(EXTRA_RESULT_RECEIVER);
            receiver.send(0, null);
        }
    }

    protected boolean isValidIntent(Uri action, Bundle extras) {
        if (extras == null || action == null || !extras.containsKey(EXTRA_RESULT_RECEIVER)) {
            // Extras contain our ResultReceiver and data is our REST action.
            // So, without these components we can't do anything useful.
            Log.e(TAG, "You did not pass extras or data with the Intent.");

            return false;
        }
        return true;
    }

    private HttpResponse executeRequest(HttpRequestBase request) throws IOException {
        Log.d(TAG, "Executing request: " + request.getMethod() + ": " + request.getURI());
        return new DefaultHttpClient().execute(request);
    }


    private void notifyReceiver(HttpResponse httpResponse, Bundle extras) {
        ResultReceiver receiver = extras.getParcelable(EXTRA_RESULT_RECEIVER);
        HttpEntity responseEntity = httpResponse.getEntity();
        StatusLine responseStatus = httpResponse.getStatusLine();
        int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;

        if (responseEntity != null) {
            receiver.send(statusCode, buildResultData(responseEntity, extras));
        } else {
            receiver.send(statusCode, new Bundle());
        }
    }

    protected abstract Bundle buildResultData(HttpEntity responseEntity, Bundle extras);
}
