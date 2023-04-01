package xyz.savvamirzoyan.allaboutapps.features.start

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import timber.log.Timber
import xyz.savvamirzoyan.allaboutapps.R
import xyz.savvamirzoyan.allaboutapps.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = viewModel()
        Timber.d("hallo log")
    }
}
