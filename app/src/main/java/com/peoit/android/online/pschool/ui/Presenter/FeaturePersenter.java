package com.peoit.android.online.pschool.ui.Presenter;

import com.peoit.android.online.pschool.ActBase;
import com.peoit.android.online.pschool.R;
import com.peoit.android.online.pschool.config.NetConstants;
import com.peoit.android.online.pschool.entity.FeatureInfo;
import com.peoit.android.online.pschool.entity.ListPageStat;
import com.peoit.android.online.pschool.net.CallBack;
import com.peoit.android.online.pschool.ui.Base.BasePresenter;
import com.peoit.android.online.pschool.ui.adapter.EntityAdapter;

import java.util.List;
import java.util.Map;

/**
 * 专栏
 *
 * author:libo
 * time:2015/8/3
 * E-mail:boli_android@163.com
 * last: ...
 */
public class FeaturePersenter extends BasePresenter<FeatureInfo>{

    private EntityAdapter<FeatureInfo> adapter;
    private ListPageStat listPageStat;

    public FeaturePersenter(ActBase actBase, EntityAdapter<FeatureInfo> adapter) {
        super(actBase);
        this.listPageStat = new ListPageStat();
        this.adapter = adapter;
    }

    @Override
    public Map<String, String> getParams() {
        return listPageStat.getParams();
    }
    @Override
    public Class<FeatureInfo> getGsonClass() {
        return FeatureInfo.class;
    }
    /**
     * 加载
     *
     */
    public void loadData(){
        listPageStat.loadData();
        load(false);
    }

    private void load(final boolean isMore) {
        mActBase.getUIShowPresenter().doShowloading();
        request(NetConstants.NET_FEATURE_LIST, new CallBack<FeatureInfo>() {

            @Override
            public void onSimpleSuccessList(List<FeatureInfo> result) {
                System.out.print("请求到的数据" + result);
                mActBase.getUIShowPresenter().doShowData();
                if (isMore) {
                    adapter.addFootDataList(result);
                } else {
                    adapter.upDateList(result);
                }
            }

            @Override
            public void onSimpleFailure(int error, String errorMsg) {
                mActBase.onResponseFailure(error, errorMsg);
                mActBase.getUIShowPresenter().doShowNodata(R.drawable.nocolumimage);
            }
        });
    }

    /**
     * 加载更多
     *
     */
    public void loadMore(){
        listPageStat.loadMore();
        load(true);
    }
}
