package jssvc.lmtao.lmt_im.model.bean;

public class MsgInfo {
    private String msg;
    private String hxid;
    private MsgStatus status;

    public MsgInfo() {
    }

    public MsgInfo(String msg, String hxid, MsgStatus status) {
        this.msg = msg;
        this.hxid = hxid;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public MsgStatus getStatus() {
        return status;
    }

    public void setStatus(MsgStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MsgInfo{" +
                "msg='" + msg + '\'' +
                ", hxid='" + hxid + '\'' +
                ", status=" + status +
                '}';
    }

    public enum MsgStatus{
        READ,
        UNREAD
    }
}
