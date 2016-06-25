package com.focusstudios.android.colouring;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CountdownFragment extends Fragment {

	private TextView mTimerLabel;
	private CountDownTimer mTimer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_countdown, container, false);
		mTimerLabel = (TextView) v.findViewById(R.id.countdown_timer_text);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Optien.ttf");
		mTimerLabel.setTypeface(font);
		mTimer = new CountDownTimer(4000,500){
			@Override
			public void onTick(long millisUntilFinished) {
				DecimalFormat df = new DecimalFormat("0");
				int count = (int) millisUntilFinished / 1000;
				String time = df.format(count);
				if (count > 0) {
					mTimerLabel.setTextColor(Color.GREEN);
					mTimerLabel.setText(time);
				}
				else if (count == 0){
					mTimerLabel.setTextColor(Color.RED);
					mTimerLabel.setText("GO!");
				}
			}
			@Override
			public void onFinish() {
				replaceFragment();
			}
		};
		mTimer.start();

		return v;
	}

	public void replaceFragment(){
		FragmentManager fm = getActivity().getSupportFragmentManager();
		fm.beginTransaction()
				.remove(this)
				.add(R.id.fragment_container, new GameFragment())
				.commit();
	}
}
