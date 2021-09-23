package me.fetsh.geekbrains.libraries.rxconvert.contract

import java.io.File

interface Contract {

    interface View {
        fun init()

        fun showInitial()
        fun showLoading()
        fun showFailure()
        fun showSuccess()
    }

    interface Presenter {
        fun setFile(file : File)
        fun compress()
        fun onDestroy()
    }

    interface Model {
        fun setFile(file : File)
        fun compress()
    }

}