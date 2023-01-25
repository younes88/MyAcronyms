package ac.nactem.myacronyms.api

import android.text.TextUtils

class APIErrorResult {
    var code: Int? = null
    var description: String? = null

    //            this.developerMessage = BuildConfig.FLAVOR;
    var developerMessage: String? = null
        get() {
            if (TextUtils.isEmpty(field)) {
//            this.developerMessage = BuildConfig.FLAVOR;
            }
            return field
        }
    var message: String? = null
    var service: String? = null
    var status: Int? = null

    companion object {
        const val ERROR_CODE_INVALID_ACT_CODE = 9003
        const val ERROR_CODE_TICKET_EXPIRED = 9002
        const val ERROR_CODE_TOKEN_EXPIRED = 9001
        const val ERROR_CODE_USER_NOT_REGISTERED_YET = 9004
    }
}