package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import jssvc.lmtao.lmt_im.R;

public class ChatActivity extends AppCompatActivity {

    public static final String CHAT_HXID ="chat_hxid" ;
    private String mHxid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initData();
    }

    private void initData() {
        //创建一个会话

        mHxid = getIntent().getStringExtra(CHAT_HXID);

    }
}
