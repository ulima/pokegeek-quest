package ceids.ulima.edu.pe.pokequest.FirebaseHelper;

import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import ceids.ulima.edu.pe.pokequest.beans.Codigo;
import ceids.ulima.edu.pe.pokequest.beans.Correo;

/**
 * Created by CarlosGabriel on 15/07/2016.
 */
public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean saveCorreo(Correo correo){
        if(correo==null){
            saved=false;
        }else{
            try {
                db.child("Correo").push().setValue(correo);

                saved=true;
            }catch (DatabaseException e){
                e.printStackTrace();
                saved=false;

            }

        }
        return saved;
    }

    public Boolean saveCodigo(Codigo codigo, String uid){
        if(codigo==null){
            saved=false;
        }else{
            try {

                db.child("Codigo").push().setValue(codigo);

                HashMap<String, Object> mapUsuario = new HashMap<>();
                mapUsuario.put("codigo", codigo.getCodigo().split("=")[0]);
                mapUsuario.put("email", codigo.getCodigo().split("=")[1]);
                mapUsuario.put("puntaje", 0);
                mapUsuario.put("pokeparadasRealizadas", "");
                HashMap<String, Object> map = new HashMap<>();
                map.put("/usuarios/" + uid, mapUsuario);
                db.updateChildren(map);
                saved=true;
            }catch (DatabaseException e){
                e.printStackTrace();
                saved=false;

            }

        }
        return saved;
    }
}
