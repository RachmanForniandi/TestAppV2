package com.example.user.testappv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvName;
    String resultName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            resultName = extras.getString("result_name");
        tvName.setText(resultName);
    }

    private void initComponents() {
        tvName = findViewById(R.id.tv_name);
    }
}
