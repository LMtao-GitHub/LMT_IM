package jssvc.lmtao.lmt_im.controller.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(),R.layout.fragment_chat,null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.lv_chat);
    }

    private void initData() {
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);

    }

    //刷新页面
    private void refreshContact() {


        }




    private EMMessageListener emMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            Log.d("打印", messages.get(0).getFrom()+"| "+messages.get(0).getBody());
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setHxid(messages.get(0).getFrom());
            msgInfo.setStatus(MsgInfo.MsgStatus.UNREAD);
            msgInfo.setMsg(messages.get(0).getBody().toString());
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



}
