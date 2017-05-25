package com.codeogic.negruption;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GOD on 5/23/2017.
 */

public class YourStoriesAdapter extends ArrayAdapter<StoryBean> {
    Context context;
    int resource,views=0,newView=0;
    ArrayList<StoryBean> storyList;
    RequestQueue requestQueue;
    StoryBean story;


    public YourStoriesAdapter(Context context,  int resource,  ArrayList<StoryBean> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        storyList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);
        requestQueue = Volley.newRequestQueue(getContext());


        TextView txtName = (TextView)view.findViewById(R.id.textViewyName);
        TextView txtTitle = (TextView)view.findViewById(R.id.textViewyStoryTitle);
        TextView txtDescription = (TextView)view.findViewById(R.id.textViewyStoryDesc);
        TextView txtViews = (TextView)view.findViewById(R.id.textViewyViews);

        TextView txtReadMore = (TextView)view.findViewById(R.id.textViewyReadMore);

        story = storyList.get(position);
        txtName.setText(story.getUsername());
        txtTitle.setText(story.getStoryTitle());
        txtDescription.setText(story.getStoryDesc());
        txtViews.setText(String.valueOf(story.getViews()));
        if (story.getStatus()==0){
            txtTitle.setTextColor(Color.RED);
        }
        else if (story.getStatus()==1){
            txtTitle.setTextColor(Color.GREEN);
        }
        txtReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.textViewyReadMore){
                    Toast.makeText(getContext(),"Read More",Toast.LENGTH_LONG).show();
                    StoryBean storyBean = storyList.get(position);
                    //story.setViews(newView);
                    notifyDataSetChanged();

                    if (storyBean.getCategory().equals("Corrupt")){
                        Intent intent = new Intent(getContext(),StoryActivity.class);
                        intent.putExtra("keyStory",storyBean);
                        getContext().startActivity(intent);
                    }
                    else if (storyBean.getCategory().equals("Honest")) {
                        Intent intent = new Intent(getContext(), DetailedHonestActivity.class);
                        intent.putExtra("keyHonestStory",storyBean);
                        getContext().startActivity(intent);
                    }

                }
            }
        });
        return view;
    }

}
