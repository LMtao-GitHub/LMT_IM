package jssvc.lmtao.lmt_im.controller.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.controller.adapter.InviteApapter;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.InvationInfo;
import jssvc.lmtao.lmt_im.model.bean.UserInfo;
import jssvc.lmtao.lmt_im.utils.Constants;

public class InviteActivity extends AppCompatActivity {
    private ListView lv_invite;
    private InviteApapter.OnInviteListener onInviteListener=new InviteApapter.OnInviteListener() {
        @Override
        public void onAccept(InvationInfo invationInfo) {
            /*//接受按钮*/
            //去服务器接受
            Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(invationInfo.getUser().getHxid());
                        //数据库更新
                        Model.getInstance().getManagerDB().getInviteTableDao().updateInvitationStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT,invationInfo.getUser().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //页面根性
                                Toast.makeText(InviteActivity.this,"接受邀请",Toast.LENGTH_LONG).show();

                                //刷新页面
                                refresh();

                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //页面根性
                                Toast.makeText(InviteActivity.this,"接受邀请失败"+e,Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }
            });


        }

        @Override
        public void onReject(InvationInfo invationInfo) {
            //拒绝按钮
            Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(invationInfo.getUser().getHxid());
                        Model.getInstance().getManagerDB().getInviteTableDao().removeInvatation(invationInfo.getUser().getHxid());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"拒绝成功了",Toast.LENGTH_LONG).show();
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"拒绝失败"+e,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });

        }
    };
    private InviteApapter inviteApapter;
    private LocalBroadcastManager mLBM;
    private BroadcastReceiver ContactInviteChangedReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        initView();

        initData();
    }

    private void initData() {
        //初始化ListView
        inviteApapter = new InviteApapter(this,onInviteListener);
        lv_invite.setAdapter(inviteApapter);

        //刷新方法
        refresh();

        //z注册邀请信息变化的广播
        mLBM = LocalBroadcastManager.getInstance(this);
        mLBM.registerReceiver(ContactInviteChangedReceiver,new IntentFilter(Constants.CONTACT_INVITE_CHANGED));
    }

    public void refresh() {
        //获取数据库中的所有星系
        List<InvationInfo> invitionInfos = Model.getInstance().getManagerDB().getInviteTableDao().getInvatation();
        //刷星适配器
        inviteApapter.refresh(invitionInfos);
        Log.d("Invite", "refresh: "+invitionInfos);

    }


    private void initView() {
        lv_invite=(ListView)findViewById(R.id.lv_invite);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mLBM.unregisterReceiver(ContactInviteChangedReceiver);
    }
}
