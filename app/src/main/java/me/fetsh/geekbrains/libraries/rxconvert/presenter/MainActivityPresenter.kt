package me.fetsh.geekbrains.libraries.rxconvert.presenter

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import me.fetsh.geekbrains.libraries.rxconvert.model.Result
import java.io.File

class MainActivityPresenter(
    private var view : Contract.View?,
    private val model : Contract.Model
    ) : Contract.Presenter {

    override fun init() {
        view?.init()
        view?.showInitial()
    }

    override fun setFile(file : File) {
        model.setFile(file)
    }

    override fun compress() {
        view?.showLoading()
        model.compress()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when(result) {
                    is Result.Success -> {
                        view?.showSuccess(result.file.name)
                    }
                    is Result.Error -> {
                        view?.showFailure(result.error.toString())
                    }
                }
            }
    }

    override fun onDestroy() {
        view = null
    }
}