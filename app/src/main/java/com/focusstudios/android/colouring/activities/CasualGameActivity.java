package com.focusstudios.android.colouring.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.focusstudios.android.colouring.R;

public class CasualGameActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//hide status bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_casual_game);

		Typeface font = Typeface.createFromAsset(getAssets(), "Exo.otf");
		TextView comingSoonText = (TextView) findViewById(R.id.coming_soon_text);
		comingSoonText.setTypeface(font);

		Toast.makeText(getApplicationContext(), R.string.goback_text, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}
}
