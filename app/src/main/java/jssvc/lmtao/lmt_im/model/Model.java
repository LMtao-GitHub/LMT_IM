package jssvc.lmtao.lmt_im.model;


import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jssvc.lmtao.lmt_im.model.bean.UserInfo;
import jssvc.lmtao.lmt_im.model.dao.UserAccountTableDao;
import jssvc.lmtao.lmt_im.model.db.ManagerDB;

/*数据模型全局类*/
public class Model {
    public static final String TAG = "打印";
    Context context;
    UserAccountTableDao userAccountDao;
    /*newCachedThreadPool()长时间不使用线程会自动关闭线程*/
    private ExecutorService executorService= Executors.newCachedThreadPool();
    //创建对象
    private static Model model=new Model();
    private ManagerDB managerDB;


    //私有构造
    private Model(){

    }
    //获取单例对象
    public static Model getInstance(){
        return model;
    }

    //创建初始化方法
    public void init(Context context){
        this.context=context;
        //创造用户账号数据库的操作对象
        userAccountDao = new UserAccountTableDao(this.context);
    }
    //获取全局线程池对象
    public ExecutorService getGlobalThreadpool(){
        return executorService;

    }

    //用户登录成功后
    public void loginSuccess(UserInfo account) {
        //登录成功后
        if(account==null){
            return;
        }
        if(managerDB!=null){
            managerDB.close();
        }
            managerDB = new ManagerDB(context,account.getName());
    }
    //获取用户操作类对象
    public UserAccountTableDao getUserAccountDao(){
        return userAccountDao;
    }
    public ManagerDB getManagerDB(){
        return managerDB;
    }

    public static String getIOS8601Timestamp(){

        /*获取当前系统时间*/
        long time = System.currentTimeMillis();

        /*时间戳转换成IOS8601字符串*/
        Date date = new Date(time);
        TimeZone tz = TimeZone.getTimeZone("Asia/Beijing");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        String nowAsIOS = df.format(date);
        return nowAsIOS;
    }
}

