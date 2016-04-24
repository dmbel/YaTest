package ru.dmbel.yandextest.view;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ru.dmbel.yandextest.R;
import ru.dmbel.yandextest.controller.IArtistController;
import ru.dmbel.yandextest.data.ImageLoader;
import ru.dmbel.yandextest.local.ITextCompositor;
import ru.dmbel.yandextest.local.TextCompositorSingle;
import ru.dmbel.yandextest.data.dataobjects.Artist;

/**
 * Created by dm on 24.04.16.
 */
public class ArtistDetailActivity extends AppCompatActivity {
    private Artist artist;
    private Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist_detail);

        ITextCompositor textCompositor = TextCompositorSingle.getTextCompositor(getApplicationContext());

        Intent intent = getIntent();
        artist = (Artist) intent.getSerializableExtra("artist");
        setTitle(artist.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((TextView)findViewById(R.id.artist_detail_ganres))
                .setText(ArtistListAdapter.getGenresString(artist.genres));
        ((TextView)findViewById(R.id.artist_detail_albums_tracks))
                .setText(textCompositor.getStringForAlbums(artist.albums) + ", "
                        + textCompositor.getStringForTracks(artist.tracks));
        ((TextView)findViewById(R.id.artist_detail_description))
                .setText(artist.description);
        final ImageView imageView = (ImageView)findViewById(R.id.artist_detail_image);


        ImageLoader imageLoader = new ImageLoader(getFilesDir());
        imageLoader.loadImageForArtist(artist.cover.big, new IArtistController.ILoadBitmapListener() {
            @Override
            public void onLoadBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
