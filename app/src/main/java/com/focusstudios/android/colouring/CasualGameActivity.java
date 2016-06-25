package com.focusstudios.android.colouring;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CasualGameActivity extends AppCompatActivity {

	private TextView mComingSoonText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_casual_game);

		Typeface font = Typeface.createFromAsset(getAssets(), "Optien.ttf");

		mComingSoonText = (TextView) findViewById(R.id.coming_soon_text);
		mComingSoonText.setTypeface(font);
	}

}
