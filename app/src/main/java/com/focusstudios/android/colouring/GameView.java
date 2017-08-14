package com.focusstudios.android.colouring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.focusstudios.android.colouring.activities.GameActivity;

public class GameView extends View {

    Board gameBoard = new Board();
    private int[] radii = new int[4]; //radii of the rings

    public GameView(Context context, int roundNumber, Board board) {
        super(context);

        //create a new board if a null board is imported
        if (board == null) {
            gameBoard = new Board();
            chooseColours();
        }
        else
            gameBoard = board;

        gameBoard.setRoundNumber(roundNumber);
        postInvalidate();
    }

    //-----INSERTING THE COLOURS-----
    @Override
    protected void onDraw(Canvas canvas) {
        //radii for each ring from in to out to make them evenly spaced
        radii[0] = getWidth() / 12;
        radii[1] = (int) ((double) getWidth() / 4.8);
        radii[2] = (int) ((double) getWidth() / 2.8);
        radii[3] = getWidth() / 2 - 10;

        //initialize gray outline for all objects
        Paint grayOutline = new Paint();
        grayOutline.setStyle(Paint.Style.STROKE);
        grayOutline.setColor(Color.GRAY);
        grayOutline.setStrokeWidth(10);

        RectF[] rectFs = new RectF[3];
        for (int i = 0; i < 3; i++){
            rectFs[i] = new RectF(getWidth()/2 - radii[i+1], getHeight()/2 - radii[i+1], getWidth()/2 + radii[i+1], getHeight()/2 + radii[i+1]);
        }

        //outer ring
        for (int i = 0; i < 4; i++){
            canvas.drawArc(rectFs[2], 270 - 90*i, 90, true, gameBoard.getCurrentPaints()[3][i]);
            canvas.drawArc(rectFs[2], 270 - 90*i, 90, true, grayOutline);
        }
        //middle ring
        for (int i = 0; i < 4; i++){
            canvas.drawArc(rectFs[1], 315 - 90*i, 90, true, gameBoard.getCurrentPaints()[2][i]);
            canvas.drawArc(rectFs[1], 315 - 90*i, 90, true, grayOutline);
        }
        //inner ring
        for (int i = 0; i < 4; i++){
            canvas.drawArc(rectFs[0], 270 - 90*i, 90, true, gameBoard.getCurrentPaints()[1][i]);
            canvas.drawArc(rectFs[0], 270 - 90*i, 90, true, grayOutline);
        }
        //center
        canvas.drawCircle(getWidth()/2, getHeight()/2, radii[0], gameBoard.getCurrentPaints()[0][0]);
        canvas.drawCircle(getWidth()/2, getHeight()/2, radii[0] + 4, grayOutline);
    }

