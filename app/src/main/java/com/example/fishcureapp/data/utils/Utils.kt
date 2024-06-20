package com.example.fishcureapp.data.utils

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import com.example.fishcureapp.R
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val MAXIMAL_SIZE = 1000000
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun createFile(application: Application): File {
    val mediasDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    val outputsDirectory = if (
        mediasDir != null && mediasDir.exists()
    ) mediasDir else application.filesDir
    return File(outputsDirectory, "$timeStamp.jpg")
}

fun createCustomTempFile(context: Context): File {
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun Uri.toFile(context: Context): File {
    val contentResolvers = context.contentResolver
    val myFiles = createCustomTempFile(context)
    val inputStream = contentResolvers.openInputStream(this) as InputStream
    val outputStream = FileOutputStream(myFiles)
    val buff = ByteArray(1024)
    var len: Int
    while (inputStream.read(buff).also { len = it } > 0) outputStream.write(buff, 0, len)
    outputStream.close()
    inputStream.close()
    return myFiles
}

fun File.toBitmap(): Bitmap {
    return BitmapFactory.decodeFile(this.path)
}

fun Bitmap.rotateBitmap(isBackCamera: Boolean = false): Bitmap {
    val matrixes = Matrix()
    return if (isBackCamera) {
        matrixes.postRotate(90f)
        Bitmap.createBitmap(
            this,
            0,
            0,
            this.width,
            this.height,
            matrixes,
            true
        )
    } else {
        matrixes.postRotate(-90f)
        matrixes.postScale(-1f, 1f, this.width / 2f, this.height / 2f)
        Bitmap.createBitmap(
            this,
            0,
            0,
            this.width,
            this.height,
            matrixes,
            true
        )
    }
}

fun String?.getTimeAgoFormat(): String {
    if (this.isNullOrEmpty()) return "Unknown"
    val formats = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val sdf = SimpleDateFormat(formats, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    val pastTime = sdf.parse(this)?.time ?: return "Unknown"
    val diffs = System.currentTimeMillis() - pastTime

    val oneMin = 60_000L
    val oneHour = 60 * oneMin
    val oneDay = 24 * oneHour
    val oneMonth = 30 * oneDay
    val oneYear = 365 * oneDay

    return when {
        diffs >= oneYear -> "${diffs / oneYear} years ago"
        diffs >= oneMonth -> "${diffs / oneMonth} months ago"
        diffs >= oneDay -> "${diffs / oneDay} days ago"
        diffs >= oneHour -> "${diffs / oneHour} hours ago"
        diffs >= oneMin -> "${diffs / oneMin} min ago"
        else -> "Just now"
    }

    fun String?.generateToken() = "Bearer $this"

    fun HttpException.getErrorMessage(): String {
        val message = response()?.errorBody()?.string().toString()
        return JSONObject(message).getString("message")
    }
}