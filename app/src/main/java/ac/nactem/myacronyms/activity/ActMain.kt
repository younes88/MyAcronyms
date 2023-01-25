package ac.nactem.myacronyms.activity

import ac.nactem.myacronyms.R
import ac.nactem.myacronyms.adapter.AdaItemMain
import ac.nactem.myacronyms.api.APIErrorResult
import ac.nactem.myacronyms.base.BaseActivity
import ac.nactem.myacronyms.data.ClsDictionaryItem
import ac.nactem.myacronyms.data.ClsLf
import ac.nactem.myacronyms.presenter.PtGetData
import ac.nactem.myacronyms.view.VwData
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.act_main.*
import yst.ymodule.MessageBox

class ActMain : BaseActivity(), VwData {
    var adaItemMain: AdaItemMain? = null
    var lstLf: MutableList<ClsLf> = emptyList<ClsLf>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        imgSearch.setOnClickListener {
            PtGetData(this).getDictionary(txtSearch.text.toString(), true)
        }
        adaItemMain = AdaItemMain(this, lstLf)
        rcrData.layoutManager = LinearLayoutManager(this)
        rcrData.adapter = adaItemMain
    }

    override fun onApiComplete(result: MutableList<ClsDictionaryItem>) {
        if (result.isNotEmpty()) {
            lstLf = result[0].lfs!!
            adaItemMain!!.updateList(lstLf)
        }
    }

    override fun onApiError(errorResult: APIErrorResult) {
        MessageBox.ShowToast(this, errorResult.message, Toast.LENGTH_SHORT)
    }
}