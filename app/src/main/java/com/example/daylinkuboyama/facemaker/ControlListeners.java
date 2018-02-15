package com.example.daylinkuboyama.facemaker;

import android.support.annotation.IdRes;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * class ControlListeners
 *
 * this class is where the button, seekBar, radioGroup, and Spinner
 * are implemented. Able to set and adjust seekBars, click on radio buttons,
 * open and choose spinner, and press button. Changes happen
 * depending on user input.
 *
 * @author daylinkuboyama
 */

    public class ControlListeners implements View.OnClickListener,
            SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener,
            AdapterView.OnItemSelectedListener {

        //access to the Face surface that I need to redraw every time
        //user changes color values/accessory
        Face reDraw;

        Spinner accessoriesSpinner;

        //three color seekBars
        SeekBar red;
        SeekBar green;
        SeekBar blue;

        //values of each color
        TextView redVal;
        TextView greenVal;
        TextView blueVal;

        RadioGroup group;
        RadioButton skin, eyes, ears, accessory;


    /**
     * parameters for constructor are only factors I want to
     * change throughout the program
     *
     * @param surface how and where the face is drawn
     * @param spinner where the accessory options are presented
     * @param red red value seekBar
     * @param green green value seekBar
     * @param blue blue value seekBar
     * @param redVal textView with red value
     * @param greenVal textView with green value
     * @param blueVal textView with blue value
     * @param group radioButtons altogether
     * @param skin skin radioButton
     * @param eyes eyes radioButton
     *@param ears ears radioButton
     *@param accessory accessory radioButton
     */
    public ControlListeners(Face surface, Spinner spinner,
                            SeekBar red, SeekBar green, SeekBar blue,
                            TextView redVal, TextView greenVal, TextView blueVal,
                            RadioGroup group, RadioButton skin, RadioButton eyes,
                            RadioButton ears, RadioButton accessory) {

        this.reDraw = (Face) surface;
        this.accessoriesSpinner = spinner;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.redVal = redVal;
        this.greenVal = greenVal;
        this.blueVal = blueVal;
        this.group = group;
        this.skin = skin;
        this.eyes = eyes;
        this.ears = ears;
        this.accessory = accessory;

        //method to replicate colors onto progress bar
        setProgressBar();
        setSpinner ();
        //initially set
    }


    @Override
    /**
     * when random button is clicked, the colors are re-randomized
     * and the surface view is redrawn to paint over the previous surface view
     *
     * @param v the surfaceView (reDraw)
     */
    public void onClick (View v) {

         //re-randomizes colors
         reDraw.randomize();
         /**
         * External Citation
         * Date: 11 February 2018
         * Problem: Couldn't figure out how to redraw surface
         * Resource: https://stackoverflow.com/questions/14850270/repaint-in-android
         * Solution: told me about the invalidate method, which replicates
         *              Java's repaint method
         **/
         reDraw.invalidate();

         //make color seekBars represent current random values of colors
         //of feature selected in radio button
         setProgressBar();
         //sets spinner to represent which accessory is on the robot
         setSpinner ();
    }


    /**
     * When user clicks on specific accessory in spinner, position in Spinner
     * array clicked will be checked if equal to accessory in accessoryArray,
     * and face will be redrawn
     *
     * @param position (the only parameter I care about) compares position in
     * spinner user clicked to position of accessory in accessoryArray
     */

    /**
     * External Citation
     * Date: 11 February 2018
     * Problem: Needed to learn about Spinner's methods in order to implement interface
     * Resource: https://developer.android.com/reference/android/widget/AdapterView.
     *              OnItemSelectedListener.html#onItemSelected(android.widget.
     *              AdapterView<?>,%20android.view.View,%20int,%20long)
     * Solution: gave me the methods, parameters, and defined parameters
     **/
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //checks what position(accessory)'s method to call to draw
        if (position ==0) /*antenna*/{
            reDraw.setAccessory(position); //tells Face which accessory user wants
            reDraw.invalidate(); //repaints the surface view
        }
        else if (position ==1) /*robot cap*/{
            reDraw.setAccessory(position);
            reDraw.invalidate();
        }
        else /*pompoms*/{
            reDraw.setAccessory(position);
            reDraw.invalidate();
        }
    }


    @Override
    /**
     * method determines which textViews to update with current color info
     * on seekBars of feature when changing feature type with radioButton.
     *
     * (the only parameters I care about)
     * @param seekBar used to check which seekBar's color value needs to be changed
     * @param progress current value of seekBar
     */
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        //represents the button currently checked
        int checkedId = group.getCheckedRadioButtonId();

        //changes the color value text to match the progress
        //based on name of seekBar
        if (seekBar == red) {
            /**
             * Date: 11 February 2018
             * Problem: App was crashing when seekbars were touched on screen
             * Resource: https://stackoverflow.com/questions/8956218/android-seekbar
             *              -setonseekbarchangelistener
             * Solution: resource reminded me of the ""+ syntax before the progress
             */
            redVal.setText(""+progress);
        }
        else if (seekBar == green) {
            greenVal.setText(""+progress);
        }
        else {
            blueVal.setText(""+progress);
        }

        int color; //color that is currently created using values on seekBar
        //sets color using method based on values
        color = Color.rgb(red.getProgress(),green.getProgress(),blue.getProgress());

        //calls set method in face accordingly to button selected in order
        //to adjust colors of specific feature user wants to change
        if (checkedId == R.id.radioButtonSkin) {
            reDraw.setSkinColor(color);
            reDraw.invalidate();
        }
        else if (checkedId == R.id.radioButtonEyes) {
            reDraw.setEyeColor(color);
            reDraw.invalidate();
        }
        else if (checkedId == R.id.radioButtonEars){
            reDraw.setEarColor(color);
            reDraw.invalidate();
        }
        else /*accessory*/{
            reDraw.setAccessoryColor(color);
            reDraw.invalidate();
        }

    }


    @Override
     //method checks the radio button that is selected out of the group.
     //Then sets feature accordingly to the colors of seekBars.
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        setProgressBar();
    }


    /**
     * Sets RGB seekBars according to its components extracted using
     * RGB color extraction methods
     *
     * @param color value in int form
     */
    public void setColorOnProgressBar (int color) {

        //extracts color values
        int redComponent = Color.red(color);
        int greenComponent = Color.green(color);
        int blueComponent = Color.blue(color);

        //sets seekBars to its color component value
        red.setProgress(redComponent);
        green.setProgress(greenComponent);
        blue.setProgress(blueComponent);
    }


    /**
     * updates spinner to represent accessory on robot when initialized and/or
     * if random button is pressed
     */
    public void setSpinner () {
        int currSpinner = reDraw.getAccessory();
        /**
         * Date: 13 February 2018
         * Problem: did not know how to make code set Spinner's display
         *           to the accessory on robot
         * Resource: https://developer.android.com/reference/android/widget
         *           /AbsSpinner.html#setSelection(int)
         * Solution: Told me about setSelection method that sets Spinner
         *           display to equal accessory (current value at equal
         *           spinner index)
         */
        accessoriesSpinner.setSelection(currSpinner);
    }


    /**
     * Method to replicate RGB color values onto corresponding seekBars
     * seekBars will change to match colors of feature type
     * example: When radioButton is changed from skin to ear, seekBars will change to
     * reflect RGB values of ear.
     */
    public void setProgressBar () {
        int color;

        //represents the button currently checked
        int checkedId = group.getCheckedRadioButtonId();

        //gets current progress bar color values for specific button
        if (checkedId == R.id.radioButtonSkin) {
            color = reDraw.getSkinColor();
        }
        else if (checkedId == R.id.radioButtonEyes) {
            color = reDraw.getEyeColor();
        }
        else if (checkedId == R.id.radioButtonEars) {
            color = reDraw.getEarColor();
         }
         else /*accessory*/  {
            color = reDraw.getAccessoryColor();
        }

        //sets color after all ids checked
        setColorOnProgressBar(color);

        //sets color value textViews
        redVal.setText(""+Color.red(color));
        greenVal.setText(""+Color.green(color));
        blueVal.setText(""+Color.blue(color));
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //does nothing.
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //does nothing.
    }
    public void onNothingSelected(AdapterView<?> parent) {
        //does nothing.
    }

}