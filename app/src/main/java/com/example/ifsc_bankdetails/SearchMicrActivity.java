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

public class SearchMicrActivity extends AppCompatActivity {

    private EditText mMicr;
    private ProgressDialog progressDialog;
    private CardView mCvBranch;
    private CardView mCvIfscDetails;
    private LinearLayout mButtons;

    private TextView mBranch;
    private TextView mBank;
    private TextView mState;
    private TextView mIfscCode;
    private TextView mContact;
    private TextView mAddress;
    private TextView mCity;
    private TextView mDistrict;

    private String STATE;
    private String BANK;
    private String BRANCH;
    private String CONTACT;
    private String ADDRESS;
    private String CITY;
    private String DISTRICT;
    private String IFSC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_micr);

        Button btnValidateMicr = findViewById(R.id.btnValidateMicr);
        mMicr = findViewById(R.id.etMicr);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        mBranch = findViewById(R.id.tvBranchMicr);
        mBank = findViewById(R.id.tvBankMicr);
        mState = findViewById(R.id.tvStateMicr);
        mIfscCode = findViewById(R.id.tvMicrMicr);
        mContact = findViewById(R.id.tvContactMicr);
        mAddress = findViewById(R.id.tvAddressMicr);
        mCity = findViewById(R.id.tvCityMicr);
        mDistrict = findViewById(R.id.tvDistrictMicr);

        mCvIfscDetails = findViewById(R.id.MicrDetails);
        mCvBranch = findViewById(R.id.branchMicr);
        mButtons = findViewById(R.id.llButtonsMicr);

        btnValidateMicr.setOnClickListener(v -> {
            String ed_text = mMicr.getText().toString().trim();

            if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals(""))
            {
                mMicr.setError("Enter the MICR Code");
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
        String micrCode = mMicr.getText().toString().toUpperCase();
        String URL_MICR = "http://api.techm.co.in/api/v1/micr/" + micrCode;

        progressDialog = new ProgressDialog(this);
        //progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Details \n\n Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(URL_MICR,
                response -> {
                    JSONObject bank = null;
                    hidePDialog();
                    try {
                        bank = new JSONObject(response);
                        JSONObject bankData = bank.getJSONObject("data");

                        ADDRESS = bankData.getString("ADDRESS");
                        BANK = bankData.getString("BANK");
                        STATE = bankData.getString("STATE");
                        BRANCH = bankData.getString("BRANCH");
                        CONTACT = bankData.getString("CONTACT");
                        CITY = bankData.getString("CITY");
                        DISTRICT = bankData.getString("DISTRICT");
                        IFSC = bankData.getString("IFSC");

                        mBranch.setText(BRANCH);
                        mBank.setText(BANK);
                        mState.setText(STATE);
                        mIfscCode.setText(IFSC);
                        mContact.setText(CONTACT);
                        mAddress.setText(ADDRESS);
                        mCity.setText(CITY);
                        mDistrict.setText(DISTRICT);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
