package com.example.myweather;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private Button saveBtn,deleteBtn;
    private EditText ettext;
    private ImageView c_img;
    private VideoView v_video;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;
    private String val;
    private File phoneFile, videoFile;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        saveBtn = findViewById(R.id.bt_save);
        deleteBtn = findViewById(R.id.bt_back);


        ettext =findViewById(R.id.et_text);
        c_img = (ImageView) findViewById(R.id.c_img);
        v_video = (VideoView) findViewById(R.id.c_video);
        saveBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                addDB();
                finish();
                break;
            case R.id.bt_back:
                finish();
                break;
            default:
                break;
        }
    }

    public void addDB(){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT, ettext.getText().toString());
        cv.put(NotesDB.TIME, getTime());
        cv.put(NotesDB.PATH, phoneFile + "");
        cv.put(NotesDB.VIDEO, videoFile + "");
        dbWriter.insert(NotesDB.TABLE_NAME, null, cv);
    }

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Bitmap bitmap = BitmapFactory.decodeFile(phoneFile
                    .getAbsolutePath());
            c_img.setImageBitmap(bitmap);
        }
        if (requestCode == 2) {
            v_video.setVideoURI(Uri.fromFile(videoFile));
            v_video.start();
        }
    }
}