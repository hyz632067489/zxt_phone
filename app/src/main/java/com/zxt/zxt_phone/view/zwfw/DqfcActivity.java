package com.zxt.zxt_phone.view.zwfw;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.adapter.CommonAdapter;
import com.zxt.zxt_phone.adapter.ViewHolder;
import com.zxt.zxt_phone.base.BaseActivity;
import com.zxt.zxt_phone.bean.model.DqfcModel;
import com.zxt.zxt_phone.utils.GlideImageLoader;
import com.zxt.zxt_phone.utils.LogUtil;
import com.zxt.zxt_phone.utils.MLog;
import com.zxt.zxt_phone.view.LoginActivity;
import com.zxt.zxt_phone.view.customview.HomeGridView;
import com.zxt.zxt_phone.view.customview.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 党群风采
 */
public class DqfcActivity extends BaseActivity {

    private String TAG = DqfcActivity.class.getCanonicalName();

    @BindView(R.id.tab_name)
    TextView tabName;

    @BindView(R.id.refreshView)
    PullToRefreshView myRefreshView;

    @BindView(R.id.gv_list)
    HomeGridView gvList;

    private  DqfcModel model;
    CommonAdapter<DqfcModel.DataNewsModel> myAdapter;
    private List<DqfcModel.DataNewsModel> listData   = new ArrayList<>();

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dqfc);

        getData();
        initView();
    }


    public void initView(){
        tabName.setText(R.string.zwfw_dqfc);


        myAdapter = new CommonAdapter<DqfcModel.DataNewsModel>(mContext,listData,R.layout.pop_list_img_item) {
            @Override
            public void convert(ViewHolder holder, DqfcModel.DataNewsModel item) {
                MLog.i(TAG,"response=="+item.getCoverImg());

                Glide.with(mContext).load(item.getCoverImg())//
                        .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
                        .error(R.drawable.ic_default_color)//
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)//
                        .into((ImageView) holder.getView(R.id.repairs_img));

                holder.setText(R.id.tv_pop_list,item.getTitle());
            }
        };
        gvList.setAdapter(myAdapter);
    }
    private void getData() {
        OkHttpUtils.get()
                .url("http://192.168.1.222:8099/api/APP1.0.aspx?method=Activities")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MLog.i(TAG,"response=="+response);
                        if(response.length()>0){
                            try {
                                JSONObject obj = new JSONObject(response);

                                model= new Gson().fromJson(response,DqfcModel.class);
                                listData.addAll(model.getData());

                                myAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}
