package jssvc.lmtao.lmt_im.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import jssvc.lmtao.lmt_im.model.dao.MsgTable;
import jssvc.lmtao.lmt_im.model.dao.MsgTableDao;

public class MsgDB extends SQLiteOpenHelper {
    public MsgDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    /**
     * 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
     * 重写onCreate方法，调用execSQL方法创建表
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MsgTable.CREATE_TAB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
