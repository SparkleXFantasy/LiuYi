package com.bytedance.sjtu.liuyi

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

// @TODO: (Save and Load Image Files)
// Not influence the function of baseline

class BitmapFileHelper {
    companion object {
        fun inputStreamToByteArray(input: InputStream?): ByteArray {
            val outputStream = ByteArrayOutputStream()
            input ?: return outputStream.toByteArray()
            val buffer = ByteArray(1024)
            var len: Int
            try {
                while (input.read(buffer).also { len = it } != -1) {
                    outputStream.write(buffer, 0, len)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    input.close()
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return outputStream.toByteArray()
        }
    }

    private fun decodeBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        /**
         * todo calculate sampleSize
         */
        return 2
    }

    private fun decodeBitmapFromFile(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }
}