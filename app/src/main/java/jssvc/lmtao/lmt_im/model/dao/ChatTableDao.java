package jssvc.lmtao.lmt_im.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import jssvc.lmtao.lmt_im.model.bean.ChatInfo;
import jssvc.lmtao.lmt_im.model.db.HelperDB;

public class ChatTableDao {
   private HelperDB helperDB;

    public ChatTableDao(HelperDB helperDB) {
        this.helperDB = helperDB;
    }
    //添加消息
    public void addMsg(ChatInfo chatInfo){
        //获取数据库
        SQLiteDatabase db = helperDB.getReadableDatabase();

        //执行添加
        ContentValues values = new ContentValues();
        values.put(ChatTable.ID, chatInfo.getId());
        values.put(ChatTable.USER_ID, chatInfo.getUser_id());
        values.put(ChatTable.DATA_MSG, chatInfo.getData_msg());
        values.put(ChatTable.FRIEND_ID, chatInfo.getFriend_id());
        values.put(ChatTable.IS_MINE_MSG, chatInfo.getIs_mine_msg());
        values.put(ChatTable.IS_READ_MSG, chatInfo.getIs_read_msg());
        values.put(ChatTable.TYPE_MSG, chatInfo.getType_msg());
        values.put(ChatTable.MSG, chatInfo.getMsg());
        db.replace(ChatTable.TAB_NAME,null,values);
        Log.d("打印", "记录添加成功");
    }
    //获取信息
    public List<ChatInfo>getMsgInfo(){
        SQLiteDatabase db = helperDB.getReadableDatabase();

        String sql = "select * from " + ChatTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql ,null);

        List<ChatInfo> chatInfos = new ArrayList<>();

        while (cursor.moveToNext()){
            ChatInfo chatInfo = new ChatInfo();
            chatInfo.setMsg(cursor.getString(cursor.getColumnIndex(ChatTable.MSG)));
            chatInfo.setId(cursor.getString(cursor.getColumnIndex(ChatTable.ID)));
            chatInfo.setFriend_id(cursor.getString(cursor.getColumnIndex(ChatTable.FRIEND_ID)));
            chatInfo.setUser_id(cursor.getString(cursor.getColumnIndex(ChatTable.USER_ID)));
            chatInfo.setData_msg(cursor.getString(cursor.getColumnIndex(ChatTable.DATA_MSG)));
            chatInfo.setType_msg(cursor.getString(cursor.getColumnIndex(ChatTable.TYPE_MSG)));
            chatInfo.setIs_read_msg(cursor.getInt(cursor.getColumnIndex(ChatTable.IS_READ_MSG)));
            chatInfo.setIs_mine_msg(cursor.getInt(cursor.getColumnIndex(ChatTable.IS_MINE_MSG)));
            chatInfos.add(chatInfo);
        }
        //关闭
        cursor.close();
        return chatInfos;
    }
    public void update(String fromid){
        SQLiteDatabase db = helperDB.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ChatTable.IS_READ_MSG,1);

        db.update(ChatTable.TAB_NAME,values, ChatTable.FRIEND_ID + "= ?",new String[]{fromid});
        db.close();
    }


}
