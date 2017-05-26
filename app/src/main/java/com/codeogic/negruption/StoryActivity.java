package com.codeogic.negruption;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class StoryActivity extends AppCompatActivity {
    @InjectView(R.id.textViewsName)
    TextView txtUserName;

    @InjectView(R.id.textViewsStoryTitle)
    TextView txtStoryTitle;

    @InjectView(R.id.textViewsStoryDesc)
    TextView txtStoryDesc;

    @InjectView(R.id.textViewsDepartment)
    TextView txtDepartment;

    @InjectView(R.id.textViewsPlace)
    TextView txtPlace;

    @InjectView(R.id.sImageView)
    ImageView imageView;

    @InjectView(R.id.btnsPlay)
    Button btnPlay;

    @InjectView(R.id.btnsPause)
    Button btnPause;

    @InjectView(R.id.btnsStop)
    Button btnStop;




    MediaPlayer mediaPlayer;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent rcv = getIntent();
        StoryBean story = (StoryBean)rcv.getSerializableExtra("keyStory");

        txtUserName.setText(story.getUsername());
        txtStoryTitle.setText(story.getStoryTitle());
        txtDepartment.setText(story.getDepartment());
        txtPlace.setText(story.getPlace());
        txtStoryDesc.setText(story.getStoryDesc());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");



        progressDialog.show();


        Log.i("info",story.toString());
        Toast.makeText(this,story.getImageProof() + story.getAudioProof() + story.getVideoProof(),Toast.LENGTH_LONG).show();


        if (!story.getImageProof().equals("null")){
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(this).load(story.getImageProof()).into(imageView);
            progressDialog.dismiss();
        }

        if (!story.getAudioProof().equals("null")){
            btnPlay.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.VISIBLE);

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(this, Uri.parse(story.getAudioProof()));
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }




    public void clickPlay(View view){
        mediaPlayer.start();

    }

    public void clickPause(View view){
        mediaPlayer.pause();

    }

    public void clickStop(View view){
        mediaPlayer.stop();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
