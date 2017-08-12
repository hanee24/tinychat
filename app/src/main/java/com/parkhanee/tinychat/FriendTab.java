package com.parkhanee.tinychat;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parkhanee.tinychat.classbox.Friend;


public class FriendTab extends Fragment implements View.OnClickListener {
    final String TAG = "FriendTab";
    private FriendTabAdapter adapter;
    private ViewGroup header;
    MySQLite db = null;
    private View myprofile;
    MyPreferences pref=null;
    UserProfileDialog.Builder dialog=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_friend,container,false);
        header = (ViewGroup)inflater.inflate(R.layout.listview_friend_header, container, false);
        myprofile = header.findViewById(R.id.myprofile);
        myprofile.setOnClickListener(this); // header안에 있는 애니까 header에서 찾아줌 !!
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (db==null){
            db = MySQLite.getInstance(getActivity());
        }
        if (pref==null){
            pref = MyPreferences.getInstance(getActivity());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((TextView)myprofile.findViewById(R.id.header_name)).setText(pref.getString("name"));
        // TODO: 2017. 8. 4. 내프로필사진 보이기
        // ((ImageView)myprofile.findViewById(R.id.header_img)) <-- (pref.getString("img"));

        adapter = new FriendTabAdapter(getActivity());
        ListView listView = (ListView) view.findViewById(R.id.friend_list_view);
        listView.setAdapter(adapter);
        listView.addHeaderView(header, null, false);
        adapter.setFriendArrayList(db.getAllFriends());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myprofile :
                // TODO: 2017. 8. 4. init and show my profile dialog
                if (dialog == null){
                    Log.d(TAG, "onClick: dialog init");
                    dialog = new UserProfileDialog.Builder(getActivity())
                            .setMine(true)
                            .setTextName(pref.getString("name"))
                            .setTextNumber(pref.getString("nid"))
                            .build();
                }
                dialog.show();
                break;
        }
    }


}
