package com.kimjinhwan.android.rxandroidbasic08.domain;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by XPS on 2017-07-20.
 */

public interface Iweather {
    @GET("{key}/json/RealtimeWeatherStation/{start}/{count}/{name}")
        //아래에서 annotation으로 Path를 사용하여 데이터가 들어가는 영역을 지정할 수 있다.
    Observable<Data> getData(@Path("key") String SERVER_KEY,
                             @Path("start") int begin_index,
                             @Path("count") int offset,
                             @Path("name") String gu);
}
