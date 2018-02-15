package com.example.daylinkuboyama.facemaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.Random;

/**
 * class Face
 *
 * this class is the structure of the face on the SurfaceView. Has both
 * functionality to draw face when app is initialized, randomized, or based on
 * user input. Able to randomize colors and set them randomly to different
 * features of face.
 *
 * @author daylinkuboyama
 */

public class Face extends SurfaceView {

    //these ints all represent the color value of each feature
    private int skinColor;
    private int eyeColor;
    private int earColor;
    private int accessoryColor;

    //int value of accessory in accessories array
    private int accessory;

    //definitions. tells correlation between accessory and numerical val
    public static final int ANTENNA = 0;
    public static final int CAP = 1;
    public static final int POMPOMS = 2;


    //constructors all have initialize in it
    public Face(Context context) {
        super(context);
        initialize();
    }
    public Face(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }
    public Face(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }


    /**
     * only called once when app first opens. Allows canvas to draw, and calls
     * randomize method to start off program with a random robot face on screen
     * //is initially randomized because user is not able to input yet
     */
    private void initialize() {
        this.setWillNotDraw(false);
        randomize();
    }


    /**
     * calls the randColor method to generate each feature's own random color
     * and determines which random accessory will be drawn on robot
     */
    public void randomize() {

        skinColor = randColor();
        eyeColor = randColor();
        earColor = randColor();
        accessoryColor = randColor();

        //randomly sets the accessory color only when random button is pressed
        Random rand = new Random();
        accessory = rand.nextInt(3);
    }


    /**
     * creates random RGB values, combine values into one color,
     * and returns that color represented as int
     *
     * @return int value of the random color (combined RGB)
     */
    public int randColor() {

        Random rand = new Random();

        int red = rand.nextInt(199) + 56;
        int green = rand.nextInt(199) + 56;
        int blue = rand.nextInt(199) + 56;

        //method to return numerical color value
        return (Color.rgb(red, green, blue));
    }


    //sets the accessory in Face equal to the current array index for Spinner
    //in ControlListeners class. allows face to draw the accessory user selects
    public void setAccessory(int val) {
        this.accessory = val;
    }


    //gets and sets colors of different components of face in order
    //to share info from ControlListeners class
    public void setSkinColor(int val) {
        this.skinColor = val;
    }
    public void setEyeColor(int val) {
        this.eyeColor = val;
    }
    public void setEarColor(int val) {
        this.earColor = val;
    }
    public void setAccessoryColor(int val) {
        this.accessoryColor = val;
    }
    public int getAccessory() {
        return accessory;
    }
    public int getSkinColor() {
        return skinColor;
    }
    public int getEyeColor() {
        return eyeColor;
    }
    public int getEarColor() {
        return earColor;
    }
    public int getAccessoryColor() {
        return accessoryColor;
    }


    @Override
    /**
     * draws parts of robot on surface view
     * coordinates of robot's parts never change, always drawn in same place
     * Knows which accessory to draw
     *
     * @param c canvas
     */
    public void onDraw(Canvas c) {

        //creating color and drawing base of face
        Paint skin = new Paint();
        skin.setColor(skinColor);
        c.drawRect(570, 500, 1200, 1200, skin);

        //creating color and drawing base of ears
        Paint ear = new Paint();
        ear.setColor(earColor);
        c.drawRect(470, 700, 570, 900, ear); //innerR
        c.drawRect(420, 750, 470, 850, ear); //outerR
        c.drawRect(1200, 700, 1300, 900, ear); //innerL
        c.drawRect(1300, 750, 1350, 850, ear); //outerL

        //creating color and drawing eyes
        Paint eye = new Paint();
        eye.setColor(eyeColor);
        c.drawCircle(750, 750, 100, eye);
        c.drawCircle(1000, 750, 100, eye);
        //pupils
        Paint innerEyeColor = new Paint(Color.BLACK);
        c.drawCircle(750, 750, 50, innerEyeColor);
        c.drawCircle(1000, 750, 50, innerEyeColor);


        /**
         * determines which accessory to draw based on value
         * in setAccessory method
         */
        if (accessory == ANTENNA) {
            drawAntenna(c);
        } else if (accessory == CAP) {
            drawRobotCap(c);
        } else {
            drawPomPoms(c);
        }

    }


    //these accessories methods are only referred to from onDraw method

    /**
     * draws antenna when called from onDraw
     *
     * @param c canvas
     */
    public void drawAntenna(Canvas c) {

        Paint antenna = new Paint();
        antenna.setColor(accessoryColor);
        c.drawRect(870, 200, 900, 500, antenna);
        c.drawRect(800, 300, 970, 320, antenna);
        c.drawRect(825, 340, 955, 360, antenna);
        c.drawCircle(885, 220, 60, antenna);
    }

    /**
     * draws cap when called from onDraw
     *
     * @param c canvas
     */
    public void drawRobotCap(Canvas c) {

        Paint cap = new Paint();
        cap.setColor(accessoryColor);
        c.drawRect(725, 425, 1050, 500, cap); //base
        c.drawRect(800, 375, 975, 425, cap);
        c.drawRect(850, 325, 925, 375, cap);
        c.drawRect(883, 250, 890, 375, cap);
        c.drawCircle(885, 250, 35, cap);

    }

    /**
     * draws pompoms when called from onDraw
     *
     * @param c canvas
     */
    public void drawPomPoms(Canvas c) {

        Paint pom = new Paint();
        pom.setColor(accessoryColor);
        c.drawCircle(750, 300, 80, pom); //left pom
        c.drawRect(740, 300, 760, 500, pom);
        c.drawCircle(1000, 300, 80, pom); //right pom
        c.drawRect(990, 300, 1010, 500, pom);
    }
}