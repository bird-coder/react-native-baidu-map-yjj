#import <React/RCTBridgeModule.h>
#import <BaiduMapAPI_Search/BMKGeocodeSearch.h>
#import <BaiduMapAPI_Search/BMKPoiSearch.h>

@interface RCTGeocodeModule : NSObject <RCTBridgeModule, BMKGeoCodeSearchDelegate>
@end

@implementation RCTGeocodeModule {
    BMKGeoCodeSearch *_search;
    RCTPromiseResolveBlock _resolve;
    RCTPromiseRejectBlock _reject;
}

RCT_EXPORT_MODULE(BaiduMapGeocode)

RCT_EXPORT_METHOD(search:(NSString *)address
                    city:(NSString *)city
      searchWithResolver:(RCTPromiseResolveBlock)resolve
                rejecter:(RCTPromiseRejectBlock)reject) {
    BMKGeoCodeSearchOption *option = [BMKGeoCodeSearchOption new];
    option.city = city;
    option.address = address;
    _resolve = resolve;
    _reject = reject;
    if (!_search) {
        _search = [BMKGeoCodeSearch new];
        _search.delegate = self;
    }
    [_search geoCode:option];
}

RCT_EXPORT_METHOD(reverse:(CLLocationCoordinate2D)coordinate
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject) {
    BMKReverseGeoCodeOption *option = [BMKReverseGeoCodeOption new];
    option.reverseGeoPoint = coordinate;
    _resolve = resolve;
    _reject = reject;
    if (!_search) {
        _search = [BMKGeoCodeSearch new];
        _search.delegate = self;
    }
    [_search reverseGeoCode:option];
}

- (void)onGetGeoCodeResult:(BMKGeoCodeSearch *)searcher result:(BMKGeoCodeResult *)result errorCode:(BMKSearchErrorCode)error {
    if (error == BMK_SEARCH_NO_ERROR) {
        _resolve(@{
            @"latitude": @(result.location.latitude),
            @"longitude": @(result.location.longitude),
            @"address": result.address,
        });
    } else {
        // TODO: provide error message
        _reject(@"", @"", nil);
    }
}

- (void)onGetReverseGeoCodeResult:(BMKGeoCodeSearch *)searcher
                           result:(BMKReverseGeoCodeResult *)result
                        errorCode:(BMKSearchErrorCode)error {
    if (error == BMK_SEARCH_NO_ERROR) {
        NSMutableArray *poiList = [NSMutableArray arrayWithCapacity:0];
        for (BMKPoiInfo *poi in result.poiList) {
            NSDictionary *temp = @{
                                   @"name": poi.name,
                                   @"uid": poi.uid,
                                   @"address": poi.address,
                                   @"city": poi.city,
                                   @"phoneNum": poi.phone,
                                   @"postCode": poi.postcode == nil || [poi.postcode isEqual:[NSNull null]] ? @"" : poi.postcode,
                                   @"type": @(poi.epoitype),
                                   @"isPano": @(poi.panoFlag),
                                   @"hasCaterDetails": [NSNumber numberWithBool:false],
                                   @"latitude": @(poi.pt.latitude),
                                   @"longitude": @(poi.pt.longitude),
                                   };
            [poiList addObject:temp];
        }
        _resolve(@{
            @"latitude": @(result.location.latitude),
            @"longitude": @(result.location.longitude),
            @"country": result.addressDetail.country,
            @"countryCode": result.addressDetail.countryCode,
            @"province": result.addressDetail.province,
            @"city": result.addressDetail.city,
            @"cityCode": result.cityCode,
            @"street": result.addressDetail.streetName,
            @"streetNumber": result.addressDetail.streetNumber,
            @"adCode": result.addressDetail.adCode,
            @"businessCircle": result.businessCircle,
            @"address": result.address,
            @"description": result.sematicDescription,
            @"poiList": poiList,
        });
    } else {
        // TODO: provide error message
        _reject(@"", @"", nil);
    }
}

@end