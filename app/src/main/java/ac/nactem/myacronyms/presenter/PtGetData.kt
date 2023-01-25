package ac.nactem.myacronyms.presenter

import ac.nactem.myacronyms.api.APIErrorResult
import ac.nactem.myacronyms.api.MyCallBack
import ac.nactem.myacronyms.api.WebServiceHelper
import ac.nactem.myacronyms.data.ClsDictionaryItem
import android.content.Context
import android.widget.Toast
import ac.nactem.myacronyms.view.VwData
import retrofit2.Call
import retrofit2.Response
import yst.ymodule.MessageBox
import yst.ymodule.SharedPrefrencesHelper

class PtGetData(context: Context) {

    var cxt = context
    val vData = context as VwData

    fun getDictionary(
        sf: String?,
        isShowLoading: Boolean
    ) {
        WebServiceHelper.post(cxt).dictionary(sf = sf)
            ?.enqueue(object : MyCallBack<MutableList<ClsDictionaryItem>?>(cxt, isShowLoading) {
                override fun onResponse2(
                    call: Call<MutableList<ClsDictionaryItem>?>?,
                    response: Response<MutableList<ClsDictionaryItem>?>?
                ) {
                    try {
                        if (response!!.body() != null) {
                            vData.onApiComplete(response.body()!!)
                        }
                    } catch (e: Exception) {
                        MessageBox.ShowToast(cxt, e.message, Toast.LENGTH_SHORT)
                    }
                }

                override fun onFailure2(
                    call: Call<MutableList<ClsDictionaryItem>?>?,
                    errorResult: APIErrorResult?
                ) {
                    errorResult?.let { vData.onApiError(it) }

                }
            })

    }


}