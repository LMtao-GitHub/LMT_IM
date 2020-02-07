package jssvc.lmtao.lmt_im.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import jssvc.lmtao.lmt_im.model.bean.UserInfo;
import jssvc.lmtao.lmt_im.model.db.UserAccountDB;

/*用户数据库的操作*/
public class UserAccountTableDao {
    private final UserAccountDB mHelper;

    public UserAccountTableDao(Context context) {
        mHelper = new UserAccountDB(context);
    }
    //添加用户到数据库中
    public void addAccout(UserInfo user){
        //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行添加操作
        //replace()数据库有就不添加，没有就添加
        ContentValues values=new ContentValues();
        values.put(UserAccountTable.COL_HXID,user.getHxid());
        values.put(UserAccountTable.COL_NAME,user.getName());
        values.put(UserAccountTable.COL_NICKNAME,user.getNickName());
        values.put(UserAccountTable.COL_PHOTO,user.getPhoto());
        db.replace(UserAccountTable.TAB_NAME,null,values);

    }

    //根据服务器ID获取用户信息
    public UserInfo getAccoutByHxId(String hxid){
        //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行查询
        String sql ="select * from "+UserAccountTable.TAB_NAME+" where "+ UserAccountTable.COL_HXID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});
        UserInfo userInfo = new UserInfo();;
        if (cursor.moveToNext()){
            //封装对象
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setNickName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICKNAME)));
            userInfo.setPhoto(cursor.getInt(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));

        }


        //关闭资源
        cursor.close();


        //返回数据
        return userInfo;
    }


}
