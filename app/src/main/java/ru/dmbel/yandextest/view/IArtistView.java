package ru.dmbel.yandextest.view;

import ru.dmbel.yandextest.controller.IArtistController;
import ru.dmbel.yandextest.data.dataobjects.Artist;

/**
 * Created by dm on 22.04.16.
 */
public interface IArtistView {

    void setArtistController(IArtistController artistController);

    /**
     * Открыть форму показа детальной информации об исполнителе
     * @param index
     * @param artist
     */
    void goToDetailView(int index, Artist artist);

    /**
     * Перейти от детальной формы к списку исполнителей
     */
    void returnToListView();

    /**
     * Создан/изменился список артистов
     */
    void notifyListChanged();
}
