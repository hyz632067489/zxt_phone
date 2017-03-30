package com.zxt.zxt_phone.utils;

/**
 * Created by hyz on 2017/3/24.
 * powered by company
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

/**
 * 进行添加为照片添加水印图片和文字 帮助类
 */
public class WaterUtils {
    /**
     * 进行添加水印图片和文字
     *
     * @param src
     * @param waterMak
     * @return
     */
    public static Bitmap createBitmap(Bitmap src, Bitmap waterMak, String title) {
        if (src == null) {
            return src;
        }
        // 获取原始图片与水印图片的宽与高
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = waterMak.getWidth();
        int wh = waterMak.getHeight();
        Log.i("jiangqq", "w = " + w + ",h = " + h + ",ww = " + ww + ",wh = "
                + wh);
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(newBitmap);
        // 往位图中开始画入src原始图片
        mCanvas.drawBitmap(src, 0, 0, null);
        // 在src的右下角添加水印
        Paint paint = new Paint();
        //paint.setAlpha(100);
        mCanvas.drawBitmap(waterMak, w - ww - 5, h - wh - 5, paint);
        // 开始加入文字
        if (null != title) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.RED);
            textPaint.setTextSize(50);
            String familyName = "宋体";
            Typeface typeface = Typeface.create(familyName,
                    Typeface.BOLD_ITALIC);
            textPaint.setTypeface(typeface);
            textPaint.setTextAlign(Paint.Align.CENTER);
            mCanvas.drawText(title, w / 2, h/4, textPaint);

        }
        mCanvas.save(Canvas.ALL_SAVE_FLAG);
        mCanvas.restore();
        return newBitmap;
    }
}
