package com.focusstudios.android.colouring.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.focusstudios.android.colouring.DBHandler;
import com.focusstudios.android.colouring.MainActivity;
import com.focusstudios.android.colouring.R;
import com.focusstudios.android.colouring.Scores;

public class HomeScreenActivity extends MainActivity {

	private MediaPlayer mSFXPlayer;
	private MediaPlayer mBGMPlayer;
	private DBHandler db = new DBHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//hide status bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_home_screen);

		//if (db.isEmpty())
        //give sqlite specific values to indicate no highscore is saved
        openOrCreateDatabase("scores_database", Context.MODE_PRIVATE, null);
        db.addScores(new Scores(1, "99:99", -1, 1, 1));

		//automatically start playing the music
		mBGMPlayer = MediaPlayer.create(this, R.raw.bgm);
		mBGMPlayer.setLooping(true);
		playBGM();

		//setup sfx player
		mSFXPlayer = MediaPlayer.create(this, R.raw.sfx);

		Typeface font = Typeface.createFromAsset(getAssets(), "Optien.ttf");

		TextView mTitleLabel1 = (TextView) findViewById(R.id.title_label1);
		mTitleLabel1.setText("Colou");
		mTitleLabel1.setTypeface(font);

		TextView mTitleLabel2 = (TextView) findViewById(R.id.title_label2);
		mTitleLabel2.setText("Ring");
		mTitleLabel2.setTypeface(font);

		Button mClassicModeButton = (Button) findViewById(R.id.classic_mode_button);
		mClassicModeButton.setTypeface(font);
		mClassicModeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSFX();
				Intent i = new Intent(HomeScreenActivity.this, ClassicGameActivity.class);
				startActivity(i);
				overridePendingTransition(0, 0);
			}
		});

		Button mCasualModeButton = (Button) findViewById(R.id.casual_mode_button);
		mCasualModeButton.setTypeface(font);
		mCasualModeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSFX();
				Intent i = new Intent(HomeScreenActivity.this, CasualGameActivity.class);
				startActivity(i);
				overridePendingTransition(0, 0);
			}
		});

		Button mTutorialButton = (Button) findViewById(R.id.tutorial_button);
		mTutorialButton.setTypeface(font);
		mTutorialButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSFX();
				Intent i = new Intent(HomeScreenActivity.this, TutorialActivity.class);
				startActivity(i);
				overridePendingTransition(0, 0);
			}
		});

		Button mOptionsButton = (Button) findViewById(R.id.options_button);
		mOptionsButton.setTypeface(font);
		mOptionsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSFX();
				Intent i = new Intent(HomeScreenActivity.this, OptionsActivity.class);
				startActivity(i);
				overridePendingTransition(0, 0);
			}
		});

		Button mAboutButton = (Button) findViewById(R.id.about_button);
		mAboutButton.setTypeface(font);
		mAboutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playSFX();
				Intent i = new Intent(HomeScreenActivity.this, AboutActivity.class);
				startActivity(i);
				overridePendingTransition(0, 0);
			}
		});
	}

	@Override
	protected void onPause(){
		stopBGM();
		super.onPause();
	}

	@Override
	protected void onRestart(){
		playBGM();
		super.onRestart();
	}

	@Override
	protected void onResume(){
		playBGM();
		super.onResume();
	}

	public void playBGM(){
		if (db.getScores(1).getMusic() == 1)
			mBGMPlayer.start();
	}

	public void stopBGM(){
		if (mBGMPlayer.isPlaying())
			mBGMPlayer.stop();
	}

	public void playSFX(){
		if (db.getScores(1).getSFX() == 1)
			mSFXPlayer.start();
	}
}
