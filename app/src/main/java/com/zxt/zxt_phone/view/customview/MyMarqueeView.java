package com.zxt.zxt_phone.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sunfusheng.marqueeview.DisplayUtil;
import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.MarqueeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangpeng on 2016/12/15.
 */

public class MyMarqueeView extends ViewFlipper {

    private Context mContext;
    private List<MarqueeModel.DataNewsModel> notices;
    private boolean isSetAnimDuration = false;
    private OnItemClickListener onItemClickListener;

    private int interval = 2000;
    private int animDuration = 500;
    private int textSize = 10;
    private int textColor = 0x555555;

    private boolean singleLine = false;
    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private static final int TEXT_GRAVITY_LEFT = 0, TEXT_GRAVITY_CENTER = 1, TEXT_GRAVITY_RIGHT = 2;

    public MyMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        if (notices == null) {
            notices = new ArrayList<>();
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);
        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval);
        isSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration);
        singleLine = typedArray.getBoolean(R.styleable.MarqueeViewStyle_mvSingleLine, false);
        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration);
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, textSize);
            textSize = DisplayUtil.px2sp(mContext, textSize);
        }
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor);
        int gravityType = typedArray.getInt(R.styleable.MarqueeViewStyle_mvGravity, TEXT_GRAVITY_LEFT);
        switch (gravityType) {
            case TEXT_GRAVITY_CENTER:
                gravity = Gravity.CENTER;
                break;
            case TEXT_GRAVITY_RIGHT:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        typedArray.recycle();

        setFlipInterval(interval);

        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        if (isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);

        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        if (isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }

    // 根据公告字符串启动轮播
    public void startWithText(final MarqueeModel.DataNewsModel notice) {
        if (TextUtils.isEmpty(notice.getDeptName())) return;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                startWithFixedWidth(notice, getWidth());
            }
        });
    }

    // 根据公告字符串列表启动轮播
    public void startWithList(List<MarqueeModel.DataNewsModel> notices) {
        setNotices(notices);
        start();
    }

    // 根据宽度和公告字符串启动轮播
    private void startWithFixedWidth(MarqueeModel.DataNewsModel item, int width) {
        String notice = item.getDeptName();
        int noticeLength = notice.length();
        int dpW = DisplayUtil.px2dip(mContext, width);
        int limit = dpW / textSize;
        if (dpW == 0) {
            throw new RuntimeException("Please set MarqueeView width !");
        }
        List list = new ArrayList();
        if (noticeLength <= limit) {
//            notices.add(item);
            list.add(notice);
        } else {
            int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
            for (int i = 0; i < size; i++) {
                int startIndex = i * limit;
                int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
//                item.getDeptName() = notice.substring(startIndex, endIndex);
                list.add(notice.substring(startIndex, endIndex));
            }
        }
        notices.addAll(list);
        start();
    }

    // 启动轮播
    public boolean start() {
        if (notices == null || notices.size() == 0) return false;
        removeAllViews();

        for (int i = 0; i < notices.size(); i++) {
//            final TextView textView = createTextView(notices.get(i).getTitle(), i);
//            final int finalI = i;
//            textView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItemClick(finalI, textView);
//                    }
//                }
//            });
            final RadioGroup radioGroup;
            final int finalI = i;

            if (i == notices.size() - 1) {  //总数为单个，且为最后一个
//                radioGroup = createRadioGroup(i, "· ["+notices.get(i).getDeptName()+"]"+notices.get(i).getTitle()+"["+notices.get(i).getEditDate()+"]", "");
                radioGroup = createRadioGroup(i, "· ["+notices.get(i).getDeptName()+"]"+notices.get(i).getTitle(), "");
            } else {
//                radioGroup = createRadioGroup(i, "· ["+notices.get(i).getDeptName()+"]"+notices.get(i).getTitle()+"["+notices.get(i).getEditDate()+"]",
//                        "· ["+notices.get(i+1).getDeptName()+"]"+notices.get(i+1).getTitle()+"["+notices.get(i+1).getEditDate()+"]");
                radioGroup = createRadioGroup(i, "· ["+notices.get(i).getDeptName()+"]"+notices.get(i).getTitle(),
                "· ["+notices.get(i+1).getDeptName()+"]"+notices.get(i+1).getTitle());
                radioGroup.getChildAt(1).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(finalI, notices.get(finalI+1));
                        Log.i("...zp...", "1  i:" + finalI);
                    }
                });
            }

            radioGroup.getChildAt(0).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(notices==null ||notices.size()==0) return;
                    onItemClickListener.onItemClick(finalI, notices.get(finalI));
                    Log.i("...zp...", "0  i:" + finalI);
                }
            });
            addView(radioGroup);
        }

        if (notices.size() > 1) {
            startFlipping();
        }
        return true;
    }

    // 创建ViewFlipper下的TextView
    private TextView createTextView(String text, int position) {
        TextView tv = new TextView(mContext);
        tv.setGravity(gravity);
        tv.setText(text);
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);
        tv.setSingleLine(singleLine);
        tv.setTag(position);
        tv.setMaxLines(1);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        Drawable drawable = ContextCompat.getDrawable(mContext,R.drawable.dynamic_notice_icon);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawablePadding(10);
        tv.setCompoundDrawables(drawable, null, null, null);
        return tv;
    }

    // 创建ViewFlipper下的TextView
    private RadioGroup createRadioGroup(int position, String... text) {

        RadioGroup rg = new RadioGroup(mContext);
        RadioButton rb1 = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.null_layout, null);


        rb1.setText(text[0]);
        rb1.setTextColor(textColor);
        rb1.setTextSize(textSize);
        rb1.setSingleLine(singleLine);
        rb1.setMaxLines(1);
        rb1.setEllipsize(TextUtils.TruncateAt.END);
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.dynamic_notice_icon);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rb1.setCompoundDrawablePadding(10);
        rb1.setCompoundDrawables(drawable, null, null, null);
        rg.addView(rb1);


        RadioButton rb2 = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.null_layout, null);
        rb2.setPadding(0, 6, 0, 0);
        rb2.setText(text[1]);
        rb2.setTextColor(textColor);
        rb2.setTextSize(textSize);
        rb2.setSingleLine(singleLine);
        rb2.setMaxLines(1);
        rb2.setEllipsize(TextUtils.TruncateAt.END);
        if (!TextUtils.isEmpty(text[1])) {
            rb2.setCompoundDrawablePadding(10);
            rb2.setCompoundDrawables(drawable, null, null, null);
        }
        rg.addView(rb2);
        rg.setGravity(gravity);
        rg.setTag(position);
        return rg;
    }

    public int getPosition() {
        return (int) getCurrentView().getTag();
    }

    public List<MarqueeModel.DataNewsModel> getNotices() {
        return notices;
    }

    public void setNotices(List<MarqueeModel.DataNewsModel> notices) {
        this.notices = notices;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, TextView textView);

        void onItemClick(int position, MarqueeModel.DataNewsModel item);
    }

    class ViewHolder {
        TextView textView1;
        TextView textView2;
    }
}
