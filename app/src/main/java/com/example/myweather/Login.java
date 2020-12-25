package com.example.myweather;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener{
    MyDatabaseHelper dbHelper1;
    EditText edittext1;//获取文本框数据
    EditText edittext2;
    Cursor cursor;
    private String username;
    private String pwd;
    //private Button back;
    private Button login;
    private Button reg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn1 = findViewById(R.id.login_btn);
        edittext1 = findViewById(R.id.user_name);//获取文本框数据
        edittext2 = findViewById(R.id.pwd);
        dbHelper1 = new MyDatabaseHelper(this, "ShopStore.db",null,3);
        login =  findViewById(R.id.login);
        reg=  findViewById(R.id.reg);
        //监听button事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
            }



        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,SignupActivity.class);
                startActivity(intent);
            }



        });
        btn1.setOnClickListener(this);
        ActionBar actionbar =  getSupportActionBar();
        if(actionbar != null)
            actionbar.hide();
    }
    public void onClick(View v)
    {

        username = edittext1.getText().toString();
        pwd = edittext2.getText().toString();
        SQLiteDatabase db = dbHelper1.getWritableDatabase();
        cursor = db.rawQuery("select * from Shop where user_name=?",new String[]{username});//数据集合
        //Toast.makeText( Login.this,"获取数值成功！",Toast.LENGTH_SHORT).show();
        if(cursor.moveToFirst())
        {
            do{
                if(pwd.equals(cursor.getString(cursor.getColumnIndex("pwd"))) || pwd == cursor.getString(cursor.getColumnIndex("pwd")))
                {
                    Toast.makeText( Login.this,"登入成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);

                }
            }while(cursor.moveToNext());
            //Toast.makeText( Login.this,"你输入的密码错误！",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText( Login.this,"不存在该用户或用户名输入错误！", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

}