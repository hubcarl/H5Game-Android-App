package com.blue.sky.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.blue.sky.h5.game.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.CircleDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@SuppressLint("HandlerLeak")
public class ImageLoadUtil {

    // 磁盘缓存的两种限制方式: 限制最大的条数，限制最大的体积
    public static final int MAX_DISK_CACHE_FILE_COUNT = 100;
    public static final int MAX_DISK_CACHE_FILE_SIZE = 30 * 1024 * 1024;
    public static final int MAX_POOL_THREAD_SIZE = 5;
    public static ImageLoader imageLoader = null;

    public enum ImageStyle {
        CIRCLE_MEMBER, USER_MEMBER, PHOTO;
    }

    public static ImageLoader getInstance(){
        return imageLoader;
    }

    // 初始化 图片加载器
    public static void configuration(Context context) {
        if (imageLoader == null) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                    .discCacheSize(MAX_DISK_CACHE_FILE_SIZE)
                            // .discCacheFileCount(MAX_DISK_CACHE_FILE_COUNT)
                    .discCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                            // .writeDebugLogs() // 输入日志，打包发布的时候注释掉
                    .build();

            ImageLoader.getInstance().init(config);

            imageLoader = ImageLoader.getInstance();
        }
    }

    /**
     * 构造图片显示的配置选项
     *
     * @param placeHolderImgRes 默认的占位显示图片
     * @param emptyUrlImgRes    空链接显示的图片
     * @param failedImgRes      加载失败显示的图片
     * @param cacheInMemory     是否内存缓存，图片过大，建议不要内存缓存
     * @return
     */
    private static DisplayImageOptions constructDisplayOption(String url, int placeHolderImgRes, int emptyUrlImgRes,
                                                              int failedImgRes, boolean cacheInMemory, BitmapDisplayer displayer) {
        DisplayImageOptions.Builder bulider = new DisplayImageOptions.Builder();
        bulider.cacheOnDisc(true); // 默认开启磁盘缓存 缓存在默认位置
        // /sdcard/Android/data/[package_name]/cache
        bulider.cacheInMemory(cacheInMemory);
        bulider.resetViewBeforeLoading(true);
        bulider.bitmapConfig(Bitmap.Config.RGB_565);
        // bulider.displayer(new FadeInBitmapDisplayer(200));//加载动画
        if (displayer != null) {
            bulider.displayer(displayer);
        }

        if (placeHolderImgRes > 0) {
            bulider.showStubImage(placeHolderImgRes);
        }

        if (emptyUrlImgRes > 0) {
            bulider.showImageForEmptyUri(emptyUrlImgRes);
        }

        if (failedImgRes > 0) {
            bulider.showImageOnFail(failedImgRes);
        }

        return bulider.build();
    }

    /**
     * *
     *
     * @param imageView 显示图片的组件
     * @param url       图片加载的URL
     *                  默认的占位显示图片
     */
    public static void loadImage(ImageView imageView, String url, ImageStyle imageStyle) {
        int loadImageRes = 0;
        if (imageStyle == ImageStyle.USER_MEMBER) {
            loadImageRes = R.drawable.ic_launcher;
        } else if (imageStyle == ImageStyle.CIRCLE_MEMBER) {
            loadImageRes = R.drawable.ic_launcher;
        } else if (imageStyle == ImageStyle.PHOTO) {
            loadImageRes = R.drawable.ic_launcher;
        }
        loadImage(imageView, url, loadImageRes);
    }

    public static void loadImageByResId(ImageView imageView, String url, int resId) {
        loadImage(imageView, url, resId);
    }

    /**
     * 加载显示图片
     *
     * @param imageView      显示图片的组件
     * @param url            图片加载的URL
     * @param placeHolderImg 默认的占位显示图片
     */
    public static void loadImage(ImageView imageView, String url, int placeHolderImg) {
        loadImage(imageView, url, placeHolderImg, true);
    }

    /**
     * 加载显示图片
     *
     * @param imageView      显示图片的组件
     * @param url            图片加载的URL
     * @param placeHolderImg 默认的占位显示图片
     * @param cacheInMemory  是否将图片缓存到内存中(大图不建议缓存到内存中)
     */
    public static void loadImage(ImageView imageView, String url, int placeHolderImg, boolean cacheInMemory) {
        loadImage(imageView, url, placeHolderImg, 0, 0, cacheInMemory, null);
    }

    /**
     * 绘制圆形头像 0表示没有边框
     *
     * @param imageView
     * @param url
     * @param color     请用getresrouce.getcolor
     */
    public static void loadCircleImage(ImageView imageView, String url, int color) {
        loadImage(imageView, url, R.drawable.icon_default_avatar, 0, 0, true, new CircleDisplayer(color));
    }

    public static void loadRoundImage(ImageView imageView, String url, int defaultImg) {
        loadImage(imageView, url, defaultImg, 0, 0, true, new RoundedBitmapDisplayer(5));
    }

    /**
     * 加载显示图片
     *
     * @param imageView      显示图片的组件
     * @param url            图片加载的URL
     * @param placeHolderImg 默认的占位显示图片
     * @param emptyUrlImgRes 空链接显示的图片，不定义请置 0
     * @param failedImgRes   加载失败显示的图片，不定义请置 0
     * @param cacheInMemory  是否将图片缓存到内存中 (大图不建议缓存到内存中)
     */
    public static void loadImage(ImageView imageView, String url, int placeHolderImg, int emptyUrlImgRes,
                                 int failedImgRes, boolean cacheInMemory, BitmapDisplayer displayer) {
        DisplayImageOptions option = constructDisplayOption(url, placeHolderImg, emptyUrlImgRes, failedImgRes,
                cacheInMemory, displayer);
        if (Strings.isEmpty(url)
                || (!url.endsWith(".jpg") && !url.endsWith(".jpeg") && !url.endsWith(".png") && !url.endsWith(".JPG")
                && !url.endsWith(".JPEG") && !url.endsWith(".PNG"))) {
            if (placeHolderImg != 0) {
                imageView.setImageResource(placeHolderImg);
            }
            return;
        }
        imageLoader.displayImage(url, imageView, option);
    }


    /**
     * 单纯缓存下来先，下次再用
     *
     * @param url
     */
    public static void loadImage(String url) {
        imageLoader.loadImage(url, null);
    }

    /**
     * 尝试获取图片资源
     *
     * @param url
     * @return
     */
    private static boolean connectImageUrl(String url) {
        try {
            URL imageUrl = new URL(url);// 创建URL对象。
            URLConnection uc = imageUrl.openConnection();// 创建一个连接对象。
            InputStream in = uc.getInputStream();// 获取连接对象输入字节流。如果地址无效则会抛出异常。
            in.close();
            return true;
        } catch (Exception e) {
            Log.e("connectImageUrl", "图片资源" + url + "不存在");
            return false;
        }
    }

    public static String getPath4Url(Context context, String url) {
        File file = StorageUtils.getIndividualCacheDirectory(context);
        String filePath = file.getAbsolutePath() + "/" + getName4Url(url);
        return filePath;
    }

    public static String getName4Url(String url) {
        return new Md5FileNameGenerator().generate(url);
    }
}
