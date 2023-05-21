package com.example.pr8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pr8.Database.TextDao;
import com.example.pr8.Database.TextDataBase;
import com.example.pr8.Database.TextEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainFragment extends Fragment {
    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = view.findViewById(R.id.spinner);
        SharedPreferences sharedPrefs = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        view.findViewById(R.id.save_button).setOnClickListener(view1 -> {
            DataStorageTypes type = DataStorageTypes.values()[spinner.getSelectedItemPosition()];
            String text = ((TextView)(view.findViewById(R.id.text_input))).getText().toString();

            switch (type) {
                case SHARED_PREFS: {
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString(getString(R.string.sharedPrefs_key), text);
                    editor.apply();
                } break;
                case APP_SPECIFIC: {
                    File file = new File(getContext().getFilesDir(), "file.txt");
                    try {
                        FileOutputStream fout = new FileOutputStream(file);
                        fout.write(text.getBytes());
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } break;
                case GENERAL: {
                    File externalStorage = Environment.getExternalStorageDirectory();
                    File file = new File(externalStorage, "file.txt");
                    try {
                        FileOutputStream fout = new FileOutputStream(file);
                        fout.write(text.getBytes());
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } break;
                case DATABASE: {
                    TextDataBase db = TextDataBase.getDatabase(getContext().getApplicationContext());
                    final TextDao textDao = db.textDao();
                    new Thread(() -> {
                        TextEntity textEntity = textDao.getText("key");
                        if (textEntity == null) {
                            textDao.saveText(new TextEntity("key", text));
                        } else {
                            TextEntity newTextEntity = new TextEntity("key", text);
                            newTextEntity.setId(textEntity.getId());
                            textDao.updateText(newTextEntity);
                        }
                    }).start();
                } break;
            }
        });

        view.findViewById(R.id.read_button).setOnClickListener(view1 -> {
            DataStorageTypes type = DataStorageTypes.values()[spinner.getSelectedItemPosition()];
            String text = "";

            switch (type) {
                case SHARED_PREFS: {
                    text = sharedPrefs
                            .getString(getString(R.string.sharedPrefs_key), "Default String");
                } break;
                case APP_SPECIFIC:{
                    File file = new File(getContext().getFilesDir(), "file.txt");
                    try {
                        FileInputStream fin = new FileInputStream(file);
                        byte[] data = new byte[(int) file.length()];
                        fin.read(data);
                        fin.close();
                        text = new String(data, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } break;
                case GENERAL: {
                    File externalStorage = Environment.getExternalStorageDirectory();
                    File file = new File(externalStorage, "file.txt");
                    try {
                        FileInputStream fin = new FileInputStream(file);
                        byte[] data = new byte[(int) file.length()];
                        fin.read(data);
                        fin.close();
                        text = new String(data, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } break;
                case DATABASE: {
                    TextDataBase db = TextDataBase.getDatabase(getContext().getApplicationContext());
                    new Thread(() -> {
                        TextDao textDao = db.textDao();
                        TextEntity textEntity = textDao.getText("key");
                        if (textEntity != null) {
                            final String loadedText = textEntity.getText();
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), loadedText, Toast.LENGTH_SHORT).show();
                            });
                        }
                    }).start();
                }
            }
            if (type != DataStorageTypes.DATABASE) {
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}