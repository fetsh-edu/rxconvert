package me.fetsh.geekbrains.libraries.rxconvert.presenter

import androidx.lifecycle.Observer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
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
    private var resultObserver : Observer<Contract.Result>? = null
    private var clicksObserver : Disposable? = null

    override fun onFirstViewAttach() {
        viewState?.init()
        resultObserver = Observer { result : Contract.Result ->
            when(result) {
                is Result.Initial -> {
                    viewState?.showInitial()
                }
                is Result.Loading -> {
                    viewState?.showLoading()
                }
                is Result.Success -> {
                    viewState?.showSuccess(result.file.name)
                }
                is Result.Error -> {
                    viewState?.showFailure(result.error.toString())
                }
                else -> viewState?.showInitial()
            }
        }
        resultObserver?.let { model.compressionResult().observeForever(it) }
    }

    override fun observeClicks(observable: Observable<Unit>) {
        clicksObserver = observable
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .map { model.compress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }



    override fun setFile(file : File) {
        model.setFile(file)
    }

    override fun onDestroy() {
        super.onDestroy()
        resultObserver?.let { model.compressionResult().removeObserver(it) }
        clicksObserver?.dispose()
    }
}