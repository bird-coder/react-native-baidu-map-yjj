#import <React/RCTBridgeModule.h>
#import <BaiduMapAPI_Search/BMKSuggestionSearch.h>

@interface RCTSuggestionModule : NSObject <RCTBridgeModule, BMKSuggestionSearchDelegate>
@end

@implementation RCTSuggestionModule {
    BMKSuggestionSearch *_search;
    RCTPromiseResolveBlock _resolve;
    RCTPromiseRejectBlock _reject;
}

RCT_EXPORT_MODULE(BaiduMapSuggestion)

RCT_EXPORT_METHOD(requestSuggestion:(NSString *)keyword
                    city:(NSString *)city
      searchWithResolver:(RCTPromiseResolveBlock)resolve
                rejecter:(RCTPromiseRejectBlock)reject) {
    BMKSuggestionSearchOption *option = [BMKSuggestionSearchOption new];
    option.cityname = city;
    option.keyword = keyword;
    _resolve = resolve;
    _reject = reject;
    if (!_search) {
        _search = [BMKSuggestionSearch new];
        _search.delegate = self;
    }
    [_search suggestionSearch:option];
}

- (void)onGetSuggestionResult:(BMKSuggestionSearch *)searcher result:(BMKSuggestionResult *)result errorCode:(BMKSearchErrorCode)error {
    if (error == BMK_SEARCH_NO_ERROR) {
        NSMutableArray *data = [NSMutableArray arrayWithCapacity:0];
        for (NSUInteger i = 0; i < result.cityList.count; ++i) {
            CLLocationCoordinate2D location;
            [result.ptList[i] getValue:&location];
            NSDictionary *temp = @{
                                   @"city": result.cityList[i],
                                   @"district": result.districtList[i],
                                   @"key": result.keyList[i],
                                   @"uid": result.poiIdList[i],
                                   @"latitude": @(location.latitude),
                                   @"longitude": @(location.longitude),
                                   };
            [data addObject:temp];
        }
        _resolve(data);
    } else {
        // TODO: provide error message
        _reject(@"", @"", nil);
    }
}

@end
