package com.focusstudios.android.colouring.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.focusstudios.android.colouring.activities.GameActivity;
import com.focusstudios.android.colouring.R;

public class EndFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        inflater = getActivity().getLayoutInflater();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Optien.ttf");

        View v = inflater.inflate(R.layout.fragment_end, container, false);

        TextView mGameText = (TextView) v.findViewById(R.id.game_text);
        mGameText.setTypeface(font, Typeface.BOLD);
        mGameText.setText("Congratulations! You passed all " + ((GameActivity) getActivity()).getNumOfRounds() + " rounds in");

        TextView mEndTimeText = (TextView) v.findViewById(R.id.end_time_text);
        mEndTimeText.setTypeface(font, Typeface.BOLD);
        mEndTimeText.setText(((GameActivity) getActivity()).getEndTime());

        TextView mHighScoreText = (TextView) v.findViewById(R.id.high_score_text);
        mHighScoreText.setTypeface(font, Typeface.BOLD);
        mHighScoreText.setText("Your high score: " + ((GameActivity) getActivity()).getHighScore());

        Button mRestartButton = (Button) v.findViewById(R.id.restart_button);
        mRestartButton.setTypeface(font);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((GameActivity) getActivity()).playSFX();
                ((GameActivity)getActivity()).restartGame();
            }
        });

        Button mHomeButton = (Button) v.findViewById(R.id.home_button);
        mHomeButton.setTypeface(font);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((GameActivity) getActivity()).playSFX();
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
            }
        });
        return v;
    }
}
