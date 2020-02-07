package jssvc.lmtao.lmt_im.model.dao;

//联系人表创建语句
public class ContactTable {
    public static final String TAB_NAME = "tab_contact";

    public static final String COL_NAME="name";
    public static final String COL_HXID="hxid";
    public static final String COL_NICKNAME="nickName";
    public static final String COL_PHOTO="photo";
    public static final String COL_IS_CONTACT="is_contact";//是否是联系人

    public static final String CREATE_TAB="create table "
            + TAB_NAME + " ("
            + COL_HXID + " text primary key,"
            + COL_NAME + " text,"
            + COL_PHOTO + " text,"
            + COL_IS_CONTACT + " integer,"
            + COL_NICKNAME + " text);";
}
