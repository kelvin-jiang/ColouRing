package com.focusstudios.android.colouring.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.focusstudios.android.colouring.R;

public class RoundNumberFragment extends Fragment {

    private Activity mActivity;

    public interface OnRoundStart {
        int getRoundNumber();
        int getNumOfRounds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_round_number, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Optien.ttf");

        TextView roundNumberText = (TextView) v.findViewById(R.id.fragment_round_number_text);
        roundNumberText.setTypeface(font);
        roundNumberText.setText("Round " + ((OnRoundStart) mActivity).getRoundNumber() + " of " +
                ((OnRoundStart) mActivity).getNumOfRounds());

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }
}
