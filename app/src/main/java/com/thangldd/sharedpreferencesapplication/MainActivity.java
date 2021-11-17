package com.thangldd.sharedpreferencesapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroupLanguage;
    private Button buttonSave, buttonWriteData;
    private TextView textViewShowLanguage;
    private static final String sharedPreferencesName = "sPName";
    private static final String putName = "pName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matchLayout();
        listenUser();
        loadLanguageSetting();
    }

    private void loadLanguageSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE);
        if (sharedPreferences != null) {
            String language = sharedPreferences.getString(putName, "Chưa có thiết lập ngôn ngữ trong ứng dụng");
            textViewShowLanguage.setText(language);
        }
    }

    private void listenUser() {
        buttonSave.setOnClickListener(view -> handleCheckedRadioGroup());
        buttonWriteData.setOnClickListener(view -> goToWriteDataActivity());
    }

    private void goToWriteDataActivity() {
        Intent intent = new Intent(this, WriteDataActivity.class);
        startActivity(intent);
    }

    private void handleCheckedRadioGroup() {
        String content = "Bạn chưa chọn ngôn ngữ trong lần sử dụng trước";
        int choice = radioGroupLanguage.getCheckedRadioButtonId();
        if (choice == R.id.radio_button_vietnamese) {
            content = "Bạn đã chọn Tiếng Việt";
            textViewShowLanguage.setText(content);
        } else if (choice == R.id.radio_button_english) {
            content = "Bạn đã chọn Tiếng Anh";
            textViewShowLanguage.setText(content);
        } else {
            String text = "Bạn chưa chọn ngôn ngữ";
            textViewShowLanguage.setText(text);
        }

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(putName, content);
        editor.apply();
    }

    private void matchLayout() {
        radioGroupLanguage = findViewById(R.id.radio_group_language);
        textViewShowLanguage = findViewById(R.id.text_view_show_language);
        buttonSave = findViewById(R.id.button_save);
        buttonWriteData = findViewById(R.id.button_write_data);
    }
}
