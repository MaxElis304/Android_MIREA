package com.example.pr2;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button next;
    private EditText tel_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        tel_num = (EditText) findViewById(R.id.editText1);
        tel_num.setText(getIntent().getStringExtra("tel"));
    }

    public void onButtonClick(){
        Log.d(TAG, tel_num.getText().toString());
        Toast toast = Toast.makeText(getApplicationContext(), tel_num.getText().toString(), Toast.LENGTH_LONG);
        toast.show();
    }
    public void sending(View view){

        onButtonClick();

        Intent intent = new Intent(this, second_activity.class);
        intent.putExtra("tel", tel_num.getText().toString());
        startActivity(intent);
    }


}