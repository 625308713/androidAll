package com.dai.daicommon.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.dai.daicommon.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 图片加载
 */
public class DemoGlideActivity extends Activity {
    private ImageView imageView;
    //圆角图片
    private CircleImageView profile_image;
    private Context context;
    String imageUrl = "http://c.hiphotos.baidu.com/image/pic/item/d439b6003af33a87433692cfca5c10385243b588.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_glide);
        context = this;
        imageView = findViewById(R.id.my_img);
        profile_image = findViewById(R.id.profile_image);

        //网络加载图片到ImageView中
        Glide.with(context).load(imageUrl).into(imageView);

        //当加载网络图片时，由于加载过程中图片未能及时显示，此时可能需要设置等待时的图片，通过placeHolder()方法
        Glide.with(context).load(imageUrl).placeholder(R.mipmap.add_tick_btn_green).into(imageView);

        //当加载图片失败时，通过error(Drawable drawable)方法设置加载失败后的图片显示：
        Glide.with(context).load(imageUrl).error(R.mipmap.add_circle_btn_gray).into(imageView);

        //图片的缩放，centerCrop()和fitCenter()：
        //使用centerCrop是利用图片图填充ImageView设置的大小，如果ImageView的Height是match_parent则图片就会被拉伸填充
        Glide.with(context).load(imageUrl).centerCrop().into(profile_image);

        //圆角图片
        Glide.with(context).load(imageUrl).transform(new GlideRoundTransform(context, 40)).into(imageView);
        //圆形图片
//        Glide.with(context).load(imageUrl).transform(new GlideCircleTransform(context)).into(imageView);
    }




    //圆形图片
    public class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }

    //圆角图片
    public class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }
}
