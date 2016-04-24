package ru.dmbel.yandextest.local;

import android.content.Context;
import ru.dmbel.yandextest.R;

/**
 * Created by dm on 24.04.16.
 */
public class EngTextCompositor implements ITextCompositor {

    private Context context;

    @Override
    public String getStringForAlbums(int count) {
        return count + " " + context.getResources().getString(R.string.albums);
    }

    @Override
    public String getStringForTracks(int count) {
        return count + " " + context.getResources().getString(R.string.tracks);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
