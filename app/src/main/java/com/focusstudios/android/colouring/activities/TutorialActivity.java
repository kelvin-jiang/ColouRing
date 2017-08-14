package com.focusstudios.android.colouring.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.focusstudios.android.colouring.R;
import com.focusstudios.android.colouring.ViewPagerAdapter;

public class TutorialActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//hide status bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tutorial_layout);

		String[] text = new String[] {getResources().getString(R.string.tutorial_text_1), getResources().getString(R.string.tutorial_text_2),
                getResources().getString(R.string.tutorial_text_3), getResources().getString(R.string.tutorial_text_4),
                getResources().getString(R.string.tutorial_text_5), getResources().getString(R.string.tutorial_text_6)};

		int[] image = new int[] {R.drawable.nothing, R.drawable.screenshot1, R.drawable.screenshot2, R.drawable.screenshot3,
                R.drawable.screenshot4, R.drawable.screenshot5};

		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		PagerAdapter adapter = new ViewPagerAdapter(TutorialActivity.this, text, image);
		viewPager.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), R.string.goback_text, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}
}
