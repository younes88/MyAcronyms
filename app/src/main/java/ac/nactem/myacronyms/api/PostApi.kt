package ac.nactem.myacronyms.api

import ac.nactem.myacronyms.BuildConfig
import ac.nactem.myacronyms.data.ClsDictionaryItem
import retrofit2.Call
import retrofit2.http.*

interface PostApi {


    @GET("$base/dictionary.py")
    fun dictionary(
        @Query("sf") sf: String?
    ): Call<MutableList<ClsDictionaryItem>?>?


    companion object {
        const val base: String = BuildConfig.BaseApi
    }
}

