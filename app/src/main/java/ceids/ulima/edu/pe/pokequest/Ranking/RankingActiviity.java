package ceids.ulima.edu.pe.pokequest.Ranking;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ceids.ulima.edu.pe.pokequest.R;
import ceids.ulima.edu.pe.pokequest.adapter.Adapter;
import ceids.ulima.edu.pe.pokequest.beans.Usuarios;

public class RankingActiviity extends Fragment {
    ArrayList<Usuarios> usuarios=new ArrayList<>();
    ArrayList<Usuarios> usuarios2=new ArrayList<>();
    private static DatabaseReference ref;
    Adapter adapter;
    ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_ranking,container,false);
        lista=(ListView) v.findViewById(R.id.listaRanking);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref= database.getReference("usuarios");
        ref.orderByChild("puntaje").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    usuarios.add(data.getValue(Usuarios.class));
                }
                for (int j=(usuarios.size()-1);j>-1;j--){
                    usuarios2.add(usuarios.get(j));
                }
                adapter=new Adapter(usuarios2,getContext());
                lista.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("CulturaActivity", "getUser:onCancelled", databaseError.toException());
            }
        });

    }
}
