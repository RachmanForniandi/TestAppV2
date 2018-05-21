package com.example.user.testappv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_name)TextView tvName;
    String resultName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            resultName = extras.getString("result_name");
        tvName.setText(resultName);
    }

}
