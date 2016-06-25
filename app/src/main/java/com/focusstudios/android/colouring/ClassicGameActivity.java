package com.focusstudios.android.colouring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ClassicGameActivity extends FragmentActivity {

	private Board gameBoard = null;
	private int timeElapsed = 0;

	Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classic_game);

		FragmentManager fm = getSupportFragmentManager();
		mFragment = fm.findFragmentById(R.id.fragment_container);

		if (mFragment == null) {
			mFragment = new CountdownFragment();
			fm.beginTransaction().add(R.id.fragment_container, mFragment).commit();
		}
	}

	@Override
	public void onBackPressed() {
		//disable user from pressing back
	}

	public void saveValuesToActivity(Board board, int timeElapsed){
		gameBoard = board;
		this.timeElapsed = timeElapsed;
	}

	public void nextRound(Board board){
		gameBoard = board;
		gameBoard.setRoundNumber(gameBoard.getRoundNumber() + 1);

		if (gameBoard.getRoundNumber() > 5) {
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EndFragment(),"RESUME").commit();
			//get time
		}
		else //keep going to next round
			getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GameFragment(), "RESUME").commit();
	}

	public Board getGameBoard(){
		return gameBoard;
	}

	public int getTimeElapsed() {
		return timeElapsed;
	}
}
