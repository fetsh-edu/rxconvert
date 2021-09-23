package me.fetsh.geekbrains.libraries.rxconvert.presenter

import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import java.io.File

class MainActivityPresenter(
    private var view : Contract.View?,
    private val model : Contract.Model
    ) : Contract.Presenter {

    init {
        view?.init()
    }

    override fun setFile(file : File) {
        model.setFile(file)
    }

    override fun compress() {
        model.compress()
    }

    override fun onDestroy() {
        view = null
    }
}