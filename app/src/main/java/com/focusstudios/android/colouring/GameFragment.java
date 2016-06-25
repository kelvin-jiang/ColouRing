package com.focusstudios.android.colouring;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameFragment extends Fragment {

	private ImageButton mPauseButton;
	private Chronometer mStopwatch;
	private GameView game = null; //arbitrary initialization value to avoid errors
	private TextView mRoundNumberText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_game, container, false);

		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Optien.ttf");

		mPauseButton = (ImageButton) v.findViewById(R.id.pause_button);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				replaceFragment(container, game);
				//pause the timer
			}
		});

		mStopwatch = (Chronometer) v.findViewById(R.id.stopwatch);
		mStopwatch.setTypeface(font);
		mStopwatch.setBase(SystemClock.elapsedRealtime() - ((ClassicGameActivity) getActivity()).getTimeElapsed());
		mStopwatch.start();

		//if there is no data in getcurrentpaints, start new game
		//if there is data, get all data from classicgameactivity methods
		if (((ClassicGameActivity) getActivity()).getGameBoard() == null || getTag().equals("RESTART")) {
			game = new GameView(getActivity(), null, getRoundNumber());
			//((ClassicGameActivity) getActivity()).setRoundNumber(1); //this needs to happen only when tag equals restart, probably cannot use the tag system
		}
		else {
			game = new GameView(getActivity(), ((ClassicGameActivity) getActivity()).getGameBoard(), getRoundNumber());
		}
		container.addView(game);

		mRoundNumberText = (TextView) v.findViewById(R.id.round_number_text);
		mRoundNumberText.setTypeface(font);
		mRoundNumberText.setText("Round " + getRoundNumber() + " of 5");

		return v;
	}

	public void replaceFragment(ViewGroup container, GameView game) {
		//save current game values to activity
		((ClassicGameActivity) getActivity()).saveValuesToActivity(game.getBoard(), chronometerToSeconds());
		container.removeView(game);
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PauseFragment()).commit();
	}

	private int chronometerToSeconds() {
		int seconds = (Integer.parseInt(mStopwatch.getText().toString().substring(0, 2)) * 60) +
				Integer.parseInt(mStopwatch.getText().toString().substring(3, 5));

		//Toast.makeText(getContext(), "Seconds: " + seconds, Toast.LENGTH_SHORT).show();
		return seconds;
	}

	private int getRoundNumber() {
		if (((ClassicGameActivity) getActivity()).getGameBoard() == null)
			return 1;
		else
			return ((ClassicGameActivity) getActivity()).getGameBoard().getRoundNumber();
	}
}
