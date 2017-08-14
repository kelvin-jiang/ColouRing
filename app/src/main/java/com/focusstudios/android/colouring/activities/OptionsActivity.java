package com.focusstudios.android.colouring.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.focusstudios.android.colouring.DBHandler;
import com.focusstudios.android.colouring.R;
import com.focusstudios.android.colouring.Scores;

public class OptionsActivity extends AppCompatActivity {

	private DBHandler db = new DBHandler(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//hide status bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_options);

		Typeface font = Typeface.createFromAsset(getAssets(), "Optien.ttf");

		TextView mOptions = (TextView) findViewById(R.id.options_title);
		mOptions.setTypeface(font);

		TextView mMusicText = (TextView) findViewById(R.id.music_text);
		mMusicText.setTypeface(font);

		ToggleButton mMusicOnOff = (ToggleButton) findViewById(R.id.music_button);
		mMusicOnOff.setTypeface(font);
		if (db.getScores(1).getMusic() == 1)
			mMusicOnOff.setChecked(true);
		else
			mMusicOnOff.setChecked(false);

		mMusicOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) { //toggle on
					db.updateScores(new Scores(1, db.getScores(1).getScore(), db.getScores(1).getGamesPlayed(), 1, db.getScores(1).getSFX()));
				} else { //toggle off
					db.updateScores(new Scores(1, db.getScores(1).getScore(), db.getScores(1).getGamesPlayed(), 0, db.getScores(1).getSFX()));
				}
			}
		});

		TextView mSFXText = (TextView) findViewById(R.id.sfx_text);
		mSFXText.setTypeface(font);

		ToggleButton mSFXOnOff = (ToggleButton) findViewById(R.id.sfx_button);
		mSFXOnOff.setTypeface(font);
		if (db.getScores(1).getSFX() == 1)
			mSFXOnOff.setChecked(true);
		else
			mSFXOnOff.setChecked(false);

		mSFXOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) { //toggle on
					db.updateScores(new Scores(1, db.getScores(1).getScore(), db.getScores(1).getGamesPlayed(), db.getScores(1).getMusic(), 1));
				} else { //toggle off
					db.updateScores(new Scores(1, db.getScores(1).getScore(), db.getScores(1).getGamesPlayed(), db.getScores(1).getMusic(), 0));
				}
			}
		});

		TextView mHighScoreText = (TextView) findViewById(R.id.options_high_score_text);
		mHighScoreText.setTypeface(font);
        if (db.getScores(1).getScore().equals("99:99"))
            mHighScoreText.setText("Your high score: N/A");
        else
            mHighScoreText.setText("Your high score: " + db.getScores(1).getScore());

		Button mResetButton = (Button) findViewById(R.id.reset_button);
		mResetButton.setTypeface(font);
		mResetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				new AlertDialog.Builder(OptionsActivity.this).setTitle("Reset Data").setMessage("Are you sure you want to reset your data? This will clear all your previous scores.").setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								db.clearTable();
                                db.addScores(new Scores(1, "99:99", -1, 1, 1));

								selfDestruct();
								overridePendingTransition(0, 0);
							}
						})
						.setNegativeButton("No", null).show();
			}
		});

		Toast.makeText(getApplicationContext(), R.string.goback_text, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}

	private void selfDestruct(){
		finish();
	}
}
