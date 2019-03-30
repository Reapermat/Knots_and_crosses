package com.matthewferry.tictac;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    //red=0, yellow=1, empty =2
    int i=1;

    int[] gameState ={2,2,2,2,2,2,2,2,2};
    int [] [] winningPositions = {{0,1,2},{3,4,5},{6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean gameActive=true;
    int count_clicks=0;
    ImageView fireworks;
    MediaPlayer mediaPlayer;

    public void DropIn(View view)
    {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter]==2 && gameActive == true) {

            gameState[tappedCounter] = i;

            counter.setTranslationY(-1000);

            if (i == 0) {
                counter.setImageResource(R.drawable.red);
                i = 1;
                count_clicks++;
            } else {
                counter.setImageResource(R.drawable.yellow);
                i = 0;
                count_clicks++;
            }
            counter.animate().translationYBy(1000).rotation(3600).setDuration(300);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    //someone has won;
                    String winner = "";

                    if (i == 1) {
                        winner = "Red";
                    } else
                        winner = "Yellow";

                    Button playAgainButton = findViewById(R.id.playAgainButton);
                    TextView winnerTextView = findViewById(R.id.winnerTextView);
                    //fireworks.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.videoplayback);
                    winnerTextView.setText("the winner is " + winner);
                    //fireworks.animate().translationYBy(1000).rotation(3600).alpha(1).setDuration(2000);
                    fireworks.animate().translationYBy(1000).rotation(3600).alpha(1).setDuration(2000);
                    mediaPlayer.start();
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);


                    gameActive = false;
                    count_clicks=0;

                }
                if(count_clicks==9) {
                    Button playAgainButton = findViewById(R.id.playAgainButton);
                    TextView winnerTextView = findViewById(R.id.winnerTextView);

                    winnerTextView.setText("Tie");

                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                    fireworks.animate().translationYBy(1000).setDuration(500);

                    gameActive = false;
                    count_clicks=0;
                }
            }
        }
    }


    public void playAgain(View view)
    {

        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);

        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);
        fireworks.animate().translationYBy(-1000).rotation(-3600).alpha(0).setDuration(2000);
        mediaPlayer.pause();

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for(int x = 0; x<gridLayout.getChildCount(); x++ )
        {

            ImageView counter = (ImageView) gridLayout.getChildAt(x);

            counter.setImageDrawable(null);

        }

        for(int y=0; y<gameState.length;y++)
        {
            gameState[y]=2;
        }

        i = 1;

        gameActive=true;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fireworks = findViewById(R.id.fireworks);
        fireworks.setTranslationY(-1000);
        mediaPlayer = MediaPlayer.create(this, R.raw.brawa);
    }
}
