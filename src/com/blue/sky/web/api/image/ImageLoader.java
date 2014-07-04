package com.blue.sky.web.api.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.blue.sky.androidwebapp.R;

public class ImageLoader {

	private MemoryCache memoryCache = new MemoryCache();
	private FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	private ExecutorService executorService;
	private boolean isSrc;

	/**
	 * @param context
	 *            上下文对象
	 * @param flag
	 *            true为source资源，false为background资源
	 */
	public ImageLoader(Context context, boolean flag) {
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
		isSrc = flag;
	}

	final int stub_id = R.drawable.ic_launcher;

	public void DisplayImage(String url, ImageView imageView) {
		String u1 = url.substring(0, url.lastIndexOf("/") + 1);
		String u2 = url.substring(url.lastIndexOf("/") + 1);
		try {
			u2 = URLEncoder.encode(u2, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url = u1 + u2;
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			if (isSrc)
				imageView.setImageBitmap(bitmap);
			else
				imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
		} else {
			queuePhoto(url, imageView);
			if (isSrc)
				imageView.setImageResource(stub_id);
			else
				imageView.setBackgroundResource(stub_id);
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url) {
		try {
			File f = fileCache.getFile(url);
			// 从sd卡
			Bitmap b = onDecodeFile(f);
			if (b != null)
				return b;
			// 从网络
			Bitmap bitmap = null;
			System.out.println("ImageLoader-->download");
			HttpUtil.CopyStream(url, f);
			bitmap = onDecodeFile(f);

			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Bitmap onDecodeFile(File f) {
		try {
			return BitmapFactory.decodeStream(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解码图像用来减少内存消耗
	 * 
	 * @param f
	 * @return
	 */
	public Bitmap decodeFile(File f) {
		try {
			// 解码图像大小
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			// 找到正确的刻度值，它应该是2的幂。
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	/**
	 * 任务队列
	 * 
	 * @author Scorpio.Liu
	 * 
	 */
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url);
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	/**
	 * 显示位图在UI线程
	 * 
	 * @author Scorpio.Liu
	 * 
	 */
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {
				if (isSrc)
					photoToLoad.imageView.setImageBitmap(bitmap);
				else
					photoToLoad.imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
			} else {
				if (isSrc)
					photoToLoad.imageView.setImageResource(stub_id);
				else
					photoToLoad.imageView.setBackgroundResource(stub_id);
			}
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

}
