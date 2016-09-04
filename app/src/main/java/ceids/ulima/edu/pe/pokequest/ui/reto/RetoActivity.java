package ceids.ulima.edu.pe.pokequest.ui.reto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ceids.ulima.edu.pe.pokequest.R;

public class RetoActivity extends AppCompatActivity {
    TextView titulo;
    TextView descripcion;
    ImageView imagen;
    Button A;
    Button B;
    Button C;
    Button D;
    public static final String RETO_CODIGO = "RETO_CODIGO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto);
        String codigo = getIntent().getStringExtra(RETO_CODIGO);
    }
}
