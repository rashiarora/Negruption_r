package com.codeogic.negruption;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class RetrieveHonestStory extends AppCompatActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    ListView honestStories;
    ArrayList<StoryBean> stories;
    HonestStoryAdapter adapter;
    StoryBean story;
    RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefreshLayout;
   // EditText eTxtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_honest_story);
       // eTxtSearch = (EditText)findViewById(R.id.editTexthSearch);
        honestStories = (ListView)findViewById(R.id.listStories_honest);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshh);
       /* progressDialog = new  (this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);*/

      /* eTxtSearch.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               String str = s.toString();
               if(adapter!=null){
                   adapter.filter(str);
               }

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });*/

        requestQueue = Volley.newRequestQueue(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                retrieveHonestStory();
            }
        });

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

    void retrieveHonestStory(){
      //  progressDialog.show();
        stories = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, Util.RETRIEVE_STORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("stories");

                    int  sid=0,views=0;
                    String username="",title="",description="",privacy="",u="",category ="",dep = " ",pl = " ";

                    stories.clear();
                    for(int i=0;i<jsonArray.length();i++) {
                       // Log.i("length",jsonArray.length()+ "");
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        username = jObj.getString("name");
                        dep = jObj.getString("department");
                        pl = jObj.getString("place");
                        sid = jObj.getInt("storyId");
                        title = jObj.getString("storyTitle");
                        description = jObj.getString("storyDesc");
                        privacy = jObj.getString("privacy");
                        category = jObj.getString("category");
                        views = jObj.getInt("views");

                        if (privacy.equals("Anonymous"))
                            u = "Anonymous";
                        else
                            u = username;


                        if (category.equals("Honest")) {
                            stories.add(new StoryBean(0, sid, title, dep,pl, description, null, null, null, u,views,null,0));
                        }
                    }

                    adapter = new HonestStoryAdapter(RetrieveHonestStory.this,R.layout.honest_list_item,stories);

                    honestStories.setAdapter(adapter);
                    honestStories.setOnItemClickListener(RetrieveHonestStory.this);
                  //  progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);

                }catch (Exception e){
                    e.printStackTrace();
                    //progressDialog.dismiss();
                    Toast.makeText(RetrieveHonestStory.this,"Some Exception"+ e,Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Toast.makeText(RetrieveHonestStory.this,"Some Error"+error,Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(request);
        requestQueue.add(request);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        story = stories.get(position);

        story.setViews(story.getViews()+1);
        Intent intent = new Intent(RetrieveHonestStory.this,DetailedHonestActivity.class);
        intent.putExtra("keyHonestStory",story);
        startActivity(intent);

        Task t = new Task();
        t.execute();
        Toast.makeText(RetrieveHonestStory.this,"You clicked"+story.getUsername(),Toast.LENGTH_LONG).show();

    }

    class  Task extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            StringRequest request = new StringRequest(Request.Method.POST, Util.UPDATE_VIEWS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("success");
                        String message = jsonObject.getString("message");

                        if(success == 1){

                            //Toast.makeText(ManageAccountActivity.this,message,Toast.LENGTH_LONG).show();
                            //Intent i = new Intent(ManageAccountActivity.this,SplashActivity.class);
                            //startActivity(i);
                            // finish();
                            Log.i("success",message);
                        }else{
                            // Toast.makeText(ManageAccountActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                        // progressDialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();

                        Log.i("exception",e.getMessage());
                        //progressDialog.dismiss();
                        //Toast.makeText(ManageAccountActivity.this,"Some Exception"+e,Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("error",error.getMessage());

                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> map = new HashMap<>();
                    map.put("views",String.valueOf(story.getViews()));
                    map.put("sid",String.valueOf(story.getStoryId()));

                    return map;
                }
            };
            requestQueue.add(request);request.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);

            return null;
        }


    }

    @Override
    public void onRefresh() {
        stories.clear();
        retrieveHonestStory();
    }

}
