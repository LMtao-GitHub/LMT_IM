package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hyphenate.chat.EMClient;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.UserInfo;

public class StartActivity extends AppCompatActivity {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //如果当前activity已经退出，那么就不需要处理handler中的信息
            if(isFinishing()){
                return;
            }
            //判断进入主页面还是登录页面
            toMainOrLogin();
        }

    };
    //判断进入主页面还是登录页面
    private void toMainOrLogin() {
       /*     new Thread(){
                @Override
                public void run() {
                }
            }.start();*/
        /*execute 执行
         * 优化线程
         * Model统一管理
         * */
        Model.getInstance().getGlobalThreadpool().execute(new Runnable(){
            @Override
            public void run() {
                //判断当前账号是否已存在登录过
                if(EMClient.getInstance().isLoggedInBefore()){//登陆过
                    //获取用户登录信息

                    UserInfo accout = Model.getInstance().getUserAccountDao().getAccoutByHxId(EMClient.getInstance().getCurrentUser());

                    if(accout==null){
                        Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        //登录成功后
                        Model.getInstance().loginSuccess(accout);

                        Log.d("打印", "run: "+accout);
                        //跳转主页
                        Intent intent = new Intent(StartActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                }else{//没登陆过
                    Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                //结束当前页面
                finish();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //发送2s的延迟消息
        handler.sendMessageDelayed(Message.obtain(),2000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁消息
        handler.removeCallbacksAndMessages(null);
    }
}
