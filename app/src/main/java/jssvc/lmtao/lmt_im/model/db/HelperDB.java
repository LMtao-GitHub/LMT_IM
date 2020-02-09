package jssvc.lmtao.lmt_im.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import jssvc.lmtao.lmt_im.model.dao.ContactTable;
import jssvc.lmtao.lmt_im.model.dao.InviteTable;
import jssvc.lmtao.lmt_im.model.dao.MsgTable;

public class HelperDB extends SQLiteOpenHelper {
    public HelperDB(@Nullable Context context, @Nullable String name) {
        super(context, "user.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建联系人
        db.execSQL(ContactTable.CREATE_TAB);
        Log.d("打印", " 建表1");
        //创建邀请信息
        db.execSQL(InviteTable.CREATE_TAB);
        Log.d("打印", " 建表2");
        db.execSQL(MsgTable.CREATE_TAB);
        Log.d("打印", " 建表3");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
