package com.focusstudios.android.colouring;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class OptionsActivity extends AppCompatActivity {

	private TextView mOptions;
	private TextView mMusicText;
	private ToggleButton mMusicOnOff;
	private TextView mSFXText;
	private ToggleButton mSFXOnOff;
	private Button mReset;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

		Typeface font = Typeface.createFromAsset(getAssets(), "Optien.ttf");

		mOptions = (TextView) findViewById(R.id.options_text);
		mOptions.setTypeface(font);

		mMusicText = (TextView) findViewById(R.id.music_text);
		mMusicText.setTypeface(font);

		mMusicOnOff = (ToggleButton) findViewById(R.id.music_button);
		mMusicOnOff.setTypeface(font);
		mMusicOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// The toggle is enabled
				} else {
					// The toggle is disabled
				}
			}
		});

		mSFXText = (TextView) findViewById(R.id.sfx_text);
		mSFXText.setTypeface(font);

		mSFXOnOff = (ToggleButton) findViewById(R.id.sfx_button);
		mSFXOnOff.setTypeface(font);
		mSFXOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// The toggle is enabled
				} else {
					// The toggle is disabled
				}
			}
		});

		mReset = (Button) findViewById(R.id.reset_button);
		mReset.setTypeface(font);
		mReset.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				new AlertDialog.Builder(OptionsActivity.this).setTitle("Reset Scores").setMessage("Are you sure you want to reset your scores?").setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								//Reset Scores
							}
						})
						.setNegativeButton("No", null).show();

			}
		});
	}
}
