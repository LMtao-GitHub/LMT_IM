package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.controller.adapter.MsgAdapter;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.MsgInfo;

public class MsgActivity extends AppCompatActivity {


    private MsgAdapter atapter;
    private List<MsgInfo> msgInfos;
    private Button bt_msg_back,bt_msg_send;
    private TextView tv_msg_name;
    private ListView lv_msg;


    public static final String CHAT_HXID ="chat_hxid" ;
    private String fromId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        initView();
        initData();
    }
    private BroadcastReceiver msgChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };



    private void initView() {
        bt_msg_back = (Button)findViewById(R.id.bt_msg_back);
        tv_msg_name = (TextView)findViewById(R.id.tv_msg_name);
        lv_msg = (ListView)findViewById(R.id.lv_msg);
        bt_msg_back = (Button)findViewById(R.id.bt_msg_back);
        bt_msg_send = (Button)findViewById(R.id.bt_msg_send);
    }

    private void initData() {

        bt_msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//设置广播的名字（设置Action）
                intent.setAction("jssvc.lmtao.lmt_im.REFRESHCONTACT");
                sendBroadcast(intent);
                Log.d(Model.TAG, "onClick: ");
            }
        });

        fromId = getIntent().getStringExtra(CHAT_HXID);
        tv_msg_name.setText(fromId);
        refreshContact();

    }
    //刷新页面
    public void refreshContact() {
         msgInfos = Model.getInstance().getManagerDB().getMsgTableDao().getMsg(fromId);
        //jiaoyan
        if (msgInfos != null && msgInfos.size() > 0) {
            atapter = new MsgAdapter(MsgActivity.this, R.layout.item_msg, msgInfos);
            lv_msg.setAdapter(atapter);
        }
    }





    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(Model.TAG, "onReceive: "+intent.getAction());

        }
    }
}
