package jssvc.lmtao.lmt_im.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jssvc.lmtao.lmt_im.model.bean.MsgInfo;
import jssvc.lmtao.lmt_im.model.db.HelperDB;

public class ChatTableDao {
   private HelperDB helperDB;

    public ChatTableDao(HelperDB helperDB) {
        this.helperDB = helperDB;
    }
    //添加消息
    public void addMsg(MsgInfo msgInfo){
        //获取数据库
        SQLiteDatabase db = helperDB.getReadableDatabase();

        //执行添加
        ContentValues values = new ContentValues();
        values.put(ChatTable.ID,msgInfo.getId());
        values.put(ChatTable.USER_ID,msgInfo.getUser_id());
        values.put(ChatTable.DATA_MSG,msgInfo.getData_msg());
        values.put(ChatTable.FRIEND_ID,msgInfo.getFriend_id());
        values.put(ChatTable.IS_MINE_MSG,msgInfo.getIs_mine_msg());
        values.put(ChatTable.IS_READ_MSG,msgInfo.getIs_read_msg());
        values.put(ChatTable.TYPE_MSG,msgInfo.getType_msg());
        values.put(ChatTable.MSG,msgInfo.getMsg());
        db.replace(ChatTable.TAB_NAME,null,values);
        Log.d("打印", "信息添加成功");
    }
    //获取信息
    public List<MsgInfo>getMsgInfo(){
        SQLiteDatabase db = helperDB.getReadableDatabase();

        String sql = "select * from " + ChatTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql ,null);

        List<MsgInfo> msgInfos = new ArrayList<>();

        while (cursor.moveToNext()){
            MsgInfo msgInfo = new MsgInfo();
            msgInfo.setMsg(cursor.getString(cursor.getColumnIndex(ChatTable.MSG)));
            msgInfo.setId(cursor.getString(cursor.getColumnIndex(ChatTable.ID)));
            msgInfo.setFriend_id(cursor.getString(cursor.getColumnIndex(ChatTable.FRIEND_ID)));
            msgInfo.setUser_id(cursor.getString(cursor.getColumnIndex(ChatTable.USER_ID)));
            msgInfo.setData_msg(cursor.getString(cursor.getColumnIndex(ChatTable.DATA_MSG)));
            msgInfo.setType_msg(cursor.getString(cursor.getColumnIndex(ChatTable.TYPE_MSG)));
            msgInfo.setIs_read_msg(cursor.getInt(cursor.getColumnIndex(ChatTable.IS_READ_MSG)));
            msgInfo.setIs_mine_msg(cursor.getInt(cursor.getColumnIndex(ChatTable.IS_MINE_MSG)));
            msgInfos.add(msgInfo);
        }
        //关闭
        cursor.close();
        return msgInfos;
    }
    public void update(String fromid){
        SQLiteDatabase db = helperDB.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ChatTable.IS_READ_MSG,1);

        db.update(ChatTable.TAB_NAME,values, ChatTable.FRIEND_ID + "= ?",new String[]{fromid});
        db.close();
    }


}
