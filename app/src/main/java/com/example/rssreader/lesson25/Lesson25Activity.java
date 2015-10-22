package com.example.rssreader.lesson25;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.rssreader.R;

public class Lesson25Activity extends AppCompatActivity {

    private static final String TAG = Lesson25Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson25);

        // TODO: [1] onCreate Log
        Log.d(TAG, "onCreate が呼ばれた");

        // TODO: [2]savedInstanceState からデータを復元しよう
        if (savedInstanceState != null) {

            String editTextString = savedInstanceState.getString("EDIT_TEXT");
            EditText editText = (EditText) findViewById(R.id.lessonEditText);
            editText.setText(editTextString);

            boolean isChecked = savedInstanceState.getBoolean("CHECK_BOX");
            CheckBox checkBox = (CheckBox) findViewById(R.id.lessonCheckBox);
            checkBox.setChecked(isChecked);
        }
    }

    // TODO: [1] onStart
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "_onStart が呼ばれた");
    }

    // TODO: [1] onResume

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "__onResume が呼ばれた");
    }

    // TODO: [1] onRestart
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "*onRestart が呼ばれた");
    }

    // TODO: [1] onPause
    @Override
    protected void onPause() {
        Log.d(TAG, "__onPause が呼ばれた");
        super.onPause();
    }

    // TODO: [1] onStop
    @Override
    protected void onStop() {
        Log.d(TAG, "_onStop が呼ばれた");
        super.onStop();
    }

    // TODO: [1] onDestroy
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy が呼ばれた");
        super.onDestroy();
    }

    // TODO: [2] onSaveInstanceState でデータを格納しよう
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        EditText editText = (EditText) findViewById(R.id.lessonEditText);
        outState.putString("EDIT_TEXT", editText.getText().toString());

        CheckBox checkBox = (CheckBox) findViewById(R.id.lessonCheckBox);
        outState.putBoolean("CHECK_BOX", checkBox.isChecked());
    }



}
