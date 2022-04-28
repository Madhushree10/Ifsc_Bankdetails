package com.example.ifsc_bankdetails;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SearchHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        DatabaseHelper db = new DatabaseHelper(this);
        final List<Bank> banks = db.getAllContacts();
        String[] listName = new String[banks.size()];

        for (int i = 0; i < listName.length; i++) {
            listName[i] = "Bank : "+ banks.get(i).get_bank() +"\n"+ "IFSC : "+ banks.get(i).get_ifsc()
                    +"\n"+ "Address : "+ banks.get(i).get_address() +"\n"+ "Contact : "+ banks.get(i).get_contact()
                    +"\n"+ "Bracnch : "+ banks.get(i).get_branch() +"\n"+ "City : "+ banks.get(i).get_city()
                    + "\n"+"MICR : "+ banks.get(i).get_micr();
        }
        if (listName.length > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_bank_row, listName);
            ListView listView = findViewById(R.id.lvContactsSql);
            listView.setAdapter(adapter);
        }

    }
}