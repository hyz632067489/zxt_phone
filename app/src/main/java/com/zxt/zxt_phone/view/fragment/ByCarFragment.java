package com.zxt.zxt_phone.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.base.BaseFragment;

/**
 * Created by hyz on 2017/3/9.
 * powered by company
 */

public class ByCarFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.by_car_activity,container,false);
        return v;
    }
}