    private void chooseColours(){
        int[][] colours = new int[4][4];
        int[] gradient; //stores the colours of the correct gradient
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

        //randomly generates a gradient path using the concept of circular arrays
        gradientIndices[0] = 0;
        gradientIndices[1] = (int) (Math.random()*4); //RNG produces 0, 1, 2, or 3
        gradientIndices[2] = (gradientIndices[1] + (int) (Math.random()*2)) % 4; //(gradientIndices[1] + (0 or 1)) mod 4 to produce ring 3's index
        gradientIndices[3] = (gradientIndices[2]  - (int) (Math.random()*2) + 4) % 4; //(gradientIndices[2] - (0 or 1)) mod 4 to produce ring 4's index

        //fill the randomly generated path with the previously generated gradient
        for (int i = 0; i < 4; i++){
            colours[i][gradientIndices[i]] = gradient[i];
        }

        for (int i = 1; i < 4; i++){
            for (int k = 0; k < 4; k++){
                if (colours[i][k] != gradient[i]) //check if the colour matches the actual colour of that ring
                    colours[i][k] = fillRandomColour(gradient, i);
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

    //primary methods
    private int[] generateGradient(){
        int[] colours = new int[4];
        int[] colour1RGB = new int[3]; //index 0 -> r, index 1 -> g, index 2 -> b
        int[] colour2RGB = new int[3];
        int[] colour3RGB = new int[3];
        int[] colour4RGB = new int[3];

        double chance = Math.random(); //50/50 randomly determine if colour1RGB is the first colour or the end colour
            if (chance < 0.5) {
                colour1RGB = chooseFirstColour();
                colour4RGB = chooseEndColour(colour1RGB);
            }
            else {
                colour4RGB = chooseFirstColour();
                colour1RGB = chooseEndColour(colour4RGB);
            }
        colours[0] = mergeRGBToCode(colour1RGB);
        colours[3] = mergeRGBToCode(colour4RGB);

        for (int i = 0; i < 3; i++){ //interpolate the inner 2 colours of the gradient
            colour2RGB[i] = (colour4RGB[i]-colour1RGB[i])/3 + colour1RGB[i];
            colour3RGB[i] = ((colour4RGB[i]-colour1RGB[i])/3)*2 + colour1RGB[i];
        }
        colours[1] = mergeRGBToCode(colour2RGB);
        colours[2] = mergeRGBToCode(colour3RGB);

        return colours;
    }

    private int[] chooseFirstColour(){
        int[] colour1 = new int[3];
        for (int i = 0; i < 3; i++){
            colour1[i] = (int) (Math.random() * (255 - getGradientDistance()));
        }
        return colour1;
    }

    private int[] chooseEndColour(int[] colour1){
        int[] colour4 = new int[3];
        for (int i = 0; i < 3; i++) {
            colour4[i] = colour1[i] + getGradientDistance();
        }
        return colour4;
    }

    private int fillRandomColour(int[] gradientPath, int ringIndex){
        int[] c1RGB = splitCodeToRGB(gradientPath[0]);
        int[] c4RGB = splitCodeToRGB(gradientPath[3]);
        int[] cARGB = splitCodeToRGB(gradientPath[ringIndex]);
        int[] cBRGB = new int[3];
        double[] cCRGB = new double[3];
        int ringDifference = getRingDifference();
        double t1, t2, t3, t4, t5; //reusable placeholders for further calculations

        t1 = (Math.random()*2 - 1) * ringDifference;
        t2 = (Math.random()*2 - 1) * ringDifference;
        t3 = Math.sqrt(t1*t1 + t2*t2); //get distance between t1 and t2
        cCRGB[0] = t1/t3 * ringDifference + cARGB[0];
        cCRGB[1] = t2/t3 * ringDifference + cARGB[1];

        t1 = cARGB[0]-cCRGB[0];
        t2 = c4RGB[0]-c1RGB[0];
        t3 = cARGB[1]-cCRGB[1];
        t4 = c4RGB[1]-c1RGB[1];
        t5 = c4RGB[2]-c1RGB[2];
        cCRGB[2] = (t1*t2 + t3*t4)/t5 + cARGB[2];

        t1 = cCRGB[0]-cARGB[0];
        t2 = cCRGB[1]-cARGB[1];
        t3 = cCRGB[2]-cARGB[2];
        double normFactor = getTolerance() / Math.sqrt(t1*t1 + t2*t2 + t3*t3);

        for (int i = 0; i < 3; i++){
            cBRGB[i] = (int) limitRGB(normFactor * (cCRGB[i] - cARGB[i]) + cARGB[i]);
        }

        //for debugging purposes
        /*System.out.println("1: " + c1RGB[0] + " " + c1RGB[1] + " "+ c1RGB[2]);
        System.out.println("4: " + c4RGB[0] + " " + c4RGB[1] + " "+ c4RGB[2]);
        System.out.println("A: " + cARGB[0] + " " + cARGB[1] + " "+ cARGB[2]);
        System.out.println("B: " + cBRGB[0] + " " + cBRGB[1] + " "+ cBRGB[2]);
        System.out.println("C: " + cCRGB[0] + " " + cCRGB[1] + " "+ cCRGB[2]);*/

        return mergeRGBToCode(cBRGB);
    }

    private double limitRGB(double RGB){
        RGB = RGB < 0 ? 0 : RGB;
        RGB = RGB > 255 ? 255 : RGB;
        return RGB;
    }

    //sub-methods
    private int mergeRGBToCode(int[] codeRGB){
        int code;
        code = (codeRGB[0] << 16) | (codeRGB[1] << 8) | codeRGB[2];
        return code;
    }

    private int[] splitCodeToRGB(int code){
        int[] codeRGB = new int[3];

        codeRGB[2] = code & 0xFF;
        codeRGB[1] = (code >> 8) & 0xFF;
        codeRGB[0] = (code >> 16) & 0xFF;

        return codeRGB;
    }

    private int getGradientDistance() {
        int roundNumber = getRoundNumberFromGame();
        switch (roundNumber) {
            case 1: return 90;
            case 2: return 90;
            case 3: return 90;
            case 4: return 100;
            case 5: return 100;
            case 6: return 100;
            case 7: return 110;
            case 8: return 110;
            case 9: return 120;
            case 10: return 120;
            default: return 10; //if something isn't right, the colours will be very very close
        }
    }

    private int getRingDifference(){
        int roundNumber = getRoundNumberFromGame();
        switch (roundNumber){
            case 1: return 10;
            case 2: return 10;
            case 3: return 9;
            case 4: return 9;
            case 5: return 8;
            case 6: return 8;
            case 7: return 7;
            case 8: return 7;
            case 9: return 6;
            case 10: return 5;
            default: return 0; //if something isn't right, the colours will be very very close
        }
    }

    private int getTolerance(){
        int roundNumber = getRoundNumberFromGame();
        switch (roundNumber){
            case 1: return 60;
            case 2: return 60;
            case 3: return 40;
            case 4: return 40;
            case 5: return 30;
            case 6: return 30;
            case 7: return 25;
            case 8: return 25;
            case 9: return 20;
            case 10: return 20;
            default: return 0; //if something isn't right, the colours will be very very close
        }
    }

    private int getRoundNumberFromGame(){
        int roundNumber;

        if (gameBoard == null)
            roundNumber = 1;
        else
            roundNumber = gameBoard.getRoundNumber();

        return roundNumber;
    }

    public Board getBoard(){
        return gameBoard;
    }

    //-----ACCEPTING INPUT-----
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int xStored = (int) event.getX();
        int yStored = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (inCircle(xStored, getWidth(), yStored, getHeight(), radii[0])){
                //center -> nothing happens
            }
            else if (inCircle(xStored, getWidth(), yStored, getHeight(), radii[1])) {
                if (xStored > getWidth()/2 && yStored < getHeight()/2)
                    respondToPress(1, 0); //inner ring Q1
                else if (xStored < getWidth()/2 && yStored < getHeight()/2)
                    respondToPress(1, 1); //inner ring Q2
                else if (xStored < getWidth()/2 && yStored > getHeight()/2)
                    respondToPress(1, 2); //inner ring Q3
                else if (xStored > getWidth()/2 && yStored > getHeight()/2)
                    respondToPress(1, 3); //inner ring Q4
            }
            else if (inCircle(xStored, getWidth(), yStored, getHeight(), radii[2])) {
                if (xStored > getWidth()/2 && yStored < getHeight()/2) {
                    if (secondRing(xStored, getWidth(), yStored, getHeight()))
                        respondToPress(2, 0); //middle ring right
                    else
                        respondToPress(2, 1); //middle ring up
                }
                else if (xStored < getWidth()/2 && yStored < getHeight()/2) {
                    if (secondRing(xStored, getWidth(), yStored, getHeight()))
                        respondToPress(2, 2); //middle ring left
                    else
                        respondToPress(2, 1); //middle ring up
                }
                else if (xStored < getWidth()/2 && yStored > getHeight()/2) {
                    if (secondRing(xStored, getWidth(), yStored, getHeight()))
                        respondToPress(2, 2); //middle ring left
                    else
                        respondToPress(2, 3); //middle ring down
                }
                else if (xStored > getWidth()/2 && yStored > getHeight()/2) {
                    if (secondRing(xStored, getWidth(), yStored, getHeight()))
                        respondToPress(2, 0); //middle ring right
                    else
                        respondToPress(2, 3); //middle ring down
                }
            }
            else if (inCircle(xStored, getWidth(), yStored, getHeight(), radii[3])) {
                if (xStored > getWidth()/2 && yStored < getHeight()/2)
                    respondToPress(3, 0); //outer ring Q1
                else if (xStored < getWidth()/2 && yStored < getHeight()/2)
                    respondToPress(3, 1); //outer ring Q2
                else if (xStored < getWidth()/2 && yStored > getHeight()/2)
                    respondToPress(3, 2); //outer ring Q3
                else if (xStored > getWidth()/2 && yStored > getHeight()/2)
                    respondToPress(3, 3); //outer ring Q4
            }
        }
        return true;
    }

    public static boolean inCircle(int xStored, int width, int yStored, int height, int radius){
        return (((xStored - (width/2)) * (xStored - (width/2)) + (yStored - (height/2)) * (yStored - (height/2))) <= (radius * radius));
    }

    public static boolean secondRing(int xStored, int width, int yStored, int height){
        return ((Math.abs((xStored - (width/2)) * (xStored - (width/2))) / Math.abs((yStored - (height/2)) * (yStored - (height/2)))) >= 1);
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
                    if ((gameBoard.getGradientIndices()[i].getColor() & 0xFFFFFF) == (selectedGradient[i].getColor() & 0xFFFFFF)) {
                        count++;
                    }
                }
                if (count == 4) {
                    ((GameActivity) getContext()).nextRound();
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
        ((GameActivity) getContext()).playSFX();
        postInvalidate();
    }

    private void unfadeOthers(int ring) {
        for (int i = 0; i < 4; i++){
            gameBoard.setOnePaint(gameBoard.getOriginalPaints()[ring][i].getColor(), ring, i);
        }
        ((GameActivity) getContext()).playSFX();
        postInvalidate();
    }
}
