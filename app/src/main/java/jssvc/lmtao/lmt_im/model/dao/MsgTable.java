package jssvc.lmtao.lmt_im.model.dao;

public class MsgTable {
        public static final String TAB_NAME = "tab_msg";
        public static final String ID = "id";
        public static final String COUNT_MSG = "count_msg";
        public static final String USER_ID = "user_id";
        public static final String FRIEND_ID = "friend_id";
        public static final String MSG = "msg";
        public static final String TYPE_MSG = "type_msg";
        public static final String DATA_MSG = "data_msg";
        public static final String IS_READ_MSG = "is_read_msg";
        public static final String IS_MINE_MSG = "is_mine_msg";
        public static final String DELETE_MSG = "delete_msg";

        public static final String CREATE_TAB="create table "
                + TAB_NAME + " ("
                + ID + " varchar primary key,"
                + USER_ID + " varchar(64),"
                + FRIEND_ID + " varchar(64),"
                + MSG + " varchar(255),"
                + TYPE_MSG + " varchar(8),"
                + DATA_MSG + " datetime,"
                + IS_READ_MSG + " integer not null default (0),"
                + IS_MINE_MSG + " integer not null default (0),"
                + DELETE_MSG + " integer,"
                + COUNT_MSG + " integer not null default (0));";
    }


