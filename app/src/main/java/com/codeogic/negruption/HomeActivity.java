package com.codeogic.negruption;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView title;
    ListView listStories;
    ArrayList<StoryBean> stories;
    StoryAdapter adapter;
    StoryBean storyBean;
    RequestQueue requestQueue;
    //ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // title=(TextView)findViewById(R.id.textViewTitle);
        listStories = (ListView)findViewById(R.id.listStories);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        sharedPreferences=getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
       editor= sharedPreferences.edit();

        String username=sharedPreferences.getString(Util.PREFS_KEYUSERNAME,"");
        //title.setText("Welcome Home "+username);
       /* progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
*/
        requestQueue = Volley.newRequestQueue(this);
       // retrieveStory();

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                retrieveStory();
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_experience) {
            Intent i=new Intent(HomeActivity.this,AddStoryActivity.class);
            startActivity(i);



        } else if (id == R.id.nav_honestStories) {
            Intent i=new Intent(HomeActivity.this,RetrieveHonestStory.class);
            startActivity(i);

        } else if (id == R.id.nav_location) {
            Intent i=new Intent(HomeActivity.this,MapsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_manageAccount) {
            Intent i=new Intent(HomeActivity.this,ManageAccountActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_questionnaire) {

        } else if (id == R.id.nav_your_story) {
            Intent i = new Intent(HomeActivity.this,YourStoriesActivity.class);
            startActivity(i);

        } else if (id== R.id.nav_audio){
            Intent i=new Intent(HomeActivity.this,SecretAudioActivity.class);
            startActivity(i);
        } else if (id==R.id.nav_logout){
            editor.clear();
            editor.commit();
            Intent i = new Intent(HomeActivity.this,SplashActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void retrieveStory(){
        //progressDialog.show();
        stories = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, Util.RETRIEVE_STORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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


                        if(cat.equals("Corrupt")){
                            stories.add(new StoryBean(0,sid,title,dep,pl,description,img,aud,vid,u,views,null,0));
                        }

                    }
                    adapter = new StoryAdapter(HomeActivity.this,R.layout.stories_list_item,stories);

                    listStories.setAdapter(adapter);
                    listStories.setOnItemClickListener(HomeActivity.this);
                   // progressDialog.dismiss();

                    swipeRefreshLayout.setRefreshing(false);

                }catch (Exception e){
                    e.printStackTrace();
                    //progressDialog.dismiss();
                    Toast.makeText(HomeActivity.this,"Some Exception"+ e,Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // progressDialog.dismiss();
                Toast.makeText(HomeActivity.this,"Some Error"+error,Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        storyBean = stories.get(position);
        Toast.makeText(HomeActivity.this,"You Clicked"+storyBean.getUsername(),Toast.LENGTH_LONG).show();
        Log.i("HomeActivity","homeActivity");


    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        retrieveStory();

    }
}

