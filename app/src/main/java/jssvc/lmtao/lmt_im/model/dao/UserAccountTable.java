package jssvc.lmtao.lmt_im.model.dao;

/*用户表创建数据库的语句*/
public class UserAccountTable {
    public static final String TAB_NAME="tab_account";
    public static final String COL_NAME="name";
    public static final String COL_HXID="hxid";
    public static final String COL_NICKNAME="nickName";
    public static final String COL_PHOTO="photo";

    public static final String CREATE_TAB="create table "
            + TAB_NAME + " ("
            + COL_HXID + " text primary key,"
            + COL_NAME + " text,"
            + COL_PHOTO + " text,"
            + COL_NICKNAME + " text);";


/*    public static final String SELECT="create table "
            + TAB_NAME + " ("
            + COL_HXID + " text primary key,"
            + COL_NAME + " text,"
            + COL_NICKNAME + " text,"
            + COL_PHOTO + " text);";*/


}

