package jssvc.lmtao.lmt_im.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Spinner;

import jssvc.lmtao.lmt_im.IMApplication;
/*工具类
* 保存
* 获取数据
*
* */
public class SpUtils {
    public static final String IS_NEW_INVITE ="is_new invite" ;//新的邀请标记
    private static SpUtils instance=new SpUtils();
    private static SharedPreferences msp;

    private SpUtils() {

    }
    public static SpUtils getInstance(){
        if(msp==null){


        msp = IMApplication.getGlobaApplication().getSharedPreferences("im", Context.MODE_PRIVATE);
        }
        return instance;
    }
    public void save(String key,Object values){
        /*instanceof判断类型*/
        if (values instanceof String){
            msp.edit().putString(key,((String)values)).commit();
        }else if (values instanceof Boolean){
                msp.edit().putBoolean(key, (Boolean) values).commit();
            }else if (values instanceof Integer){
                //int
            msp.edit().putInt(key, (Integer) values).commit();
            }
    }

    //获取
    public String getString(String key,String defValues){
        return msp.getString(key,defValues);
    }

    //获取
    public Boolean getBoolean(String key,Boolean defValues){
        return msp.getBoolean(key,defValues);
    }
    //获取
    public int getInt(String key,int defValues){
        return msp.getInt(key,defValues);
    }


}
