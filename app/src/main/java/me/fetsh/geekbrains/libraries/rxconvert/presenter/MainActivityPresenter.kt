package me.fetsh.geekbrains.libraries.rxconvert.presenter

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import me.fetsh.geekbrains.libraries.rxconvert.model.MainActivityModel
import me.fetsh.geekbrains.libraries.rxconvert.model.Result
import moxy.InjectViewState
import moxy.MvpPresenter
import java.io.File

@InjectViewState
class MainActivityPresenter(
    cacheDir : File
) : MvpPresenter<Contract.View>(), Contract.Presenter {

    private val model : MainActivityModel = MainActivityModel(cacheDir)

    override fun onFirstViewAttach() {
        viewState?.init()
        viewState?.showInitial()
    }

    override fun observeClicks(observable: Observable<Unit>) {
        observable
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext { viewState?.showLoading() }
            .observeOn(Schedulers.io())
            .map { model.compress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when(result) {
                    is Result.Success -> {
                        viewState?.showSuccess(result.file.name)
                    }
                    is Result.Error -> {
                        viewState?.showFailure(result.error.toString())
                    }
                }
            }
    }

    override fun setFile(file : File) {
        model.setFile(file)
    }
}