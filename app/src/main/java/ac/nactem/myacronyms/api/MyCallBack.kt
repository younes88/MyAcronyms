package ac.nactem.myacronyms.api

import ac.nactem.myacronyms.R
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Handler
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yst.ymodule.LogCatHelper
import yst.ymodule.MessageBox
import yst.ymodule.PersianReshape
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

abstract class MyCallBack<T> : Callback<T?> {
    var context: Context
    var showLoading: Boolean
    var allowCache = true
    //    Repository dataBaseHelper;
    /**
     * show loading and show loading message
     *
     * @param context
     * @param showLoading
     * @param showError
     */
    constructor(context: Context, showLoading: Boolean) {
        this.context = context
        this.showLoading = showLoading
        if (showLoading) MessageBox.ShowLoading(context, "", "", true)
    }


    /**
     * get response and check response code
     *
     * @param call
     * @param response
     */
    override fun onResponse(call: Call<T?>, response: Response<T?>) {
        MessageBox.HideLoading(context)
        var error: APIErrorResult? = APIErrorResult()
        if (response.code() < 100 && response.code() > 299 || response.body() == null) {
            var err = ""
            try {
                error = Gson().fromJson(response.body().toString(), APIErrorResult::class.java)
            } catch (e: Exception) {
                err = e.message.toString()
            }
            if (error == null) {
                error = APIErrorResult()
                error.code = -1
                error.description = "Cannot parse response to APIErrorResult: $err"
                try {
                    error.developerMessage = """
                        Response Body: ${response.body()}
                        Response Message: ${response.message()}
                        """.trimIndent()
                } catch (ignored: Exception) {
                }
                error.message = PersianReshape.reshape(
                    context,
                    ir.yst.ymodule.R.string.error_connecting_to_server
                )
            }
            error.code = response.code()
            error.message = Gson().toJson(response.body())
            LogCatHelper.ShowErrorLog(
                null, """
     ERROR IN WS: ${error.description}
     ${error.developerMessage}
     """.trimIndent()
            )
            onFailure2(call, error)
            //            }
        } else if (response.code() in 101..298) {
            try {
                onResponse2(call, response)
            } catch (th: Throwable) {
                LogCatHelper.ShowErrorLog("tag", th.message)
            }
        } else if (response.code() == 404) {
            var err = ""
            try {
                error = Gson().fromJson(response.body().toString(), APIErrorResult::class.java)
            } catch (e: Exception) {
                err = e.message.toString()
            }
            if (error == null) {
                error = APIErrorResult()
                error.code = -1
                error.description = "Cannot parse response to APIErrorResult: $err"
                try {
                    error.developerMessage = """
                        Response Body: ${response.body()}
                        Response Message: ${response.message()}
                        """.trimIndent()
                } catch (ignored: Exception) {
                }
                error.message = PersianReshape.reshape(
                    context,
                    ir.yst.ymodule.R.string.error_connecting_to_server
                )
            }
            error.code = response.code()
            LogCatHelper.ShowErrorLog(
                null, """
     ERROR IN WS: ${error.description}
     ${error.developerMessage}
     """.trimIndent()
            )
            onFailure2(call, error)
            //            }
        } else if (response.code() == 400) {
            var err = ""
            try {
                error = Gson().fromJson(response.body().toString(), APIErrorResult::class.java)
            } catch (e: Exception) {
                err = e.message.toString()
            }
            if (error == null) {
                error = APIErrorResult()
                error.code = -1
                error.description = "Cannot parse response to APIErrorResult: $err"
                try {
                    error.developerMessage = """
                        Response Body: ${response.body()}
                        Response Message: ${response.message()}
                        """.trimIndent()
                } catch (ignored: Exception) {
                }
            }
            error.code = response.code()
            error.message = Gson().toJson(response.body())
            LogCatHelper.ShowErrorLog(
                null, """
     ERROR IN WS: ${error.description}
     ${error.developerMessage}
     """.trimIndent()
            )
            onFailure2(call, error)
            //            }
        } else {
            var err = ""
            try {
                error = Gson().fromJson(response.body().toString(), APIErrorResult::class.java)
            } catch (e: Exception) {
                err = e.message.toString()
            }
            if (error == null) {
                error = APIErrorResult()
                error.code = -1
                error.description = "Cannot parse response to APIErrorResult: $err"
                try {
                    error.developerMessage = """
                        Response Body: ${response.body()}
                        Response Message: ${response.message()}
                        """.trimIndent()
                } catch (ignored: Exception) {
                }
                error.message = PersianReshape.reshape(
                    context,
                    ir.yst.ymodule.R.string.error_connecting_to_server
                )
            }
            error.code = response.code()
            LogCatHelper.ShowErrorLog(
                null, """
     ERROR IN WS: ${error.description}
     ${error.developerMessage}
     """.trimIndent()
            )
            onFailure2(call, error)
            //            }
        }
    }

