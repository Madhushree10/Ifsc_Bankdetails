package com.example.ifsc_bankdetails;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NavigatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        Button mGetIfsc = findViewById(R.id.btnGetIfsc);
        Button mValidateIfsc = findViewById(R.id.btnValidateIfsc);
        Button mSearchByMicr = findViewById(R.id.btnMicr);
        Button mHistory = findViewById(R.id.btnSearchHistory);

        mGetIfsc.setOnClickListener(v -> {
            Intent findIfsc = new Intent(getBaseContext(), MainActivity.class);
            startActivity(findIfsc);
        });

        mValidateIfsc.setOnClickListener(v -> {
            Intent validateIfsc = new Intent(getBaseContext(), ValidateIfscActivity.class);
            startActivity(validateIfsc);
        });

        mSearchByMicr.setOnClickListener(v -> {
            Intent validateMicr = new Intent(getBaseContext(), SearchMicrActivity.class);
            startActivity(validateMicr);
        });

        mHistory.setOnClickListener(v -> {
            Intent history = new Intent(getBaseContext(), SearchHistoryActivity.class);
            startActivity(history);
        });
    }

}