package com.zxt.zxt_phone.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.constant.Url;


public class GlideImage extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context.getApplicationContext())
                .load(Url.BASE_L+path)
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(imageView);

    }


}
