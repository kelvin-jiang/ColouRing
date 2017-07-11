package com.focusstudios.android.colouring;

import android.graphics.Paint;

public class Board {
    private int mRoundNumber = 1;
    private Paint[][] mCurrentPaints = new Paint[4][4];
    private Paint[][] mOriginalPaints = new Paint[4][4];
    private Paint[] mGradientIndices = new Paint[4];

    public Board(){
        //initialize the arrays
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                mCurrentPaints[i][j] = new Paint();
                mOriginalPaints[i][j] = new Paint();
            }
            mGradientIndices[i] = new Paint();
        }
    }

    public int getRoundNumber() {
        return mRoundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        mRoundNumber = roundNumber;
    }

    public Paint[][] getCurrentPaints() {
        return mCurrentPaints;
    }

    public void setCurrentPaints(Paint[][] currentPaints) {
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                mCurrentPaints[i][j].setColor(currentPaints[i][j].getColor());
            }
        }
    }

    public Paint[][] getOriginalPaints() {
        return mOriginalPaints;
    }

    public void setOriginalPaints(Paint[][] originalPaints) {
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                mOriginalPaints[i][j].setColor(originalPaints[i][j].getColor());
            }
        }
    }

    public void setOnePaint(int colour, int i, int j) {
        mCurrentPaints[i][j].setColor(colour);
    }

    public Paint[] getGradientIndices() {
        return mGradientIndices;
    }

    public void setOneGradient(int colour, int i) {
        mGradientIndices[i].setColor(colour);
    }
}
