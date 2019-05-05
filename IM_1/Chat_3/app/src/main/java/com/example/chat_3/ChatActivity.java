package com.example.chat_3;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class  ChatActivity extends AppCompatActivity {

    private EditText inputText;
    private Button send;
    private static ListView msgListView;
    private static MsgAdapter adapter;
    private static List<Msg> msgList = new ArrayList<Msg>();


    private static final String TAG = "TAG";
    private static final String HOST = "192.168.43.47";
    private static final int PORT = 9000;
    private BufferedReader in;

    private PrintWriter printWriter;
    private ExecutorService mExecutorService = null;
    private String receiveMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_chat);

        //open the thread pool
        mExecutorService = Executors.newCachedThreadPool();

        //get connect
        connect();

        //get the button
        adapter = new MsgAdapter(ChatActivity.this, R.layout.msg_item, msgList);
        inputText = (EditText)findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.send);
        msgListView = (ListView)findViewById(R.id.msg_list_view);

        msgListView.setAdapter(adapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiveMsg = inputText.getText().toString();
                if(!"".equals(receiveMsg)) {
                    Msg msg = new Msg(receiveMsg, Msg.TYPE_SEND);
                    msgList.add(msg);

                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");

                    send(receiveMsg);
                }
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        //close the thread
        send("CLOSE");
        mExecutorService.shutdown();
    }




    public void send(String xx) {
        mExecutorService.execute(new sendService(xx));
    }


    private class sendService implements Runnable {
        private String msg;

        sendService(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            printWriter.println(this.msg);
        }
    }



    public void connect() {
        mExecutorService.execute(new connectService());  //在一个新的线程中请求 Socket 连接
    }

    private class connectService implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(HOST, PORT);
                socket.setSoTimeout(60000);

                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

                receiveMsg();
            } catch (Exception e) {
                Log.e(TAG, ("connectService:" + e.getMessage()));   //如果Socket对象获取失败，即连接建立失败，会走到这段逻辑
            }
        }
    }

    private void receiveMsg() {
        try {
            while (true) {
                if ((receiveMsg = in.readLine()) != null) {
                    Log.d(TAG, "receiveMsg:" + receiveMsg);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addList(receiveMsg,Msg.TYPE_RECEIVED);
                         //   mTextView.setText(receiveMsg + "\n\n" + mTextView.getText());
                        }
                    });
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "receiveMsg: ");
            e.printStackTrace();
        }
    }

    public static void addList(String msg,int type){

        if(!"".equals(msg)) {
            Msg msg1=new Msg(msg,type);
            msgList.add(msg1);

            adapter.notifyDataSetChanged();
            msgListView.setSelection(msgList.size());
        }

    }

}