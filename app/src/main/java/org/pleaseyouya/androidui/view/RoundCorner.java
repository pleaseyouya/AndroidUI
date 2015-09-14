package org.pleaseyouya.androidui.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * 将一个图片变为圆角图片
 */
public class RoundCorner {

    /**
     * 获取一张图片对应的圆角图片
     * @param bitmap  需要转化成圆角的位图
     * @param roundDegree  圆角的度数, 数值越大, 圆角越大
     * @return  处理后的圆角位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int roundDegree) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        // 在canvas上面画的东西要指定最后保存进的bitmap为output
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        int color = 0xff424242;
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        // float版本的Rect
        RectF rectF = new RectF(rect);
        float roundDeg = roundDegree;

        //打开抗锯齿
        //类似的还有
        // setDither, 打开或关闭图像抖动, 如果位图与屏幕的像素配置不同时会用到, 如一张ARGB_8888的图片和一个RGB565的显示屏
        // setFilter, 打开或关闭滤镜, 当收缩或者拉伸位图时使用这个让位图看上去更加平滑
        // setTileMode, 如果设置true, 位图将被重复并铺满容器, 就像瓦片一样, 此时gravity属性将失效. 常用的mode有
        //     clamp:复制位图边缘的颜色来填充容器剩下的空白部分
        //     repeat: 复制位图填充容器
        //     mirror: 和repeat的区别在于相邻的两张是对称的
        paint.setAntiAlias(true);

        // 用黑色填满canvas
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        // 在canvas上画出圆角矩形, 需要指出长轴的圆角度和短轴的圆角度
        canvas.drawRoundRect(rectF, roundDeg, roundDeg, paint);

        // 设置在canvas上画图时将发生重叠该如何处理, DST在下层, SRC在上层
        // 常用的mode有
        // SRC_IN 取交集, 显示上层
        // SRC 显示上层
        // DST 显示下层
        // SRC_OVER 正常叠加显示
        // DST_OVER 上下层都显示, 下层居上
        // DST_IN 取交集, 显示下层
        // SRC_OUT 取上层, 绘制非交集
        // DST_OUT 取下层, 绘制非交集
        // SRC_ATOP 取下层非交集部分与上层交集部分
        // DST_ATOP 取上层非交集部分与下层交集部分
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 第一个rect表示对图片bitmap进行裁剪的区域, 第二个rect表示要在canvas上显示的区域
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
