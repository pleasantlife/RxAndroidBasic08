package com.kimjinhwan.android.rxandroidbasic08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;



import com.kimjinhwan.android.rxandroidbasic08.domain.Data;
import com.kimjinhwan.android.rxandroidbasic08.domain.Iweather;
import com.kimjinhwan.android.rxandroidbasic08.domain.Row;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import io.reactivex.schedulers.Schedulers;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    //http://openAPI.seoul.go.kr:8088/(인증키)/json/RealtimeWeatherStation/1/5/중구
    // 인증키 : 486a78476d706c653833457479494a

    public static final String SERVER = "http://openAPI.seoul.go.kr:8088/";
    public static final String SERVER_KEY = "486a78476d706c653833457479494a" ;

    RecyclerView recyclerView;

    List<Weather> weathers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        //1. 레트로핏 생성~!
        retrofit2.Retrofit client = new retrofit2.Retrofit.Builder().baseUrl(SERVER).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        //2. 서비스 생성
        Iweather service = client.create(Iweather.class);


        //3. 옵저버블 생성
        Observable<Data> observable = service.getData(SERVER_KEY, 1, 10, "서초");



        //4. 발행 시작
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())

                //구독 시작
                .subscribe(
                        data -> {
                            Row rows[] = data.getRealtimeWeatherStation().getRow();
                            for( Row row : rows){
                                Log.i("Weather", "지역명=" + row.getSTN_NM());
                                Log.i("Weather", "온도=" + row.getSAWS_TA_AVG() + "도");
                                Log.i("Weather", "습도=" + row.getSAWS_HD() + "%");
                                String location = row.getSTN_NM().toString();

                            }
                        }
                );
    }


}











