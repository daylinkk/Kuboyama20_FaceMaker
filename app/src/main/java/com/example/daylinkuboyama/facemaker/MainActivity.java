package com.example.daylinkuboyama.facemaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * class MainActivity
 *
 *creates instance of ControlListeners class in order to register
 * widgets with corresponding listeners
 *
 * @author daylinkuboyama
 */

public class MainActivity extends AppCompatActivity {

     //array of accessories for spinner; are referenced in other classes
     //using numerical values
    public String [] accessories =
            { "Antenna" , "Robot Cap" , "Pom poms" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates reference to listener class, and acts when widgets are used
        //only send widgets that need to be updated/changed
        ControlListeners listener =
                new ControlListeners((Face)findViewById(R.id.surfaceViewFace),
                        (Spinner)findViewById(R.id.spinnerAccessoryChooser),
                        (SeekBar)findViewById(R.id.seekBarRed),
                        (SeekBar)findViewById(R.id.seekBarGreen),
                        (SeekBar)findViewById(R.id.seekBarBlue),
                        (TextView)findViewById(R.id.textViewRedChanger),
                        (TextView)findViewById(R.id.textViewGreenChanger),
                        (TextView)findViewById(R.id.textViewBlueChanger),
                        (RadioGroup)findViewById(R.id.radioGroup),
                        (RadioButton)findViewById(R.id.radioButtonSkin),
                        (RadioButton)findViewById(R.id.radioButtonEars),
                        (RadioButton)findViewById(R.id.radioButtonEyes),
                        (RadioButton)findViewById(R.id.radioButtonAccessory));

        //register listener to radio group and buttons
        RadioGroup group = (RadioGroup)findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(listener);

        //register listener to seekBars
        SeekBar red = (SeekBar) findViewById(R.id.seekBarRed);
        red.setOnSeekBarChangeListener(listener);
        SeekBar green = (SeekBar) findViewById(R.id.seekBarGreen);
        green.setOnSeekBarChangeListener(listener);
        SeekBar blue = (SeekBar) findViewById(R.id.seekBarBlue);
        blue.setOnSeekBarChangeListener(listener);


        //register listener to spinner
        ArrayAdapter <String> accessoriesAdapter =
                new ArrayAdapter<String> (this, android.R.layout.
                                        simple_spinner_dropdown_item, accessories);
        Spinner accessories = (Spinner) findViewById(R.id.spinnerAccessoryChooser);
        accessories.setAdapter(accessoriesAdapter);
        accessories.setOnItemSelectedListener(listener);


        //register listener to button
        Button randButton = (Button)findViewById(R.id.buttonRandom);
        randButton.setOnClickListener(listener);
    }
}
