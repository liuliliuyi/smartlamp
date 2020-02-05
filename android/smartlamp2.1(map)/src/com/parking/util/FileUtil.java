package com.parking.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

public class FileUtil {
	private static final String TAG = "FileUtil";
	private static final File parentPath = Environment
			.getExternalStorageDirectory();
	private static String storagePath = "";
	private static final String DST_FOLDER_NAME = "yichiCamera";
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/yichipic/";
	static File f111;
	private static FileOutputStream fileOutputStream;

	private static String initPath() {
		if (storagePath.equals("")) {
			storagePath = parentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
			File f = new File(storagePath);
			if (!f.exists()) {
				f.mkdir();
			}
		}
		return storagePath;
	}

	private static String path = "";
	static {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory() + "/yichi";
		} else {
			path = Environment.getDataDirectory().getAbsolutePath() + "/yichi";
		}
	}

	// ɾ���ļ�
	public static void delFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}
	public static String getWaterPhotoPath12(String path1) {

		File file = new File(path +path1);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path + path1;
	}
	
	public static String getWaterPhotoPath11() {

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	public static String getWaterPhotoPath() {

		File file = new File(path + "/yichiPhoto/");
		if (!file.exists()) {
			file.mkdirs();
		}
		return path + "/yichiPhoto/";
	}

	public static String getWaterPhotoPath1() {
		File file = new File(path + "/yichilife/");
		if (!file.exists()) {
			file.mkdirs();
		}
		return path + "/yichilife/";
	}

	public static String getdebug() {

		File file = new File(path + "/Yichidebug/");
		if (!file.exists()) {
			file.mkdirs();
		}
		return path + "/Yichidebug/";
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static File saveBitmap1(Bitmap bm, String picName)

	{

		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			f111 = new File(SDPATH, picName + ".JPEG");
			if (f111.exists()) {
				f111.delete();
			}
			FileOutputStream out = new FileOutputStream(f111);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f111;
	}

	public static void saveDate(OutputStream outputStream, String contentString)
			throws Exception {
		outputStream.write(contentString.getBytes());
		outputStream.close();
	}

}
