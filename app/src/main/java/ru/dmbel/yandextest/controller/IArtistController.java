package ru.dmbel.yandextest.controller;

import android.graphics.Bitmap;

import ru.dmbel.yandextest.view.IArtistView;
import ru.dmbel.yandextest.data.dataobjects.Artist;

/**
 * Created by dm on 22.04.16.
 */
public interface IArtistController {

    public static final int IMAGE_SMALL = 0;
    public static final int IMAGE_BIG = 1;

    void setArtistView(IArtistView artistView);

    /**
     * Запуск
     */
    void start();

    /**
     * Пользователь выбрал исполнителя
     * @param index индекс исполнителя в списке
     */
    void onArtistItemSelected(int index);

    /**
     * Пользователь нажал кнопку возврата к списку исполнителей
     */
    void onBackToListViewClick();

    /**
     * Получить количество исполнителей в списке
     * @return количество исполнителей
     */
    int getArtistsCount();

    /**
     * Получить данные об исполнителе
     * @param index индекс исполнителя в списке
     * @return данные об исполнителе
     */
    Artist getArtistsItem(int index);

    public interface ILoadBitmapListener {
        void onLoadBitmap(Bitmap bitmap);
    }

    /**
     * Загрузить картинку и отдать ее через callback
     * @param index
     * @param what
     */
    void loadImage(int index, int what, ILoadBitmapListener listener);

    /**
     * Пользователь нажал кнопку обновления данных с сервера
     * На случа если допишу кэширование
     */
    void onUpdateDataClick();
}
