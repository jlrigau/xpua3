package com.android.xpua.service.rest;


import android.os.Bundle;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class StringRestService extends RestService {

    private static final String TAG = StringRestService.class.getSimpleName();

    @Override
    protected Bundle buildResultData(HttpEntity responseEntity, Bundle extras) {
        Bundle resultData = null;
        try {
            resultData = new Bundle();
            resultData.putString(REST_RESULT, EntityUtils.toString(responseEntity));
        } catch (IOException e) {
            Log.e(TAG, "Couldn't convert to string", e);
        }
        return resultData;
    }
}
