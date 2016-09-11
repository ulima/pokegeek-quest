package ceids.ulima.edu.pe.pokequest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ceids.ulima.edu.pe.pokequest.R;
import ceids.ulima.edu.pe.pokequest.beans.Usuarios;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by CarlosGabriel on 10/09/2016.
 */
public class Adapter extends BaseAdapter {

    private List<Usuarios> usuarios;
    private LayoutInflater mInflater;
    private Context context;

    public Adapter(List<Usuarios> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
        mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=mInflater.inflate(R.layout.ranking_item_list,parent,false);

        final TextView codigo=(TextView) view.findViewById(R.id.codigo);
        final TextView puntaje=(TextView) view.findViewById(R.id.puntuacion);
        final Usuarios usuarios=(Usuarios) getItem(position);
        codigo.setText(usuarios.getCodigo());
        puntaje.setText(String.valueOf(usuarios.getPuntaje()));
        return view;
    }

}
