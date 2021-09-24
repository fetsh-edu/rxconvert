package me.fetsh.geekbrains.libraries.rxconvert.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.rxjava3.core.Single
import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import java.io.File
import java.io.FileOutputStream
import java.lang.RuntimeException


class MainActivityModel(private val cacheDir : File) : Contract.Model {
    private var sourceFile : File? = null

    override fun setFile(file : File) {
        sourceFile = file
    }

    override fun compress(): Single<Contract.Result> {
        return Single.fromCallable {
            sourceFile?.let { source ->
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
                    return@fromCallable Result.Success(resultFile)
                } catch (e: Throwable) {
                    return@fromCallable Result.Error(e)
                }
            } ?: Result.Error(IllegalStateException("No file to process"))
        }
    }
}