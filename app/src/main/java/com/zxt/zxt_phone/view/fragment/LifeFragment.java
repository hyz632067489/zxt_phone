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

public class LifeFragment extends BaseFragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.life_fragment_view, null);
        return view;
    }
}
