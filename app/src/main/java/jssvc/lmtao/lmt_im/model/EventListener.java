package jssvc.lmtao.lmt_im.model;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import jssvc.lmtao.lmt_im.model.bean.InvationInfo;
import jssvc.lmtao.lmt_im.model.bean.UserInfo;
import jssvc.lmtao.lmt_im.utils.Constants;
import jssvc.lmtao.lmt_im.utils.SpUtils;

//全局事件监听类
public class EventListener {

    private Context context;
    private final LocalBroadcastManager mLBM;

    public EventListener(Context context) {
        this.context = context;
        //chuangjian一个广播的管理对象
        mLBM = LocalBroadcastManager.getInstance(context);
        //注册一个联系人裱花的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }

    //注册一个联系人裱花的监听
    private final EMContactListener emContactListener=new EMContactListener() {
        //lianxiren增加后执行
        @Override
        public void onContactAdded(String hxid) {
            //数据更新
            Model.getInstance().getManagerDB().getContactTableDao().saveContact(new UserInfo(hxid),true);

            //发送联系人de广播
            mLBM.sendBroadcast(new Intent(Constants.CONTACT_CHANGED));


        }

        //删除后
        @Override
        public void onContactDeleted(String hxid) {

            //更新
            Model.getInstance().getManagerDB().getContactTableDao().deleteContactHxById(hxid);
            Model.getInstance().getManagerDB().getInviteTableDao().removeInvatation(hxid);
            //
            mLBM.sendBroadcast(new Intent(Constants.CONTACT_CHANGED));


        }

        //接受到新邀请
        @Override
        public void onContactInvited(String hxid, String reason) {
            //数据库更新
            InvationInfo invitationInfo=new InvationInfo();
            invitationInfo.setUser(new UserInfo(hxid));
            invitationInfo.setReason(reason);
            invitationInfo.setStatus(InvationInfo.InvitationStatus.NEW_INVITE);
            Model.getInstance().getManagerDB().getInviteTableDao().addInvitation(invitationInfo);

            //红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);

            //发送邀请信息变化
            mLBM.sendBroadcast(new Intent(Constants.CONTACT_INVITE_CHANGED));

        }


        //同意邀请后
        @Override
        public void onFriendRequestAccepted(String hxid) {
            InvationInfo invationInfo=new InvationInfo();
            invationInfo.setUser(new UserInfo(hxid));
            invationInfo.setStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);//别人同意你的邀请

            Model.getInstance().getManagerDB().getInviteTableDao().addInvitation(invationInfo);

            //红点
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.CONTACT_INVITE_CHANGED));


        }

        //拒绝
        @Override
        public void onFriendRequestDeclined(String username) {
            //红点
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);
            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.CONTACT_INVITE_CHANGED));


        }
    };
}
