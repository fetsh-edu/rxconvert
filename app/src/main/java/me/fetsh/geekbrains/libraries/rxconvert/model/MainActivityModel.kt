package me.fetsh.geekbrains.libraries.rxconvert.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import java.io.File
import java.io.FileOutputStream


class MainActivityModel(private val cacheDir : File) : Contract.Model {
    private var sourceFile : File? = null

    override fun setFile(file : File) {
        sourceFile = file
    }

    override fun compress(): Contract.Result {
        android.util.Log.d("AAA", "Compressing started")
        return sourceFile?.let { source ->
            try {
                val resultFile = File.createTempFile(
                    "${source.nameWithoutExtension}_${System.currentTimeMillis()}",
                    ".png",
                    cacheDir
                )
                val bmp: Bitmap = BitmapFactory.decodeFile(source.path)
                FileOutputStream(resultFile).also { os ->
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, os)
                    os.close()
                }
                android.util.Log.d("AAA", "Compressing finished")
                Result.Success(resultFile)
            } catch (e: Throwable) {
                android.util.Log.d("AAA", "Compressing failed")
                Result.Error(e)
            }
        } ?: Result.Error(IllegalStateException("No file to process"))
    }
}