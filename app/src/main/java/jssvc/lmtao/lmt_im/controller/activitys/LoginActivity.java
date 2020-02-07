package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.security.spec.ECField;
import java.util.concurrent.RunnableFuture;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.UserInfo;

public class LoginActivity extends AppCompatActivity {

    private EditText login_name_et;
    private EditText login_password_et;
    private Button login_register_bt;
    private Button login_login_bt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initView();
        //监听初始化
        initListener();
    }

    private void initListener() {
        //注册
        login_register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registe();
            }
        });
        //登录
        login_login_bt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    //注册按钮的页面逻辑处理
    private void registe() {
        //1、获取输入的用户名和密码
        /*replaceAll(" ", "")替换字符*/
        final String registe_name =login_name_et.getText().toString().replaceAll(" ", "");
        final String registe_password=login_password_et.getText().toString().replaceAll(" ","");

        //2、校验用户名和密码
        if(TextUtils.isEmpty(registe_name)||TextUtils.isEmpty(registe_password)){
            Toast.makeText(LoginActivity.this,"输入的用户名或密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }

        //3、去服务器注册账号
        Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //去服务器注册
                    EMClient.getInstance().createAccount(registe_name,registe_password);
                    //子线程不能直接更新更新UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                            Log.d("dayin", "run: 成功");
                        }
                    });
                } catch (final  HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!LoginActivity.this.isFinishing()){
                                //// 注册成功
                                ///
                                int errorCode = e.getErrorCode();
                                if (errorCode == EMError.NETWORK_ERROR){

                                }else if (errorCode==EMError.USER_ALREADY_EXIST){

                                }else if (errorCode==EMError.USER_AUTHENTICATION_FAILED){

                                }else if (errorCode==EMError.USER_ILLEGAL_ARGUMENT){

                                }else {

                                }

                            }
                            Toast.makeText(LoginActivity.this,"注册失败"+e,Toast.LENGTH_LONG).show();

                        }
                    });

                }

            }
        });

    }
    //登录按钮的页面逻辑处理
    private void login() {
        //1、获取输入的用户名和密码
        /*replaceAll(" ", "")替换字符*/
        final String login_name =login_name_et.getText().toString().replaceAll(" ", "");
        final String login_password=login_password_et.getText().toString().replaceAll(" ","");

        //2、校验用户名和密码
        if(TextUtils.isEmpty(login_name)||TextUtils.isEmpty(login_password)){
            Toast.makeText(LoginActivity.this,"输入的用户名或密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }

        //登录逻辑
        Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
            @Override
            public void run() {
                //去服务器登录回调
                EMClient.getInstance().login(login_name, login_password, new EMCallBack() {

                    //登录成功执行
                    @Override
                    public void onSuccess() {
                        //对模型数据处理
                        Model.getInstance().loginSuccess(new UserInfo(login_name));

                        //保存用户数据账号信息到本地数据库
                        Model.getInstance().getUserAccountDao().addAccout(new UserInfo(login_name));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //提示成功
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                //跳转主页面
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });


                    }

                    //登录失败
                    @Override
                    public void onError(int code, String error) {
                        //提示登录失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"登录失败"+error,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    //登录过程
                    @Override
                    public void onProgress(int progress, String status) {
                        Log.d("打印", "登录聊天服务器失败！");
                    }
                });
            }
        });



    }

    private void initView() {
        login_name_et=(EditText)findViewById(R.id.login_name_et);
        login_password_et=(EditText)findViewById(R.id.login_password_et);
        login_login_bt=(Button)findViewById(R.id.login_login_bt);
        login_register_bt=(Button)findViewById(R.id.login_register_bt);

    }
}
