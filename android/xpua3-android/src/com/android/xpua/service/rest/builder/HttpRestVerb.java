package com.android.xpua.service.rest.builder;

import android.net.Uri;
import android.os.Bundle;
import org.apache.http.client.methods.*;

import static com.android.xpua.service.rest.builder.HttpRestRequestBuilder.buildHttpRequestWithParamsAndAction;

public enum HttpRestVerb {

    GET {
        @Override
        public ParamStrategy getParamStrategy() {
            return ParamStrategy.URI;
        }

        @Override
        public HttpRequestBase getDefaultHttpRequest() {
            return new HttpGet();
        }

    }, PUT {
        @Override
        public HttpRequestBase getDefaultHttpRequest() {
            return new HttpPut();
        }

        @Override
        public ParamStrategy getParamStrategy() {
            return ParamStrategy.ENTITY;
        }

    }, POST {
        @Override
        public HttpRequestBase getDefaultHttpRequest() {
            return new HttpPost();
        }

        @Override
        public ParamStrategy getParamStrategy() {
            return ParamStrategy.ENTITY;
        }

    }, DELETE {
        @Override
        public HttpRequestBase getDefaultHttpRequest() {
            return new HttpDelete();
        }

        @Override
        public ParamStrategy getParamStrategy() {
            return ParamStrategy.URI;
        }
    };

    public enum ParamStrategy {
        URI, ENTITY;
    }

    public abstract ParamStrategy getParamStrategy();

    public abstract HttpRequestBase getDefaultHttpRequest();

    public HttpRequestBase getHttpRequest(Uri action, Bundle params) {
        return buildHttpRequestWithParamsAndAction(this, action, params);
    }

}