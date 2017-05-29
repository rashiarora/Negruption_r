package com.codeogic.negruption;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class YourStoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener{

    @InjectView(R.id.listyStories)
    ListView yourList;


    @InjectView(R.id.swipeRefreshy)
    SwipeRefreshLayout swipeRefreshLayout;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<StoryBean> stories;
    YourStoriesAdapter adapter;
    StoryBean storyBean;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    int count=0, userId, pos;
    ArrayList<Integer> c;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_stories);

        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);

        editor = sharedPreferences.edit();

        userId = sharedPreferences.getInt(Util.PREFS_KEYUSERID,0);




        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);
       //

        c = new ArrayList<>();


        yourList.setOnItemClickListener(this);
        yourList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;
                storyBean = stories.get(position);
                Toast.makeText(YourStoriesActivity.this,"You Long clicked"+storyBean.getUsername(),Toast.LENGTH_LONG).show();
                showOptions();


                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
              retrieveStory();
            }
        });
       retrieveStory();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }




    void deleteStory(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ storyBean.getStoryTitle());
        builder.setMessage("Do you wish to Delete?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteFromCloud();

            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }



    void showOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items ={"Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case 0:
                        deleteStory();
                        break;

                }

            }
        });

        builder.create().show();

    }

    void deleteFromCloud(){
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.DELETE_STORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success==1){
                        stories.remove(pos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(YourStoriesActivity.this,message,Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }else {
                        Toast.makeText(YourStoriesActivity.this,message,Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(YourStoriesActivity.this,"Some Exception: " + e.getMessage(),Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(YourStoriesActivity.this,"Some Volley Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("storyId",String.valueOf(storyBean.getStoryId()));
                return map;
            }
        }
                ;
        requestQueue.add(stringRequest);

    }




    void retrieveStory(){
       // progressDialog.show();
        stories = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, Util.RETRIEVE_YOUR_STORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              //  Log.i("serverResponse",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("stories");

                    int  sid=0,views =0, status = 0;
                    String username="",title="",description="",privacy="",dep = " ",pl = " ",u = "", img = " ", aud = " ", vid = " ", cat = " ";


                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        username = jObj.getString("name");
                        sid = jObj.getInt("storyId");
                        title = jObj.getString("storyTitle");
                        description = jObj.getString("storyDesc");
                        dep = jObj.getString("department");
                        pl = jObj.getString("place");
                        privacy = jObj.getString("privacy");
                        img = jObj.getString("imageProof");
                        aud = jObj.getString("audioProof");
                        vid = jObj.getString("videoProof");
                        cat = jObj.getString("category");
                        views = jObj.getInt("views");
                        status = jObj.getInt("status");

                        if (privacy.equals("Anonymous"))
                            u = "Anonymous";
                        else
                            u = username;



                            stories.add(new StoryBean(0,sid,title,dep,pl,description,img,aud,vid,u,views,cat,status));


                    }
                    adapter = new YourStoriesAdapter(YourStoriesActivity.this,R.layout.your_stories_list,stories);

                    yourList.setAdapter(adapter);
                    yourList.setOnItemClickListener(YourStoriesActivity.this);
                    //progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);

                }catch (Exception e){
                    e.printStackTrace();
                    //progressDialog.dismiss();
                    //Toast.makeText(YourStoriesActivity.this,"Some Exception"+ e,Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder=new AlertDialog.Builder(YourStoriesActivity.this);
                    builder.setTitle("No Stories yet!");
                    builder.setMessage(" You have not published any stories yet  ");

                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(YourStoriesActivity.this,HomeActivity.class);
                            YourStoriesActivity.this.startActivity(i);

                            //Toast.makeText(YourStoriesActivity.this,"Clicked Okay",Toast.LENGTH_LONG).show();


                        }
                    });
                    builder.create().show();

                    swipeRefreshLayout.setRefreshing(false);
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // progressDialog.dismiss();
                Toast.makeText(YourStoriesActivity.this,"Some Error"+error,Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userId",String.valueOf(userId));
                return map;
            }
        };

        requestQueue.add(request);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       storyBean = stories.get(position);
        Toast.makeText(YourStoriesActivity.this,"You clicked"+storyBean.getUsername(),Toast.LENGTH_LONG).show();

        if (storyBean.getCategory().equals("Corrupt")){
            Intent intent = new Intent(YourStoriesActivity.this,StoryActivity.class);
            intent.putExtra("keyStory",storyBean);
            startActivity(intent);
        }
        else if (storyBean.getCategory().equals("Honest")) {
            Intent intent = new Intent(YourStoriesActivity.this, DetailedHonestActivity.class);
            intent.putExtra("keyHonestStory",storyBean);
            startActivity(intent);
        }
       /* count++;
        storyBean.setViews(count);*/



}

    @Override
    public void onRefresh() {
        retrieveStory();
    }

    public boolean isNetworkConnected(){

        connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isNetworkConnected()){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("No Network");
            builder.setMessage(" Please Turn On The Internet ");

            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    YourStoriesActivity.this.startActivity(new Intent(Settings.ACTION_SETTINGS));

                    Toast.makeText(YourStoriesActivity.this,"Clicked Okay",Toast.LENGTH_LONG).show();


                }
            });
            builder.create().show();
        }
    }
}
