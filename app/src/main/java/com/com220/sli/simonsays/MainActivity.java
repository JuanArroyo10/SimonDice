package com.com220.sli.simonsays;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    Button topLeft;
    Button topRight;
    Button bottomLeft;
    Button bottomRight;
    Button start;

    TextView score;
    TextView textView;
    TextView hiScore;

    ArrayList<Integer> randomSequence = new ArrayList<Integer>();
    ArrayList<Integer> userSequence = new ArrayList<Integer>();

    StringBuilder randomSequenceString = new StringBuilder();
    StringBuilder userSequenceString = new StringBuilder();

    int numUserInputs = 0;
    int numOfItems = 4;

    Boolean gameStart = false;
    Boolean keepGoing = true;
    Boolean sound = true;

    int miniScore = 0;
    int scoreLevel = 0;
    int puzzlePiece = 0;
    int tempUS = 0;
    int tempRS = 0;

    public static void saveData(String key, String value, Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getData(String key, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "#VAMOSCONTODO", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        topLeft = (Button) findViewById(R.id.topleft_button);
        topRight = (Button) findViewById(R.id.topright_button);
        bottomRight = (Button) findViewById(R.id.bottomright_button);
        bottomLeft = (Button) findViewById(R.id.bottomleft_button);
        start = (Button) findViewById(R.id.start);

        score = (TextView) findViewById(R.id.score);
        textView = (TextView)findViewById(R.id.textView);
        hiScore = (TextView)findViewById(R.id.newHighScore);

        final Animation blink = new AlphaAnimation(1,0);
        blink.setDuration(800);
        blink.setInterpolator(new LinearInterpolator());


        final MediaPlayer soundOne = MediaPlayer.create(this, R.raw.smb_coin2);
        final MediaPlayer soundTwo = MediaPlayer.create(this, R.raw.smb_fireball);
        final MediaPlayer soundThree = MediaPlayer.create(this, R.raw.smb_stomp);
        final MediaPlayer soundFour = MediaPlayer.create(this, R.raw.smb_kick);

        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                randomSequence.clear();
                randomSequenceString.setLength(0);

                userSequence.clear();
                userSequenceString.setLength(0);

                start.setText("REINICIAR");
                keepGoing = true;

                long runtimeSequence = (numOfItems * 1000) + 500;
                new CountDownTimer(runtimeSequence, 1000)
                {
                    public void onTick(long mill)
                    {
                        Random rdm = new Random();
                        int randomNumber = rdm.nextInt(4);
                        String aS = randomNumber + "";
                        randomSequenceString.append(aS);
                        randomSequence.add(randomNumber);

                        if(randomNumber == 0)
                        {
                            topLeft.startAnimation(blink);
                            soundOne.start();
                        }
                        else if(randomNumber == 1)
                        {
                            topRight.startAnimation(blink);
                            soundTwo.start();
                        }
                        else if(randomNumber == 2)
                        {
                            bottomLeft.startAnimation(blink);
                            soundThree.start();
                        }
                        else if(randomNumber == 3)
                        {
                            bottomRight.startAnimation(blink);
                            soundFour.start();
                        }
                        else
                        {
                            start.setText("ERROR: " + randomNumber);
                        }
                    }
                    @Override
                    public void onFinish()
                    {

                        gameStart = true;
                    }
                }.start();
            }
        });
        topLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                topLeft.startAnimation(blink);  //ANIMATES THE BUTTON WHEN PRESSED
                topRight.clearAnimation();
                bottomLeft.clearAnimation();
                bottomRight.clearAnimation();
                int buttonNumber = 0;
                try
                {
                    soundOne.start();
                    compareSequences(buttonNumber);
                }
                catch(IndexOutOfBoundsException e)
                {
                    start.setText("topLeft Error");
                }
            }
        });
        topRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                topRight.startAnimation(blink);//ANIMATES THE BUTTON WHEN PRESSED
                topLeft.clearAnimation();
                bottomLeft.clearAnimation();
                bottomRight.clearAnimation();
                int buttonNumber = 1;
                try {
                    soundTwo.start();
                    compareSequences(buttonNumber);
                }
                catch(IndexOutOfBoundsException e)
                {
                    start.setText("topRight Error");
                }
            }
        });
        bottomLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomLeft.startAnimation(blink);//ANIMATES THE BUTTON WHEN PRESSED
                topLeft.clearAnimation();
                topRight.clearAnimation();
                bottomRight.clearAnimation();
                int buttonNumber = 2;
                try {
                    soundThree.start();
                    compareSequences(buttonNumber);
                }
                catch(IndexOutOfBoundsException e)
                {
                    start.setText("bottomLeft Error");
                }
            }
        });
        bottomRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomRight.startAnimation(blink);//ANIMATES THE BUTTON WHEN PRESSED
                topLeft.clearAnimation();
                topRight.clearAnimation();
                bottomLeft.clearAnimation();
                int buttonNumber = 3;
                try
                {
                    soundFour.start();
                    compareSequences(buttonNumber);
                }
                catch(IndexOutOfBoundsException e)
                {
                    start.setText("bottomRight Error");
                }
            }
        });
        Button back = (Button)findViewById(R.id.mainClose);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    public void compareSequences(int buttonNumber)
    {
        final MediaPlayer soundFive = MediaPlayer.create(this, R.raw.smb_powerup);
        final MediaPlayer soundSix = MediaPlayer.create(this, R.raw.smb_mariodie);
        if(gameStart == true)
        {
            userSequence.add(buttonNumber);
            if(randomSequence.get(numUserInputs).intValue() != buttonNumber)
            {
                puzzlePiece = 0;
                saveData("currentScore", scoreLevel + "", getApplicationContext());
                randomSequence.clear();
                randomSequenceString.setLength(0);
                userSequence.clear();
                userSequenceString.setLength(0);
                numOfItems = 4;
                numUserInputs = 0;
                gameStart = false;
                scoreLevel = 0;
                start.setText("HAS PERDIDO... QUIERES JUGAR DE NUEVO??");
                score.setText(0 + "");
                //textView.setText("Score: ");
                soundSix.start();
                keepGoing = false;
            }
            if(keepGoing == true)
            {
                userSequenceString.append(buttonNumber + "");
                miniScore++;
                score.setText(miniScore + "");
                numUserInputs++;

            }
        }
        else
        {
            //start.setText("Play the game!");
        }
        if(numUserInputs >= numOfItems)
        {
            for(int i = 0; i < numOfItems; i++)
            {
                tempUS = userSequence.get(i).intValue();
                tempRS = randomSequence.get(i).intValue();
                try
                {
                    if(tempUS == tempRS)
                    {
                        puzzlePiece++;
                        if(puzzlePiece == numOfItems)
                        {
                            puzzlePiece = 0;
                            scoreLevel++;
                            saveData("currentScore", scoreLevel + "", getApplicationContext());
                            try
                            {
                                String highScoreString = getData("savedHighScore", getApplicationContext());
                                int highScore = Integer.parseInt(highScoreString);
                                if (scoreLevel >= highScore)
                                {
                                    highScore = scoreLevel;
                                    saveData("savedHighScore", highScore + "", getApplicationContext());
                                }
                            }
                            catch(NullPointerException e)
                            {
                                saveData("savedHighScore", 0 + "", getApplicationContext());
                                String highScoreString = getData("savedHighScore", getApplicationContext());
                                int highScore = Integer.parseInt(highScoreString);
                                if (scoreLevel >= highScore)
                                {
                                    highScore = scoreLevel;
                                    saveData("savedHighScore", highScore + "", getApplicationContext());
                                }
                            }
                            catch(NumberFormatException e)
                            {
                                saveData("savedHighScore", 0 + "", getApplicationContext());
                                String highScoreString = getData("savedHighScore", getApplicationContext());
                                int highScore = Integer.parseInt(highScoreString);
                                if (scoreLevel >= highScore)
                                {
                                    highScore = scoreLevel;
                                    saveData("savedHighScore", highScore + "", getApplicationContext());
                                }
                            }
                            hiScore.setText(scoreLevel + "");
                            randomSequence.clear();
                            randomSequenceString.setLength(0);
                            userSequence.clear();
                            userSequenceString.setLength(0);
                            numUserInputs = 0;
                            numOfItems++;
                            gameStart = false;
                            start.setText("BIEN HECHO!!! QUIERES JUGAR EL NIVEL " + (scoreLevel + 1) + "?");

                            soundFive.start();
                            break;
                        }
                    }
                    else
                    {
                        puzzlePiece = 0;
                        randomSequence.clear();
                        randomSequenceString.setLength(0);
                        userSequence.clear();
                        userSequenceString.setLength(0);
                        numOfItems = 4;
                        numUserInputs = 0;
                        gameStart = false;
                        scoreLevel = 0;
                        start.setText("HAS PERDIDO... QUIERES JUGAR DE NUEVO??");

                        soundSix.start();
                        break;
                    }
                }
                catch(IndexOutOfBoundsException e)
                {
                    start.setText("Exception Occurred");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        AudioManager audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            if(item.isChecked() == true)
            {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                item.setChecked(false);
                sound = false;
            }
            else if(item.isChecked() == false)
            {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                item.setChecked(true);
                sound = true;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
