package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.RadioGroup;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.controller.fragments.ChatFragent;
import jssvc.lmtao.lmt_im.controller.fragments.ContactListFragment;
import jssvc.lmtao.lmt_im.controller.fragments.SettingFragment;

public class MainActivity extends FragmentActivity {
    private RadioGroup main_rg;
    private ChatFragent chatFragent;
    private ContactListFragment contactListFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        initView();
        //c初始化数据
        initData();
        //初始化监听
        initListener();



    }

    private void initListener() {
        //RadioGroup的选择事件
        main_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment=null;
                switch (checkedId){
                    //会话
                    case R.id.main_rb_chat:
                        fragment=chatFragent;
                        break;

                        //联系人
                    case R.id.main_rb_contact:
                        fragment = contactListFragment;
                        break;

                        //设置
                    case R.id.main_rb_setting:
                        fragment = settingFragment;
                        break;
                }
                switchFragent(fragment);
            }
        });
        main_rg.check(R.id.main_rb_chat);
    }

    private void switchFragent(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.maon_fl,fragment).commit();
    }

    private void initData() {
        //创建三个fragment对象
        chatFragent = new ChatFragent();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();
    }

    private void initView() {
        main_rg=(RadioGroup)findViewById(R.id.main_rg);
    }
}
