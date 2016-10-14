package com.android.app.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class FileAccess {
	/**
	 * �����ļ���
	 * 
	 * @param dirName
	 */
	public static void MakeDir(String dirName) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File destDir = new File(dirName);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
		}
	}

	/**
	 * ��ĳ���ļ����µ�ͼƬת�浽������ļ�����
	 * 
	 * @param sourcePath
	 *            ԭ·��
	 * @param destinationPath
	 *            Ŀ��·��
	 */
	public static void restoreImg(String sourcePath, String imgName,
			String destinationPath) {
		try {
			FileAccess.MakeDir(destinationPath);
			String sourceFileName = sourcePath + imgName;
			Log.i("--source img file path--", sourceFileName);
			Bitmap bitmap = BitmapFactory.decodeFile(sourceFileName);
			String targetFileName = destinationPath + imgName;
			Log.i("--target img file path--", targetFileName);
			bitmap.compress(CompressFormat.PNG, 50, new FileOutputStream(
					targetFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ͨ���ļ��к�ͼƬ�����SD�����ͼƬ
	 * 
	 * @param folder
	 * @param imgName
	 * @return
	 */
	public static Drawable getDrawableByFolder(String folder, String imgName) {
		Bitmap bitmap = BitmapFactory.decodeFile(folder + imgName);
		return new BitmapDrawable(bitmap);
	}


	/**
	 * ���� ���ű���
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
//		Log.i("calculateInSampleSize", "reqWidth:" + reqWidth + "    reqHeight:" + reqHeight);
		final int height = options.outHeight;
		final int width = options.outWidth;
//		Log.i("calculateInSampleSize", "outWidth:" + options.outWidth + "    outHeight:" + options.outHeight);
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
//		Log.i("calculateInSampleSize", "inSampleSize:" + inSampleSize);
		return inSampleSize;
	}

	/**
	 * �������ͼƬ�ߴ�
	 * 
	 * @param outFileName
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(String outFileName,
			int reqWidth, int reqHeight) {
//		Log.i("decodeSampledBitmapFromResource", "reqWidth:" + reqWidth + "    reqHeight:" + reqHeight);
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(outFileName, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inTempStorage = new byte[16*1024];
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(outFileName, options);
	}

	public static Bitmap decodeSampledBitmapFromByteArray(byte[] b,
			int reqWidth, int reqHeight) {
//		Log.i("decodeSampledBitmapFromByteArray", "reqWidth:" + reqWidth + "    reqHeight:" + reqHeight);
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(b, 0, b.length, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inTempStorage = new byte[16*1024];
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(b, 0, b.length, options);
	}

	/**
	 * ɾ��ָ��·�����ļ�
	 * 
	 * @param filePath
	 *            �ļ�·��
	 */
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * ɾ��ĳһ�ļ��������е��ļ�(ע�⣺������ļ����»����ļ��У������ᱻɾ��)
	 * 
	 * @param folder
	 *            �ļ��е�·��
	 */
	public static void deleteAllFiles(String folder) {
		File file = new File(folder);
		if (!file.exists()) {
			Log.e("deleteAllFiles", "foler doesn't exists");
			return;
		}
		if (!file.isDirectory()) {
			Log.e("deleteAllFiles", "folder isn't a Directory");
			return;
		}
		File fileList[] = file.listFiles();
		for (File f : fileList) {
			if (f.isFile()) {
				f.delete();
			}
		}
		Log.i("deleteAllFiles", "clean complete");
	}

	/**
	 * ��ȡĳһ�ļ����������ļ��Ĵ�С�ܺ�(ע�⣺������ļ����»������ļ��У����ļ��еĴ�С�����ᱻ����)
	 * 
	 * @param folder
	 *            �ļ��е�·�����������������һ���ļ�����ֱ�ӷ������Ĵ�С��
	 * @return �ʼ۴�С���ܺ�
	 */
	public static long getFolderSize(String folder) {
		File file = new File(folder);
		if (!file.exists()) {
			Log.e("getFolderSize", "foler doesn't exists");
			return -1L;
		}
		if (!file.isDirectory()) {
			Log.e("deleteAllFiles", "folder isn't a Directory");
			return file.length();
		}
		long sum = 0L;
		File fileList[] = file.listFiles();
		for (File f : fileList) {
			if (f.isFile()) {
				sum += f.length();
			}
		}
		Log.i("getFolderSize", "get completed  -- " + sum);
		return sum;
	}

	/**
	 * ת���ļ���С������Ѻõ��׶����ַ���
	 * 
	 * @param fileS
	 *            �ļ��Ĵ�С
	 * @return
	 */
	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	/**
	 * ����inSampleSize��ֵ
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
}
