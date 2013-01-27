package com.android.xpua.service.rest.builder;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HttpRestRequestBuilder {

    private static final String TAG =  HttpRestRequestBuilder.class.getSimpleName();

    public static HttpRequestBase buildHttpRequestWithParamsAndAction(HttpRestVerb verb, Uri action, Bundle params) {

        HttpRequestBase request = verb.getDefaultHttpRequest();

        switch (verb.getParamStrategy()) {
            case URI:
                action = appendParamsToActionUri(action, params);
                break;
            case ENTITY:
                appendParamsToRequestEntity((HttpEntityEnclosingRequestBase) request, params);
                break;
        }

        setUriInRequest(request, action);

        return request;

    }

    public static void appendParamsToRequestEntity(HttpEntityEnclosingRequestBase request, Bundle params) {
        if (params != null) {
            try {
                request.setEntity(new UrlEncodedFormEntity(paramsToList(params)));
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "A UrlEncodedFormEntity was created with an unsupported encoding.", e);
                throw new NullPointerException();
            }
        }
    }

    public static void setUriInRequest(HttpRequestBase request, Uri action) {
        try {
            request.setURI(new URI(action.toString()));
        } catch (URISyntaxException e) {
            Log.e(TAG, "URI syntax was incorrect: " + action.toString(), e);
            throw new NullPointerException();
        }
    }

    public static Uri appendParamsToActionUri(Uri uri, Bundle params) {
        if (params == null) {
            return uri;
        }
        Uri.Builder uriBuilder = uri.buildUpon();

        // Loop through our params and append them to the Uri.
        for (BasicNameValuePair param : paramsToList(params)) {
            uriBuilder.appendQueryParameter(param.getName(), param.getValue());
        }

        return uriBuilder.build();
    }

    public static List<BasicNameValuePair> paramsToList(Bundle params) {
        ArrayList<BasicNameValuePair> formList = new ArrayList<BasicNameValuePair>(params.size());

        for (String key : params.keySet()) {
            Object value = params.get(key);

            // We can only put Strings in a form entity, so we call the toString()
            // method to enforce. We also probably don't need to check for null here
            // but we do anyway because Bundle.get() can return null.
            if (value != null) formList.add(new BasicNameValuePair(key, value.toString()));
        }

        return formList;
    }
}
