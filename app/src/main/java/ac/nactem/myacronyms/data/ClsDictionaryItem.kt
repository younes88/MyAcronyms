package ac.nactem.myacronyms.data

import java.io.Serializable

class ClsDictionaryItem : Serializable {
    var lfs: MutableList<ClsLf>? = null
    var sf: String = ""
}