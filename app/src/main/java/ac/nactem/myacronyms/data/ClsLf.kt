package ac.nactem.myacronyms.data

import java.io.Serializable

class ClsLf: Serializable {
     var freq: Int=0
     var lf: String=""
     var since: Int=0
     var vars: MutableList<ClsVar>?=null
    constructor()
 }