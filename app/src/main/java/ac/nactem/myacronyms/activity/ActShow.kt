package ac.nactem.myacronyms.activity

import ac.nactem.myacronyms.R
import ac.nactem.myacronyms.adapter.AdaItemMain
import ac.nactem.myacronyms.adapter.AdaItemShow
import ac.nactem.myacronyms.base.BaseActivity
import ac.nactem.myacronyms.data.ClsLf
import ac.nactem.myacronyms.data.ClsVar
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ActShow : BaseActivity() {
    var mLf = ClsLf()
    var adaItemShow: AdaItemShow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        val bundle = intent.extras
        if (bundle != null) {
            mLf = bundle.getSerializable(EXTRA_LF) as ClsLf
        } else {
            finish()
        }

        imgLogo.visibility = GONE
        lblItem.text = mLf.lf

        if (mLf.vars!!.isNotEmpty()) {
            adaItemShow = AdaItemShow(mLf.vars!!)
        }
        rcrData.layoutManager = LinearLayoutManager(this)
        rcrData.adapter = adaItemShow


        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            @SuppressLint("DefaultLocale")
            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                if (p0!!.isNotEmpty()) {
                    val temp: MutableList<ClsVar> = ArrayList()
                    for (d in mLf.vars!!) {
                        if (d.lf.toLowerCase().contains(p0.toString().toLowerCase())) {
                            temp.add(d)
                        }
                    }
                    adaItemShow!!.updateList(temp)
                } else {
                    adaItemShow!!.updateList(mLf.vars!!)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }

    companion object {
        const val EXTRA_LF = "exLf"
        var active: Boolean = false
    }


    override fun onDestroy() {
        super.onDestroy()
        active = false
    }

    override fun finish() {
        super.finish()
        active = false
    }

    override fun onStart() {
        super.onStart()
        active = true
    }
}