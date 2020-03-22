package com.luniva.lunivacaredemo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luniva.lunivacaredemo.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements MapEventsReceiver {
    private MapView map;
    private RoadManager manager;
    ArrayList<GeoPoint> wayspoint=new ArrayList<GeoPoint>();;
    Polyline roadOverlay;
    MapEventsOverlay mapEventsOverlay;
    MyLocationNewOverlay myLocationNewOverlay;
    private LocationManager locationManager;
    Context ctx;
    private ProgressDialog progressDialog;

    GeoPoint geoPoint;
    Marker userChoice;
    Marker nodeMarker;
    Location locationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);


        loadMap();
    }
    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        if(roadOverlay!=null){
            map.getOverlays().remove(roadOverlay);
            map.getOverlayManager().remove(roadOverlay);
            map.invalidate();
        }
        if(userChoice!=null){
            userChoice.remove(map);

        }

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        userChoice.setPosition(p);
        userChoice.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlayManager().add(userChoice);
        Toast.makeText(getApplicationContext(),"Getting direction",Toast.LENGTH_LONG);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e( "run: ","hello" );
                if(geoPoint!=null) {
                    Marker startmarker = new Marker(map);
                    startmarker.setPosition(geoPoint);
                    startmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);


                    startmarker.setIcon(getResources().getDrawable(R.drawable.ic_location_on_black_24dp));
                    startmarker.setTitle("You are Here");
                    startmarker.showInfoWindow();
                    map.getOverlays().add(startmarker);

                }

                loadDirection(geoPoint,p);
                progressDialog.dismiss();
            }

        },2000);
        Log.e("Helper: ","you are here" );
        map.invalidate();
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
    }

    class loadRoadWays extends AsyncTask<GeoPoint, Void, Boolean> {
        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(GeoPoint... geoPoints) {
            ArrayList<GeoPoint> wayspoint = new ArrayList<>();
            wayspoint.add(geoPoints[0]);
            wayspoint.add(geoPoints[1]);
            Road road = manager.getRoad(wayspoint);
//            if(road.mStatus==Road.STATUS_TECHNICAL_ISSUE) {
////                finish();
////                startActivity(getIntent());
//
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(map.getContext(), "Technical issue when getting the route", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//
//            }

//            if(road.mStatus==Road.STATUS_OK) {
            if (roadOverlay != null) {
                map.getOverlayManager().remove(roadOverlay);
            }
            roadOverlay = RoadManager.buildRoadOverlay(road);
            roadOverlay.setColor(Color.RED);
            roadOverlay.setWidth(15);
            map.getOverlays().add(roadOverlay);
            Drawable nodeIcon = getResources().getDrawable(R.drawable.ic_location_on_black_24dp);
            for (int i = 0; i < road.mNodes.size(); i++) {
                RoadNode node = road.mNodes.get(i);



                nodeMarker.setPosition(node.mLocation);
                if(nodeMarker!=null){
                    map.getOverlays().remove(nodeMarker);

                    Log.e( "doInBackground: ","called" );
                }
                nodeMarker.setIcon(nodeIcon);
                nodeMarker.setTitle("Step " + i);
                map.getOverlays().add(nodeMarker);
                nodeMarker.setSnippet(node.mInstructions);
                nodeMarker.setSubDescription(Road.getLengthDurationText(getApplicationContext(), node.mLength, node.mDuration));

//                Drawable icon = getResources().getDrawable(R.drawable.ic_continue);
//                nodeMarker.setImage(icon);
            }

//            }

            return map.getOverlayManager().add(roadOverlay);

        }


        @Override
        protected void onPostExecute(Boolean r) {
            map.getOverlayManager().add(roadOverlay);
            map.invalidate();
            super.onPostExecute(r);
        }
    }
    private void loadMap(){
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        myLocationNewOverlay=new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),map);
        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.enableFollowLocation();
        userChoice=new Marker(map);
        nodeMarker = new Marker(map);
//        locationModel=new Location();
        myLocationNewOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {

                geoPoint = new GeoPoint(myLocationNewOverlay.getMyLocation().getLatitude(),myLocationNewOverlay.getMyLocation().getLongitude());
                Log.e( "run: ", String.valueOf(myLocationNewOverlay.getMyLocation().getLatitude()));
                Log.e( "run: ", String.valueOf(myLocationNewOverlay.getMyLocation().getLongitude()));


            }
        });


        GeoPoint mapview = new GeoPoint(27.70731,85.32256);

        IMapController mapController = map.getController();
        mapController.setZoom(19D);
        mapController.setCenter(mapview);

        mapEventsOverlay=new MapEventsOverlay(this);
        map.getOverlays().add(mapEventsOverlay);
        map.getOverlays().add(myLocationNewOverlay);




    }



    private void mapMarker(GeoPoint startPoint,GeoPoint endPoint){



//        GeoPoint endpoint=new GeoPoint(27.7169,85.3202);
        Marker endtmarker=new Marker(map);

        endtmarker.setPosition(endPoint);
        endtmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        endtmarker.setIcon(getResources().getDrawable(R.drawable.ic_location_on_black_24dp));
        map.getOverlays().add(endtmarker);
        loadDirection(startPoint,endPoint);

// marker when user click map
        mapEventsOverlay=new MapEventsOverlay(this);
    }



    private void loadDirection(GeoPoint startPoint,GeoPoint endPoint){
        manager=new OSRMRoadManager(getApplicationContext());
        wayspoint.add(startPoint);
        wayspoint.add(endPoint);
        map.invalidate();
        new loadRoadWays().execute(startPoint,endPoint);
//        Log.e("loadDirection: ", String.valueOf(locationModel.getLat()+"+"+locationModel.getLon()));

    }

   }
