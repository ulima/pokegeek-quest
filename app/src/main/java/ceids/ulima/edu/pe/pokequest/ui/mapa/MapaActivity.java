package ceids.ulima.edu.pe.pokequest.ui.mapa;

import android.content.Intent;
import android.hardware.Camera;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ceids.ulima.edu.pe.pokequest.Login.LoginActiviry;
import ceids.ulima.edu.pe.pokequest.R;
import ceids.ulima.edu.pe.pokequest.ui.reto.RetoActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private GoogleMap mMap;




//    private Button butScanQR;
//    private TextView eteTextoQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        setupViews();

//        butScanQR = (Button) findViewById(R.id.butScanQR);
//        eteTextoQR = (TextView) findViewById(R.id.eteTextoQR);

//        butScanQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new IntentIntegrator(MapaActivity.this).initiateScan();
//            }
//        });
    }

    private void setupViews() {
        setupActionBar();

        setupMapFragment();

        setupNavigationDrawer();

    }

    private void setupActionBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_ham);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupMapFragment() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, mapFragment);
        ft.commit();

        mapFragment.getMapAsync(this);
    }

    private void setupNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);

        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()){
                    case R.id.menSalir:
                        finish();
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menPokemonQR:
                IntentIntegrator integrator=new IntentIntegrator(MapaActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();;

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng uLima = new LatLng(-12.085357, -76.971495);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uLima, 20));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            Intent intent = new Intent(this, RetoActivity.class);
            Toast.makeText(this, "Scanned: " + scanResult.getContents(), Toast.LENGTH_LONG).show();
            intent.putExtra(RetoActivity.RETO_CODIGO, scanResult.getContents());
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(MapaActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Quiere Salir")
                .setContentText("Usted saldra de su cuenta")
                .setCancelText("No")
                .setConfirmText("Si")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent mainIntent = new Intent(MapaActivity.this,LoginActiviry.class);
                        MapaActivity.this.startActivity(mainIntent);
                        MapaActivity.this.finish();
                    }
                })
                .show();
    }

}
