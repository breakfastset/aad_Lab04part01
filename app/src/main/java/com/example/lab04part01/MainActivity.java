package com.example.lab04part01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String KEY_MAKE = "keyMake";
    public static final String KEY_YEAR = "keyYear";
    public static final String KEY_COLOR = "keyColor";
    public static final String KEY_NOTE = "keyNote";

    public static final String KEY_FUEL = "keyFuel";
    public static final String KEY_NEW = "keyNew";
    public static final String KEY_RIGHT_HAND = "keyRightHand";


    private static final int REQUEST_EDIT = 1234;

    private static final int REQUEST_DOWNLOAD = 5678;

    private Spinner spinnerMaker;  // replace editMake
    private EditText editTextYear;
    private RadioGroup radioGroupColour;  // replace editColour
    private EditText editTextNote;

    private Switch switchFuel;
    private CheckBox checkBoxNew;
    private CheckBox checkBoxRightHand;
    private Button buttonImage;

    private String[] arrayCarMaker;
    private String make;


    @Override
    protected void onCreate(Bundle savedInstanceState) {  // 1st method to be called
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerMaker = findViewById(R.id.spinnerMake);
        switchFuel = findViewById(R.id.switchFuel);
        editTextYear = findViewById(R.id.inputYear);
        radioGroupColour = findViewById(R.id.radioColour);
        checkBoxNew = findViewById(R.id.isNew);
        checkBoxRightHand = findViewById(R.id.isRightHand);
        buttonImage = findViewById(R.id.buttonImage);
        editTextNote = findViewById(R.id.inputNote);

        // carMaker is an array of car makes
        arrayCarMaker = getResources().getStringArray(R.array.car_maker);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,
                R.array.car_maker, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaker.setAdapter(adapter);
        spinnerMaker.setOnItemSelectedListener(this);
    }


    public void onGoEditClick(View view) {
        Intent intentEdit = new Intent(this, NoteEditingActivity.class);
        startActivityForResult(intentEdit, REQUEST_EDIT);
    }

    public void onGoDisplayClick(View view) {
        Intent intentDisplay = new Intent();
        intentDisplay.setAction("com.example.lab04part01.ThirdActivity");

        // for make, fuel, year
        intentDisplay.putExtra(KEY_MAKE, make);
        intentDisplay.putExtra(KEY_FUEL, switchFuel.isChecked());
        intentDisplay.putExtra(KEY_YEAR, Integer.parseInt(editTextYear.getText().toString()));

        Bundle bundle = new Bundle();

        // for colour selection
        String colour;
        int selectedId = radioGroupColour.getCheckedRadioButtonId();
        RadioButton radioColourButton = findViewById(selectedId);
        colour = radioColourButton.getText().toString();
        bundle.putString(KEY_COLOR, colour);

        // for check boxes (option)
        bundle.putBoolean(KEY_NEW, checkBoxNew.isChecked());
        bundle.putBoolean(KEY_RIGHT_HAND, checkBoxRightHand.isChecked());

        // for the editing note
        bundle.putString(KEY_NOTE, editTextNote.getText().toString());

        intentDisplay.putExtras(bundle);

        startActivity(intentDisplay);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            editTextNote.setText(data.getData().toString());
        }

        if (requestCode == REQUEST_DOWNLOAD && resultCode == RESULT_OK) {
            buttonImage.setText("");
            buttonImage.setBackgroundResource(data.getIntExtra(DownloadActivity.KEY_DRAWABLE,
                    R.mipmap.ic_launcher));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void onGoDownloadClick(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivityForResult(intent, REQUEST_DOWNLOAD);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        make = arrayCarMaker[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        make = "No Maker Selected";
    }
}
