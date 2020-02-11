package jssvc.lmtao.lmt_im.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.model.bean.MsgInfo;

public class MsgAdapter extends ArrayAdapter<MsgInfo> {
    private int resourceId;
    private RelativeLayout rl_msg_left,rl_msg_right;
    private TextView tv_msg_left,tv_msg_right,tv_msg_data;

    public MsgAdapter(@NonNull Context context, int resource, @NonNull List<MsgInfo> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MsgInfo msgInfo = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        tv_msg_data = (TextView) view.findViewById(R.id.tv_msg_data);
        tv_msg_left = (TextView) view.findViewById(R.id.tv_msg_left);
        tv_msg_right = (TextView) view.findViewById(R.id.tv_msg_right);
        rl_msg_left = (RelativeLayout)view.findViewById(R.id.rl_msg_left);
        rl_msg_right = (RelativeLayout)view.findViewById(R.id.rl_msg_right);

        if(msgInfo.getIs_mine_msg() == 0){
            //对方消息
            tv_msg_left.setText(msgInfo.getMsg());
            rl_msg_left.setVisibility(View.VISIBLE);
            rl_msg_right.setVisibility(View.GONE);
        }else if (msgInfo.getIs_mine_msg() == 1){
            tv_msg_right.setText(msgInfo.getMsg());
            rl_msg_left.setVisibility(View.GONE);
            rl_msg_right.setVisibility(View.VISIBLE);
        }
        tv_msg_data.setText(msgInfo.getData_msg());
        return view;
    }
}
