package com.focusstudios.android.colouring;

import android.graphics.Paint;

public class Board {

    int roundNumber = 1;
    Paint[][] currentPaints = new Paint[4][4];
    Paint[][] originalPaints = new Paint[4][4];
    Paint[] ringsGradient = new Paint[4];

    public Board(){
        //initialize the arrays
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                currentPaints[i][j] = new Paint();
                originalPaints[i][j] = new Paint();
            }
            ringsGradient[i] = new Paint();
        }
    }

    public Board (int roundNumber, Paint[][] currentPaints, Paint[][] originalPaints, Paint[] ringsGradient){
        //copy the data
        this.roundNumber = roundNumber;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                this.currentPaints[i][j].setColor(currentPaints[i][j].getColor());
                this.originalPaints[i][j].setColor(originalPaints[i][j].getColor());
            }
            this.ringsGradient[i].setColor(ringsGradient[i].getColor());
        }
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Paint[][] getCurrentPaints() {
        return currentPaints;
    }

    public void setCurrentPaints(Paint[][] currentPaints) {
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                this.currentPaints[i][j].setColor(currentPaints[i][j].getColor());
            }
        }
    }

    public Paint[][] getOriginalPaints() {
        return originalPaints;
    }

    public void setOriginalPaints(Paint[][] originalPaints) {
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                this.originalPaints[i][j].setColor(originalPaints[i][j].getColor());
            }
        }
    }

    public void setOnePaint(int colour, int i, int j){
        this.currentPaints[i][j].setColor(colour);
    }

    public Paint[] getRingsGradient() {
        return ringsGradient;
    }

    public void setRingsGradient(Paint[] ringsGradient) {
        for (int i = 0; i < 4; i++){
            this.ringsGradient[i].setColor(ringsGradient[i].getColor());
        }
    }

    public void setOneGradient(int colour, int i){
        ringsGradient[i].setColor(colour);
    }
}
