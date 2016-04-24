package ru.dmbel.yandextest.local;

import android.content.Context;

/**
 * Created by dm on 23.04.16.
 */
public interface ITextCompositor {
    /**
     * Формирование строки с количеством альбомов.
     * Например если count==121, то "121 альбом"
     * @param count
     * @return
     */
    String getStringForAlbums(int count);

    /**
     * Формирование строки с количеством песен.
     * @param count
     * @return
     */
    String getStringForTracks(int count);

    /**
     * Передать генератору текстов ссылку на контекст
     */
    void setContext(Context context);
}
