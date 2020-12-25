package com.example.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_add,note_back,camera,jisuanqi;
    private ListView lv;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private Cursor cursor;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initView();
    }

    public void initView(){
        lv = findViewById(R.id.list);
        bt_add = findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        note_back = findViewById(R.id.note_back);
        note_back.setOnClickListener(this);
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(this);
        jisuanqi = findViewById(R.id.jisuanqi);
        jisuanqi.setOnClickListener(this);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                cursor.moveToPosition(position);
                Intent i = new Intent(NoteActivity.this, SelectActivity.class);
                i.putExtra(NotesDB.ID,
                        cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT, cursor.getString(cursor
                        .getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,
                        cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                i.putExtra(NotesDB.PATH,
                        cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO,
                        cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.bt_add:
                intent= new Intent(this,AddActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
            case R.id.note_back:
                finish();
                break;
            case R.id.camera:
                intent= new Intent(this,CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.jisuanqi:
                intent= new Intent(this,JisuanqiActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    public void selectDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,
                null, null);
        adapter = new MyAdapter(this, cursor);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

}