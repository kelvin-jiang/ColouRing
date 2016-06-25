package com.focusstudios.android.colouring;

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

public class PauseFragment extends Fragment {

    private TextView mPausedTitle;
    private TextView mMusicText;
    private ToggleButton mMusicOnOff;
    private TextView mSFXText;
    private ToggleButton mSFXOnOff;
    private Button mResumeButton;
    private Button mHomeButton;
    private Button mRestartButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        inflater = getActivity().getLayoutInflater();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Optien.ttf");

        View v = inflater.inflate(R.layout.fragment_pause, null);

        mPausedTitle = (TextView) v.findViewById(R.id.paused_title);
        mPausedTitle.setTypeface(font, Typeface.BOLD);

        mMusicText = (TextView) v.findViewById(R.id.music_text);
        mMusicText.setTypeface(font);

        mMusicOnOff = (ToggleButton) v.findViewById(R.id.music_button);
        mMusicOnOff.setTypeface(font);
        mMusicOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        mSFXText = (TextView) v.findViewById(R.id.sfx_text);
        mSFXText.setTypeface(font);

        mSFXOnOff = (ToggleButton) v.findViewById(R.id.sfx_button);
        mSFXOnOff.setTypeface(font);
        mSFXOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });

        mResumeButton = (Button) v.findViewById(R.id.resume_button);
        mResumeButton.setTypeface(font);
        mResumeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //pause timer
                //needs to retrieve gamefragment from backstack which it does not do right now
                removeThisFragment("RESUME");
            }
        });

        mHomeButton = (Button) v.findViewById(R.id.home_button);
        mHomeButton.setTypeface(font);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mRestartButton = (Button) v.findViewById(R.id.restart_button);
        mRestartButton.setTypeface(font);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //restart the game, which needs to be done
                removeThisFragment("RESTART");
            }
        });
        return v;
    }

    private void removeThisFragment(String tag) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GameFragment(), tag).commit();
    }
}
