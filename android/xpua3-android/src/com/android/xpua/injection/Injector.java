package com.android.xpua.injection;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.android.xpua.injection.annotation.FragmentById;
import com.android.xpua.injection.annotation.ViewById;

import java.lang.reflect.Field;

public class Injector {

    private Injector(){

    }

    private static final String TAG = Injector.class.getSimpleName();

    public static void injectViews(FragmentActivity target) {
        for (Field f : target.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                injectFragment(target, f);
                injectView(target, f);
            }catch(IllegalArgumentException e){
                Log.w(TAG, e);
            }catch(IllegalAccessException e){
                Log.w(TAG, e);
            }
        }
    }

    private static void injectFragment(FragmentActivity target, Field f) throws IllegalAccessException {
        FragmentById fragmentByIdAnnotation = f.getAnnotation(FragmentById.class);
        if (fragmentByIdAnnotation != null) {
            f.set(target, target.getSupportFragmentManager().findFragmentById(fragmentByIdAnnotation.value()));
        }
    }

    public static void injectViews(Activity target) {
        for (Field f : target.getClass().getDeclaredFields()) {
            try {
                injectView(target, f);
            }catch(IllegalArgumentException e){
                Log.w(TAG, e);
            }catch(IllegalAccessException e){
                Log.w(TAG, e);
            }
        }
    }

    private static void injectView(Activity target, Field f) throws IllegalAccessException {
        ViewById viewByIdAnnotation = f.getAnnotation(ViewById.class);
        if (viewByIdAnnotation != null) {
            f.set(target, target.findViewById(viewByIdAnnotation.value()));
        }
    }


}
