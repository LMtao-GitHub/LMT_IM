package jssvc.lmtao.lmt_im.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import jssvc.lmtao.lmt_im.model.bean.GroupInfo;
import jssvc.lmtao.lmt_im.model.bean.InvationInfo;
import jssvc.lmtao.lmt_im.model.bean.UserInfo;
import jssvc.lmtao.lmt_im.model.db.HelperDB;

/*邀请信息表的操作类*/
public class InviteTableDao {
    private HelperDB helper;

    public InviteTableDao(HelperDB helperDB)     {
        helper=helperDB;
    }


    //添加邀请
    public void addInvitation(InvationInfo invationInfo){
        //获取数据库
        SQLiteDatabase db = helper.getReadableDatabase();

        //执行添加
        ContentValues values=new ContentValues();
        values.put(InviteTable.COL_REASON,invationInfo.getReason());//原因
        values.put(InviteTable.COL_STATUS,invationInfo.getStatus().ordinal());//状态

        UserInfo user = invationInfo.getUser();
        if (user!=null){
            //联系人
            values.put(InviteTable.COL_USER_HXID,invationInfo.getUser().getHxid());
            values.put(InviteTable.COL_USER_NAME,invationInfo.getUser().getName());
        }else {
            //群组
            values.put(InviteTable.COL_GROUP_HXID,invationInfo.getGroup().getGroupId());
            values.put(InviteTable.COL_GROUP_NAME,invationInfo.getGroup().getGroupName());
           values.put(InviteTable.COL_USER_HXID,invationInfo.getGroup().getInvatePerson());
        }

        db.replace(InviteTable.TAB_NAME,null,values);

    }



    //获取所有邀请星系
    public List<InvationInfo>getInvatation(){
        //获取数据库
        SQLiteDatabase db=helper.getReadableDatabase();
        //chax
        String sql="select * from "+InviteTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        List<InvationInfo>invationInfos=new ArrayList<>();
        while (cursor.moveToNext()){
            InvationInfo invationInfo=new InvationInfo();
            invationInfo.setReason(cursor.getString(cursor.getColumnIndex(InviteTable.COL_REASON)));
            invationInfo.setStatus(invitationStatus(cursor.getInt(cursor.getColumnIndex(InviteTable.COL_STATUS))));
            String group=cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID));
            if(group==null){
                //user
                UserInfo userInfo = new UserInfo();
                userInfo.setHxid(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                userInfo.setName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
                userInfo.setNickName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
                invationInfo.setUser(userInfo);/*记住添加到邀请数据里*/
            }else  {
                //group
                GroupInfo groupInfo = new GroupInfo();
                groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID)));
                groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_NAME)));
                groupInfo.setInvatePerson(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                invationInfo.setGroup(groupInfo);
            }
            //添加本次循环的邀请信息
            invationInfos.add(invationInfo);

        }
        //关闭
        cursor.close();
        //返回
        return invationInfos;
    };

    //将int转化为状态
    public InvationInfo.InvitationStatus invitationStatus(int intStatus){
        if (intStatus == InvationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {

            return InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }


    if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
        return InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
    }

    if (intStatus == InvationInfo.InvitationStatus.GROUPO_ACCEPT_APPLICATION.ordinal()) {
        return InvationInfo.InvitationStatus.GROUPO_ACCEPT_APPLICATION;
    }

    if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
        return InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
    }

    if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
        return InvationInfo.InvitationStatus.GROUP_REJECT_INVITE;
    }

    return null;
}

    //删除
    public void removeInvatation(String hxid){
        if (hxid==null){
            return;
        }
        SQLiteDatabase db = helper.getReadableDatabase();
        //zhi型三处
        db.delete(InviteTable.TAB_NAME,InviteTable.COL_USER_HXID+"=?",new String[]{hxid});
    }

    //更新邀请状态
    public void updateInvitationStatus(InvationInfo.InvitationStatus invitationStatus,String hxid){
        if(hxid == null){
            return;
        }
        //
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(InviteTable.COL_STATUS,invitationStatus.ordinal());

        /*表   改那个数据   根据生么改   hxid*/
        db.update(InviteTable.TAB_NAME,values,InviteTable.COL_USER_HXID+"=?",new String[]{hxid});
    }

}
