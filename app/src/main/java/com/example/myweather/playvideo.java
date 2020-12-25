package com.example.myweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class playvideo extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;
    private Button b1;
    private Button b2;
    private Button b3;
    private  Button b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo);
        videoView=findViewById(R.id.video_view);
        b1=findViewById(R.id.play);
        b2=findViewById(R.id.pause);
        b3=findViewById(R.id.replay);
        b4=findViewById(R.id.fanhui);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(playvideo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(playvideo.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            initvideoPlayer();
        }
    }

    private void initvideoPlayer() {
        File file = new File(getExternalFilesDir(null),"节气.mp4");
        videoView.setVideoPath(file.getPath());
    }
    @Override
    public void onRequestPermissionsResult(int requesCode , String[] permission,int[] grantResults) {
        switch (requesCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initvideoPlayer();
                } else {
                    Toast.makeText(this, "拒绝访问", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.play:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.replay:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
            case R.id.fanhui:
                Intent intent = new Intent(playvideo.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }
}