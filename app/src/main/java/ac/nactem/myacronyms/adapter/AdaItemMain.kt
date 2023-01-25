package ac.nactem.myacronyms.adapter

import ac.nactem.myacronyms.R
import ac.nactem.myacronyms.activity.ActShow
import ac.nactem.myacronyms.data.ClsLf
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main.view.*
import java.io.Serializable
import java.util.*

/**
 * Created by y.saadat88@gmail.com
 */
class AdaItemMain(
    var activity: Activity,
    private var contactList: MutableList<ClsLf>
) : RecyclerView.Adapter<AdaItemMain.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = contactList[position]
        holder.itemView.lblItem.text = item.lf
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, ActShow::class.java)
            intent.putExtra(ActShow.EXTRA_LF, item)
            if (!ActShow.active)
                activity.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return contactList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listC: MutableList<ClsLf>) {
        contactList = listC
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {


    }
}