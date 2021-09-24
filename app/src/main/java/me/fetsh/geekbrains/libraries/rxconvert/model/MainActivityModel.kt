package me.fetsh.geekbrains.libraries.rxconvert.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import java.io.File
import java.io.FileOutputStream


class MainActivityModel(private val cacheDir : File) : Contract.Model {
    private var sourceFile : File? = null

    private val result = MutableLiveData<Contract.Result>(Result.Initial)
    override fun compressionResult(): LiveData<Contract.Result> = result


    override fun setFile(file : File) {
        sourceFile = file
    }

    override fun compress() {
        android.util.Log.d("AAA", "Compressing started")
        result.postValue(Result.Loading)
        result.postValue(
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
                    android.util.Log.d("AAA", "Compressing finished")
                    Result.Success(resultFile)
                } catch (e: Throwable) {
                    android.util.Log.d("AAA", "Compressing failed")
                    Result.Error(e)
                }
            } ?: Result.Error(IllegalStateException("No file to process"))
        )
    }
}