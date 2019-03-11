package com.birdcoder.baidumap

import android.view.View
import com.birdcoder.baidumap.mapview.*
import com.birdcoder.baidumap.modules.BaiduMapGeocodeModule
import com.birdcoder.baidumap.modules.BaiduMapInitializerModule
import com.birdcoder.baidumap.modules.BaiduMapLocationModule
import com.birdcoder.baidumap.modules.BaiduMapSuggestionModule
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.JavaScriptModule
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager

/**
 * Created by yujiajie on 2019/3/10.
 */

class BaiduMapPackage : ReactPackage {
    override fun createNativeModules(context: ReactApplicationContext): List<NativeModule> {
        return listOf(
                BaiduMapLocationModule(context),
                BaiduMapGeocodeModule(context),
                BaiduMapInitializerModule(context),
                BaiduMapSuggestionModule(context)
        )
    }

    override fun createViewManagers(context: ReactApplicationContext): List<ViewManager<*, *>> {
        return listOf(
                BaiduMapViewManager(),
                BaiduMapMarkerManager(),
                BaiduMapCalloutManager(),
                BaiduMapPolylineManager(),
                BaiduMapPolygonManager(),
                BaiduMapCircleManager(),
                BaiduMapHeatMapManager(),
                BaiduMapTextManager()
        )
    }

}