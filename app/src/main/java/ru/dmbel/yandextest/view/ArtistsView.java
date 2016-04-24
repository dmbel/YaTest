package ru.dmbel.yandextest.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ListView;

import ru.dmbel.yandextest.R;
import ru.dmbel.yandextest.controller.IArtistController;
import ru.dmbel.yandextest.data.dataobjects.Artist;

/**
 * Created by dm on 22.04.16.
 */
public class ArtistsView implements IArtistView {

    private MainActivity mainActivity;
    private IArtistController artistsController;
    private ArtistListAdapter listAdapter;
    private ListView listView;

    public ArtistsView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        listView = (ListView) mainActivity.findViewById(R.id.artists_list);
    }



    @Override
    public void setArtistController(IArtistController artistController) {
        this.artistsController = artistController;
    }

    @Override
    public void goToDetailView(int index,final Artist artist) {
        Intent intent = new Intent(mainActivity, ArtistDetailActivity.class);
        intent.putExtra("artist", artist);
        mainActivity.startActivity(intent);
    }

    @Override
    public void returnToListView() {

    }

    @Override
    public void notifyListChanged() {
        listAdapter = new ArtistListAdapter(artistsController, mainActivity.getApplicationContext());
        listView.setAdapter(listAdapter);
    }
/*

    public ArtistsView getInstanceByHash(String){

    }
*/

}
