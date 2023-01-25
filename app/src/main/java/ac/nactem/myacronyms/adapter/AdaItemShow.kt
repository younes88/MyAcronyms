package ac.nactem.myacronyms.adapter

import ac.nactem.myacronyms.R
import ac.nactem.myacronyms.data.ClsLf
import ac.nactem.myacronyms.data.ClsVar
import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main.view.*
import java.util.*

/**
 * Created by y.saadat88@gmail.com
 */
class AdaItemShow(
    private var contactList: MutableList<ClsVar>
) : RecyclerView.Adapter<AdaItemShow.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contactList[position]
        holder.itemView.lblItem.text = "${item.lf} (${item.since})"
    }


    override fun getItemCount(): Int {
        return contactList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listC: MutableList<ClsVar>) {
        contactList = listC
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


    }
}