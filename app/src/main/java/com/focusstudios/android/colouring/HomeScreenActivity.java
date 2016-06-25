package com.focusstudios.android.colouring;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity {

	private TextView mTitleLabel1;
	private TextView mTitleLabel2;
	private Button mClassicModeButton; //all buttons will turn into ImageButtons so they can be fancy
	private Button mCasualModeButton;
	private Button mTutorialButton;
	private Button mOptionsButton;
	private Button mAboutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);

		Typeface font = Typeface.createFromAsset(getAssets(), "Optien.ttf");

		mTitleLabel1 = (TextView) findViewById(R.id.title_label1);
		mTitleLabel1.setText("Colou");
		mTitleLabel1.setTypeface(font);

		mTitleLabel2 = (TextView) findViewById(R.id.title_label2);
		mTitleLabel2.setText("Ring");
		mTitleLabel2.setTypeface(font);

		mClassicModeButton = (Button) findViewById(R.id.classic_mode_button);
		mClassicModeButton.setTypeface(font);
		mClassicModeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeScreenActivity.this, ClassicGameActivity.class);
				startActivity(i);
			}
		});

		mCasualModeButton = (Button) findViewById(R.id.casual_mode_button);
		mCasualModeButton.setTypeface(font);
		mCasualModeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeScreenActivity.this, CasualGameActivity.class);
				startActivity(i);
			}
		});

		mTutorialButton = (Button) findViewById(R.id.tutorial_button);
		mTutorialButton.setTypeface(font);
		mTutorialButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeScreenActivity.this, TutorialActivity.class);
				startActivity(i);
			}
		});

		mOptionsButton = (Button) findViewById(R.id.options_button);
		mOptionsButton.setTypeface(font);
		mOptionsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeScreenActivity.this, OptionsActivity.class);
				startActivity(i);
			}
		});

		mAboutButton = (Button) findViewById(R.id.about_button);
		mAboutButton.setTypeface(font);
		mAboutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(HomeScreenActivity.this, AboutActivity.class);
				startActivity(i);
			}
		});
	}
}

