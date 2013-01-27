package com.android.xpua.service.rest;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;

import java.io.IOException;

public class JsonRestService extends RestService {

    public static final String EXTRA_JSON_CLASS = "com.android.xpua.EXTRA_JSON_CLASS";

    private static final String TAG = JsonRestService.class.getSimpleName();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final JsonFactory factory = new JsonFactory();

    @Override
    protected Bundle buildResultData(HttpEntity responseEntity, Bundle extras) {
        Parcelable jsonClass = extras.getParcelable(EXTRA_JSON_CLASS);
        Bundle result = new Bundle();
        if(jsonClass == null){
            return result;
        }
        try {
            JsonParser parser = factory.createParser(responseEntity.getContent());
            result.putParcelable(REST_RESULT, objectMapper.readValue(parser, jsonClass.getClass()));
        } catch (IOException e) {
            Log.e(TAG, "Couldn't convert to string", e);
        }
        return result;
    }
}
