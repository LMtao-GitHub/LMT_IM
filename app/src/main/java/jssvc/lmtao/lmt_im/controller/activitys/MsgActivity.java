package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.controller.adapter.MsgAdapter;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.ChatInfo;
import jssvc.lmtao.lmt_im.model.bean.MsgInfo;

public class MsgActivity extends AppCompatActivity {


    private MsgAdapter atapter;
    private List<MsgInfo> msgInfos;
    private Button bt_msg_back,bt_msg_send;
    private TextView tv_msg_name;
    private ListView lv_msg;
    private EditText et_msg_text;


    public static final String CHAT_HXID ="chat_hxid" ;
    private String fromId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        initView();
        initData();
        refreshContact();
    }




    private void initView() {
        bt_msg_back = (Button)findViewById(R.id.bt_msg_back);
        tv_msg_name = (TextView)findViewById(R.id.tv_msg_name);
        lv_msg = (ListView)findViewById(R.id.lv_msg);
        bt_msg_back = (Button)findViewById(R.id.bt_msg_back);
        bt_msg_send = (Button)findViewById(R.id.bt_msg_send);
        et_msg_text = (EditText)findViewById(R.id.et_msg_text);
    }

    private void initData() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);


        bt_msg_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!et_msg_text.getText().equals("")){
                    data(et_msg_text.getText().toString());
                    et_msg_text.setText("");
                }


            }
        });

        fromId = getIntent().getStringExtra(CHAT_HXID);
        tv_msg_name.setText(fromId);


    }
    EMMessageListener msgListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            data(messages);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };
    //刷新页面
    public void refreshContact() {
         msgInfos = Model.getInstance().getManagerDB().getMsgTableDao().getMsg(fromId);
        //jiaoyan
        if (msgInfos != null && msgInfos.size() > 0) {
            atapter = new MsgAdapter(MsgActivity.this, R.layout.item_msg, msgInfos);
            lv_msg.setAdapter(atapter);
            lv_msg.setSelection(msgInfos.size());

        }
    }

    private void data(List<EMMessage> messages) {
        //最新记录数据
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setFriend_id(messages.get(0).getFrom());
        //查询是否有阅读，没有就+1；
        chatInfo.setIs_read_msg(1);
        chatInfo.setMsg(messages.get(0).getBody().toString());
        chatInfo.setId(messages.get(0).getFrom());
        chatInfo.setIs_mine_msg(0);//0对方消息
        Model.getInstance().getManagerDB().getChatTableDao().addMsg(chatInfo);
        //所有聊天历史记录
        MsgInfo msgInfo = new MsgInfo();
        Log.d(Model.TAG, "data: ");
        msgInfo.setId(String.valueOf(Model.getInstance().getManagerDB().getMsgTableDao().getMsgCount()+1));
        msgInfo.setCount(Model.getInstance().getManagerDB().getMsgTableDao().getMsgCount()+1);
        msgInfo.setIs_mine_msg(0);
        msgInfo.setFriend_id(messages.get(0).getFrom());
        msgInfo.setMsg(messages.get(0).getBody().toString());
        Model.getInstance().getManagerDB().getMsgTableDao().addMsg(msgInfo);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshContact();
            }
        });
    }

    private void data(String text) {
        //最新记录数据
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setFriend_id(fromId);
        //查询是否有阅读，没有就+1；
        chatInfo.setIs_read_msg(1);
        chatInfo.setMsg(text);
        chatInfo.setId(fromId);
        chatInfo.setIs_mine_msg(1);//0对方消息
        Model.getInstance().getManagerDB().getChatTableDao().addMsg(chatInfo);
        //所有聊天历史记录
        MsgInfo msgInfo = new MsgInfo();
        Log.d(Model.TAG, "data: ");
        msgInfo.setId(String.valueOf(Model.getInstance().getManagerDB().getMsgTableDao().getMsgCount()+1));
        msgInfo.setCount(Model.getInstance().getManagerDB().getMsgTableDao().getMsgCount()+1);
        msgInfo.setIs_mine_msg(1);
        msgInfo.setData_msg(Model.getIOS8601Timestamp());
        msgInfo.setFriend_id(fromId);
        msgInfo.setMsg(text);
        Model.getInstance().getManagerDB().getMsgTableDao().addMsg(msgInfo);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EMMessage message = EMMessage.createTxtSendMessage(et_msg_text.getText().toString(), fromId);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                refreshContact();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        Log.d(Model.TAG, "关闭 msgListener");
        finish();
    }

}
