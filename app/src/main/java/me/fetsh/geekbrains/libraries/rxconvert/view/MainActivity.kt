package me.fetsh.geekbrains.libraries.rxconvert.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import me.fetsh.geekbrains.libraries.rxconvert.R
import me.fetsh.geekbrains.libraries.rxconvert.contract.Contract
import me.fetsh.geekbrains.libraries.rxconvert.databinding.ActivityMainBinding
import me.fetsh.geekbrains.libraries.rxconvert.model.MainActivityModel
import me.fetsh.geekbrains.libraries.rxconvert.presenter.MainActivityPresenter
import java.io.File

class MainActivity : AppCompatActivity(), Contract.View {

    private var binding : ActivityMainBinding? = null
    private var presenter: Contract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        presenter = MainActivityPresenter(this, MainActivityModel())
    }

    override fun init() {
        presenter?.setFile(File(resources.getIdentifier("image", "raw", packageName)))
        binding?.button?.setOnClickListener { presenter?.compress() }

    }

    override fun showInitial() {
        binding?.status?.setTextColor(ContextCompat.getColor(this, R.color.secondary))
        binding?.status?.text = "Press the button to compress"
        binding?.status?.visibility = View.VISIBLE
        binding?.progressBar?.visibility = View.GONE
        binding?.button?.isEnabled = true
    }

    override fun showLoading() {
        binding?.status?.visibility = View.GONE
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.button?.isEnabled = false
    }

    override fun showFailure() {
        binding?.status?.setTextColor(ContextCompat.getColor(this, R.color.red))
        binding?.status?.text = "Failure"
        binding?.status?.visibility = View.VISIBLE
        binding?.progressBar?.visibility = View.GONE
        binding?.button?.isEnabled = true
    }

    override fun showSuccess() {
        binding?.status?.setTextColor(ContextCompat.getColor(this, R.color.teal_700))
        binding?.status?.text = "Success"
        binding?.status?.visibility = View.VISIBLE
        binding?.progressBar?.visibility = View.GONE
        binding?.button?.isEnabled = true
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }
}