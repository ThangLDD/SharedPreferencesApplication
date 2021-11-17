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

public class ExternalStorageActivity extends AppCompatActivity {
    private TextView textViewPath, textViewName, textViewId;
    private Button buttonShowData, buttonBackToWrite;
    private static final String FILE_NAME = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);
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
        if (isExternalStorageReadable()) {
            try {
                File folder = getExternalFilesDir("MyFile");
                File file = new File(folder, FILE_NAME);
                FileInputStream fileInputStream = new FileInputStream(file);
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

                textViewPath.setText(file.getPath());
                textViewName.setText(name);
                textViewId.setText(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Không thể đọc dữ liệu từ bộ nhớ", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private void matchLayout() {
        textViewPath = findViewById(R.id.text_view_path_e);
        textViewName = findViewById(R.id.text_view_name_e);
        textViewId = findViewById(R.id.text_view_id_e);
        buttonShowData = findViewById(R.id.button_show_data_e);
        buttonBackToWrite = findViewById(R.id.button_backToWrite_e);
    }
}
