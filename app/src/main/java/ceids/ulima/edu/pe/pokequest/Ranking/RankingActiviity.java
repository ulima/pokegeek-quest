package ceids.ulima.edu.pe.pokequest.Ranking;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ceids.ulima.edu.pe.pokequest.R;

public class RankingActiviity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_ranking,container,false);
        return v;
    }
}
