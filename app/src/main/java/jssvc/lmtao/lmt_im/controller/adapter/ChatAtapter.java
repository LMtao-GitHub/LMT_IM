package jssvc.lmtao.lmt_im.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;


import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.model.bean.ChatInfo;


public class ChatAtapter extends ArrayAdapter<ChatInfo> {

    private ChatInfo chatInfo;
    private int resource;
    private ViewHodler hodler;

    public ChatAtapter(Context context , int resource ,List<ChatInfo> chatInfos) {
        super(context, resource, chatInfos);
        this.resource = resource;
    }
    public ChatInfo getChatInfo(){
        return chatInfo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        hodler = new ViewHodler();
        chatInfo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        hodler.tv_chat_name = (TextView)view.findViewById(R.id.tv_chat_name);
        hodler.tv_chat_msg = (TextView)view.findViewById(R.id.tv_chat_msg);
        hodler.tv_chat_dot = (TextView)view.findViewById(R.id.tv_chat_dot);
        hodler.tv_chat_name.setText(getChatInfo().getId());
        hodler.tv_chat_msg.setText(getChatInfo().getMsg());
        if (getChatInfo().getIs_read_msg()== 0){
            hodler.tv_chat_dot.setVisibility(View.VISIBLE);
        }else if (getChatInfo().getIs_read_msg()== 1){
            hodler.tv_chat_dot.setVisibility(View.GONE);
        }

        return view;

    }

    public interface OnIsChatReadListener{
        void onread(ChatInfo chatInfo);
        void onUnread(ChatInfo chatInfo);
    }
    private class ViewHodler{
        private TextView tv_chat_name;
        private TextView tv_chat_msg;
        private TextView tv_chat_dot;
    }
}
