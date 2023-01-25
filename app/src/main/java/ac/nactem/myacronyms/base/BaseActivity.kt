package ac.nactem.myacronyms.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import yst.ymodule.MessageBox
import java.util.concurrent.TimeUnit


open class BaseActivity : AppCompatActivity() {
    var isFinished = false
    private var handler = Handler()
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

    }

    //------ FOR SET FONT TO ALL VIEWS IN THIS ACTIVITY--------------------
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    //####### FOR SET FONT TO ALL VIEWS IN THIS ACTIVITY--------------------
    override fun onDestroy() {
        isFinished = true
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this)
//        }
    }

    override fun onStop() {
        super.onStop()
    }


}