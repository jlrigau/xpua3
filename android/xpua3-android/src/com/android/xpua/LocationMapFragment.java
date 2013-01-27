package com.android.xpua;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom;

public class LocationMapFragment extends SupportMapFragment {

    private static final String TAG = LocationMapFragment.class.getSimpleName();

    private Address defaultLocation;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        defaultLocation = searchLocation("Paris");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstance) {
        View layout = super.onCreateView(inflater, view, savedInstance);

        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        frameLayout.requestTransparentRegion(layout);
        ((ViewGroup) layout).addView(frameLayout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        LatLng defaultLocationPosition = new LatLng(defaultLocation.getLatitude(), defaultLocation.getLongitude());
        CameraUpdate update = newLatLngZoom(defaultLocationPosition, 15);
        GoogleMap googleMap = getMap();
        googleMap.moveCamera(update);
        googleMap.addMarker(new MarkerOptions()
                .position(defaultLocationPosition)
                .title("Paris")
                .snippet("Population: Beaucoup"));
    }

    public void updateLocationOnMap(String newLocationWished) {
        Address newLocation = searchLocation(newLocationWished);
        if (newLocation == null) {
            Toast.makeText(this.getActivity(), "Location not found", Toast.LENGTH_LONG).show();
            return;
        }
        CameraUpdate update = newLatLngZoom(new LatLng(newLocation.getLatitude(), newLocation.getLongitude()), 15);
        getMap().moveCamera(update);

    }

    private Address searchLocation(String newLocationWished) {
        List<Address> addresses;
        try {
            addresses = new Geocoder(this.getActivity()).getFromLocationName(newLocationWished, 1);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return defaultLocation;
        }
        return addresses.isEmpty() ? null : addresses.get(0);
    }

}
