package com.zxt.zxt_phone.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.constant.Url;
import com.zxt.zxt_phone.utils.GlideImageLoader;

import java.util.zip.Inflater;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by hyz on 2017/3/12.
 * powered by company
 */

public class ViewHolder
{
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Activity mActivity;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position)
    {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position)
    {
        if (convertView == null)
        {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView()
    {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageByUrlIcon(int viewId, String url)
    {

        Glide.with(mConvertView.getContext()).load(Url.BASE_L+url)//
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存修改过的图片
                .override(120,120)
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into((ImageView) getView(viewId));
//        ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
//                (ImageView) getView(viewId));
        return this;
    }
    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, String url)
    {

        Glide.with(mConvertView.getContext()).load(Url.BASE_L+url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存修改过的图片
                .override(250,250)
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into((ImageView) getView(viewId));
//        ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
//                (ImageView) getView(viewId));
        return this;
    }
    public int getPosition()
    {
        return mPosition;
    }

}
