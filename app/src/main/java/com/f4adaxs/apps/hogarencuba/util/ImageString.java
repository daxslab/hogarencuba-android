package com.f4adaxs.apps.hogarencuba.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by rigo on 3/28/18.
 */

public class ImageString {

    public static Boolean saveBitmapToFileName(Bitmap bitmap, String fileName) {
        Boolean result = false;
        try {
            OutputStream fOut = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fOut);
            fOut.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] decodeBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, buffer);
        return buffer.toByteArray();
    }
}
