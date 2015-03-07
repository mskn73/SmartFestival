package com.underphones.smartfestival;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esri.android.map.MapView;
import com.esri.core.symbol.SimpleMarkerSymbol;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private MapView mMapView;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.centerAndZoom(-411192.387, 4925816.466, 16);

        SimpleMarkerSymbol sms = new SimpleMarkerSymbol(Color.BLUE, 5, SimpleMarkerSymbol.STYLE.CROSS);

        /*SimpleRenderer simRenderer = new SimpleRenderer(sms);

        LocationListener locationListener = new MyLocationListener();
        LocationManager locationManager = null;
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        */

        SmartLocation.with(getActivity()).location()
                .start(new MyLocationListener());


        return rootView;

    }

    private class MyLocationListener implements OnLocationUpdatedListener {

        @Override
        public void onLocationUpdated(Location location) {
            mMapView.centerAndZoom(location.getLatitude(), location.getLongitude(), 16);
        }
    }
}
