package com.project.autoservicemobile

import android.content.Context
import com.project.autoserviceapi.breakdown.BreakdownApi
import com.project.autoserviceapi.login.AccountApi
import com.project.autoservicedata.profile.UserContext
import com.project.autoservicedata.token.TokenManager
import com.project.autoservicedatabase.AutoserviceDatabase
import com.project.newsapi.news.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient?{
//        if(BuildConfig.DEBUG){
//            val logging = HttpLoggingInterceptor()
//                .setLevel(HttpLoggingInterceptor.Level.BODY)
//            return   OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build()
//        }
        if (BuildConfig.DEBUG)
            return getUnsafeOkHttpClient()

        return null
    }

    @Provides
    @Singleton
    fun provideJsonForConverterFactory(): Json?{
        return Json{
            explicitNulls = false
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
    private fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val trustManagerFactory: TrustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers: Array<TrustManager> =
                trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                "Unexpected default trust managers:" + trustManagers.contentToString()
            }

            val trustManager =
                trustManagers[0] as X509TrustManager

            val logging = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val builder = OkHttpClient.Builder()
            builder.addInterceptor(logging)
            builder.sslSocketFactory(sslSocketFactory, trustManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
    @Provides
    @Singleton
    fun provideAccountApi(okHttpClient: OkHttpClient?, json: Json?): AccountApi {
        return AccountApi(
            baseUrl = BuildConfig.API_BASE_URL + BuildConfig.LOGIN_API_BASE_URL,
            okHttpClient = okHttpClient,
            json = json
        )
    }

    @Provides
    @Singleton
    fun provideBreakdownApi(okHttpClient: OkHttpClient?, json: Json?): BreakdownApi {
        return BreakdownApi(
            baseUrl = BuildConfig.API_BASE_URL + BuildConfig.BREAKDOWNS_API_BASE_URL,
            okHttpClient = okHttpClient,
            json = json
        )
    }

    @Provides
    @Singleton
    fun provideNewsCarsApi(okHttpClient: OkHttpClient?, json: Json?): NewsApi {
        return NewsApi(
            baseUrl = BuildConfig.NEWS_API_URL,
            okHttpClient = okHttpClient,
            apiKey = BuildConfig.NEWS_API_KEY,
            json = json
        )
    }

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)

    @Provides
    @Singleton
    fun provideUserContext(autoserviceDatabase: AutoserviceDatabase): UserContext = UserContext(autoserviceDatabase)

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): AutoserviceDatabase {
        return AutoserviceDatabase(context)
    }
//    @Provides
//    @Singleton
//    fun provideClothesDatabase(@ApplicationContext context: Context): ClothesDatabase {
//        return ClothesDatabase(context)
//    }
//    @Provides
//    @Singleton
//    fun provideAppCoroutineDispatchers(): AppDispatchers{
//        return AppDispatchers()
//    }
}