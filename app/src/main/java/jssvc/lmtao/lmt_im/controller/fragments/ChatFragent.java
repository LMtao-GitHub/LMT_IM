package jssvc.lmtao.lmt_im.controller.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.controller.activitys.ChatActivity;
import jssvc.lmtao.lmt_im.controller.adapter.ChatAtapter;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.MsgInfo;

//会话页面
public class ChatFragent extends Fragment {
    private ListView listView;
    private ChatAtapter atapter;
    private List<MsgInfo> msgInfos;
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
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                fromId = msgInfos.get(position).getFriend_id();
                intent.putExtra("fromId",fromId);
                Model.getInstance().getManagerDB().getMsgTableDao().update(fromId);
                refreshContact();
                startActivity(intent);
            }
        });

    }

/*    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        MsgInfo msgInfo = (MsgInfo) listView.getItemAtPosition(position);
        fromId = msgInfo.getFriend_id();
        Log.d("打印", "onItemClick: "+fromId);

    }*/

    //刷新页面
    private void refreshContact() {

        msgInfos = Model.getInstance().getManagerDB().getMsgTableDao().getMsgInfo();
        Log.d("打印", ": " + msgInfos);

        //jiaoyan
        if (msgInfos != null && msgInfos.size() > 0) {

            atapter = new ChatAtapter(getActivity(), R.layout.item_chat, msgInfos);
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
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setFriend_id(messages.get(0).getFrom());
        //查询是否有阅读，没有就+1；
        msgInfo.setIs_read_msg(0);
        msgInfo.setMsg(messages.get(0).getBody().toString());
        msgInfo.setId(messages.get(0).getFrom());
        Model.getInstance().getManagerDB().getMsgTableDao().addMsg(msgInfo);
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



}
