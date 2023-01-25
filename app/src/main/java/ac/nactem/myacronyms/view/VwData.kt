package ac.nactem.myacronyms.view

import ac.nactem.myacronyms.api.APIErrorResult
import ac.nactem.myacronyms.data.ClsDictionaryItem


interface VwData {

    fun onApiComplete(result: MutableList<ClsDictionaryItem>)
    fun onApiError(errorResult: APIErrorResult)
}