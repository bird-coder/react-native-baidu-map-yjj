package com.birdcoder.baidumap.modules

import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.birdcoder.baidumap.toWritableMap
import com.facebook.react.bridge.*

/**
 * Created by yujiajie on 2019/3/10.
 */

@Suppress("unused")
class BaiduMapSuggestionModule(context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    private var promise: Promise? = null
    private val mSuggestionSearch by lazy {
        val mSuggestionSearch = SuggestionSearch.newInstance()
        mSuggestionSearch.setOnGetSuggestionResultListener(object : OnGetSuggestionResultListener {
            override fun onGetSuggestionResult(result: SuggestionResult?) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    promise?.reject("", "")
                } else {
                    val data = Arguments.createArray()
                    for (suggestion in result.allSuggestions) {
                        val temp = suggestion.pt.toWritableMap()
                        temp.putString("address", suggestion.address)
                        temp.putString("city", suggestion.city)
                        temp.putString("district", suggestion.district)
                        temp.putString("key", suggestion.key)
                        temp.putString("tag", suggestion.tag)
                        temp.putString("uid", suggestion.uid)
                        data.pushMap(temp)
                    }
                    promise?.resolve(data)
                }
                promise = null
            }
        })
        mSuggestionSearch
    }

    override fun getName(): String {
        return "BaiduMapSuggestion"
    }

    override fun canOverrideExistingModule(): Boolean {
        return true
    }

    @ReactMethod
    fun requestSuggestion(keyword: String, city: String, promise: Promise) {
        if (this.promise == null) {
            this.promise = promise
            mSuggestionSearch.requestSuggestion(SuggestionSearchOption().city(city).keyword(keyword))
        } else {
            promise.reject("", "This callback type only permits a single invocation from native code")
        }
    }
}