package com.s15540.smb.grocerylist.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.s15540.smb.grocerylist.R;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsActivity extends AppCompatActivity {

    private int currentColor;
    private int currentSize = 12;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings2);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        final Button btnColor = findViewById(R.id.btn_color);

        currentColor = pref.getInt("currentColor", 0xff0000ff);

        btnColor.setBackgroundColor(currentColor);

        super.onCreate(savedInstanceState);


        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        Spinner dropdown = findViewById(R.id.spinner_size);
        final String[] items = new String[]{"12", "18", "24"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentSize = Integer.parseInt(items[position]);
                btnColor.setTextSize(currentSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                btnColor.setTextSize(12);
            }

        });
    }

    protected void openColorPicker() {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                editor.putInt("currentColor", currentColor);
                editor.commit();
                Button btnColor = findViewById(R.id.btn_color);
                btnColor.setBackgroundColor(currentColor);
            }
        });

        dialog.show();
    }
}