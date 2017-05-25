package com.codeogic.negruption;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

public class RetrieveHonestStory extends AppCompatActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    ListView honestStories;
    ArrayList<StoryBean> stories;
    HonestStoryAdapter adapter;
    StoryBean storyBean;
    RequestQueue requestQueue;
    //ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_honest_story);
        honestStories = (ListView)findViewById(R.id.listStories_honest);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshh);
       /* progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);*/

        requestQueue = Volley.newRequestQueue(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                retrieveHonestStory();
            }
        });

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
                    String username="",title="",description="",privacy="",u="",category ="";

                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        username = jObj.getString("name");
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
                                stories.add(new StoryBean(0, sid, title, null, null, description, null, null, null, u,views,null,0));
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
        storyBean = stories.get(position);
        Toast.makeText(RetrieveHonestStory.this,"You clicked"+storyBean.getUsername(),Toast.LENGTH_LONG).show();
        //int c = count++;

    }

    @Override
    public void onRefresh() {
        retrieveHonestStory();
    }
}
