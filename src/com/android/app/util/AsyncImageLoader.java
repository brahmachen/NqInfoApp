package com.android.app.util;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.android.ais.app.Constants;

public class AsyncImageLoader {
	public HashMap<String, SoftReference<Drawable>> imageCache;
	public String defaultFolder;
	public int reqImgWidth;
	public int reqImgHeight;

	public AsyncImageLoader(int reqImgWidth, int reqImgHeight) {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
		defaultFolder = Constants.NEWS_LIST_IMG_CACHE_FOLDER; // Ĭ�ϻ���·���������б��ͼƬ����·��
		this.reqImgWidth = reqImgWidth;
		this.reqImgHeight = reqImgHeight;
		Log.i("create Loader", "" + imageCache.size());
	}

	public AsyncImageLoader(String SDcardCacheFolder, int reqImgWidth,
			int reqImgHeight) {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
		this.defaultFolder = SDcardCacheFolder; // ����ͼƬ����·��
		this.reqImgWidth = reqImgWidth;
		this.reqImgHeight = reqImgHeight;
	}

	/**
	 * ���ص����ز����浽SD��
	 * @param imageUrl
	 * @param imageCallback
	 * @return
	 */
	public Drawable loadDrawable(final String imageUrl, final ListView mListView,
			final ImageCallback imageCallback) {
		final String folder = defaultFolder;
		//
	
		//ע���ļ���
		String outFileName = folder
				+ imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		
		File file = new File(outFileName);
		if (imageCache.containsKey(imageUrl)) {
		
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			} else {
				if (file.exists()) {
					Bitmap bitmap = FileAccess.decodeSampledBitmapFromResource(
							outFileName, reqImgWidth, reqImgHeight);
					// Bitmap bitmap = BitmapFactory.decodeFile(outFileName);
					Drawable tmpDrawable = new BitmapDrawable(bitmap);
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							tmpDrawable)); // cache������������Ѿ���ϵͳ�ջأ����°�ͼƬ��SD����������ڴ�
					System.gc();
					return tmpDrawable;
				}
			}
		} else if (file.exists()) {
			
			Bitmap bitmap = FileAccess.decodeSampledBitmapFromResource(
					outFileName, reqImgWidth, reqImgHeight);
			// Bitmap bitmap = BitmapFactory.decodeFile(outFileName);
			Drawable drawable = new BitmapDrawable(bitmap);
			imageCache.put(imageUrl, new SoftReference<Drawable>(drawable)); // ��SD�������ͼƬ����cache
			System.gc();
			return drawable;
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				
				imageCallback.imageLoaded((Drawable) message.obj, imageUrl ,mListView);
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = HttpUtil.loadImageFromUrlWithStore(folder,
						imageUrl, reqImgWidth, reqImgHeight);
				if (drawable == null) {
					drawable = HttpUtil.loadImageFromUrl(imageUrl, "tmp");
					if (drawable != null) {
						imageCache.put(imageUrl, new SoftReference<Drawable>(
								drawable));

					}
				} else {
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
				}
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl,ListView mListView);
	}
}
