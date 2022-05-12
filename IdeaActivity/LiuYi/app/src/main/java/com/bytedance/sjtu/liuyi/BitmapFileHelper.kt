package com.bytedance.sjtu.liuyi

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

// @TODO: (Save and Load Image Files)
// Not influence the function of baseline

class BitmapFileHelper {
    fun fileToBitmap(filePath: String): Bitmap {
        val file = File(filePath)
        val options = BitmapFactory.Options()
        options.inPurgeable = true
        options.inSampleSize = 2
        return BitmapFactory.decodeFile(filePath, options)
    }
}