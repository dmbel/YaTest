package ru.dmbel.yandextest.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.dmbel.yandextest.data.dataobjects.Artist;

/**
 * Created by dm on 21.04.16.
 */
public class DataLoader {

    private static final int MSG_IMAGE_ONLOAD = 1;

    private List<IOnDataLoadListener> listeners = new ArrayList<>();
    private List<Artist> artists;

    private void onLoadNotify() {
        for (IOnDataLoadListener lsn : listeners) lsn.onDataLoad(artists);
    }

    public DataLoader(File dir) {

    }

    public void setOnDataLoadListener(IOnDataLoadListener listener) {
        listeners.add(listener);
    }

    public void getArtists() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder().create();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YandexAPI.YANDEX_API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();
        YandexAPI api = retrofit.create(YandexAPI.class);
        artists = new ArrayList<>();
        api.getArtists().enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                artists = response.body();
                onLoadNotify();
                //loadImages();
            }
            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                Log.d("yandextest","DataLoader onFailure ");
            }
        });
    }

    public interface IOnDataLoadListener {
        void onDataLoad(List<Artist> artists);
    }

}
