package com.example.testdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDatabase db;
    ListView lv;
    ArrayList<userInfo> userList;
    userInfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lvNoiDung);
        userList = new ArrayList<>();
        adapter = new userInfoAdapter(this, R.layout.user_information, userList);
        lv.setAdapter(adapter);

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
        db.queryData(addStatement1);
        db.queryData(addStatement2);

        //Get data
        Cursor dataUserInfo = db.getData("SELECT * FROM UserInfo");
        while(dataUserInfo.moveToNext()){
            String name = dataUserInfo.getString(0);
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            userList.add(new userInfo());
        }
    }
}