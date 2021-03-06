package jssvc.lmtao.lmt_im.model.db;

import android.content.Context;
import android.widget.ProgressBar;

import jssvc.lmtao.lmt_im.model.dao.ContactTableDao;
import jssvc.lmtao.lmt_im.model.dao.InviteTableDao;
import jssvc.lmtao.lmt_im.model.dao.ChatTableDao;
import jssvc.lmtao.lmt_im.model.dao.MsgTableDao;


/*联系人和邀请表操作类的管理类*/
public class ManagerDB {

    private final ContactTableDao contactTableDao;
    private final InviteTableDao inviteTableDao;
    private final HelperDB helperDB;
    private final ChatTableDao chatTableDao;
    private final MsgTableDao msgTableDao;


    public ManagerDB(Context context, String name) {
        //创建数据库
        helperDB = new HelperDB(context,name);
        //创建该数据库表中两张表的数据操作类
        contactTableDao = new ContactTableDao(helperDB);
        inviteTableDao = new InviteTableDao(helperDB);
        chatTableDao = new ChatTableDao(helperDB);
        msgTableDao = new MsgTableDao(helperDB);
    }
    //获取联系人表的操作对象
    public ContactTableDao getContactTableDao(){
        return contactTableDao;
    }
    //获取信息表的操作对象
    public ChatTableDao getChatTableDao(){
        return chatTableDao;
    }

    //获取邀请表的操作类对象
    public InviteTableDao getInviteTableDao(){
        return inviteTableDao;
    }
    //获取消息的操作类对象
    public MsgTableDao getMsgTableDao(){
        return msgTableDao;
    }


    //关闭数据库的方法
    public void close() {
        helperDB.close();

    }

}
