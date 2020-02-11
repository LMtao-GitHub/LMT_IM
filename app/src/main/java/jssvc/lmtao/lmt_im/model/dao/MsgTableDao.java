package jssvc.lmtao.lmt_im.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.MsgInfo;
import jssvc.lmtao.lmt_im.model.db.HelperDB;


public class MsgTableDao {
    private HelperDB helperDB;


    public MsgTableDao(HelperDB helperDB) {
        this.helperDB = helperDB;
    }

    //添加消息记录
    public void addMsg(MsgInfo msgInfo){
        //获取数据库
        SQLiteDatabase db = helperDB.getReadableDatabase();

        //执行添加
        ContentValues values = new ContentValues();
        values.put(MsgTable.ID, msgInfo.getId());
        values.put(MsgTable.USER_ID, msgInfo.getUser_id());
        values.put(MsgTable.DATA_MSG, msgInfo.getData_msg());
        values.put(MsgTable.FRIEND_ID, msgInfo.getFriend_id());
        values.put(MsgTable.IS_MINE_MSG, msgInfo.getIs_mine_msg());
        values.put(MsgTable.IS_READ_MSG, msgInfo.getIs_read_msg());
        values.put(MsgTable.TYPE_MSG, msgInfo.getType_msg());
        values.put(MsgTable.MSG, msgInfo.getMsg());
        values.put(MsgTable.COUNT_MSG,msgInfo.getCount());
        db.insert(MsgTable.TAB_NAME,null,values);
        Log.d(Model.TAG, "消息添加成功");
        db.close();
    }
    /*获取所有历史记录*/
    public List<MsgInfo>getMsg(String id){
        SQLiteDatabase db = helperDB.getReadableDatabase();

        String sql = "select * from "+ MsgTable.TAB_NAME +" where "+ MsgTable.FRIEND_ID +"="+"'"+id+"'";
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
            msgInfo.setCount(cursor.getInt(cursor.getColumnIndex(MsgTable.COUNT_MSG)));
            msgInfos.add(msgInfo);
        }
        //关闭
        cursor.close();
        return msgInfos;

    }
    /*查看是第几条消息*/
    public int getMsgCount(){
        SQLiteDatabase db = helperDB.getReadableDatabase();

        String sql = "select * from " + MsgTable.TAB_NAME;
/*        String sql1 = "select count(*) from " + MsgTable.TAB_NAME;*/

        Cursor cursor = db.rawQuery(sql ,null);
        MsgInfo msgInfo = new MsgInfo();
        while (cursor.moveToNext()){
            msgInfo.setMsg(cursor.getString(cursor.getColumnIndex(MsgTable.MSG)));
            msgInfo.setId(cursor.getString(cursor.getColumnIndex(MsgTable.ID)));
            msgInfo.setFriend_id(cursor.getString(cursor.getColumnIndex(MsgTable.FRIEND_ID)));
            msgInfo.setUser_id(cursor.getString(cursor.getColumnIndex(MsgTable.USER_ID)));
            msgInfo.setData_msg(cursor.getString(cursor.getColumnIndex(MsgTable.DATA_MSG)));
            msgInfo.setType_msg(cursor.getString(cursor.getColumnIndex(MsgTable.TYPE_MSG)));
            msgInfo.setIs_read_msg(cursor.getInt(cursor.getColumnIndex(MsgTable.IS_READ_MSG)));
            msgInfo.setIs_mine_msg(cursor.getInt(cursor.getColumnIndex(MsgTable.IS_MINE_MSG)));
            msgInfo.setCount(cursor.getInt(cursor.getColumnIndex(MsgTable.COUNT_MSG)));
        }


        if (msgInfo.getCount()==0){
            Log.d(Model.TAG, "getMsgCount: "+cursor.getCount());
            return cursor.getCount();

        }
        //关闭
        cursor.close();
        return msgInfo.getCount();
    }


}
