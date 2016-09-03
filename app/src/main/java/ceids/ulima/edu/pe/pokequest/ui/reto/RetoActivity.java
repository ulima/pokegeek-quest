package ceids.ulima.edu.pe.pokequest.ui.reto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ceids.ulima.edu.pe.pokequest.R;

public class RetoActivity extends AppCompatActivity {

    public static final String RETO_CODIGO = "RETO_CODIGO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto);

        String codigo = getIntent().getStringExtra(RETO_CODIGO);
        ((TextView)findViewById(R.id.tviPokemonCode)).setText(codigo);
    }
}
