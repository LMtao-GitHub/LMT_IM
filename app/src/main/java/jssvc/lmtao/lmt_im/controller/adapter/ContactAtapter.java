package jssvc.lmtao.lmt_im.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import jssvc.lmtao.lmt_im.R;

import jssvc.lmtao.lmt_im.model.bean.UserInfo;

public class ContactAtapter extends ArrayAdapter<UserInfo> {
    private int resource;
    private UserInfo userInfo;

    public ContactAtapter(@NonNull Context context, int resource, List<UserInfo>userInfos) {
        super(context, resource,userInfos);
        this.resource=resource;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        userInfo = getItem(position);
       /* ViewHodler hodler;
        if (convertView==null){
            hodler=new ViewHodler();
            convertView=View.inflate(getContext(), R.layout.item_contact,null);
            hodler.iv_photo_contact=(ImageView)convertView.findViewById(R.id.iv_photo_contact);
            hodler.tv_name_contact = (TextView)convertView.findViewById(R.id.tv_name_contact);
            hodler.tv_nickName_contact = (TextView)convertView.findViewById(R.id.tv_nickName_contact);
            convertView.setTag(hodler);
        }else{
            hodler=(ViewHodler) convertView.getTag();
        }
        //2获取ietm数据*/

        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        ImageView iv_photo_contact = (ImageView)view.findViewById(R.id.iv_photo_contact);
        TextView tv_name_contact = (TextView)view.findViewById(R.id.tv_name_contact);
        TextView tv_nickName_contact = (TextView)view.findViewById(R.id.tv_nickName_contact);
        iv_photo_contact.setImageResource(userInfo.getPhoto());
        tv_name_contact.setText(userInfo.getName());
        tv_nickName_contact.setText(userInfo.getNickName());
        return view;
    }



    private class ViewHodler{
       private ImageView iv_photo_contact;
       private TextView tv_nickName_contact,tv_name_contact;


    }
}
