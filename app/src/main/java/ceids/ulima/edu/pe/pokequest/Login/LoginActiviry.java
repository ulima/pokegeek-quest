package ceids.ulima.edu.pe.pokequest.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import ceids.ulima.edu.pe.pokequest.BaseActivity;
import ceids.ulima.edu.pe.pokequest.Codigo.CodigoActiviry;
import ceids.ulima.edu.pe.pokequest.Menu.MenuActivity;
import ceids.ulima.edu.pe.pokequest.R;
import ceids.ulima.edu.pe.pokequest.beans.Correo;
import ceids.ulima.edu.pe.pokequest.ui.mapa.MapaActivity;

public class LoginActiviry extends BaseActivity{
    private static final String TAG = "EmailPassword";
    private static DatabaseReference ref;
    ArrayList<Correo> correo=new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]
    private CallbackManager mCallbackManager;
    boolean estado=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

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
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };

        // [END auth_state_listener]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]
    }


    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]





    private void signOut() {
        mAuth.signOut();
        //LoginManager.getInstance().logOut();
        updateUI(null);
    }



    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActiviry.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }else{
                            final String correito=mAuth.getCurrentUser().getEmail().toString();
                            ref= database.getReference("Correo");
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()){
                                        correo.add(data.getValue(Correo.class));
                                    }
                                    if(correo.size()!=0){
                                        for(int i=0;i<correo.size();i++){
                                            if(correo.get(i).getCorreo().equals(correito)){
                                                estado=false;
                                                break;
                                            }

                                        }
                                        if (estado==false){
                                            Intent mainIntent = new Intent(LoginActiviry.this,MapaActivity.class);
                                            LoginActiviry.this.startActivity(mainIntent);
                                            LoginActiviry.this.finish();
                                            mainIntent.putExtra("correo",mAuth.getCurrentUser().getEmail());
                                            mainIntent.putExtra("foto",mAuth.getCurrentUser().getPhotoUrl());
                                            LoginManager.getInstance().logOut();
                                            startActivity(mainIntent);
                                        }else{
                                            Intent mainIntent = new Intent(LoginActiviry.this,CodigoActiviry.class);
                                            LoginActiviry.this.startActivity(mainIntent);
                                            LoginActiviry.this.finish();
                                            mainIntent.putExtra("correo",mAuth.getCurrentUser().getEmail());
                                            mainIntent.putExtra("foto",mAuth.getCurrentUser().getPhotoUrl());
                                            LoginManager.getInstance().logOut();
                                            startActivity(mainIntent);
                                        }
                                    }else{
                                        Intent mainIntent = new Intent(LoginActiviry.this,CodigoActiviry.class);
                                        LoginActiviry.this.startActivity(mainIntent);
                                        LoginActiviry.this.finish();
                                        mainIntent.putExtra("correo",mAuth.getCurrentUser().getEmail());
                                        mainIntent.putExtra("foto",mAuth.getCurrentUser().getPhotoUrl());
                                        LoginManager.getInstance().logOut();
                                        startActivity(mainIntent);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("CulturaActivity", "getUser:onCancelled", databaseError.toException());
                                }
                            });

                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]
}