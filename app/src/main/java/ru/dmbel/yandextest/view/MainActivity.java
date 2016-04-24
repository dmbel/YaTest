package ru.dmbel.yandextest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import ru.dmbel.yandextest.R;
import ru.dmbel.yandextest.controller.ArtistController;
import ru.dmbel.yandextest.controller.IArtistController;
import ru.dmbel.yandextest.local.TextCompositorSingle;

public class MainActivity extends AppCompatActivity {

    private IArtistController artistController;
    private IArtistView artistView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextCompositorSingle.invalidateInstance();

        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.list_screen_label));
        artistView = new ArtistsView(this);
        artistController = new ArtistController(getApplicationContext());
        artistView.setArtistController(artistController);
        artistController.setArtistView(artistView);
        artistController.start();
    }

}
