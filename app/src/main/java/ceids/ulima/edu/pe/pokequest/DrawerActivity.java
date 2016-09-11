package ceids.ulima.edu.pe.pokequest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import ceids.ulima.edu.pe.pokequest.Login.LoginActiviry;
import ceids.ulima.edu.pe.pokequest.Ranking.RankingActiviity;
import ceids.ulima.edu.pe.pokequest.ui.mapa.MapaActivity;
import ceids.ulima.edu.pe.pokequest.ui.reto.RetoActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Log.i("correoA",getIntent().getStringExtra("correo"));


        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MapaActivity mapaActivity = new MapaActivity();
        android.support.v4.app.FragmentTransaction fragmentPromocion = getSupportFragmentManager().beginTransaction();
        fragmentPromocion.replace(R.id.frame,mapaActivity);
        fragmentPromocion.commit();

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        View viewHeader = LayoutInflater.from(this).inflate(R.layout.header, navigationView, false);
        ((TextView) viewHeader.findViewById(R.id.email)).setText(getIntent().getStringExtra("correo"));
        Picasso.with(viewHeader.getContext()).load((Uri) getIntent().getParcelableExtra("foto")).into((CircleImageView)  viewHeader.findViewById(R.id.profile_image));

        navigationView.addHeaderView(viewHeader);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.Mapa:
                        Toast.makeText(getApplicationContext(),"Mapa", Toast.LENGTH_SHORT).show();
                        MapaActivity mapaActivity = new MapaActivity();
                        android.support.v4.app.FragmentTransaction fragmentPromocion = getSupportFragmentManager().beginTransaction();
                        fragmentPromocion.replace(R.id.frame,mapaActivity);
                        fragmentPromocion.commit();
                        toolbar.setTitle("Mapa");

                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.ranking:
                        Toast.makeText(getApplicationContext(),"Ranking", Toast.LENGTH_SHORT).show();
                        RankingActiviity rankingActiviity=new RankingActiviity();
                        android.support.v4.app.FragmentTransaction transaccionCultura = getSupportFragmentManager().beginTransaction();
                        transaccionCultura.replace(R.id.frame,rankingActiviity);
                        transaccionCultura.commit();
                        toolbar.setTitle("Ranking");

                        return true;
                    case R.id.Salir:
                        new SweetAlertDialog(DrawerActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Quiere Salir")
                                .setContentText("Usted saldra de su cuenta")
                                .setCancelText("No")
                                .setConfirmText("Si")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent mainIntent = new Intent(DrawerActivity.this,LoginActiviry.class);
                                        DrawerActivity.this.startActivity(mainIntent);
                                        DrawerActivity.this.finish();
                                    }
                                })
                                .show();
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                /*
                correo=(TextView) drawerView.findViewById(R.id.email);
                correo.setText(getIntent().getStringExtra("correo"));*/



                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(DrawerActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Quiere Salir")
                .setContentText("Usted saldra de su cuenta")
                .setCancelText("No")
                .setConfirmText("Si")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent mainIntent = new Intent(DrawerActivity.this,LoginActiviry.class);
                        DrawerActivity.this.startActivity(mainIntent);
                        DrawerActivity.this.finish();
                    }
                })
                .show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menPokemonQR:
                IntentIntegrator integrator=new IntentIntegrator(this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (!scanResult.getContents().equalsIgnoreCase(null) ) {
            Intent intent = new Intent(DrawerActivity.this, RetoActivity.class);
            intent.putExtra(RetoActivity.RETO_CODIGO, scanResult.getContents());
            startActivity(intent);
        }else{
            Log.i("MapaActivity", "Se regreso del QR Code");
        }


    }




}
