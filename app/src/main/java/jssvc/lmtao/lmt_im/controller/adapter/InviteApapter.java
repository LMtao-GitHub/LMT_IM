package jssvc.lmtao.lmt_im.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.model.bean.InvationInfo;
import jssvc.lmtao.lmt_im.model.bean.UserInfo;

public class InviteApapter extends BaseAdapter {
    private OnInviteListener onInviteListener;
    private Context context;
    private List<InvationInfo>mInvationInfos=new ArrayList<>();
    private ViewHodler hoder;


    public InviteApapter(Context context,OnInviteListener onInviteListener) {
        this.context=context;
        this.onInviteListener=onInviteListener;
    }

    //刷新数据的方法
    public void refresh(List<InvationInfo>invationInfos){
        if (invationInfos!=null&&invationInfos.size()>=0){
            mInvationInfos.clear();
            mInvationInfos.addAll(invationInfos);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mInvationInfos==null? 0:mInvationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1获取或创建
        ViewHodler hodler=null;
        if (convertView==null){
                hodler=new ViewHodler();
            convertView=View.inflate(context, R.layout.item_invite,null);

            hodler.name=(TextView)convertView.findViewById(R.id.tv_invite_name);
            hodler.reason=(TextView)convertView.findViewById(R.id.tv_invite_reason);
            hodler.accept=(Button)convertView.findViewById(R.id.bt_invite_accept);
            hodler.reject=(Button)convertView.findViewById(R.id.bt_invite_reject);
            convertView.setTag(hodler);
        }else {
            hodler=(ViewHodler)convertView.getTag();
        }
        hoder = new ViewHodler();

        //2获取ietm数据
        InvationInfo invationInfo = mInvationInfos.get(position);

        //3显示当前item
        UserInfo user=invationInfo.getUser();
        if (user!=null){
            //联系人
            hodler.name.setText(invationInfo.getUser().getName());

            hodler.accept.setVisibility(View.GONE);
            hodler.reject.setVisibility(View.GONE);

            //原因
            if(invationInfo.getStatus()==InvationInfo.InvitationStatus.NEW_INVITE){
                if(invationInfo.getReason()==null){
                    hodler.reason.setText("添加好友");
                }else {
                    hodler.reason.setText(invationInfo.getReason());
                }
                hodler.accept.setVisibility(View.VISIBLE);
                hodler.reject.setVisibility(View.VISIBLE);

            }else if(invationInfo.getStatus()==InvationInfo.InvitationStatus.INVITE_ACCEPT){
                //接受
                if(invationInfo.getReason()==null){
                    hodler.reason.setText("接受邀请");

                }else {
                    hodler.reason.setText(invationInfo.getReason());
                }

            }else if (invationInfo.getStatus()==InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER){
                //邀请被接受
                if(invationInfo.getReason()==null){
                    hodler.reason.setText("已接受邀请");

                }else {
                    hodler.reason.setText(invationInfo.getReason());
                }
            }

            //接受按钮
            hodler.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onInviteListener.onAccept(invationInfo);
                }
            });
            //拒绝按钮
            hodler.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onInviteListener.onReject(invationInfo);

                }
            });

        }else {
            //群组

        }
        return convertView;
    }
    private class ViewHodler{
        private TextView name;
        private TextView reason;
        private Button accept;
        private Button reject;

    }

    public interface OnInviteListener{
        //联系人接受
        void onAccept(InvationInfo invationInfo);

        //联系热拒绝
        void onReject(InvationInfo invationInfo);
    }
}
