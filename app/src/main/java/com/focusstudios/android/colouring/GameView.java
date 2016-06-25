package com.focusstudios.android.colouring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class GameView extends View {

    Board gameBoard = new Board();

    public GameView(Context context, Board board, int roundNumber) {

        super(context);

        gameBoard = new Board();
        chooseColours();
        gameBoard.setRoundNumber(roundNumber);
        postInvalidate();
    }

    //-----INSERTING THE COLOURS-----
    @Override
    protected void onDraw(Canvas canvas) {
        int mWidth = getWidth();
        int mHeight = getHeight();
        int mRadius = getWidth() / 2 - 10;
        int mRadius2 = (int)((double)getWidth() / 2.8);
        int mRadius3 = (int)((double)getWidth() / 4.8);
        int mRadiusCenter = getWidth() / 12;

        RectF rectF1 = new RectF(mWidth/2- mRadius3, mHeight/2 - mRadius3, mWidth/2 + mRadius3, mHeight/2 + mRadius3);
        RectF rectF2 = new RectF(mWidth/2- mRadius2, mHeight/2 - mRadius2, mWidth/2 + mRadius2, mHeight/2 + mRadius2);
        RectF rectF3 = new RectF(mWidth/2- mRadius, mHeight/2 - mRadius, mWidth/2 + mRadius, mHeight/2 + mRadius);

        Paint grayOutline = new Paint();
        grayOutline.setStyle(Paint.Style.STROKE);
        grayOutline.setColor(Color.GRAY);
        grayOutline.setStrokeWidth(10);

        //outwards -> in
        //Third Ring
        canvas.drawArc(rectF3, 270, 90, true, gameBoard.getCurrentPaints()[3][0]);
        canvas.drawArc(rectF3, 270, 90, true, grayOutline);
        canvas.drawArc(rectF3, 180, 90, true, gameBoard.getCurrentPaints()[3][1]);
        canvas.drawArc(rectF3, 180, 90, true, grayOutline);
        canvas.drawArc(rectF3, 90, 90, true, gameBoard.getCurrentPaints()[3][2]);
        canvas.drawArc(rectF3, 90, 90, true, grayOutline);
        canvas.drawArc(rectF3, 0, 90, true, gameBoard.getCurrentPaints()[3][3]);
        canvas.drawArc(rectF3, 0, 90, true, grayOutline);

        //Second Ring
        canvas.drawArc(rectF2, 225, 90, true, gameBoard.getCurrentPaints()[2][0]);
        canvas.drawArc(rectF2, 225, 90, true, grayOutline);
        canvas.drawArc(rectF2, 135, 90, true, gameBoard.getCurrentPaints()[2][1]);
        canvas.drawArc(rectF2, 135, 90, true, grayOutline);
        canvas.drawArc(rectF2, 45, 90, true, gameBoard.getCurrentPaints()[2][2]);
        canvas.drawArc(rectF2, 45, 90, true, grayOutline);
        canvas.drawArc(rectF2, 315, 90, true, gameBoard.getCurrentPaints()[2][3]);
        canvas.drawArc(rectF2, 315, 90, true, grayOutline);

        //First Ring
        canvas.drawArc(rectF1, 270, 90, true, gameBoard.getCurrentPaints()[1][0]);
        canvas.drawArc(rectF1, 270, 90, true, grayOutline);
        canvas.drawArc(rectF1, 180, 90, true, gameBoard.getCurrentPaints()[1][1]);
        canvas.drawArc(rectF1, 180, 90, true, grayOutline);
        canvas.drawArc(rectF1, 90, 90, true, gameBoard.getCurrentPaints()[1][2]);
        canvas.drawArc(rectF1, 90, 90, true, grayOutline);
        canvas.drawArc(rectF1, 0, 90, true, gameBoard.getCurrentPaints()[1][3]);
        canvas.drawArc(rectF1, 0, 90, true, grayOutline);

        //Center
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadiusCenter, gameBoard.getCurrentPaints()[0][0]);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadiusCenter + 4, grayOutline);
    }

    private void chooseColours(){
        int[][] colours = new int[4][4];
        int[] gradient = new int[4]; //stores the colours of the correct gradient
        int[] gradientIndices = new int[4]; //stores the indices in the 2d array of the correct gradient path
                                                                //for example, {0, 2, 3, 2} means the answers are stored in [0][0], [1][2], [2][3], [3][2]
        Paint[][] paints = new Paint[4][4];

        //initialize paints
        for (int k = 0; k < 4; k++){
            for (int l = 0; l < 4; l++){
                paints[k][l] = new Paint();
            }
        }

        gradient = generateGradient();
        for (int i = 0; i < 4; i++){
            gameBoard.setOneGradient(gradient[i], i);
        }

        //fill the correct gradient path in random spots
        gradientIndices[0] = 0;
        colours[0][0] = gradient[0];

        gradientIndices[1] = (int) (Math.random()*4); //4 is not included
        colours[1][gradientIndices[1]] = gradient[1];

        if (gradientIndices[1] == 0) {
            gradientIndices[2] = (int) (Math.random() + 1);
            if (gradientIndices[2] == 1)
                gradientIndices[2] = 3; //if RNG produces 1, then become 3
        }
        else {
            gradientIndices[2] = (int) (Math.random() + gradientIndices[1]);
        }
        colours[2][gradientIndices[2]] = gradient[2];

        if (gradientIndices[2] == 3){
            gradientIndices[3] = (int) (Math.random() + 1);
            if (gradientIndices[3] == 1)
                gradientIndices[3] = 3; //if RNG produces 1, then become 3
        }
        else {
            gradientIndices[3] = (int) (Math.random() + gradientIndices[2] + 1);
        }
        colours[3][gradientIndices[3]] = gradient[3];

        Toast.makeText(getContext(), gradientIndices[0] + ", " +
                gradientIndices[1] + ", " +
                gradientIndices[2] + ", " +
                gradientIndices[3], Toast.LENGTH_SHORT).show();

        for (int i = 1; i < 4; i++){
            for (int k = 0; k < 4; k++){
                if (colours[i][k] != gradient[i]) //check if the colour matches the actual colour of that ring
                    colours[i][k] = fillRandomColour(i, gradient);
            }
        }

        for (int k = 0; k < 4; k++){
            for (int l = 0; l < 4; l++){
                paints[k][l].setColor((colours[k][l] | 0xFF000000));
                paints[k][l].setStyle(Paint.Style.FILL);
                paints[k][l].setAntiAlias(true);
            }
        }
        gameBoard.setOriginalPaints(paints);
        gameBoard.setCurrentPaints(paints);
    }

    //central methods
    private int[] generateGradient(){
        int[] colours = new int[4];
        int[] colour1RGB = new int[3]; //index 0 -> r, index 1 -> g, index 2 -> b
        int[] colour2RGB = new int[3];
        int[] colour3RGB = new int[3];
        int[] colour4RGB = new int[3];

        colours[0] = (int) (Math.random()*16777215);
        colour1RGB = splitCodeIntoRGB(colours[0]);

        colours[3] = (int) (Math.random()*16777215);
        colour4RGB = splitCodeIntoRGB(colours[3]);

        int distance = getGradientDistance();
        for (int i = 0; i < 3; i++){
            colour4RGB[i] = (int) (adjustToDistance(colour1RGB, colour4RGB, distance) * differenceOf(colour1RGB[i], colour4RGB[i]) + colour1RGB[i]);
        }

        colours[3] =  mergeRGBIntoCode(colour4RGB);

        for (int i = 0; i < 3; i++){
            colour2RGB[i] = (colour4RGB[i]-colour1RGB[i])/3 + colour1RGB[i];
        }
        for (int i = 0; i < 3; i++){
            colour3RGB[i] = ((colour4RGB[i]-colour1RGB[i])/3)*2 + colour1RGB[i];
        }

        colours[1] = mergeRGBIntoCode(colour2RGB);
        colours[2] = mergeRGBIntoCode(colour3RGB);

        return colours;
    }

    //NEEDS FIXING, THE GENERATED COLOURS ARE VERY SIMILAR FOR SOME REASON
    private int fillRandomColour(int ringIndex, int[] gradientPath){
        int colour;
        int[] gradient1RGB = new int[3];
        int[] gradient4RGB = new int[3];
        int[] gradient0RGB = new int[3];
        double[] onPlaneRGB = new double[3];
        int[] colourRGB = new int[3];

        gradient1RGB = splitCodeIntoRGB(gradientPath[0]);
        gradient4RGB = splitCodeIntoRGB(gradientPath[3]);
        gradient0RGB = splitCodeIntoRGB(gradientPath[ringIndex]);

        onPlaneRGB[0] = Math.random()*10;
        onPlaneRGB[1] = Math.random()*10;
        //use distance formula to create third coordinate with custom distance
        onPlaneRGB[2] = ((differenceOf(onPlaneRGB[0], gradient0RGB[0]) * differenceOf(gradient1RGB[0], gradient4RGB[0]) +
                (differenceOf(onPlaneRGB[1], gradient0RGB[1]) * differenceOf(gradient1RGB[1], gradient4RGB[1]))) /
                differenceOf(gradient1RGB[2], gradient4RGB[2])) + gradient0RGB[2];

        int radius = getRadius();
        for (int i = 0; i < 3; i++){
            colourRGB[i] = (int) (adjustToRadius(gradient0RGB, onPlaneRGB, radius) * differenceOf(gradient0RGB[i], onPlaneRGB[i]) + gradient0RGB[i]);
        }

        colour = mergeRGBIntoCode(colourRGB);
        return colour;
    }

    //sub-methods
    private int mergeRGBIntoCode(int[] codeRGB){
        int code;
        code = (codeRGB[0] << 16) | (codeRGB[1] << 8) | codeRGB[2];
        return code;
    }

    private int[] splitCodeIntoRGB(int code){
        int[] codeRGB = new int[3];

        codeRGB[2] = code & 0xFF;
        codeRGB[1] = (code >> 8) & 0xFF;
        codeRGB[0] = (code >> 16) & 0xFF;

        return codeRGB;
    }

    private double differenceOf(double x1, double x2){
        return x2 - x1;
    }

    private double adjustToDistance(int[] colour1RGB, int[] colour4RGB, int d){
        //creating a tolerance range from 80% of distance -> distance
        double factor = (Math.random()*d+(d*0.8)) / (Math.sqrt(Math.pow(differenceOf(colour1RGB[0], colour4RGB[0]), 2) +
                Math.pow(differenceOf(colour1RGB[1], colour4RGB[1]), 2) +
                Math.pow(differenceOf(colour1RGB[2], colour4RGB[2]), 2)));
        return factor;
    }

    private double adjustToRadius(int[] gradient0RGB, double[] onPlaneRGB, int r){
        //creating a tolerance range for radius from 20% of radius -> radius
        double factor = (Math.random()*r+(r*0.2)) / (Math.sqrt(Math.pow(differenceOf(gradient0RGB[0], onPlaneRGB[0]), 2) +
                Math.pow(differenceOf(gradient0RGB[1], onPlaneRGB[1]), 2) +
                Math.pow(differenceOf(gradient0RGB[2], onPlaneRGB[2]), 2)));
        return factor;
    }

    private int getRadius(){
        int roundNumber;

        if (gameBoard == null)
            roundNumber = 1;

        else
            roundNumber = gameBoard.getRoundNumber();

        switch (roundNumber){
            case 1: return 100;
            case 2: return 90;
            case 3: return 80;
            case 4: return 70;
            case 5: return 50;
            default: return 0; //if something isn't right, the colours will be very very close
        }
    }

    private int getGradientDistance(){
        int roundNumber;

        if (gameBoard == null)
            roundNumber = 1;

        else
            roundNumber = gameBoard.getRoundNumber();

        switch (roundNumber){
            case 1: return 100;
            case 2: return 120;
            case 3: return 140;
            case 4: return 160;
            case 5: return 180;
            default: return 10; //if something isn't right, the colours will be very very close
        }
    }

    public Board getBoard(){
        return gameBoard;
    }

    //-----ACCEPTING INPUT-----
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //super.onTouchEvent(event);

        int mWidth = getWidth();
        int mHeight = getHeight();
        int mRadius = getWidth() / 2 - 10;
        int mRadius2 = (int)((double)getWidth() / 2.8);
        int mRadius3 = (int)((double)getWidth() / 4.8);
        int mRadiusCenter = getWidth() / 12;

        int xStored = (int)event.getX();
        int yStored = (int)event.getY();

        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(inCircle(xStored, mWidth, yStored, mHeight, mRadiusCenter)){
                //center
                //nothing happens
            }

            else if(inCircle(xStored, mWidth, yStored, mHeight, mRadius3)) {

                if(xStored > mWidth / 2 && yStored < mHeight / 2){
                    //ring 1 Q1
                    respondToPress(1, 0);
                }

                else if(xStored < mWidth / 2 && yStored < mHeight / 2){
                    //ring 1 Q2
                    respondToPress(1, 1);
                }

                else if(xStored < mWidth / 2 && yStored > mHeight / 2){
                    //ring 1 Q3
                    respondToPress(1, 2);
                }

                else if(xStored > mWidth / 2 && yStored > mHeight / 2){
                    //ring 1 Q4
                    respondToPress(1, 3);
                }
            }

            else if(inCircle(xStored, mWidth, yStored, mHeight, mRadius2)) {
                if(xStored > mWidth / 2 && yStored < mHeight / 2) {
                    if(secondRing(xStored, mWidth, yStored, mHeight)){
                        //ring 2 right
                        respondToPress(2, 3);
                    }

                    else{
                        //ring 2 up
                        respondToPress(2, 0);
                    }
                }
                else if(xStored < mWidth / 2 && yStored < mHeight / 2) {
                    if(secondRing(xStored, mWidth, yStored, mHeight)){
                        //ring 2 left
                        respondToPress(2, 1);
                    }

                    else{
                        //ring 2 up
                        respondToPress(2, 0);
                    }
                }

                else if(xStored < mWidth / 2 && yStored > mHeight / 2) {
                    if(secondRing(xStored, mWidth, yStored, mHeight)){
                        //ring 2 left
                        respondToPress(2, 1);
                    }

                    else{
                        //ring 2 down
                        respondToPress(2, 2);
                    }
                }

                else if(xStored > mWidth / 2 && yStored > mHeight / 2) {
                    if(secondRing(xStored, mWidth, yStored, mHeight)){
                        //ring 2 right
                        respondToPress(2, 3);
                    }

                    else{
                        //ring 2 down
                        respondToPress(2, 2);
                    }
                }
            }

            else if(inCircle(xStored, mWidth, yStored, mHeight, mRadius)) {
                if(xStored > mWidth / 2 && yStored < mHeight / 2){
                    //ring 3 q1
                    respondToPress(3, 0);
                }

                else if(xStored < mWidth / 2 && yStored < mHeight / 2){
                    //ring 3 q2
                    respondToPress(3, 1);
                }

                else if(xStored < mWidth / 2 && yStored > mHeight / 2){
                    //ring 3 q3
                    respondToPress(3, 2);
                }

                else if(xStored > mWidth / 2 && yStored > mHeight / 2){
                    //ring 3 q4
                    respondToPress(3, 3);
                }
            }

            else {}
        }
        return true;
    }

    public static boolean inCircle(int xStored, int mWidth, int yStored, int mHeight, int mRadius) {
        if ((xStored - (mWidth/2)) * (xStored - (mWidth/2)) + (yStored - (mHeight/2)) * (yStored - (mHeight/2)) <= mRadius * mRadius)
            return true;
        else
            return false;
    }

    public static boolean secondRing(int xStored, int mWidth, int yStored, int mHeight) {
        if (Math.abs((xStored - (mWidth/2)) * (xStored - (mWidth/2))) / Math.abs((yStored - (mHeight/2)) * (yStored - (mHeight/2))) >= 1)
            return true;
        else
            return false;
    }

    private void respondToPress(int ring, int indexInRing){
        if (gameBoard.getCurrentPaints()[ring][0].getColor() == Color.GRAY || gameBoard.getCurrentPaints()[ring][1].getColor() == Color.GRAY ||
                gameBoard.getCurrentPaints()[ring][2].getColor() == Color.GRAY || gameBoard.getCurrentPaints()[ring][3].getColor() == Color.GRAY) {
            unfadeOthers(ring);
        }
        else {
            fadeOthersInRing(ring, indexInRing);

            //count number of gray areas
            int numOfGray = 0;
            for (int i = 1; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (gameBoard.getCurrentPaints()[i][j].getColor() == Color.GRAY)
                        numOfGray++;
                }
            }

            //if a full path is selected, check to see if it is the answer
            if (numOfGray == 9) {
                Paint[] selectedGradient = new Paint[4];

                //initialize local ringsGradient
                for (int k = 0; k < 4; k++) {
                    selectedGradient[k] = new Paint();
                }

                //store selected colours in temporary array
                selectedGradient[0].setColor(gameBoard.getCurrentPaints()[0][0].getColor());
                for (int i = 1; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (gameBoard.getCurrentPaints()[i][j].getColor() != Color.GRAY)
                            selectedGradient[i].setColor(gameBoard.getCurrentPaints()[i][j].getColor());
                    }
                }

                int count = 0;
                //check temporary array
                for (int i = 0; i < 4; i++) {
                    if ((gameBoard.getRingsGradient()[i].getColor() & 0xFFFFFF) == (selectedGradient[i].getColor() & 0xFFFFFF)) {
                        count++;
                    }
                }
                if (count == 4) {
                    Toast.makeText(getContext(), "Congratulations, that was the correct gradient!", Toast.LENGTH_SHORT).show();
                    ((ClassicGameActivity) getContext()).nextRound(getBoard());
                    ((ViewGroup) this.getParent()).removeView(this);
                    //send information back to fragment to start next round
                }
            }
        }
    }

    private void fadeOthersInRing(int ring, int indexInRing){
        for (int i = 0; i < 4; i++) {
            if (i != indexInRing)
                gameBoard.setOnePaint(Color.GRAY, ring, i);
        }
        postInvalidate();
    }

    private void unfadeOthers(int ring) {
        for (int i = 0; i < 4; i++){
            gameBoard.setOnePaint(gameBoard.getOriginalPaints()[ring][i].getColor(), ring, i);
        }
        postInvalidate();
    }
}