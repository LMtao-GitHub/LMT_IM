package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.UserInfo;

public class AddContactActivity extends AppCompatActivity {
    private TextView tv_add_find ,tv_add_name;
    private EditText et_add_name;
    private Button bt_add_add;
    private RelativeLayout rl_add;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initView();

        initListener();
    }

    private void initListener() {
        //查找按钮点击事件处理
        tv_add_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find();
            }
        });
        //添加按钮处理实际
        bt_add_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }
    //查找处理
    private void find() {

        //获取文本内容
        final String name = et_add_name.getText().toString().replaceAll(" ", "");


        //校验内容是否为空
        if(name==null||name.equals("")){
            Toast.makeText(AddContactActivity.this,"输入用户名不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        //去服务器判断当前用户是否存在
        Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
            @Override
            public void run() {
                //去服务器查找的用户是否存在
                userInfo = new UserInfo(name);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tv_add_name.setText(userInfo.getName());
                        rl_add.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
    }

    //添加处理
    private void add() {
        Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
            @Override
            public void run() {
                //去服务器添加好友
                try {
                    EMClient.getInstance().contactManager().addContact(userInfo.getName(),"添加好友");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this,"发送成功好友邀请",Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this,"发送失败"+e.toString(),Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }
        });
    }

    private void initView() {
        bt_add_add = (Button)findViewById(R.id.bt_add_add);
        tv_add_find = (TextView)findViewById(R.id.tv_add_find);
        tv_add_name = (TextView)findViewById(R.id.tv_add_name);
        et_add_name = (EditText) findViewById(R.id.et_add_name);
        rl_add = (RelativeLayout)findViewById(R.id.rl_add);


    }
}
