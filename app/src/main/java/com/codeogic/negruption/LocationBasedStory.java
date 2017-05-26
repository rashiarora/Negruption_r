package com.codeogic.negruption;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocationBasedStory extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listStories;
    ArrayList<StoryBean> stories;
    StoryAdapter adapter;
    StoryBean storyBean;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_based_story);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        listStories = (ListView)findViewById(R.id.listView_location);
        requestQueue = Volley.newRequestQueue(this);
        retrieveStory();
        Intent rcv =getIntent();
        location = rcv.getStringExtra("location");
        //location = rcv.getParcelableExtra("location");
       // Log.i("loc",location);
        Toast.makeText(this,location,Toast.LENGTH_LONG).show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    void retrieveStory(){
        progressDialog.show();
        stories = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, Util.RETRIEVE_STORY_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("locationStories");

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

                            stories.add(new StoryBean(0,sid,title,dep,pl,description,img,aud,vid,u,views,null,0));


                    }
                    adapter = new StoryAdapter(LocationBasedStory.this,R.layout.stories_list_item,stories);

                    listStories.setAdapter(adapter);
                    listStories.setOnItemClickListener(LocationBasedStory.this);
                    progressDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(LocationBasedStory.this,"Some Exception"+ e,Toast.LENGTH_LONG).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LocationBasedStory.this,"Some Error"+error,Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("location",location);
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        storyBean = stories.get(position);
        Toast.makeText(LocationBasedStory.this,"You clicked"+storyBean.getUsername(),Toast.LENGTH_LONG).show();

    }
}
