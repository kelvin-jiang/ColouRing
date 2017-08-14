package com.focusstudios.android.colouring;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.batch.android.Batch;
import com.batch.android.BatchUnlockListener;
import com.batch.android.Feature;
import com.batch.android.Offer;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements BatchUnlockListener{

    private static final String NO_ADS_REFERENCE = "NO_ADS";

    @Override
    protected void onStart() {
        Batch.Unlock.setUnlockListener(this);
        Batch.onStart(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        Batch.onStop(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Batch.onDestroy(this);
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Batch.onNewIntent(this, intent);
        super.onNewIntent(intent);
    }

    @Override
    public void onRedeemAutomaticOffer(Offer offer) {

        for (Feature feature : offer.getFeatures()) {
            if ((feature.getReference()).equals(NO_ADS_REFERENCE)) {
                final Map<String, String> additionalParameters = offer.getOfferAdditionalParameters();
                if (additionalParameters.containsKey("reward_message")) {
                    String rewardMessage = additionalParameters.get("reward_message");

                    //build the dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(rewardMessage).setTitle("Congratulations!").setPositiveButton("Thanks!", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }
}
