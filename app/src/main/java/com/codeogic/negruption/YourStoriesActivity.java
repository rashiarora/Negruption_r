package com.codeogic.negruption;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class YourStoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @InjectView(R.id.listyStories)
    ListView yourList;

    @InjectView(R.id.btnRefresh)
    ImageButton btnRefresh;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<StoryBean> stories;
    YourStoriesAdapter adapter;
    StoryBean storyBean;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    int count=0, userId, pos;
    ArrayList<Integer> c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_stories);

        ButterKnife.inject(this);

        sharedPreferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);

        editor = sharedPreferences.edit();

        userId = sharedPreferences.getInt(Util.PREFS_KEYUSERID,0);




        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);
       //
         retrieveStory();
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



    }


    public void Refresh(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);

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
        progressDialog.show();
        stories = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, Util.RETRIEVE_YOUR_STORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              //  Log.i("serverResponse",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("stories");

                    int  sid=0,views =0;
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

                        if (privacy.equals("Anonymous"))
                            u = "Anonymous";
                        else
                            u = username;



                            stories.add(new StoryBean(0,sid,title,dep,pl,description,img,aud,vid,u,views,cat));


                    }
                    adapter = new YourStoriesAdapter(YourStoriesActivity.this,R.layout.your_stories_list,stories);

                    yourList.setAdapter(adapter);
                    yourList.setOnItemClickListener(YourStoriesActivity.this);
                    progressDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(YourStoriesActivity.this,"Some Exception"+ e,Toast.LENGTH_LONG).show();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(YourStoriesActivity.this,"Some Error"+error,Toast.LENGTH_LONG).show();

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
       /* count++;
        storyBean.setViews(count);*/



}
}
