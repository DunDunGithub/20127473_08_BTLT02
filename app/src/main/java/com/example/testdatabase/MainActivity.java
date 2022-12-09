package com.example.testdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDatabase db;
    ListView lv;
    ArrayList<userInfo> userList;
    userInfoAdapter adapter;
    EditText edtTen;
    Button btnThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lvNoiDung);
        edtTen = findViewById(R.id.edtTen);
        btnThem = findViewById(R.id.btnThem);
        userList = new ArrayList<>();
        adapter = new userInfoAdapter(this, R.layout.user_information, userList);
        lv.setAdapter(adapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtTen.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Vui long nhap ten", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.queryData("INSERT INTO UserInfo VALUES ('"+name+"', '123456', 'US2', 'DEF', 5, 24, 1, 4)");
                getDataAction();
            }
        });

        //Create database
        db = new MyDatabase(this, "ProManager.sqlite", null, 1);

        //Create table
        String createUserInfo = "CREATE TABLE IF NOT EXISTS UserInfo(username VARCHAR(100) PRIMARY KEY," +
                "pass VARCHAR(100)," +
                "fullname NVARCHAR(100)," +
                "overview NVARCHAR(1000)," +
                "totalTasks smallint," +
                "totalHours smallint," +
                "currentTasks smallint," +
                "currentFinished smallint)";
        db.queryData(createUserInfo);

        //Add data
        String addStatement1 = "INSERT INTO UserInfo VALUES ('username1', '123456', 'US1', 'ABC', 10, 30, 3, 7)";
        String addStatement2 = "INSERT INTO UserInfo VALUES ('username2', '123456', 'US2', 'DEF', 5, 24, 1, 4)";
//        db.queryData(addStatement1);
//        db.queryData(addStatement2);

        //Get data
        getDataAction();

    }

    private void getDataAction() {
        Cursor dataUserInfo = db.getData("SELECT * FROM UserInfo");
        userList.clear();
        while(dataUserInfo.moveToNext()){
            String username = dataUserInfo.getString(0);
            String pass = dataUserInfo.getString(1);
            String fullname = dataUserInfo.getString(2);
            String overview = dataUserInfo.getString(3);

//            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            userList.add(new userInfo(username, pass, fullname, overview));
        }
        adapter.notifyDataSetChanged();
    }
}