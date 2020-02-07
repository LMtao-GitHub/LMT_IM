package jssvc.lmtao.lmt_im.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import jssvc.lmtao.lmt_im.model.dao.UserAccountTable;

public class UserAccountDB extends SQLiteOpenHelper {
    //构造方法
    public UserAccountDB(@Nullable Context context ){
        super(context, "account.db",null,1);
    }

    //数据库创建时
    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行创建表语句
        db.execSQL(UserAccountTable.CREATE_TAB);

    }

    //数据库升级后
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
