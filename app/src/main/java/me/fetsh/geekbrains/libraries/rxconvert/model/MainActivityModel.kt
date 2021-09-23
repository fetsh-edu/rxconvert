package me.fetsh.geekbrains.libraries.rxconvert.model

import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import java.io.File

class MainActivityModel : Contract.Model {
    private var sourceFile : File? = null

    override fun setFile(file : File) {
        sourceFile = file
    }

    override fun compress() {
        TODO("Not yet implemented")
    }
}