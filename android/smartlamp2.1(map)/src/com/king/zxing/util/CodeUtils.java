/*
 * Copyright (C) 2018 Jenly Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.king.zxing.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.text.TextPaint;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.king.zxing.DecodeFormatManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CodeUtils {

    private CodeUtils(){
        throw new AssertionError();
    }

    /**
     * 鐢熸垚浜岀淮鐮�
     * @param content
     * @param heightPix
     * @return
     */
    public static Bitmap createQRCode(String content, int heightPix) {
        return createQRCode(content,heightPix,null);
    }

    /**
     * 鐢熸垚浜岀淮鐮�
     * @param content
     * @param heightPix
     * @param logo
     * @return
     */
    public static Bitmap createQRCode(String content, int heightPix, Bitmap logo) {
        //閰嶇疆鍙傛暟
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put( EncodeHintType.CHARACTER_SET, "utf-8");
        //瀹归敊绾у埆
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //璁剧疆绌虹櫧杈硅窛鐨勫搴�
        hints.put(EncodeHintType.MARGIN, 1); //default is 4
        return createQRCode(content,heightPix,logo,hints);
    }

    /**
     * 鐢熸垚浜岀淮鐮�
     * @param content
     * @param heightPix
     * @param logo
     * @param hints
     * @return
     */
    public static Bitmap createQRCode(String content, int heightPix, Bitmap logo,Map<EncodeHintType,?> hints) {
        try {

            // 鍥惧儚鏁版嵁杞崲锛屼娇鐢ㄤ簡鐭╅樀杞崲
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, heightPix, heightPix, hints);
            int[] pixels = new int[heightPix * heightPix];
            // 涓嬮潰杩欓噷鎸夌収浜岀淮鐮佺殑绠楁硶锛岄�愪釜鐢熸垚浜岀淮鐮佺殑鍥剧墖锛�
            // 涓や釜for寰幆鏄浘鐗囨í鍒楁壂鎻忕殑缁撴灉
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < heightPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * heightPix + x] = 0xff000000;
                    } else {
                        pixels[y * heightPix + x] = 0xffffffff;
                    }
                }
            }

            // 鐢熸垚浜岀淮鐮佸浘鐗囩殑鏍煎紡
            Bitmap bitmap = Bitmap.createBitmap(heightPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, heightPix, 0, 0, heightPix, heightPix);

            if (logo != null) {
                bitmap = addLogo(bitmap, logo);
            }

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 鍦ㄤ簩缁寸爜涓棿娣诲姞Logo鍥炬
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //鑾峰彇鍥剧墖鐨勫楂�
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo澶у皬涓轰簩缁寸爜鏁翠綋澶у皬鐨�1/6
        float scaleFactor = srcWidth * 1.0f / 6 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 瑙ｆ瀽浜岀淮鐮佸浘鐗�
     * @param bitmapPath
     * @return
     */
    public static String parseQRCode(String bitmapPath) {
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        return parseQRCode(bitmapPath,hints);
    }

    /**
     * 瑙ｆ瀽浜岀淮鐮佸浘鐗�
     * @param bitmapPath
     * @param hints
     * @return
     */
    public static String parseQRCode(String bitmapPath, Map<DecodeHintType,?> hints){
        try {
            QRCodeReader reader = new QRCodeReader();

            Result result = null;
            RGBLuminanceSource source = getRGBLuminanceSource(compressBitmap(bitmapPath));
            if (source != null) {
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    result = reader.decode(bitmap,hints);
                } catch (Exception e) {
                    //瑙ｆ瀽澶辫触鍒欓�氳繃GlobalHistogramBinarizer 鍐嶈瘯涓�娆�
                    BinaryBitmap bitmap1 = new BinaryBitmap(new GlobalHistogramBinarizer(source));
                    try {
                        result = reader.decode(bitmap1);
                    } catch (NotFoundException ne) {

                    }
                } finally {
                    reader.reset();
                }
            }
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 瑙ｆ瀽涓�缁寸爜/浜岀淮鐮佸浘鐗�
     * @param bitmapPath
     * @return
     */
    public static String parseCode(String bitmapPath){
        Map<DecodeHintType,Object> hints = new HashMap<>();
        //娣诲姞鍙互瑙ｆ瀽鐨勭紪鐮佺被鍨�
        Vector<BarcodeFormat> decodeFormats = new Vector<>();
        decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.AZTEC_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.PDF417_FORMATS);

        hints.put(DecodeHintType.TRY_HARDER,Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        return parseCode(bitmapPath,hints);
    }

    /**
     * 瑙ｆ瀽涓�缁寸爜/浜岀淮鐮佸浘鐗�
     * @param bitmapPath
     * @param hints 瑙ｆ瀽缂栫爜绫诲瀷
     * @return
     */
    public static String parseCode(String bitmapPath, Map<DecodeHintType,Object> hints){

        try {
            MultiFormatReader reader = new MultiFormatReader();
            reader.setHints(hints);
            Result result = null;
            RGBLuminanceSource source = getRGBLuminanceSource(compressBitmap(bitmapPath));
            if (source != null) {
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    result = reader.decodeWithState(bitmap);
                } catch (Exception e) {//瑙ｆ瀽澶辫触鍒欓�氳繃GlobalHistogramBinarizer 鍐嶈瘯涓�娆�
                    BinaryBitmap bitmap1 = new BinaryBitmap(new GlobalHistogramBinarizer(source));
                    try {
                        result = reader.decodeWithState(bitmap1);
                    } catch (Exception ne) {

                    }
                } finally {
                    reader.reset();
                }
            }
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 鍘嬬缉鍥剧墖
     * @param path
     * @return
     */
    private static Bitmap compressBitmap(String path){

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 寮�濮嬭鍏ュ浘鐗囷紝姝ゆ椂鎶妎ptions.inJustDecodeBounds 璁惧洖true浜�
        newOpts.inJustDecodeBounds = true;//鑾峰彇鍘熷鍥剧墖澶у皬
        BitmapFactory.decodeFile(path, newOpts);// 姝ゆ椂杩斿洖bm涓虹┖
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float width = 800f;
        float height = 480f;
        // 缂╂斁姣斻�傜敱浜庢槸鍥哄畾姣斾緥缂╂斁锛屽彧鐢ㄩ珮鎴栬�呭鍏朵腑涓�涓暟鎹繘琛岃绠楀嵆鍙�
        int be = 1;// be=1琛ㄧず涓嶇缉鏀�
        if (w > h && w > width) {// 濡傛灉瀹藉害澶х殑璇濇牴鎹搴﹀浐瀹氬ぇ灏忕缉鏀�
            be = (int) (newOpts.outWidth / width);
        } else if (w < h && h > height) {// 濡傛灉楂樺害楂樼殑璇濇牴鎹搴﹀浐瀹氬ぇ灏忕缉鏀�
            be = (int) (newOpts.outHeight / height);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 璁剧疆缂╂斁姣斾緥
        // 閲嶆柊璇诲叆鍥剧墖锛屾敞鎰忔鏃跺凡缁忔妸options.inJustDecodeBounds 璁惧洖false浜�
        newOpts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, newOpts);
    }

    /**
     * 鑾峰彇RGBLuminanceSource
     * @param bitmap
     * @return
     */
    private static RGBLuminanceSource getRGBLuminanceSource(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        return new RGBLuminanceSource(width, height, pixels);

    }

    /**
     * 鐢熸垚鏉″舰鐮�
     * @param content
     * @param format
     * @param desiredWidth
     * @param desiredHeight
     * @return
     */
    public static Bitmap createBarCode(String content,BarcodeFormat format, int desiredWidth, int desiredHeight) {
        return createBarCode(content,format,desiredWidth,desiredHeight,null);

    }

    /**
     * 鐢熸垚鏉″舰鐮�
     * @param content
     * @param format
     * @param desiredWidth
     * @param desiredHeight
     * @param hints
     * @return
     */
    public static Bitmap createBarCode(String content, BarcodeFormat format, int desiredWidth, int desiredHeight, Map<EncodeHintType,?> hints) {
        return createBarCode(content,format,desiredWidth,desiredHeight,hints,false,40,Color.BLACK);
    }

    /**
     * 鐢熸垚鏉″舰鐮�
     * @param content
     * @param format
     * @param desiredWidth
     * @param desiredHeight
     * @param hints
     * @param isShowText
     * @return
     */
    public static Bitmap createBarCode(String content, BarcodeFormat format, int desiredWidth, int desiredHeight, Map<EncodeHintType,?> hints, boolean isShowText) {
        return createBarCode(content,format,desiredWidth,desiredHeight,hints,isShowText,40,Color.BLACK);
    }

    /**
     * 鐢熸垚鏉″舰鐮�
     * @param content
     * @param format
     * @param desiredWidth
     * @param desiredHeight
     * @param hints
     * @param isShowText
     * @param textSize
     * @param textColor
     * @return
     */
    public static Bitmap createBarCode(String content,BarcodeFormat format, int desiredWidth, int desiredHeight,Map<EncodeHintType,?> hints,boolean isShowText,int textSize, int textColor) {
        if(TextUtils.isEmpty(content)){
            return null;
        }
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix result = writer.encode(content, format, desiredWidth,
                    desiredHeight, hints);
            int width = result.getWidth();
            int height = result.getHeight();
            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            if(isShowText){
                return addCode(bitmap,content,textSize,textColor,textSize/2);
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 鏉″舰鐮佷笅闈㈡坊鍔犳枃鏈俊鎭�
     * @param src
     * @param code
     * @param textSize
     * @param textColor
     * @return
     */
    private static Bitmap addCode(Bitmap src,String code,int textSize,int textColor,int offset) {
        if (src == null) {
            return null;
        }

        if (TextUtils.isEmpty(code)) {
            return src;
        }

        //鑾峰彇鍥剧墖鐨勫楂�
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();

        if (srcWidth <= 0 || srcHeight <= 0) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight + textSize + offset * 2, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            TextPaint paint = new TextPaint();
            paint.setTextSize(textSize);
            paint.setColor(textColor);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(code,srcWidth/2,srcHeight + textSize /2 + offset,paint);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.printStackTrace();
        }

        return bitmap;
    }

}
