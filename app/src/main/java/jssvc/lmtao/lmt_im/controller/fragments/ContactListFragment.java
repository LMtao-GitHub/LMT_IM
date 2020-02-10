package jssvc.lmtao.lmt_im.controller.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.controller.activitys.AddContactActivity;
import jssvc.lmtao.lmt_im.controller.activitys.MsgActivity;
import jssvc.lmtao.lmt_im.controller.activitys.InviteActivity;
import jssvc.lmtao.lmt_im.controller.adapter.ContactAtapter;
import jssvc.lmtao.lmt_im.model.Model;
import jssvc.lmtao.lmt_im.model.bean.UserInfo;
import jssvc.lmtao.lmt_im.utils.Constants;
import jssvc.lmtao.lmt_im.utils.SpUtils;

import static jssvc.lmtao.lmt_im.controller.activitys.MsgActivity.CHAT_HXID;

//联系人界面
public class ContactListFragment extends Fragment {
    //获取数据
    LinearLayout contact_invite_ll,contact_grooup_ll;
    ImageView iv_contact_invite,iv_contact_red;
    private LocalBroadcastManager mLBM;
    private BroadcastReceiver ContactInviteChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //更新红点显示
            iv_contact_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,true);
        }
    };
    private ListView listView;
    private ContactAtapter adapter;
    private List<UserInfo> contacts;
    private BroadcastReceiver ContactChangeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新
            refreshContact();
        }
    };
    private String mHxid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_contactlist, null);
        initView(view);
        initData();


        return view;
    }

    private void initData() {
        iv_contact_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });

        //邀请信息条目点击
        contact_invite_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_contact_red.setVisibility(View.GONE);
                SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE,false);

                //跳转到信息列表里面
                Intent intent = new Intent(getActivity(), InviteActivity.class);
                startActivity(intent);


            }
        });
        //初始化红点
        Boolean isNewInvite = SpUtils.getInstance().getBoolean(SpUtils.IS_NEW_INVITE, false);
        iv_contact_red.setVisibility(isNewInvite? View.VISIBLE:View.GONE);
        //注册广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(ContactInviteChangeReceiver,new IntentFilter(Constants.CONTACT_INVITE_CHANGED));
        mLBM.registerReceiver(ContactChangeReceiver,new IntentFilter(Constants.CONTACT_CHANGED));

        //冲服务器获取联系人星系
        getContactFromHxServer();

        //绑定注册一下list view和contexttmenu
        registerForContextMenu(listView);
        //设置条目点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MsgActivity.class);
                intent.putExtra(CHAT_HXID,mHxid);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //huo取服务器id
        int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        UserInfo userInfo = (UserInfo) listView.getItemAtPosition(position);
        mHxid = userInfo.getName();

        //添加布局对象
        getActivity().getMenuInflater().inflate(R.menu.delete,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        if(item.getItemId()== R.id.contact_delete){
            //执行删除联系人
            deleteContact();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    //执行删除联系人
    private void deleteContact() {

        Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
            @Override
            public void run() {
                //去服务器删除
                try {
                    Log.d("wwww", "删除前: "+Model.getInstance().getManagerDB().getContactTableDao().getContacts());
                    EMClient.getInstance().contactManager().deleteContact(mHxid);


                    //本地库更新
                    Model.getInstance().getManagerDB().getContactTableDao().deleteContactHxById(mHxid);
                    Log.d("wwww", "删除后: "+Model.getInstance().getManagerDB().getContactTableDao().getContacts());

                    //页面刷新
                    if(getActivity()==null){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"删除好友“"+mHxid+"”成功", Toast.LENGTH_LONG).show();

                            refreshContact();

                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    if(getActivity()==null){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"删除“"+mHxid+"”失败 原因："+e,Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }

    private void getContactFromHxServer() {
        Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取服务器id
                    List<String> hxids = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.d("wwww", "hxids: "+hxids);
                    if(hxids !=null &&hxids.size()>=0){
                        List<UserInfo>contacts=new ArrayList<UserInfo>();
                        //转化
                        for (String hxid:hxids){
                            UserInfo userInfo=new UserInfo(hxid);
                            contacts.add(userInfo);
                        }
                        //保存好友星系到本地
                        Log.d("wwww", "保存前: "+Model.getInstance().getManagerDB().getContactTableDao().getContacts());
                        Model.getInstance().getManagerDB().getContactTableDao().saveContact(contacts,true);
                        Log.d("wwww", "保存后: "+Model.getInstance().getManagerDB().getContactTableDao().getContacts());

                        if(getActivity()==null){
                            return;
                        }
                        //shua新
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //刷新页面fangfa
                                refreshContact();

                            }
                        });

                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //刷新页面
        private void refreshContact() {

            contacts = Model.getInstance().getManagerDB().getContactTableDao().getContacts();
            Log.d("打印", "刷新"+ contacts);
            //jiaoyan
            if(contacts !=null && contacts.size()>0){

                adapter = new ContactAtapter(getActivity(),R.layout.item_contact, contacts);
                Log.d("打印", "refreshContact: "+ adapter);
                listView.setAdapter(adapter);
            }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLBM.unregisterReceiver(ContactInviteChangeReceiver);
        mLBM.unregisterReceiver(ContactChangeReceiver);
    }

    private void initView(View v) {
        contact_grooup_ll=(LinearLayout)v.findViewById(R.id.contact_grooup_ll);
        contact_invite_ll=(LinearLayout)v.findViewById(R.id.contact_invite_ll);
        iv_contact_invite=(ImageView)v.findViewById(R.id.iv_contact_invite);
        iv_contact_red=(ImageView)v.findViewById(R.id.iv_contact_red);
        listView = (ListView)v.findViewById(R.id.lv_contact);
      /*  userInfos.add()*/



    }
}
