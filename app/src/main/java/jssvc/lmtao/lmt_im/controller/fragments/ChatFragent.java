package jssvc.lmtao.lmt_im.controller.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.controller.activitys.MsgActivity;
import jssvc.lmtao.lmt_im.controller.adapter.ChatAtapter;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.ChatInfo;
import jssvc.lmtao.lmt_im.model.bean.MsgInfo;
import jssvc.lmtao.lmt_im.model.dao.MsgTable;

//会话页面
public class ChatFragent extends Fragment {
    private ListView listView;
    private ChatAtapter atapter;
    private List<ChatInfo> chatInfos;
    private String fromId;
    private LocalBroadcastManager mLBM;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(),R.layout.fragment_chat,null);
        initView(view);
        initData();
        refreshContact();
        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.lv_chat);
    }

    private void initData() {
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MsgActivity.class);
                fromId = chatInfos.get(position).getFriend_id();
                intent.putExtra(MsgActivity.CHAT_HXID,fromId);
                Model.getInstance().getManagerDB().getChatTableDao().update(fromId);
                refreshContact();
                startActivity(intent);
            }
        });

    }
    //刷新页面
    private void refreshContact() {
        chatInfos = Model.getInstance().getManagerDB().getChatTableDao().getMsgInfo();
        //jiaoyan
        if (chatInfos != null && chatInfos.size() > 0) {

            atapter = new ChatAtapter(getActivity(), R.layout.item_chat, chatInfos);
            listView.setAdapter(atapter);
        }
    }



    private EMMessageListener emMessageListener = new EMMessageListener() {
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

    private void data(List<EMMessage> messages) {
        //最新记录数据
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setFriend_id(messages.get(0).getFrom());
        //查询是否有阅读，没有就+1；
        chatInfo.setIs_read_msg(0);
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
        add(chatInfo,msgInfo);
        if (getActivity()==null){
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshContact();
            }
        });

    }
    private void add(ChatInfo chatInfo,MsgInfo msgInfo){


    }



}
