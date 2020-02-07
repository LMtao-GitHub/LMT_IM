package jssvc.lmtao.lmt_im.controller.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import jssvc.lmtao.lmt_im.R;
import jssvc.lmtao.lmt_im.controller.activitys.LoginActivity;
import jssvc.lmtao.lmt_im.model.Model;

public class SettingFragment extends Fragment {
    private Button setting_out_btn;
    private TextView setting_user_txt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_setting, null);
        initView(view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initView(View view) {
        setting_out_btn=(Button) view.findViewById(R.id.setting_out_btn);
        setting_user_txt=(TextView)view.findViewById(R.id.setting_user_txt);

    }



    private void initData() {
        //在Button上显示用户名名称
        setting_user_txt.setText(EMClient.getInstance().getCurrentUser());

        setting_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().getGlobalThreadpool().execute(new Runnable() {
                    @Override
                    public void run() {
                        //登录服务器退出
                        EMClient.getInstance().logout(false, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                //关闭数据库
                                Model.getInstance().getManagerDB().close();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //提示退出成功
                                        Toast.makeText(getContext(),"退出成功",Toast.LENGTH_SHORT).show();
                                        //回到登录见面
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });

                            }

                            @Override
                            public void onError(int code, String error) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"退出失败"+error,Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }
                        });

                    }
                });
            }
        });

    }
}
