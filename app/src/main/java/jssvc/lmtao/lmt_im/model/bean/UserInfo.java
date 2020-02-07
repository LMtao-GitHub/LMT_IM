package jssvc.lmtao.lmt_im.model.bean;

/*用户信息*/
public class UserInfo {

    private String name;//用户名称
    private String hxid;//服务器id
    private String nickName;//用户昵称
    private int photo;//用户头像

    public UserInfo() {
    }

    public UserInfo(String name/*, String hxid, String nickName, String photo*/) {
        this.name = name;
        this.hxid = name;
        this.nickName =name;

    }
    public UserInfo(int photo){
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHxid() {
        return hxid;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", hxid='" + hxid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}

