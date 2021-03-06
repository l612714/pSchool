package com.peoit.android.online.pschool.ui.Presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.GridView;

import com.easemob.chatuidemo.activity.ChatActivity;
import com.easemob.chatuidemo.utils.NetWorkHelper;
import com.peoit.android.online.pschool.ActBase;
import com.peoit.android.online.pschool.R;
import com.peoit.android.online.pschool.UserTypeBase;
import com.peoit.android.online.pschool.config.CommonUtil;
import com.peoit.android.online.pschool.config.UserTypeCallBack;
import com.peoit.android.online.pschool.entity.HomeItemInfo;
import com.peoit.android.online.pschool.exception.NoLoginEcxeption;
import com.peoit.android.online.pschool.ui.Base.PsApplication;
import com.peoit.android.online.pschool.ui.activity.BankICActivity;
import com.peoit.android.online.pschool.ui.activity.FeatureActivity;
import com.peoit.android.online.pschool.ui.activity.NoticeSortActivity;
import com.peoit.android.online.pschool.ui.activity.OnlineVideoActivity;
import com.peoit.android.online.pschool.ui.activity.ShopOnlineActivity;
import com.peoit.android.online.pschool.ui.adapter.HomeItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页item控制类
 * <p/>
 * author:libo
 * time:2015/8/25
 * E-mail:boli_android@163.com
 * last: ...
 */
public class HomeItemPresenter implements UserTypeBase {
    private final ActBase mActBase;
    private final GridView mGv_item;


    private String chatname;

    private List<HomeItemInfo> homeItemInfos = new ArrayList<>();
    private HomeItemAdapter adapter;

    //    private String groupId;
    HomeZhuanJiaPresenter HomeZhuanJiaPresenter;

    public HomeItemPresenter(ActBase actBase, GridView gv) {
        this.mActBase = actBase;
        if (gv == null)
            throw new NullPointerException(" @libo gridView is null ");
        this.mGv_item = gv;
        initData();
        init();
    }

    private void initData() {
        try {
            UserTypeCallBack callBack = new UserTypeCallBack(this);
            callBack.start();
        } catch (NoLoginEcxeption noLoginEcxeption) {
            noLoginEcxeption.printStackTrace();
            return;
        }
    }

    private void init() {
        mGv_item.setAdapter(adapter);

    }

    public void changeMarkCount(int count) {
        if (adapter != null) {
            adapter.changeMarkCount(count);
        }
    }

//    public void changeGroupId(String groupId) {
//        this.groupId = groupId;
//    }

    @Override
    public void current_is_parent() {
        homeItemInfos.add(new HomeItemInfo(R.mipmap.icimage, "金融IC一卡通", false));
        homeItemInfos.add(new HomeItemInfo(R.mipmap.schoolbarimage, "网校专栏", false));
        homeItemInfos.add(new HomeItemInfo(R.drawable.vedioimage, "网校视频", false));
        homeItemInfos.add(new HomeItemInfo(R.mipmap.notiimage, "通知", false));
        homeItemInfos.add(new HomeItemInfo(R.mipmap.chatimage, "班级交流", true));
        homeItemInfos.add(new HomeItemInfo(R.drawable.onlineshoppingimage, "在线商城", false));

        adapter = new HomeItemAdapter(mActBase.getActivity(), R.layout.act_home_gv_item, homeItemInfos);
        adapter.setOnItemListener(new HomeItemAdapter.OnItemListener() {
            @Override
            public void onItem(int position) {
                switch (position) {
                    case 0:
                        //金融IC卡充值
                        BankICActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 1:
                        //学校专栏
                        if (mActBase.isLoginAndToLogin())
                            FeatureActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 3:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            NoticeSortActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 4:
                        toChat();
                        break;
                    case 5:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            ShopOnlineActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 2:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            OnlineVideoActivity.startThisActivity(mActBase.getActivity());
                        break;
                }
            }
        });
    }

    public void toChat() {
        //交流
        if (mActBase.isLoginAndToLogin() && !TextUtils.isEmpty(PsApplication.getInstance().getUserName())) {
            if (!NetWorkHelper.checkNetState(mActBase.getActivity())) {
                mActBase.showToast("当前网络不可用");
                return;
            }
            String groupId = CommonUtil.getGroupid();
            if (NetWorkHelper.checkNetState(mActBase.getActivity()) && TextUtils.isEmpty(groupId)) {
                return;
            }
            Intent intent = new Intent(mActBase.getContext(), ChatActivity.class);
            intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
            intent.putExtra("groupId", groupId);
            mActBase.getActivity().startActivityForResult(intent, 0);
        }
    }

    @Override
    public void current_is_teacher() {
        homeItemInfos.add(new HomeItemInfo(R.mipmap.icimage, "金融IC一卡通", false));
        homeItemInfos.add(new HomeItemInfo(R.mipmap.schoolbarimage, "网校专栏", false));
        homeItemInfos.add(new HomeItemInfo(R.drawable.vedioimage, "网校视频", false));
        homeItemInfos.add(new HomeItemInfo(R.mipmap.notiimage, "通知", false));
        homeItemInfos.add(new HomeItemInfo(R.mipmap.chatimage, "班级交流", true));
        homeItemInfos.add(new HomeItemInfo(R.drawable.onlineshoppingimage, "在线商城", false));

        adapter = new HomeItemAdapter(mActBase.getActivity(), R.layout.act_home_gv_item, homeItemInfos);
        adapter.setOnItemListener(new HomeItemAdapter.OnItemListener() {
            @Override
            public void onItem(int position) {
                switch (position) {
                    case 0:
                        //金融IC卡充值
                        BankICActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 1:
                        //学校专栏
                        if (mActBase.isLoginAndToLogin())
                            FeatureActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 3:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            NoticeSortActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 4:
                        //交流
                        toChat();
                        break;
                    case 5:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            ShopOnlineActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 2:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            OnlineVideoActivity.startThisActivity(mActBase.getActivity());
                        break;
                }
            }
        });
    }

    @Override
    public void current_is_expert() {
        homeItemInfos.add(new HomeItemInfo(R.mipmap.schoolbarimage, "网校专栏", false));
        homeItemInfos.add(new HomeItemInfo(R.drawable.vedioimage, "网校视频", false));
        homeItemInfos.add(new HomeItemInfo(R.mipmap.notiimage, "通知", false));
        homeItemInfos.add(new HomeItemInfo(R.mipmap.chatimage, "专家交流", true));
        homeItemInfos.add(new HomeItemInfo(R.drawable.onlineshoppingimage, "在线商城", false));

        adapter = new HomeItemAdapter(mActBase.getActivity(), R.layout.act_home_gv_item, homeItemInfos);
        adapter.setOnItemListener(new HomeItemAdapter.OnItemListener() {
            @Override
            public void onItem(int position) {
                switch (position) {
                    case 0:
                        //学校专栏
                        if (mActBase.isLoginAndToLogin())
                            FeatureActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 2:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            NoticeSortActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 4:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            ShopOnlineActivity.startThisActivity(mActBase.getActivity());
                        break;
                    case 3:
                        //交流
                        toChat();
                        break;
                    case 1:
                        //校园通知
                        if (mActBase.isLoginAndToLogin())
                            OnlineVideoActivity.startThisActivity(mActBase.getActivity());
                        break;
                }
            }
        });
    }
}
