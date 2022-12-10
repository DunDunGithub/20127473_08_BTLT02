package com.example.testdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    ImageView imgSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lvNoiDung);
        edtTen = findViewById(R.id.edtTen);
        btnThem = findViewById(R.id.btnThem);
        imgSearch = findViewById(R.id.imgTimkiem);
        userList = new ArrayList<>();
        adapter = new userInfoAdapter(this, R.layout.user_information, userList);
        lv.setAdapter(adapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtTen.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.queryData("INSERT INTO UserInfo VALUES ('"+name+"', '123456', 'US2', 'DEF', 5, 24, 1, 4)");
                getDataAction();
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtTen.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor dataUserInfo = db.getData("SELECT * FROM UserInfo WHERE username LIKE '%"+content+"%'");
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
                adapter = new userInfoAdapter(MainActivity.this, R.layout.user_information, userList);
                lv.setAdapter(adapter);
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

    public void dialogUpdate(String pass, final String username){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);

        EditText edtSua = (EditText) dialog.findViewById(R.id.edtSua);
        Button btnSua = (Button) dialog.findViewById(R.id.btnSua);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);

        edtSua.setText(pass);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPass = edtSua.getText().toString().trim();
                if(TextUtils.isEmpty(newPass)){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                db.queryData("UPDATE UserInfo SET pass = '"+newPass+"' WHERE username = '"+username+"'");
                dialog.dismiss();
                getDataAction();
            }
        });

        dialog.show();
    }

    public void dialogDelete(String username){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa "+username+"?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.queryData("DELETE FROM UserInfo WHERE username = '"+username+"'");
                getDataAction();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
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