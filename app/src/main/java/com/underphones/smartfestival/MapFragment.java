package com.underphones.smartfestival;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleMarkerSymbol;

import io.nlopez.smartlocation.OnLocationUpdatedListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private MapView mMapView;
    private float latitude;
    private float longitude;

    private MapFragment(Bundle b) {


    }

    public MapFragment(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.map);

        //mMapView.centerAndZoom();
        //mMapView.setExtent(-411192.387, 4925816.466, 16);

        SimpleMarkerSymbol sms = new SimpleMarkerSymbol(Color.BLUE, 5, SimpleMarkerSymbol.STYLE.CROSS);
        GraphicsLayer graphicsLayer = new GraphicsLayer();
        // create a point marker symbol (red, size 10, of type circle)
        SimpleMarkerSymbol simpleMarker = new SimpleMarkerSymbol(Color.RED, 20, SimpleMarkerSymbol.STYLE.CIRCLE);

// create a point at x=-302557, y=7570663 (for a map using meters as units; this depends         // on the spatial reference)
        Point pointGeometry = new Point(latitude, longitude);

// create a graphic with the geometry and marker symbol
        Graphic pointGraphic = new Graphic(pointGeometry, simpleMarker);
// add the graphic to the graphics layer
        graphicsLayer.addGraphic(pointGraphic);
        mMapView.addLayer(graphicsLayer);
        /*SimpleRenderer simRenderer = new SimpleRenderer(sms);

        LocationListener locationListener = new MyLocationListener();
        LocationManager locationManager = null;
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        */

        /*SmartLocation.with(getActivity()).location()
                .start(new MyLocationListener());
*/

        return rootView;

    }

    private class MyLocationListener implements OnLocationUpdatedListener {

        @Override
        public void onLocationUpdated(Location location) {
            Log.i("Localizacion", "Localizacion" + String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude()));
            mMapView.centerAndZoom(location.getLatitude(), location.getLongitude(), 25);
        }
    }
}
