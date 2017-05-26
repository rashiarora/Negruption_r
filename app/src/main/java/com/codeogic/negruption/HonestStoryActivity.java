package com.codeogic.negruption;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HonestStoryActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    @InjectView(R.id.editTextStoryTitle_Honest)
    EditText txtHonestStoryTitle;

    @InjectView(R.id.editTextStory_Honest)
    EditText txtHonestStoryDesc;

    @InjectView(R.id.spinnerCities_Honest)
    Spinner spinnerMainHonestCities;

    @InjectView(R.id.spinnerDepartment_Honest)
    Spinner spinnerHonestDepartment;


    @InjectView(R.id.chkAnym_Honest)
    CheckBox chckHonest;

    @InjectView(R.id.buttonUpload_Honest)
    Button btnHonestUpload;

    @InjectView(R.id.imageViewHonestOfficer)
    ImageView imageViewOfficer;

    StoryBean storyBean;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String privacy = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    int userId;
    ArrayAdapter<String> arrayAdapterHonestCities,arrayAdapterHonestDepartment;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honest_story);
        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(3500); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        imageViewOfficer.startAnimation(anim);

        setHonestDepartment();
        setHonestCities();
        btnHonestUpload.setOnClickListener(this);
        storyBean = new StoryBean();

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading...");

        chckHonest.setOnCheckedChangeListener(this);
        sharedPreferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);

        editor = sharedPreferences.edit();

        userId = sharedPreferences.getInt(Util.PREFS_KEYUSERID,0);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setHonestDepartment() {

        arrayAdapterHonestDepartment = new ArrayAdapter<String>(HonestStoryActivity.this, android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterHonestDepartment.add("--  Which Department ? --");
        arrayAdapterHonestDepartment.add("Airports");
        arrayAdapterHonestDepartment.add("Banking");
        arrayAdapterHonestDepartment.add("Bureau Of Immigration");
        arrayAdapterHonestDepartment.add("Commercial Tax , Sales Tax , VAT");
        arrayAdapterHonestDepartment.add("Customs,Excise And Service Tax");
        arrayAdapterHonestDepartment.add("Education");
        arrayAdapterHonestDepartment.add("Electricity And Power Supply");
        arrayAdapterHonestDepartment.add("Food And Drug Administration");
        arrayAdapterHonestDepartment.add("Food,Civil Supplies And Consumer Rights");
        arrayAdapterHonestDepartment.add("Foreign Trade");
        arrayAdapterHonestDepartment.add("Forest");
        arrayAdapterHonestDepartment.add("Health And Family Welfare");
        arrayAdapterHonestDepartment.add("Income Tax");
        arrayAdapterHonestDepartment.add("Insurance");
        arrayAdapterHonestDepartment.add("Judiciary");
        arrayAdapterHonestDepartment.add("Labour");
        arrayAdapterHonestDepartment.add("Municipal Services");
        arrayAdapterHonestDepartment.add("Passport");
        arrayAdapterHonestDepartment.add("Pension");
        arrayAdapterHonestDepartment.add("Police");
        arrayAdapterHonestDepartment.add("Post Office");
        arrayAdapterHonestDepartment.add("Public Undertakings");
        arrayAdapterHonestDepartment.add("Public Services");
        arrayAdapterHonestDepartment.add("Public Works Department");
        arrayAdapterHonestDepartment.add("Railways");
        arrayAdapterHonestDepartment.add("Religious Trusts");
        arrayAdapterHonestDepartment.add("Revenue");
        arrayAdapterHonestDepartment.add("Slum Development");
        arrayAdapterHonestDepartment.add("Social Welfare");
        arrayAdapterHonestDepartment.add("Stamps And Registration");
        arrayAdapterHonestDepartment.add("Telecom Services");
        arrayAdapterHonestDepartment.add("Transport");
        arrayAdapterHonestDepartment.add("Urban Development Authorities");
        arrayAdapterHonestDepartment.add("Water Sewage");
        arrayAdapterHonestDepartment.add("Others");

        spinnerHonestDepartment.setAdapter(arrayAdapterHonestDepartment);
        spinnerHonestDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    storyBean.setDepartment(arrayAdapterHonestDepartment.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void setHonestCities(){
        arrayAdapterHonestCities=new ArrayAdapter<String>(HonestStoryActivity.this,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterHonestCities.add("-- Select City --");

        arrayAdapterHonestCities.add("Agra");
        arrayAdapterHonestCities.add("Ahmedabad");
        arrayAdapterHonestCities.add("Ambala");
        arrayAdapterHonestCities.add("Amritsar");
        arrayAdapterHonestCities.add("Bangalore");
        arrayAdapterHonestCities.add("Bhopal");
        arrayAdapterHonestCities.add("Chandigarh");
        arrayAdapterHonestCities.add("Delhi");
        arrayAdapterHonestCities.add("Gandhinagar");
        arrayAdapterHonestCities.add("Gurgaon");
        arrayAdapterHonestCities.add("Gurdaspur");
        arrayAdapterHonestCities.add("Guwahati");
        arrayAdapterHonestCities.add("Jalandhar");
        arrayAdapterHonestCities.add("Jaipur");
        arrayAdapterHonestCities.add("Jodhpur");
        arrayAdapterHonestCities.add("Kanniyakumari");
        arrayAdapterHonestCities.add("Kapurthala");
        arrayAdapterHonestCities.add("Karnal");
        arrayAdapterHonestCities.add("Ludhiana");
        arrayAdapterHonestCities.add("Noida");
        arrayAdapterHonestCities.add("Kolkata");
        arrayAdapterHonestCities.add("Mumbai");
        arrayAdapterHonestCities.add("Panipat");
        arrayAdapterHonestCities.add("Panchkula");
        arrayAdapterHonestCities.add("Patiala");
        arrayAdapterHonestCities.add("Patna");
        arrayAdapterHonestCities.add("Pune");
        arrayAdapterHonestCities.add("Mysore");
        arrayAdapterHonestCities.add("Panaji");
        arrayAdapterHonestCities.add("Shimla");
        arrayAdapterHonestCities.add("Surat");
        arrayAdapterHonestCities.add("Ranchi");
        arrayAdapterHonestCities.add("Trivandrum");
        arrayAdapterHonestCities.add("Visakhapatnam");


        spinnerMainHonestCities.setAdapter(arrayAdapterHonestCities);
        spinnerMainHonestCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position !=0)
                    storyBean.setPlace(arrayAdapterHonestCities.getItem(position));
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.buttonUpload_Honest){

            if (validInput()){
                if(isNetworkConnected()){
                    storyBean.setStoryTitle(txtHonestStoryTitle.getText().toString().trim());
                    storyBean.setStoryDesc(txtHonestStoryDesc.getText().toString().trim());
                    uploadHonestStory();
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("No Network");
                    builder.setMessage(" Please Turn On The Internet ");

                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            HonestStoryActivity.this.startActivity(new Intent(Settings.ACTION_SETTINGS));

                            Toast.makeText(HonestStoryActivity.this,"Clicked Okay",Toast.LENGTH_LONG).show();


                        }
                    });
                    builder.create().show();
                }


        }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId()==R.id.chkAnym_Honest) {
            if (isChecked) {
                privacy = "Anonymous";
            } else if (!isChecked)
                privacy = "Not Any";
        }

    }

    public boolean isNetworkConnected(){

        connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }


    void uploadHonestStory(){
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.INSERT_HONEST_STORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success=jsonObject.getInt("success");
                    String message=jsonObject.getString("message");


                    if (success == 1){

                        progressDialog.dismiss();

                        Toast.makeText(getApplication(), "Story Uploaded Success"  + message , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HonestStoryActivity.this,HomeActivity.class);
                        startActivity(intent);

                    }else {
                        progressDialog.dismiss();

                        Toast.makeText(getApplication(), "Story Uploaded Failure"  + message , Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getApplication(), "Some Volley Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("honestStoryTitle",storyBean.getStoryTitle());
                map.put("honest_department",storyBean.getDepartment());
                map.put("honest_place",storyBean.getPlace());
                map.put("honestStoryDesc",storyBean.getStoryDesc());
                map.put("category","Honest");
                map.put("honest_privacy",privacy);
                map.put("honest_userId",String.valueOf(userId));

                Log.i("HonestStories",storyBean.toString());



                return map;
            }

        };
        requestQueue.add(request);;
    }


    public boolean validInput(){

        boolean flag = true;

        if (txtHonestStoryDesc.getText().toString().trim().isEmpty()){
            flag=false;
            txtHonestStoryDesc.setError("Please Write The Story");

        }

        if (txtHonestStoryTitle.getText().toString().trim().isEmpty()){
            flag=false;
            txtHonestStoryTitle.setError("Please Fill The Title");

        }

        if(spinnerHonestDepartment.getSelectedItem().toString().trim().equalsIgnoreCase("--  Which Department ? --")){

            flag=false;
            Toast.makeText(this,"Please Select Department",Toast.LENGTH_LONG).show();
        }

        if (spinnerMainHonestCities.getSelectedItem().toString().trim().equalsIgnoreCase("-- Select City --")){

            flag=false;
            Toast.makeText(this,"Please Select City",Toast.LENGTH_LONG).show();
        }

        return flag;


    }

}
