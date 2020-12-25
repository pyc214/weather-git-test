package com.example.myweather;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = SignupActivity.class.getName();
    private String str1="";
    private EditText username;
    private EditText password;
    private EditText password2;
    private Button button;
    private String str2="";
    private String str="";
    private Code verifyCode;
    private EditText et_phoneCode;
    private ImageView image;
    private MyDatabaseHelper dbHelper;
    String realCode;
    private Button login;
    private Button reg;
    // private Button back;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_phoneCode =  findViewById(R.id.et_phoneCodes);
        dbHelper= new MyDatabaseHelper(this,"ShopStore.db",null,3);
        button =  findViewById(R.id.login_btn);
        image = findViewById(R.id.iv_showCode);
        //获取工具类生成的图片验证码对象
        image.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
        image.setOnClickListener(new View.OnClickListener() {
            //给注册按钮设置监听
            public void onClick(View v) {
                image.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                Log.v(TAG,"realCode"+realCode);
            }
        });



        login =  findViewById(R.id.login);
        reg=  findViewById(R.id.reg);
        //监听button事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, Login.class);
                startActivity(intent);
            }



        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,SignupActivity.class);
                startActivity(intent);
            }



        });

        //监听button事

        button.setOnClickListener(new View.OnClickListener() {
            //给注册按钮设置监听
            public void onClick (View v) {
                password = findViewById(R.id.et_password);
                username = findViewById(R.id.username);
                password2 = findViewById(R.id.et_password2);
                str=username.getText().toString().trim();
                str1=password.getText().toString().trim();//第一次输入的密码赋值给password
                str2 = password2.getText().toString().trim();//第二次输入的密码赋值给password



                SQLiteDatabase db= dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("user_name",str);
                values.put("pwd",str1);
                String phoneCode = et_phoneCode.getText().toString().toLowerCase();
                //插入ContentValues中的数据
                if (str1.equals(str2) && phoneCode.equals(realCode)) {



                    db.insert("Shop", null,values);
                    //  values.clear();
                    Intent intent = new Intent(SignupActivity.this,Login.class);

                    startActivity(intent);



                }


                //开始添加第一条数据



                else if(phoneCode.equals(realCode)==false){
                    Toast.makeText(getApplication(), " 验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplication(), "    密码不一致，请重新输入", Toast.LENGTH_SHORT).show();

                }
            }

        });
    }
}