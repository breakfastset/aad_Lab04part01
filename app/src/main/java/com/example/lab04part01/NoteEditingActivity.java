package com.example.lab04part01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NoteEditingActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editing);
        editText = findViewById(R.id.inputNote);
    }

    public void onDoneClick(View view) {
        Intent intent = new Intent();
        Uri uri = Uri.parse(editText.getText().toString());
        intent.setData(uri);   // works in hand in hand with startActivityForResult
        setResult(RESULT_OK, intent);
        finish();   // activity is destroyed and this case returns to the MainActivity
    }
}
