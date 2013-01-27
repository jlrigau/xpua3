package com.android.xpua.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.android.xpua.R;
import com.google.android.gcm.GCMRegistrar;

import static com.android.xpua.common.Constants.SENDER_ID;
import static com.android.xpua.common.Constants.SERVER_ROOT_URL;

public class GcmRegistrationService extends IntentService {

    private static final String TAG = GcmRegistrationService.class.getSimpleName();

    public GcmRegistrationService() {
        super(GcmRegistrationService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        checkGcmInformationsValidity();

        final String registrationId = GCMRegistrar.getRegistrationId(this);
        if (registrationId.isEmpty()) {
            registerDeviceOnGcm();
        } else {
            // Device is already registered on GCM, check server.
            registerDeviceOnServer(registrationId);
        }
    }

    private void registerDeviceOnGcm() {
        GCMRegistrar.register(this, SENDER_ID);
    }

    private void registerDeviceOnServer(String registrationId) {
        if (GCMRegistrar.isRegisteredOnServer(this)) {
            // Skips registration.
            Log.i(TAG, "Already regestered on server for gcm");
        } else {
            if (!ServerUtilities.register(this, registrationId)) {
                // At this point all attempts to register with the app server failed, so we need to unregister the device
                // from GCM - the app will try to register again when it is restarted. Note that GCM will send an
                // unregistered callback upon completion, but GcmIntentService.onUnregistered() will ignore it.
                GCMRegistrar.unregister(this);
            }
        }
    }

    private void checkGcmInformationsValidity() {
        checkNotNull(SERVER_ROOT_URL, "SERVER_URL");
        checkNotNull(SENDER_ID, "SENDER_ID");

        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);
    }


    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    getString(R.string.error_config, name));
        }
    }
}
