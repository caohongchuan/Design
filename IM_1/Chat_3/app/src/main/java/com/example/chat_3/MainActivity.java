package com.example.chat_3;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText passwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button button1 = (Button) findViewById(R.id.Login);
        Button button2 = (Button) findViewById(R.id.forget_pd);
        Button button3 = (Button) findViewById(R.id.register);

        username = (EditText) findViewById(R.id.username);
        passwd = (EditText) findViewById(R.id.passwd);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.Login:
                String un = username.getText().toString();
                String pd = passwd.getText().toString();

                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);

                break;
            case R.id.forget_pd:
                break;
            case R.id.register:
                break;
            default:
                break;
        }
    }




}
