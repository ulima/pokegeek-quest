package ceids.ulima.edu.pe.pokequest.ui.reto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ceids.ulima.edu.pe.pokequest.Codigo.CodigoActiviry;
import ceids.ulima.edu.pe.pokequest.R;
import ceids.ulima.edu.pe.pokequest.beans.Correo;
import ceids.ulima.edu.pe.pokequest.beans.Opcion;
import ceids.ulima.edu.pe.pokequest.beans.Pokeparada;
import ceids.ulima.edu.pe.pokequest.beans.Pregunta;
import ceids.ulima.edu.pe.pokequest.ui.mapa.MapaActivity;

public class RetoActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    TextView titulo;
    TextView descripcion;
    ImageView imagen;
    Button A;
    Button B;
    Button C;
    Button D;
    private static DatabaseReference ref;
    private static DatabaseReference ref2;
    private static DatabaseReference ref3;
    ArrayList<Pokeparada> pokeparadas=new ArrayList<>();
    ArrayList<Pregunta> preguntas=new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]
    public static final String RETO_CODIGO = "RETO_CODIGO";
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
        final String codigo = getIntent().getStringExtra(RETO_CODIGO);
        A=(Button) findViewById(R.id.A);
        B=(Button) findViewById(R.id.B);
        C=(Button) findViewById(R.id.C);
        D=(Button) findViewById(R.id.D);
        ref= database.getReference("pokeparada");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    pokeparadas.add(data.getValue(Pokeparada.class));
                }
                    final int qr=Integer.parseInt(codigo);
                    for(int i=0;i<pokeparadas.size();i++){
                        if(pokeparadas.get(i).getPregunta()==qr){
                            ref2= database.getReference("preguntas");
                            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()){
                                        preguntas.add(data.getValue(Pregunta.class));
                                    }
                                    for (int j=0;j<preguntas.size();j++){
                                        if(preguntas.get(j).getKey()==qr){
                                            A.setText(preguntas.get(j).getOpcion().getUno());
                                            B.setText(preguntas.get(j).getOpcion().getUno());
                                            C.setText(preguntas.get(j).getOpcion().getUno());
                                            D.setText(preguntas.get(j).getOpcion().getUno());
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("CulturaActivity", "getUser:onCancelled", databaseError.toException());
                                }
                            });
                        }
                    }

                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("CulturaActivity", "getUser:onCancelled", databaseError.toException());
            }
        });

    }
}
