package ru.dmbel.yandextest.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.dmbel.yandextest.data.dataobjects.Artist;

/**
 * Created by dm on 21.04.16.
 */
public interface YandexAPI {
    //public static final String YANDEX_API_BASE_URL = "http://cache-default03h.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/";
    public static final String YANDEX_API_BASE_URL = "http://download.cdn.yandex.net/mobilization-2016/";

    @GET("artists.json")
    Call<List<Artist>> getArtists();
}
