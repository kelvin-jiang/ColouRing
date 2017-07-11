package com.focusstudios.android.colouring.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.focusstudios.android.colouring.Board;
import com.focusstudios.android.colouring.fragments.CountdownFragment;
import com.focusstudios.android.colouring.DBHandler;
import com.focusstudios.android.colouring.fragments.EndFragment;
import com.focusstudios.android.colouring.fragments.GameViewFragment;
import com.focusstudios.android.colouring.fragments.PauseButtonFragment;
import com.focusstudios.android.colouring.fragments.PauseFragment;
import com.focusstudios.android.colouring.R;
import com.focusstudios.android.colouring.fragments.RoundNumberFragment;
import com.focusstudios.android.colouring.Scores;
import com.focusstudios.android.colouring.fragments.StopwatchFragment;

public class GameActivity extends FragmentActivity implements GameViewFragment.OnGameStart,
        RoundNumberFragment.OnRoundStart, PauseFragment.OnPauseScreen, PauseButtonFragment.OnPause {

	private final int NUM_OF_ROUNDS = 10;

	private Board mGameBoard = null;
	private int mRoundNumber = 1;
	private String mEndTime;
	private MediaPlayer mSFXPlayer;
	private MediaPlayer mBGMPlayer;

	private DBHandler mDB = new DBHandler(this);

    //overridden methods
	@Override
	protected void onPause(){
		mBGMPlayer.pause();
		super.onPause();
	}

	@Override
	protected void onRestart(){
		playBGM();
		super.onRestart();
	}

	@Override
	public void onBackPressed() {
		//disable user from pressing back
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//hide status bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mBGMPlayer = MediaPlayer.create(this, R.raw.bgm);
        mBGMPlayer.setLooping(true);
        playBGM();
        mSFXPlayer = MediaPlayer.create(this, R.raw.sfx);
	}

	//miscellaneous methods
	public void nextRound(){
		mRoundNumber++;
		mGameBoard = null;

		if (mRoundNumber > NUM_OF_ROUNDS) { //end game
			mEndTime = ((StopwatchFragment) getSupportFragmentManager().findFragmentById(R.id.stopwatch_container)).getElapsedTime();
			removeAllFragments();
			getSupportFragmentManager().beginTransaction().add(R.id.game_container, new EndFragment(), "END").commit();
			String currentScore = ((StopwatchFragment) getSupportFragmentManager().findFragmentById(R.id.stopwatch_container)).getElapsedTime();

			if (currentScore.compareTo(mDB.getScores(1).getScore()) < 0)
				mDB.updateScores(new Scores(1, currentScore, mDB.getScores(1).getGamesPlayed() + 1, mDB.getScores(1).getMusic(), mDB.getScores(1).getSFX()));
			else
				mDB.updateScores(new Scores(1, mDB.getScores(1).getScore(), mDB.getScores(1).getGamesPlayed() + 1, mDB.getScores(1).getMusic(), mDB.getScores(1).getSFX()));
		}
		else { //keep going to next round
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.game_container, new GameViewFragment());
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            transaction.replace(R.id.round_number_container, new RoundNumberFragment());
            transaction.commit();
		}
	}

	public void restartGame(){
		mRoundNumber = 1;
		mGameBoard = null;
		removeAllFragments();
		getSupportFragmentManager().beginTransaction().add(R.id.game_container, new CountdownFragment()).commit();
		overridePendingTransition(0, 0);
	}

	public void removeAllFragments(){
		Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.pause_button_container);
		removeFragment(fragment);

		fragment = getSupportFragmentManager().findFragmentById(R.id.round_number_container);
		removeFragment(fragment);

		fragment = getSupportFragmentManager().findFragmentById(R.id.stopwatch_container);
		removeFragment(fragment);

		fragment = getSupportFragmentManager().findFragmentById(R.id.game_container);
		removeFragment(fragment);
	}

	public void removeFragment(Fragment fragment){
		if (fragment != null)
			getSupportFragmentManager().beginTransaction().remove(fragment).commit();
	}

	public void startStopwatch() {
        ((StopwatchFragment) getSupportFragmentManager().findFragmentById(R.id.stopwatch_container)).startStopwatch();
    }

	public void stopStopwatch() {
        ((StopwatchFragment) getSupportFragmentManager().findFragmentById(R.id.stopwatch_container)).stopStopwatch();
    }

	public void saveBoard(Board board) {
		mGameBoard = board;
	}

	//public getters and setters
	public int getNumOfRounds() {
		return NUM_OF_ROUNDS;
	}

	public int getRoundNumber() {
		return mRoundNumber;
	}

	public Board getGameBoard() {
		return mGameBoard;
	}

	public String getEndTime() {
		return mEndTime;
	}

	public String getHighScore() { return mDB.getScores(1).getScore(); }

	//BGM/SFX methods
	public void playBGM(){
		if (mDB.getScores(1).getMusic() == 1) {
			mBGMPlayer.start();
		}
	}

	public void prepareBGM() {
		try {
			mBGMPlayer.prepare();
		} catch (Exception e) {}
	}

	public void stopBGM(){
		if (mBGMPlayer.isPlaying())
			mBGMPlayer.stop();
	}

	public void playSFX(){
		if (mDB.getScores(1).getSFX() == 1)
			mSFXPlayer.start();
	}
}
