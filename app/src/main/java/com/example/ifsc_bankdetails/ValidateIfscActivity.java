
package com.example.ifsc_bankdetails;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

class ValidateIfscActivity extends AppCompatActivity {

    private EditText mIfsc;
    private ProgressDialog progressDialog;
    private CardView mCvBranch;
    private CardView mCvIfscDetails;
    private LinearLayout mButtons;

    private TextView mBranch;
    private TextView mBank;
    private TextView mState;
    private TextView mMicrCode;
    private TextView mContact;
    private TextView mAddress;
    private TextView mCity;
    private TextView mDistrict;

    private String STATE;
    private String BANK;
    private String MICRCODE;
    private String BRANCH;
    private String CONTACT;
    private String ADDRESS;
    private String CITY;
    private String DISTRICT;
    private String IFSC;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_ifsc);

        db = new DatabaseHelper(this);

        Button mValidateIfsc = findViewById(R.id.btnValidate);
        mIfsc = findViewById(R.id.etIFSC);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        mBranch = findViewById(R.id.tvBranch);
        mBank = findViewById(R.id.tvBank);
        mState = findViewById(R.id.tvState);
        mMicrCode = findViewById(R.id.tvMicr);
        mContact = findViewById(R.id.tvContact);
        mAddress = findViewById(R.id.tvAddress);
        mCity = findViewById(R.id.tvCity);
        mDistrict = findViewById(R.id.tvDistrict);

        mCvIfscDetails = findViewById(R.id.ifscDetails);
        mCvBranch = findViewById(R.id.ifscBranch);
        mButtons = findViewById(R.id.llButtons);

        mValidateIfsc.setOnClickListener(v -> {
            String ed_text = mIfsc.getText().toString().trim();

            if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals(""))
            {
                mIfsc.setError("Enter the IFSC Code");
            }
            else
            {
                getDetails();
                mCvIfscDetails.setVisibility(View.VISIBLE);
                mCvBranch.setVisibility(View.VISIBLE);
                mButtons.setVisibility(View.VISIBLE);

            }
        });
    }

    public void getDetails(){
        String ifscCode = mIfsc.getText().toString().toUpperCase();
        String URL_IFSC = "http://api.techm.co.in/api/v1/ifsc/" + ifscCode;

        progressDialog = new ProgressDialog(this);
        //progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Details \n\n Please wait...");
        progressDialog.show();

        //Toast.makeText(ValidateIfscActivity.this, URL_IFSC, Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(URL_IFSC,
                response -> {
                    JSONObject bank = null;
                    hidePDialog();
                    try {
                        //Parsing the fetched Json String to JSON Object
                        bank = new JSONObject(response);
                        JSONObject bankData = bank.getJSONObject("data");

                        ADDRESS = bankData.getString("ADDRESS");
                        BANK = bankData.getString("BANK");
                        STATE = bankData.getString("STATE");
                        MICRCODE = bankData.getString("MICRCODE");
                        BRANCH = bankData.getString("BRANCH");
                        CONTACT = bankData.getString("CONTACT");
                        CITY = bankData.getString("CITY");
                        DISTRICT = bankData.getString("DISTRICT");
                        IFSC = bankData.getString("IFSC");

                        mBranch.setText(BRANCH);
                        mBank.setText(BANK);
                        mState.setText(STATE);
                        mMicrCode.setText(MICRCODE);
                        mContact.setText(CONTACT);
                        mAddress.setText(ADDRESS);
                        mCity.setText(CITY);
                        mDistrict.setText(DISTRICT);

                        if(BANK.isEmpty() || BANK.length() == 0 || BANK.equals("")) {

                        }else {
                            db.addContact(new Bank(BANK, STATE, MICRCODE, BRANCH, CONTACT, ADDRESS, CITY, DISTRICT, IFSC));
                        }

                        //Toast.makeText(ValidateIfscActivity.this, URL_IFSC, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}