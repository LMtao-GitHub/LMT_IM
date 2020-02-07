package jssvc.lmtao.lmt_im.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import jssvc.lmtao.lmt_im.model.dao.ContactTable;
import jssvc.lmtao.lmt_im.model.dao.InviteTable;

public class HelperDB extends SQLiteOpenHelper {
    public HelperDB(@Nullable Context context, @Nullable String name) {
        super(context, name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建联系人
        db.execSQL(ContactTable.CREATE_TAB);

        //创建邀请信息
        db.execSQL(InviteTable.CREATE_TAB);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
