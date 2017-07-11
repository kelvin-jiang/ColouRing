package com.focusstudios.android.colouring.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.focusstudios.android.colouring.R;

public class StopwatchFragment extends Fragment {

    private Chronometer mStopwatch;
    private long timeStopped = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Optien.ttf");

        mStopwatch = new Chronometer(getActivity());
        mStopwatch = (Chronometer) v.findViewById(R.id.fragment_stopwatch);
        mStopwatch.setTypeface(font);
        startStopwatch();

        return v;
    }

    public void startStopwatch() {
        mStopwatch.setBase(SystemClock.elapsedRealtime() + timeStopped);
        mStopwatch.start();
    }

    public void stopStopwatch() {
        timeStopped = mStopwatch.getBase() - SystemClock.elapsedRealtime();
        mStopwatch.stop();
    }

    public String getElapsedTime() {
        return mStopwatch.getText().toString();
    }
}
