package com.focusstudios.android.colouring.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.focusstudios.android.colouring.Board;
import com.focusstudios.android.colouring.R;

public class PauseButtonFragment extends Fragment {

    private Activity mActivity;

    public interface OnPause {
        void playSFX();
        void saveBoard(Board board);
        void startStopwatch();
        void stopStopwatch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pause_button, container, false);

        ImageButton pauseButton = (ImageButton) v.findViewById(R.id.fragment_pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment pauseFragment = getActivity().getSupportFragmentManager().findFragmentByTag("PAUSE");

                if (pauseFragment == null) {
                    ((OnPause) mActivity).playSFX();
                    ((OnPause) mActivity).stopStopwatch();
                    ((OnPause) mActivity).saveBoard(((GameViewFragment) (getActivity().getSupportFragmentManager().findFragmentById(R.id.game_container))).getBoard()); //save board to mActivity
                    ((GameViewFragment) (getActivity().getSupportFragmentManager().findFragmentById(R.id.game_container))).removeBoard();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.game_container, new PauseFragment(), "PAUSE").commit();
                }

                else {
                    ((OnPause) mActivity).playSFX();
                    ((OnPause) mActivity).startStopwatch();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.game_container, new GameViewFragment()).commit();
                }
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
