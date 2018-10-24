package com.com220.sli.simonsays;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScorePage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "#VAMOSCONTODO", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button back = (Button)findViewById(R.id.scoresClose);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String lastScoreString = sp.getString("currentScore", "0");
        String highScoreString = sp.getString("savedHighScore", "0");

        TextView currentScoreText = (TextView)findViewById(R.id.last_score);
        TextView highScoreText = (TextView)findViewById(R.id.high_score);

        currentScoreText.setText(lastScoreString);
        highScoreText.setText(highScoreString);
    }
}
