package com.focusstudios.android.colouring;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

	private TextView mAboutText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		Typeface font = Typeface.createFromAsset(getAssets(), "Optien.ttf");

		mAboutText = (TextView) findViewById(R.id.about_info_text);
		mAboutText.setTypeface(font);
	}
}
