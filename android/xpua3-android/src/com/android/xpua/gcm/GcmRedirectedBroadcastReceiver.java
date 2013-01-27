package com.android.xpua.gcm;

import android.content.Context;
import com.google.android.gcm.GCMBroadcastReceiver;


public class GcmRedirectedBroadcastReceiver extends GCMBroadcastReceiver {

    /**
     * Gets the class name of the intent service that will handle GCM messages.
     *
     * Used to override class name, so that GcmIntentService can live in a
     * subpackage.
     */
    @Override
    protected String getGCMIntentServiceClassName(Context context) {
        return GcmIntentService.class.getCanonicalName();
    }
}
