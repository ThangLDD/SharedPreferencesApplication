package com.thangldd.sharedpreferencesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;

public class InternalStorageActivity extends AppCompatActivity {
    private TextView textViewPath, textViewName, textViewId;
    private Button buttonShowData, buttonBackToWrite;
    private static final String INTERNAL_PATH = Environment.getDataDirectory().getPath()
            + "/data/com.thangldd.sharedpreferencesapplication/";
    private static final String FILE_NAME = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);
        matchLayout();
        listenUser();
    }

    private void listenUser() {
        buttonShowData.setOnClickListener(view -> getData());
        buttonBackToWrite.setOnClickListener(view -> backToWriteDataActivity());
    }

    private void backToWriteDataActivity() {
        Intent intent = new Intent(this, WriteDataActivity.class);
        startActivity(intent);
    }

    private void getData() {
        try {
            String path = INTERNAL_PATH + FILE_NAME;
            File file = new File(path);
            if (!file.exists()) {
                Toast.makeText(this, "No directory", Toast.LENGTH_SHORT).show();
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(path);
            StringBuilder data = new StringBuilder();
            int len;
            byte[] buff = new byte[1024];
            while ((len = fileInputStream.read(buff)) > 0) {
                data.append(new String(buff, 0, len));
            }
            fileInputStream.close();

            String[] result = data.toString().split(":");
            String name = result[0];
            String id = result[1];
            System.out.println("Day la Name: " + name);
            System.out.println("Day la ID:" + id);
            textViewPath.setText(path);
            textViewName.setText(name);
            textViewId.setText(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void matchLayout() {
        textViewPath = findViewById(R.id.text_view_path_i);
        textViewName = findViewById(R.id.text_view_name_i);
        textViewId = findViewById(R.id.text_view_id_i);
        buttonShowData = findViewById(R.id.button_show_data_i);
        buttonBackToWrite = findViewById(R.id.button_backToWrite_i);
    }
}
