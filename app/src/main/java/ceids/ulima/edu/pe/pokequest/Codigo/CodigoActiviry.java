package ceids.ulima.edu.pe.pokequest.Codigo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ceids.ulima.edu.pe.pokequest.FirebaseHelper.FirebaseHelper;
import ceids.ulima.edu.pe.pokequest.Login.LoginActiviry;
import ceids.ulima.edu.pe.pokequest.R;
import ceids.ulima.edu.pe.pokequest.beans.Codigo;
import ceids.ulima.edu.pe.pokequest.beans.Correo;
import ceids.ulima.edu.pe.pokequest.ui.mapa.MapaActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CodigoActiviry extends AppCompatActivity {
    TextView codigito;
    Button boton;
    DatabaseReference db;
    FirebaseHelper helper;
    private static DatabaseReference ref;
    ArrayList<Codigo> codigos=new ArrayList<>();
    boolean estado=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);

        codigito=(TextView) findViewById(R.id.eteSugerencias);
        boton=(Button) findViewById(R.id.btnSugerencia);

        db= FirebaseDatabase.getInstance().getReference();

        helper=new FirebaseHelper(db);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton.setEnabled(false);
                final String correito=getIntent().getStringExtra("correo").toString();
                final Correo correo=new Correo(correito);
                final String codigoinput=codigito.getText().toString();
                final Codigo codigo=new Codigo(codigoinput+correito);
                if(codigoinput.length()==8 && codigoinput!=null){
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    ref= database.getReference("Codigo");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()){
                                codigos.add(data.getValue(Codigo.class));
                            }
                            if(codigos.size()!=0){
                                for(int i=0;i<codigos.size();i++){
                                    if(codigos.get(i).getCodigo().substring(0,8).equals(codigoinput)){
                                        estado=false;
                                        break;
                                    }else{
                                        estado=true;
                                    }

                                }
                                if (estado==true){
                                    if(helper.saveCodigo(codigo)){
                                        codigito.setText("");
                                        helper.saveCorreo(correo);
                                        Intent mainIntent = new Intent(CodigoActiviry.this,MapaActivity.class);
                                        CodigoActiviry.this.startActivity(mainIntent);
                                        CodigoActiviry.this.finish();
                                        mainIntent.putExtra("correo",correito);
                                        startActivity(mainIntent);
                                    }
                                }else{
                                    codigito.setText("");
                                    Toast.makeText(CodigoActiviry.this,"El codigo ya existe",Toast.LENGTH_SHORT).show();
                                    boton.setEnabled(true);
                                }
                            }else{
                                if (estado==true){
                                    if(helper.saveCodigo(codigo)){
                                        codigito.setText("");
                                        helper.saveCorreo(correo);
                                        Intent mainIntent = new Intent(CodigoActiviry.this,MapaActivity.class);
                                        CodigoActiviry.this.startActivity(mainIntent);
                                        CodigoActiviry.this.finish();
                                        mainIntent.putExtra("correo",correito);
                                        startActivity(mainIntent);
                                    }
                                }else{
                                    codigito.setText("");
                                    Toast.makeText(CodigoActiviry.this,"El codigo ya existe",Toast.LENGTH_SHORT).show();
                                    boton.setEnabled(true);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("CulturaActivity", "getUser:onCancelled", databaseError.toException());
                        }
                    });

                }else{
                    codigito.setText("");
                    Toast.makeText(CodigoActiviry.this,"El codigo debe ser de 8 digitos",Toast.LENGTH_SHORT).show();
                    boton.setEnabled(true);

                }
            }
        });
    }

    public void onBackPressed() {
        new SweetAlertDialog(CodigoActiviry.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Quiere Salir")
                .setContentText("Usted saldra de su cuenta")
                .setCancelText("No")
                .setConfirmText("Si")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent mainIntent = new Intent(CodigoActiviry.this,LoginActiviry.class);
                        CodigoActiviry.this.startActivity(mainIntent);
                        CodigoActiviry.this.finish();
                    }
                })
                .show();
    }


}
