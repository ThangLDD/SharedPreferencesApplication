package com.thangldd.sharedpreferencesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteDataActivity extends AppCompatActivity {
    private EditText editTextName, editTextId;
    private Button buttonWriteInternal, buttonWriteExternal, buttonReadInternal, buttonReadExternal, buttonBackToMain;
    private static final String INTERNAL_PATH = Environment.getDataDirectory().getPath()
            + "/data/com.thangldd.sharedpreferencesapplication/";
    private static final String FILE_NAME = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_data);
        matchLayout();
        listenUser();
    }

    private void listenUser() {
        buttonWriteInternal.setOnClickListener(view -> writeInternalStorage());
        buttonWriteExternal.setOnClickListener(view -> writeExternalStorage());
        buttonReadInternal.setOnClickListener(view -> goToInternalActivity());
        buttonReadExternal.setOnClickListener(view -> goToExternalActivity());
        buttonBackToMain.setOnClickListener(view -> backToMainActivity());
    }

    private void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void writeInternalStorage() {
        try {
            String path = INTERNAL_PATH + FILE_NAME;
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            String data = editTextName.getText().toString() + ":" + editTextId.getText().toString();
            byte[] buff = data.getBytes();
            fileOutputStream.write(buff, 0, buff.length);
            fileOutputStream.close();
            Toast.makeText(this, "Ghi dữ liệu thành công", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ghi dữ liệu thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeExternalStorage() {
        if (isExternalStorageWritable()) {
            try {
                File folder = getExternalFilesDir("MyFile");
                File file = new File(folder, FILE_NAME);
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                String data = editTextName.getText().toString() + ":" + editTextId.getText().toString();
                byte[] buff = data.getBytes();
                fileOutputStream.write(buff, 0, buff.length);
                fileOutputStream.close();
                Toast.makeText(this, "Ghi dữ liệu thành công", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Không tìm thấy file", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ghi dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Không thể ghi vào bộ nhớ", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void goToExternalActivity() {
        Intent intent = new Intent(this, ExternalStorageActivity.class);
        startActivity(intent);
    }

    private void goToInternalActivity() {
        Intent intent = new Intent(this, InternalStorageActivity.class);
        startActivity(intent);
    }

    private void matchLayout() {
        editTextName = findViewById(R.id.edit_text_name_write);
        editTextId = findViewById(R.id.edit_text_id_write);
        buttonReadExternal = findViewById(R.id.button_read_e);
        buttonReadInternal = findViewById(R.id.button_read_i);
        buttonWriteExternal = findViewById(R.id.button_write_e);
        buttonWriteInternal = findViewById(R.id.button_write_i);
        buttonBackToMain = findViewById(R.id.button_backToMain);
    }
}
