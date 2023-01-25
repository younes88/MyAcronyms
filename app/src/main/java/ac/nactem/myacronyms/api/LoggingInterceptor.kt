package ac.nactem.myacronyms.api

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlin.Throws
import yst.ymodule.LogCatHelper
import okhttp3.*
import okio.Buffer
import yst.ymodule.SharedPrefrencesHelper
import java.io.IOException
import java.lang.Double
import java.lang.Exception

class LoggingInterceptor : Interceptor {
    private var context: Context? = null
    fun setContext(context: Context?) {
        this.context = context
    }

    /**
     * custom intercept retrofit web service
     * set header to request
     * get response and  check response and to model with Gson
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val responseLog: String
        var bodyString = ""
        val request = chain.request().newBuilder().build()
        var response = chain.proceed(request)
        val t1 = System.nanoTime()
        val requestLog =
            String.format("Sending request %s on %s%n", request.url(), chain.connection())
        //        Logg.i("request", bodyToString(request));
        val t2: Long
        val m: MediaType?
        val objArr: Array<Any?>
        if (request.method().compareTo("post", ignoreCase = true) == 0) {
            try {
                LogCatHelper.ShowDebugLog("tag", "REQUEST:\n$requestLog")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            //            Logg.e("REQUEST", "REQUEST:" + "\n" + requestLog);
            t2 = System.nanoTime()
            m = response.body()!!.contentType()
            objArr = arrayOfNulls(2)
            objArr[0] = response.request().url()
            objArr[1] = Double.valueOf((t2 - t1).toDouble() / 1000000.0)
            responseLog = String.format("Received response for %s in %.1fms%n", *objArr)
            //            bodyString = BuildConfig.FLAVOR;
        } else {
            LogCatHelper.ShowDebugLog("tag", "REQUEST:\n$requestLog")
            //            Logg.e("REQUEST", "REQUEST:" + "\n" + requestLog);
            t2 = System.nanoTime()
            m = response.body()!!.contentType()
            objArr = arrayOfNulls(2)
            objArr[0] = response.request().url()
            objArr[1] = Double.valueOf((t2 - t1).toDouble() / 1000000.0)
            responseLog = String.format("Received response for %s in %.1fms%n", *objArr)
            //            bodyString = BuildConfig.FLAVOR;
        }
        try {
            bodyString = response.body()!!.string()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            bodyString =
                GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(bodyString))
        } catch (e2: JsonSyntaxException) {
            e2.printStackTrace()
        }
        if (response.isSuccessful) {
            LogCatHelper.ShowDebugLog("tag", "RESPONSE:\n$responseLog\n$bodyString")
            //            Logg.i("RESPONSE", "RESPONSE:" + "\n" + responseLog + "\n" + bodyString);
        } else {
            LogCatHelper.ShowErrorLog("tag", "RESPONSE:\n$responseLog\n$bodyString")
            //            Logg.i("RESPONSE", "RESPONSE:" + "\n" + responseLog + "\n" + bodyString);
        }
        response = response.newBuilder()
            .body(ResponseBody.create(response.body()!!.contentType(), bodyString)).build()
        if (response.isSuccessful) {
            LogCatHelper.ShowInfoLog("tag", response.toString())
            return response
        }
        val gson = Gson()
        var error: Any? = null
        try {
            error = gson.fromJson<Any>(response.body()!!.string(), APIErrorResult::class.java)
        } catch (e3: Exception) {
            LogCatHelper.ShowErrorLog("tag", "Error Calling WS: " + e3.message)
            //            Logg.i(r"Error", "Error Calling WS: " + e3.getMessage());
        }
        if (error == null) {
            error = APIErrorResult()
            //            error.setCode(Integer.valueOf(-1));
//            error.setMessage("error");
        }
        return response.newBuilder()
            .body(ResponseBody.create(response.body()!!.contentType(), Gson().toJson(error)))
            .build()
    }

    companion object {
        /**
         * convert request to string
         *
         * @param request
         * @return
         */
        fun bodyToString(request: Request): String {
            return try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body()!!.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }
}