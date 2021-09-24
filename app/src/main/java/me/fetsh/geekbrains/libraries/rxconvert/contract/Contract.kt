package me.fetsh.geekbrains.libraries.rxconvert.contract

import io.reactivex.rxjava3.core.Observable
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import java.io.File

interface Contract {

    interface View : MvpView {
        @AddToEndSingle
        fun init()

        @AddToEndSingle
        fun showInitial()

        @AddToEndSingle
        fun showLoading()

        @AddToEndSingle
        fun showFailure(string: String)

        @AddToEndSingle
        fun showSuccess(string: String)
    }

    interface Presenter {
        fun observeClicks(observable : Observable<Unit>)
        fun setFile(file : File)
    }

    interface Model {
        fun setFile(file : File)
        fun compress() : Result
    }

    interface Result

}