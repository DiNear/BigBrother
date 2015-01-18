package bigbrother.bigbrotherapp;

import android.content.SharedPreferences;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;


public class MapActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {

    private static int DEFAULT_FREQUENCY = 5;

    private GoogleApiClient client;
    private GoogleMap map;
    private LocationRequest lr;

    private Pinger pinger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Create a GoogleApiClient instance
        client = new GoogleApiClient.Builder(this)
               .addApi(LocationServices.API)
               .addConnectionCallbacks(this)
               .addOnConnectionFailedListener(this)
               .build();
        client.connect();

        SharedPreferences prefs = getSharedPreferences("saved", MODE_PRIVATE);
        int frequency = prefs.getInt("frequency", DEFAULT_FREQUENCY);
        pinger = Pinger.getInstance();

        lr = new LocationRequest();
        lr.setInterval(frequency * 1000);
        lr.setFastestInterval(1000);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        this.map = map;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.
        Location userLoc = LocationServices.FusedLocationApi.getLastLocation(client);
        LatLng ll_loc;

        if(userLoc != null) {
            ll_loc = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll_loc, 12));
            map.addMarker(new MarkerOptions()
                    .title("You")
                    .snippet("You are here.")
                    .position(ll_loc));
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(client, lr, this);

    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        //
        // More about this in the next section.
        //...
    }

    @Override
    public void onLocationChanged(Location location) {
        double lng = location.getLongitude(), lat = location.getLatitude();

        pinger.setLat(lat);
        pinger.setLong(lng);
        new Relax().execute(pinger.getPing());

        LatLng new_loc = new LatLng(lat, lng);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new_loc, 13));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
