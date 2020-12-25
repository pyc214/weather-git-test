package com.example.myweather;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SqlActivity extends AppCompatActivity implements View.OnClickListener{
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        dbHelper = new MyDatabaseHelper(this, "ShopStore.db",null,1);//数据库名称ShopStore.db
        Button sql_btn = findViewById(R.id.test);
        sql_btn.setOnClickListener(this);
    }
    public void onClick(View v)
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_name","1245");
        values.put("pwd","123456");
        values.put("phone",15079554);
        db.insert("Shop",null,values);
        values.clear();
        dbHelper.close();
    }
}