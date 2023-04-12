package com.example.pr2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class second_activity extends AppCompatActivity {
    private EditText editTextPhone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editTextPhone2 = (EditText) findViewById(R.id.editTextPhone2);

        editTextPhone2.setText(getIntent().getStringExtra("tel"));
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tel", editTextPhone2.getText().toString());
        startActivity(intent);
    }
}