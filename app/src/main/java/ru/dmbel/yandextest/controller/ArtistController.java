package ru.dmbel.yandextest.controller;

import android.content.Context;

import java.util.List;

import ru.dmbel.yandextest.view.IArtistView;
import ru.dmbel.yandextest.data.DataLoader;
import ru.dmbel.yandextest.data.ImageLoader;
import ru.dmbel.yandextest.data.dataobjects.Artist;
import ru.dmbel.yandextest.data.dataobjects.CoverSet;

/**
 * Created by dm on 22.04.16.
 */
public class ArtistController implements IArtistController {

    private IArtistView view;
    private List<Artist> artists;
    private DataLoader dataLoader;
    private ImageLoader imageLoader;
    private Context context;


    public ArtistController(Context context) {
        this.context = context;
        dataLoader = new DataLoader(context.getFilesDir());
        imageLoader = new ImageLoader(context.getFilesDir());
    }

    @Override
    public void setArtistView(IArtistView artistView) {
        view = artistView;
    }

    @Override
    public void start() {
        dataLoader.setOnDataLoadListener(new DataLoader.IOnDataLoadListener() {
            @Override
            public void onDataLoad(List<Artist> list) {
                artists = list;
                view.notifyListChanged();
            }
        });
        dataLoader.getArtists();
    }

    @Override
    public void onArtistItemSelected(int index) {
        view.goToDetailView(index, artists.get(index));
    }

    @Override
    public void onBackToListViewClick() {
        view.returnToListView();
    }

    @Override
    public int getArtistsCount() {
        return artists.size();
    }

    @Override
    public Artist getArtistsItem(int index) {
        return artists.get(index);
    }

    @Override
    public void loadImage(int index, int what, ILoadBitmapListener listener) {
        CoverSet covers = artists.get(index).cover;
        imageLoader.loadImageForArtist((what == IMAGE_SMALL) ? covers.small : covers.big, listener);
    }

    @Override
    public void onUpdateDataClick() {

    }


}
