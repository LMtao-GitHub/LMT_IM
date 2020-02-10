package jssvc.lmtao.lmt_im.model.bean;

public class ChatInfo {
    private String msg;
    private String id;
    private String user_id;
    private String friend_id;
    private String type_msg;
    private String data_msg;
    private int is_read_msg;
    private int is_mine_msg;
    private int delete_msg;


    public ChatInfo() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getType_msg() {
        return type_msg;
    }

    public void setType_msg(String type_msg) {
        this.type_msg = type_msg;
    }

    public String getData_msg() {
        return data_msg;
    }

    public void setData_msg(String data_msg) {
        this.data_msg = data_msg;
    }

    public int getIs_read_msg() {
        return is_read_msg;
    }

    public void setIs_read_msg(int is_read_msg) {
        this.is_read_msg = is_read_msg;
    }

    public int getIs_mine_msg() {
        return is_mine_msg;
    }

    public void setIs_mine_msg(int is_mine_msg) {
        this.is_mine_msg = is_mine_msg;
    }

    public int getDelete_msg() {
        return delete_msg;
    }

    public void setDelete_msg(int delete_msg) {
        this.delete_msg = delete_msg;
    }

    @Override
    public String toString() {
        return "ChatInfo{" +
                "msg='" + msg + '\'' +
                ", id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", friend_id='" + friend_id + '\'' +
                ", type_msg='" + type_msg + '\'' +
                ", data_msg='" + data_msg + '\'' +
                ", is_read_msg=" + is_read_msg +
                ", is_mine_msg=" + is_mine_msg +
                ", delete_msg=" + delete_msg +
                '}';
    }
}





