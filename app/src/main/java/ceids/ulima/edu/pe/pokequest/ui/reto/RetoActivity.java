package ceids.ulima.edu.pe.pokequest.ui.reto;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import ceids.ulima.edu.pe.pokequest.Codigo.CodigoActiviry;
import ceids.ulima.edu.pe.pokequest.Login.LoginActiviry;
import ceids.ulima.edu.pe.pokequest.R;
import ceids.ulima.edu.pe.pokequest.beans.Correo;
import ceids.ulima.edu.pe.pokequest.beans.Opcion;
import ceids.ulima.edu.pe.pokequest.beans.Pokeparada;
import ceids.ulima.edu.pe.pokequest.beans.Pregunta;
import ceids.ulima.edu.pe.pokequest.ui.mapa.MapaActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RetoActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    TextView titulo;
    TextView rDescripcion;
    ImageView imagen;
    Spinner spiOpciones;

    ArrayAdapter<Opcion> adapter;
    String codigo; // Pokeparada
    int preguntaId;
    int respuestaPregunta;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]
    public static final String RETO_CODIGO = "RETO_CODIGO";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


        setContentView(R.layout.activity_reto);
        rDescripcion = (TextView) findViewById(R.id.rDescripcion);
        spiOpciones = (Spinner) findViewById(R.id.spiOpciones);

        codigo = getIntent().getStringExtra(RETO_CODIGO);

        database.getReference("pokeparada").child(codigo)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Pokeparada pokeparada = dataSnapshot.getValue(Pokeparada.class);
                        preguntaId = pokeparada.getPregunta();
                        obtenerPregunta(preguntaId);

                        Log.i("RetoActivity", pokeparada.getLatitud() + "");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("RetoActivity", "Se cancelo la conexion");
                    }
                });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void obtenerPregunta(int pregunta) {
        database.getReference("preguntas").child(String.valueOf(pregunta))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Pregunta preg = dataSnapshot.getValue(Pregunta.class);
                        respuestaPregunta = preg.getRespuesta();
                        rDescripcion.setText(preg.getPregunta());

                        // Llenar data de preguntas
                        obtenerOpciones(dataSnapshot.getRef());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("RetoActivity", "Se cancelo obtener pregunta");
                    }
                });
    }

    private void obtenerOpciones(DatabaseReference ref) {
        ref.child("opciones").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Opcion> opciones = new ArrayList<>();
                Iterator<DataSnapshot> iterator  = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot ds = iterator.next();
                    Opcion opcion = new Opcion();
                    opcion.setCodigo(ds.getKey());
                    opcion.setTexto(ds.getValue().toString());
                    opciones.add(opcion);
                }
                adapter = new ArrayAdapter<Opcion>(
                        RetoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, opciones);
                spiOpciones.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("RetoActivity", "Se cancelo obtener opciones");
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Reto Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void onLanzarClick(View view) {
        // Comprobar respuesta
        comprobarRespuesta((Opcion)spiOpciones.getItemAtPosition(
                spiOpciones.getSelectedItemPosition()));
    }

    private void comprobarRespuesta(Opcion opcion) {
        if (opcion.getCodigo().equals(String.valueOf(respuestaPregunta))){
            // Respuesta correcta - Subir puntaje
            marcarRespuestaCorrecta(mAuth.getCurrentUser().getUid());


            new SweetAlertDialog(RetoActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Respuesta correcta")
                    .setContentText("Ha ganado 10 puntos")
                    .setConfirmText("Continuar")
                    .showCancelButton(false)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    })
                    .show();
        }else{
            new SweetAlertDialog(RetoActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Respuesta incorrecta")
                    .setContentText("Desea volver a intentarlo?")
                    .setCancelText("No")
                    .setConfirmText("Si")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    private void marcarRespuestaCorrecta(final String uid) {
        guardarNuevoPuntaje(uid);

    }



    private void guardarNuevoPuntaje(final String uid) {
        database.getReference("usuarios").child(uid).child("puntaje")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int puntaje = Integer.parseInt(dataSnapshot.getValue().toString());

                HashMap<String, Object> map = new HashMap<>();
                map.put("puntaje", puntaje + 10);
                //map.put("pokeparadasRealizadas", pokeparadas.concat())
                database.getReference("usuarios").child(uid).updateChildren(map);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
