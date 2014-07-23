///*
// * Myro/Java license - GPL
// * 
// * Myro/Java is a Java implementation of the Myro API, defined by the Institute for Robots in
// * Education (IPRE).  See http://wiki.roboteducation.org for more information.
// * 
// * Copyright 2013-2014 Douglas Harms dharms@depauw.edu
// * 
// * This file is part of Myro/Java.
// * 
// * Myro/Java is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * any later version.
// * 
// * Myro/Java is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// * 
// * You should have received a copy of the GNU General Public License
// * along with Myro/Java.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package depauw.edu.myro.original;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import android.content.res.Resources.NotFoundException;
//import android.graphics.Bitmap;
//import android.util.Log;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.BinaryBitmap;
//import com.google.zxing.ChecksumException;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.FormatException;
//import com.google.zxing.LuminanceSource;
//import com.google.zxing.MultiFormatReader;
//import com.google.zxing.Reader;
//import com.google.zxing.ReaderException;
//import com.google.zxing.Result;
//import com.google.zxing.ResultPoint;
//import com.google.zxing.common.HybridBinarizer;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//
///**
// * Classes which allows a programmer to utilize QR codes. Read and write a QR code.
// * 
// * @author Alexander Miller, Leonora Bresette-Buccino
// * @version Summer 2014
// * 
// * Idea taken from URL: http://javapapers.com/core-java/java-qr-code/
// * Example: https://code.google.com/p/zxing/source/browse/trunk/javase/src/com/google/zxing/client/j2se/MatrixToImageWriter.java?r=1226
// */
//
//public class MyroQRReader
//{
//
//    String charset;
//    Map<EncodeHintType, ErrorCorrectionLevel> hintMap;
//    int height;
//    int width;
//    @SuppressWarnings("unused")
//	private static final int BLACK = 0xFF000000;
//    @SuppressWarnings("unused")
//	private static final int WHITE = 0xFFFFFFFF;
//
//    /**
//     * Constructs a new MyroQRReader with default width and height of 200
//     */
//    public MyroQRReader()
//    {
//        this.charset = "UTF-8";
//        this.hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        this.height = 200;
//        this.width = 200;
//    }
//
//    /**
//     * Constructs and new MyroQRReader with given height and width
//     * @param height
//     *    an int 
//     * @param width
//     */
//    public MyroQRReader(int height, int width)
//    {
//        this.charset = "UTF-8";
//        this.hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        this.height = height;
//        this.width = width;
//    }
//
//    /**
//     * Returns the string that was encoded in the QR code
//     * @param im
//     *  a bufferedImage which contains the QR code
//     * @return returns a decoded string if it worked, empty string if not.
//     * @throws ReaderException 
//     */
////    public String readQRCode(Bitmap im)
////    {
////        try
////        {
////            Map hintMap1 = this.hintMap;
////
////       //     BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new LuminanceSource(im)));
////   //        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap1);
////          return qrCodeResult.getText();
////        }
////        catch (NotFoundException e)
////        {
////            return "";
////            
////        } 
////        catch (com.google.zxing.NotFoundException e) {
////        	
////        	return "";
////		}
////    }
//
//    
//    @SuppressWarnings("unused")
//	public String decode(Bitmap bmp) throws ReaderException {
//
//
//        if (bmp == null) {
//            Log.i("tag", "wtf");
//        }
//        //bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
//
//        int[] intArray = new int[bmp.getWidth() * bmp.getHeight()];
//        bmp.getPixels(intArray, 0, bmp.getWidth(), 0, 0, bmp.getWidth(),
//                bmp.getHeight());
//
//        LuminanceSource source = new com.google.zxing.RGBLuminanceSource(
//                bmp.getWidth(), bmp.getHeight(), intArray);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        Reader reader = new MultiFormatReader();
//        try {
//            Result result = reader.decode(bitmap, null);
//            String text = "";
//            text = result.getText();
//            byte[] rawBytes = result.getRawBytes();
//            BarcodeFormat format = result.getBarcodeFormat();
//            ResultPoint[] points = result.getResultPoints();
//            return text;
//
//        } catch (NotFoundException e) {
//
//            e.printStackTrace();
//        } catch (ChecksumException e) {
//
//            e.printStackTrace();
//        } catch (FormatException e) {
//
//            e.printStackTrace();
//
//        }
//        return null;
//    }
//}