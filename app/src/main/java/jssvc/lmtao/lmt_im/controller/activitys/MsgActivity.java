package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.model.Model;

public class MsgActivity extends AppCompatActivity {

    public static final String CHAT_HXID ="chat_hxid" ;
    private String fromId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initData();
    }
    private void initData() {
        //创建一个会话
        fromId = getIntent().getStringExtra(CHAT_HXID);
        Log.d(Model.TAG, "initData: "+fromId);
    }
}
