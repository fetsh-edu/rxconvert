package me.fetsh.geekbrains.libraries.rxconvert.model

import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import java.io.File

sealed class Result : Contract.Result {
    data class Success(val file: File) : Result()
    data class Error(val error: Throwable) : Result()
    object Loading : Result()
    object Initial : Result()
}