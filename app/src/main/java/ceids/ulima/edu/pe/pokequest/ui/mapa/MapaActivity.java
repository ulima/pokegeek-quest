package ceids.ulima.edu.pe.pokequest.ui.mapa;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.util.ArrayList;
import java.util.List;

import ceids.ulima.edu.pe.pokequest.Login.LoginActiviry;
import ceids.ulima.edu.pe.pokequest.R;
import ceids.ulima.edu.pe.pokequest.beans.Pokeparada;
import ceids.ulima.edu.pe.pokequest.ui.reto.RetoActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MapaActivity extends Fragment implements OnMapReadyCallback {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private GoogleMap mMap;

    private DatabaseReference mDb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_mapa,container,false);
        setupViews();
        return v;
    }


    private void setupViews() {

        setupMapFragment();


    }

    private void setupMapFragment() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, mapFragment);
        ft.commit();

        mapFragment.getMapAsync(this);
    }

    private void setupData() {
        mDb = FirebaseDatabase.getInstance().getReference();
        cargarPokeparadas();

    }

    private void cargarPokeparadas(){
        mDb.child("pokeparada").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //List<Pokeparada> pokeparadas = new ArrayList<>();
                for (DataSnapshot pp : dataSnapshot.getChildren()){
                    Pokeparada pokeparada = pp.getValue(Pokeparada.class);

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(
                                    pokeparada.getLatitud() / 1000000.0,
                                    pokeparada.getLongitud() / 1000000.0))
                            .title(pokeparada.getNombre()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng uLima = new LatLng(-12.085357, -76.971495);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uLima, 20));

        setupData();
    }

}