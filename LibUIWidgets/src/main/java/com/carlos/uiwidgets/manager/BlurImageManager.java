package com.carlos.uiwidgets.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by Carlos on 2016/8/17.
 */
public class BlurImageManager {

    // ===========================================================
    // Constants
    // ===========================================================

    /**
     * 图片缩放比例
     */
    private static final float BITMAP_SCALE = 0.1f;
    /**
     * 最大模糊度(在0.0到25.0之间)
     */
    private static final float BLUR_RADIUS = 5f;

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    public static Bitmap doBlur(Context context, Bitmap src) {
        // 计算图片缩小后的长宽
        int scaledWidth = Math.round(src.getWidth() * BITMAP_SCALE);
        int scaledHeight = Math.round(src.getHeight() * BITMAP_SCALE);

        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(src, scaledWidth, scaledHeight, false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        // api 17
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        // api 11
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        // api 17
        blurScript.setRadius(BLUR_RADIUS);
        // 设置blurScript对象的输入内存
        // api 17
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        // api 17
        tmpOut.copyTo(outputBitmap);

        rs.destroy();
        blurScript.destroy();
        tmpIn.destroy();
        tmpOut.destroy();

        return outputBitmap;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================    

}
