package com.focusstudios.android.colouring.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.focusstudios.android.colouring.Board;
import com.focusstudios.android.colouring.GameView;
import com.focusstudios.android.colouring.R;

public class GameViewFragment extends Fragment {

    private GameView game = null; //arbitrary initialization value to avoid errors
    private Activity mActivity;

    public interface OnGameStart {
        Board getGameBoard();
        int getRoundNumber();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gameview, container, false);

        Board gameboard = ((OnGameStart) mActivity).getGameBoard();
        game = new GameView(getActivity(), ((OnGameStart) mActivity).getRoundNumber(), gameboard);
        container.addView(game);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public Board getBoard() {
        return game.getBoard();
    }

    public void removeBoard(){
        ((ViewGroup) game.getParent()).removeView(game);
    }
}
