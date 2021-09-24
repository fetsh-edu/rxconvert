package me.fetsh.geekbrains.libraries.rxconvert.contract

import io.reactivex.rxjava3.core.Single
import java.io.File

interface Contract {

    interface View {
        fun init()

        fun showInitial()
        fun showLoading()
        fun showFailure(string: String)
        fun showSuccess(string: String)
    }

    interface Presenter {
        fun init()
        fun setFile(file : File)
        fun compress()
        fun onDestroy()
    }

    interface Model {
        fun setFile(file : File)
        fun compress() : Single<Result>
    }

    interface Result

}