// @flow
import { NativeModules } from 'react-native'
import type { LatLng } from '../types'

const { BaiduMapSuggestion } = NativeModules

type Suggestion = {
    address: string,
    city: string,
    district: string,
    key: string,
    tag: string,
    uid: string,
} & LatLng

type SuggestionResult = array

export default {
    requestSuggestion(keyword: string, city: string = '') : Promise<SuggestionResult> {
        return BaiduMapSuggestion.requestSuggestion(keyword, city)
    },
}
