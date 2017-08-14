package com.focusstudios.android.colouring.activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.focusstudios.android.colouring.fragments.CountdownFragment;
import com.focusstudios.android.colouring.R;

public class ClassicGameActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_classic_game);

        getSupportFragmentManager().beginTransaction().add(R.id.game_container, new CountdownFragment()).commit();
        overridePendingTransition(0, 0);
    }
}
