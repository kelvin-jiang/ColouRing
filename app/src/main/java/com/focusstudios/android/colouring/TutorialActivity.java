package com.focusstudios.android.colouring;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class TutorialActivity extends Activity {

	// Declare Variables
	ViewPager viewPager;
	PagerAdapter adapter;
	String[] text;
	int[] image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		String text1 = getResources().getString(R.string.tutorial_text_1);
		String text2 = getResources().getString(R.string.tutorial_text_2);
		String text3 = getResources().getString(R.string.tutorial_text_3);
		String text4 = getResources().getString(R.string.tutorial_text_4);
		String text5 = getResources().getString(R.string.tutorial_text_5);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_layout);

		text = new String[] {text1, text2, text3, text4, text5};

		image = new int[] {R.drawable.nothing, R.drawable.screenshot1, R.drawable.screenshot2, R.drawable.screenshot3, R.drawable.screenshot4,};

		viewPager = (ViewPager) findViewById(R.id.pager);

		adapter = new ViewPagerAdapter(TutorialActivity.this, text, image);

		viewPager.setAdapter(adapter);

	}
}