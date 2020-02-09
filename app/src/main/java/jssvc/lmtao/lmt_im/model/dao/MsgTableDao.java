package jssvc.lmtao.lmt_im.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jssvc.lmtao.lmt_im.model.bean.MsgInfo;
import jssvc.lmtao.lmt_im.model.db.HelperDB;

public class MsgTableDao {
   private HelperDB helperDB;

    public MsgTableDao(HelperDB helperDB) {
        this.helperDB = helperDB;
    }
    //添加消息
    public void addMsg(MsgInfo msgInfo){
        //获取数据库
        SQLiteDatabase db = helperDB.getReadableDatabase();

        //执行添加
        ContentValues values = new ContentValues();
        values.put(MsgTable.ID,msgInfo.getId());
        values.put(MsgTable.USER_ID,msgInfo.getUser_id());
        values.put(MsgTable.DATA_MSG,msgInfo.getData_msg());
        values.put(MsgTable.FRIEND_ID,msgInfo.getFriend_id());
        values.put(MsgTable.IS_MINE_MSG,msgInfo.getIs_mine_msg());
        values.put(MsgTable.IS_READ_MSG,msgInfo.getIs_read_msg());
        values.put(MsgTable.TYPE_MSG,msgInfo.getType_msg());
        values.put(MsgTable.MSG,msgInfo.getMsg());
        db.replace(MsgTable.TAB_NAME,null,values);
        Log.d("打印", "信息添加成功");
    }
    //获取信息
    public List<MsgInfo>getMsgInfo(){
        SQLiteDatabase db = helperDB.getReadableDatabase();

        String sql = "select * from " + MsgTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql ,null);

        List<MsgInfo> msgInfos = new ArrayList<>();

        while (cursor.moveToNext()){
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setMsg(cursor.getString(cursor.getColumnIndex(MsgTable.MSG)));
            msgInfo.setId(cursor.getString(cursor.getColumnIndex(MsgTable.ID)));
            msgInfo.setFriend_id(cursor.getString(cursor.getColumnIndex(MsgTable.FRIEND_ID)));
            msgInfo.setUser_id(cursor.getString(cursor.getColumnIndex(MsgTable.USER_ID)));
            msgInfo.setData_msg(cursor.getString(cursor.getColumnIndex(MsgTable.DATA_MSG)));
            msgInfo.setType_msg(cursor.getString(cursor.getColumnIndex(MsgTable.TYPE_MSG)));
            msgInfo.setIs_read_msg(cursor.getInt(cursor.getColumnIndex(MsgTable.IS_READ_MSG)));
            msgInfo.setIs_mine_msg(cursor.getInt(cursor.getColumnIndex(MsgTable.IS_MINE_MSG)));
            msgInfos.add(msgInfo);
        }
        //关闭
        cursor.close();
        return msgInfos;
    }
    public void update(String fromid){
        SQLiteDatabase db = helperDB.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(MsgTable.IS_READ_MSG,1);

        db.update(MsgTable.TAB_NAME,values,MsgTable.FRIEND_ID + "= ?",new String[]{fromid});
        db.close();
    }


}
