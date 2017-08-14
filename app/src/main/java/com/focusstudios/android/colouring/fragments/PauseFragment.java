package com.focusstudios.android.colouring.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.focusstudios.android.colouring.DBHandler;
import com.focusstudios.android.colouring.R;
import com.focusstudios.android.colouring.Scores;

public class PauseFragment extends Fragment {

    private Activity mActivity;

    public interface OnPauseScreen {
        void prepareBGM();
        void playBGM();
        void stopBGM();
        void playSFX();
        void restartGame();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        DBHandler db = new DBHandler(getActivity());

        inflater = getActivity().getLayoutInflater();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Optien.ttf");

        View v = inflater.inflate(R.layout.fragment_pause, container, false);

        TextView mPausedTitle = (TextView) v.findViewById(R.id.paused_title);
        mPausedTitle.setTypeface(font, Typeface.BOLD);

        TextView mMusicText = (TextView) v.findViewById(R.id.music_text);
        mMusicText.setTypeface(font);

        ToggleButton mMusicOnOff = (ToggleButton) v.findViewById(R.id.music_button);
        mMusicOnOff.setTypeface(font);
        if (db.getScores(1).getMusic() == 1)
            mMusicOnOff.setChecked(true);
        else
            mMusicOnOff.setChecked(false);

        mMusicOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DBHandler db = new DBHandler(getActivity());
                if (isChecked) { //toggle on
                    db.updateScores(new Scores(1, db.getScores(1).getScore(), db.getScores(1).getGamesPlayed(), 1, db.getScores(1).getSFX()));
                    ((OnPauseScreen) mActivity).prepareBGM();
                    ((OnPauseScreen) mActivity).playBGM();
                } else { //toggle off
                    db.updateScores(new Scores(1, db.getScores(1).getScore(), db.getScores(1).getGamesPlayed(), 0, db.getScores(1).getSFX()));
                    ((OnPauseScreen) mActivity).stopBGM();
                }
            }
        });

        TextView mSFXText = (TextView) v.findViewById(R.id.sfx_text);
        mSFXText.setTypeface(font);

        ToggleButton mSFXOnOff = (ToggleButton) v.findViewById(R.id.sfx_button);
        mSFXOnOff.setTypeface(font);
        if (db.getScores(1).getSFX() == 1)
            mSFXOnOff.setChecked(true);
        else
            mSFXOnOff.setChecked(false);

        mSFXOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DBHandler db = new DBHandler(getActivity());
                if (isChecked) { //toggle on
                    db.updateScores(new Scores(1, db.getScores(1).getScore(), db.getScores(1).getGamesPlayed(), db.getScores(1).getMusic(), 1));
                } else { //toggle off
                    db.updateScores(new Scores(1, db.getScores(1).getScore(), db.getScores(1).getGamesPlayed(), db.getScores(1).getMusic(), 0));
                }
            }
        });

        Button mRestartButton = (Button) v.findViewById(R.id.restart_button);
        mRestartButton.setTypeface(font);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((OnPauseScreen) mActivity).playSFX();
                ((OnPauseScreen) mActivity).restartGame();
            }
        });

        Button mHomeButton = (Button) v.findViewById(R.id.home_button);
        mHomeButton.setTypeface(font);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((OnPauseScreen) mActivity).playSFX();
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}