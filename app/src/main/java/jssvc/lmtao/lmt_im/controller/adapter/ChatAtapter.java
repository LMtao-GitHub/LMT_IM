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
import jssvc.lmtao.lmt_im.model.bean.MsgInfo;


public class ChatAtapter extends ArrayAdapter<MsgInfo> {

    private MsgInfo msgInfo;
    private int resource;
    private ViewHodler hodler;

    public ChatAtapter(Context context , int resource ,List<MsgInfo> msgInfos) {
        super(context, resource,msgInfos);
        this.resource = resource;
    }
    public MsgInfo getMsgInfo(){
        return msgInfo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        hodler = new ViewHodler();
        msgInfo = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        hodler.tv_chat_name = (TextView)view.findViewById(R.id.tv_chat_name);
        hodler.tv_chat_msg = (TextView)view.findViewById(R.id.tv_chat_msg);
        hodler.tv_chat_dot = (TextView)view.findViewById(R.id.tv_chat_dot);
        hodler.tv_chat_name.setText(getMsgInfo().getHxid());
        hodler.tv_chat_msg.setText(getMsgInfo().getMsg());
        if (getMsgInfo().getStatus()== MsgInfo.MsgStatus.READ){
            hodler.tv_chat_dot.setVisibility(View.GONE);
        }else if (getMsgInfo().getStatus()== MsgInfo.MsgStatus.READ){
            hodler.tv_chat_dot.setVisibility(View.GONE);
        }

        return view;

    }

    public interface OnIsChatReadListener{
        void onread(MsgInfo msgInfo);
        void onUnread(MsgInfo msgInfo);
    }
    private class ViewHodler{
        private TextView tv_chat_name;
        private TextView tv_chat_msg;
        private TextView tv_chat_dot;
    }
}
