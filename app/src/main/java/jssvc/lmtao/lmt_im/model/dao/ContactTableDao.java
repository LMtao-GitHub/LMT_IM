package jssvc.lmtao.lmt_im.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jssvc.lmtao.lmt_im.model.bean.UserInfo;
import jssvc.lmtao.lmt_im.model.db.HelperDB;

//联系人表的操作
public class ContactTableDao {
    private HelperDB helper;
    public ContactTableDao(HelperDB helper) {
        this.helper=helper;
    }

    //获取所有联系人
   public List<UserInfo> getContacts(){
        //获取服务器链接
       SQLiteDatabase db=helper.getReadableDatabase();
       //执行查询语句
       String sql="select * from "+ ContactTable.TAB_NAME + " where "+ContactTable.COL_IS_CONTACT+"=1";
       Cursor cursor = db.rawQuery(sql, null);
       /*封装*/
       List<UserInfo>userInfos=new ArrayList<>();//所有联系人信息
       while (cursor.moveToNext()){
           UserInfo userInfo = new UserInfo();
           userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
           userInfo.setPhoto(cursor.getInt(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
           userInfo.setNickName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICKNAME)));
           userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
           userInfos.add(userInfo);
       }
       //关闭支援
       cursor.close();

       //返回数据
        return userInfos;
   }
   //通过服务器ID获取联系人
    public UserInfo getContactsByHxId(String hxid){
        if(hxid==null){
            return null;
        }
        //获取数据库链接
        SQLiteDatabase db=helper.getReadableDatabase();

        //执行chaxun语句
        String sql="select * from " + ContactTable.TAB_NAME+ " where " + ContactTable.COL_HXID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});

        /*封装*/
        UserInfo userInfo=null;
        if(cursor.moveToNext()){
            userInfo = new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setPhoto(cursor.getInt(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
            userInfo.setNickName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICKNAME)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));

        }

        //关闭
        cursor.close();

        //返回

        return userInfo;

    }

    //通过服务器ID获得用户联系人信息
    public List<UserInfo>getContactsByHxId(List<String> hxids){
        if (hxids==null||hxids.size()<=0){
            return null;
        }
        List<UserInfo>contacts=new ArrayList<>();
        //遍历查找
        for (String hxid:hxids){
            UserInfo contact = getContactsByHxId(hxid);
            contacts.add(contact);
        }
        return contacts;
    }

    //保存单个联系人
    public void saveContact(UserInfo user,boolean isMyContact){
        if(user==null){
            return;
        }
        SQLiteDatabase db = helper.getReadableDatabase();

        /*db.insert()在后方添加
        * db.replace()有相同是替换修改，没有在添加*/
        ContentValues values=new ContentValues();
        values.put(ContactTable.COL_HXID,user.getHxid());
        values.put(ContactTable.COL_NAME,user.getName());
        values.put(ContactTable.COL_NICKNAME,user.getNickName());
        values.put(ContactTable.COL_PHOTO,user.getPhoto());
        /*boolean型转化int型  ?1:0 结构 */
        values.put(ContactTable.COL_IS_CONTACT,isMyContact?1:0);

        db.replace(ContactTable.TAB_NAME,null,values);

    }
    //保存联系人信息
    public void saveContact(List<UserInfo>contacts,boolean isMyContact){
        if (contacts==null||contacts.size()<=0){
            return;
        }
        for (UserInfo contact:contacts){
            saveContact(contact,isMyContact);
        }
    }
    //删除联系人
    public void deleteContactHxById(String hxid){

        if (hxid!=null){
            SQLiteDatabase db=helper.getReadableDatabase();
            /*删除表，id=？  new 窜进来到的id哪一列*/
            db.delete(ContactTable.TAB_NAME,ContactTable.COL_HXID+"=?",new String[]{hxid});
            Log.d("wwww", "deleteContactHxById: 删除成功");
        }
    }
}
