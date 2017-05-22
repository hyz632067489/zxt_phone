package com.zxt.zxt_phone.view.fragment.life;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseFragment;
import com.zxt.zxt_phone.bean.TipDataModel;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.utils.SharedPrefsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by hkc on 2017/5/22.
 */

public class serveFragment extends BaseFragment {

    private String TAG = "serveFragment";
    View view;

    Unbinder unbinder;


    @BindView(R.id.left_title)
    ListView leftTitle;
    @BindView(R.id.right_content)
    GridView rightContent;

    CommonAdapter<CommonModel> leftAdapter;
    List<CommonModel> leftDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.serve_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getData();
        initView();
    }

    private void getData() {
        leftDatas.clear();
        leftDatas.addAll(TipDataModel.getLife());

    }

    private void initView() {
        leftAdapter = new CommonAdapter<CommonModel>(getContext(), leftDatas, R.layout.grid_item_layout) {
            @Override
            public void convert(ViewHolder holder, CommonModel item) {

                holder.getView(R.id.im_item).setVisibility(View.GONE);
                holder.setText(R.id.tv_item, item.getName());
            }
        };
        leftTitle.setAdapter(leftAdapter);
        leftTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("社区活动".equals(leftDatas.get(position).getName())) {
                    getAllDatas();
                }
            }
        });
    }


    /**
     * 获取每个种类的内容
     */
    private void getAllDatas() {

        showProgressDialog();

        HashMap<String, String> params = new HashMap<>();
        params.put("TVInfoId", SharedPrefsUtil.getString(getActivity(), "TVInfoId"));
        params.put("method", "IndexNews");
        params.put("PageSize", "6");
        params.put("Page", 1 + "");
        params.put("Key", SharedPrefsUtil.getString(getActivity(), "Key"));

        OkHttpUtils.get()
                .url(Url.BASE_URL)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG, "response==" + response);
                        closeProgressDialog();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
