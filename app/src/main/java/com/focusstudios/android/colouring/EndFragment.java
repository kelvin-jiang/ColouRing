package com.focusstudios.android.colouring;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EndFragment extends Fragment {

    private TextView mGameText;
    private Button mHomeButton;
    private Button mRestartButton;
    private int time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        inflater = getActivity().getLayoutInflater();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Optien.ttf");

        View v = inflater.inflate(R.layout.fragment_end, null);
        mGameText = (TextView) v.findViewById(R.id.game_text);
        mGameText.setTypeface(font, Typeface.BOLD);

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