    override fun onFailure(call: Call<T?>, t: Throwable) {
        var error = APIErrorResult()
        error = CheckInternetTask(context = context).execute(error)
            .get().errorResult!!
        error.description = t.message
//        if (showError) MessageBox.ShowToast(context, error!!.message + "", Toast.LENGTH_LONG)
        LogCatHelper.ShowErrorLog(
            null,
            """ERROR CALL WS: ${error!!.description}${error!!.developerMessage}""".trimIndent()
        )
        Handler().postDelayed(Runnable { onFailure2(call, error) }, 3000)
        this.call = call
    }

    class CheckInternetResult {
        var internetState: Boolean? = null
        var errorResult: APIErrorResult? = null
    }

    var call: Call<T?>? = null
    abstract fun onResponse2(call: Call<T?>?, response: Response<T?>?)
    abstract fun onFailure2(call: Call<T?>?, errorResult: APIErrorResult?)


    /**
     * Check connection Internet Task
     */
    @SuppressLint("StaticFieldLeak")
    class CheckInternetTask(val context: Context?) :
        AsyncTask<APIErrorResult?, Void?, CheckInternetResult>() {
        override fun doInBackground(vararg p0: APIErrorResult?): CheckInternetResult? {
            val result = CheckInternetResult()
            result.errorResult = p0[0]
            result.internetState = CheckInternetConnection(context!!)
            return result
        }

        override fun onPostExecute(result: CheckInternetResult) {
            super.onPostExecute(result)
            try {
                if (result.internetState!!) {
                    result.errorResult!!.message =
                        PersianReshape.reshape(
                            context!!,
                            ir.yst.ymodule.R.string.error_connecting_to_server
                        )
                } else {
                    result.errorResult!!.message =
                        PersianReshape.reshape(context!!, ir.yst.ymodule.R.string.no_internet)
                    result.errorResult!!.code = 1009
//                    MessageBox.ShowToast(
//                        context,
//                        result.errorResult!!.message + "",
//                        Toast.LENGTH_LONG
//                    )
                }
                MessageBox.HideLoading(context!!)

                LogCatHelper.ShowErrorLog(
                    null,
                    """ERROR CALL WS: ${result.errorResult!!.description}${result.errorResult!!.developerMessage}""".trimIndent()
                )
            } catch (ignored: Exception) {
            }
        }


    }

    companion object {
        /**
         * ping site
         * check connection to network
         *
         * @param context
         * @return
         */
        @SuppressLint("MissingPermission")
        fun CheckInternetConnection(context: Context): Boolean {
            LogCatHelper.ShowDebugLog("tag", "Checking Internet Connectivity...")
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            var urlc: HttpURLConnection? = null
            if (netInfo != null && netInfo.isConnected) {
                LogCatHelper.ShowDebugLog(null, "Connection Found,Testing Internet...")
                try {
                    val url = URL("http://" + "www.google.com")
                    urlc = url.openConnection() as HttpURLConnection
                    urlc.setRequestProperty("Connection", "close")
                    urlc!!.connectTimeout = 1500
                    urlc.connect()
                    val ResponseCode = urlc.responseCode
                    if (ResponseCode == 302) {
                        val NewUrl = URL(urlc.getHeaderField("Location"))
                        val urlcNew = NewUrl.openConnection() as HttpURLConnection
                        urlcNew.connectTimeout = 10000
                        urlcNew.connect()
                        val ResponseCodeNew = urlcNew.responseCode
                        if (ResponseCodeNew == 200) {
                            try {
                                urlc.disconnect()
                            } catch (e: Exception) {
                            }
                            LogCatHelper.ShowDebugLog(null, "Internet Connected")
                            return true
                        }
                        try {
                            urlc.disconnect()
                        } catch (e: Exception) {
                        }
                        return false
                    } else if (urlc.responseCode == 200) {
                        try {
                            urlc.disconnect()
                        } catch (e: Exception) {
                        }
                        LogCatHelper.ShowDebugLog(null, "Internet Connected")
                        return true
                    }
                } catch (e1: MalformedURLException) {
                    e1.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            LogCatHelper.ShowDebugLog(null, "No internet Connection!")
            try {
                urlc!!.disconnect()
            } catch (e: Exception) {
            }
            return false
        }
    }
}