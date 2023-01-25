package ac.nactem.myacronyms.api

import ac.nactem.myacronyms.BuildConfig
import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object WebServiceHelper {
    /**
     * POST API
     *
     * @param context
     * @return
     */
    fun post(context: Context?): PostApi {
        val base = BuildConfig.BaseApi
        val interceptor = LoggingInterceptor()
        interceptor.setContext(context)
        return Retrofit.Builder()
            .baseUrl(base)
            .client(getUnsafeOkHttpClient(interceptor))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApi::class.java)
    }


    /**
     * OkHttpClient SSL TrustManager
     *
     * @param interceptor
     * @return
     */
    private fun getUnsafeOkHttpClient(interceptor: LoggingInterceptor): OkHttpClient {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }
            builder.addNetworkInterceptor(StethoInterceptor())
            builder.addInterceptor(interceptor)
            //                .cache(cache)
            builder.connectTimeout(15, TimeUnit.MINUTES)
            builder.readTimeout(15, TimeUnit.MINUTES)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }
            builder.addNetworkInterceptor(StethoInterceptor())
//            builder.addInterceptor(interceptor)
            //                .cache(cache)
            builder.connectTimeout(15, TimeUnit.MINUTES)
            builder.readTimeout(15, TimeUnit.MINUTES)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}