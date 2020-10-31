package com.example.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText name, weight, height;
    Spinner gender;
    TextView bmi;
    private SharedPreferences userData;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setUpViews();
        this.setUpSharedPref();
        this.checkPreviousData();


    }

    public void setUpViews() {
        //connect to xml attributes.
        name = findViewById(R.id.editTextName);
        height = findViewById(R.id.editTextHeight);
        weight = findViewById(R.id.editTextWeight);
        gender = findViewById(R.id.spinnerGender);
        bmi = findViewById(R.id.textViewBMI);
    }

    public void calculateBMI() {

        float h = Float.parseFloat(height.getText().toString());
        float w = Float.parseFloat(weight.getText().toString());
        float bmi = (float) ((h / 100.0) / (w * w));
        this.bmi.setText("BMI= " + bmi);

    }

    public boolean validate() {
        if (height.getText().toString().equals("") ||
                weight.getText().toString().equals("") ||
                name.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    public void btnBmiOnClick(View view) {
        if (this.validate())
            this.calculateBMI();
        else {
            Toast.makeText(this, "All Fields Required", Toast.LENGTH_SHORT).show();
        }
    }

    public void setUpSharedPref() {
        userData = PreferenceManager.getDefaultSharedPreferences(this);
        editor = userData.edit();
    }

    public void btnSaveOnClick(View view) {
        if (this.validate()) {
            String name = this.name.getText().toString();
            String gender = this.gender.getSelectedItem().toString();
            float h = Float.parseFloat(height.getText().toString());
            float w = Float.parseFloat(weight.getText().toString());
            float bmi = (float) ((h / 100.0) / (w * w));


            editor.putString("name", name);
            editor.putString("gender", gender);
            editor.putFloat("height", h);
            editor.putFloat("weight", w);
            editor.putFloat("bmi", bmi);
            editor.putBoolean("FLAG", true);
            editor.commit();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "All Fields Required", Toast.LENGTH_SHORT).show();
        }

    }

    public void checkPreviousData() {
        boolean flag = userData.getBoolean("FLAG", false);
        if (flag) {
            name.setText(userData.getString("name", ""));
            height.setText(String.valueOf(userData.getFloat("height", (float) 0.0)));
            weight.setText(String.valueOf(userData.getFloat("weight", (float) 0.0)));
            bmi.setText("BMI= " + userData.getFloat("bmi", (float) 0.0));
            if (userData.getString("gender", "").equals("Male")) {
                gender.setSelection(0);
            } else gender.setSelection(1);

        }
    }

    public void btnCounterOnClick(View view) {
        Intent intent = new Intent(this, Counter.class);
        startActivity(intent);
    }

    public void btnClearOnClick(View view) {
        this.height.setText("");
        this.weight.setText("");
        this.name.setText("");
        this.gender.setSelection(0);
        this.bmi.setText("");

        editor.putBoolean("FLAG", false);
        editor.commit();
        Toast.makeText(this, "User Data Cleared", Toast.LENGTH_SHORT).show();

    }
}