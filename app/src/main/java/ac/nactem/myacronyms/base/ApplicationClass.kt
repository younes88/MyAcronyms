package ac.nactem.myacronyms.base

import ac.nactem.myacronyms.BuildConfig
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.calligraphy3.R
import io.github.inflationx.viewpump.ViewPump
import yst.ymodule.LogCatHelper

class ApplicationClass : Application() {
    var application: Application? = null

    override fun onCreate() {
        super.onCreate()
        /**
         * Stetho lib in chrome test network (chrome://inspect/#devices)
         */
        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build()
            )
            LogCatHelper.isDebug = true
        } else
            LogCatHelper.isDebug = false
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        /**
         * ViewPump set font app
         */
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("font/Roboto-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
        context = this

    }

    override fun onTerminate() {
        super.onTerminate()
        context = null
    }

    companion object {
        var context: Context? = null
    }
}