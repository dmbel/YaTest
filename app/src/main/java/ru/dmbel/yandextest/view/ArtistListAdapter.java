package ru.dmbel.yandextest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import ru.dmbel.yandextest.R;
import ru.dmbel.yandextest.controller.IArtistController;
import ru.dmbel.yandextest.local.ITextCompositor;
import ru.dmbel.yandextest.local.TextCompositorSingle;
import ru.dmbel.yandextest.data.dataobjects.Artist;

/**
 * Created by dm on 23.04.16.
 */
public class ArtistListAdapter extends BaseAdapter {

    private IArtistController artistsController;
    private Context context;
    private LayoutInflater linf;
    private View.OnClickListener listClickListener;

    public ArtistListAdapter(final IArtistController artistsController, Context context) {
        this.artistsController = artistsController;
        this.context = context;
        linf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        listClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                artistsController.onArtistItemSelected(((ArtistViewHolder) view.getTag()).index);
            }
        };
    }

    @Override
    public int getCount() {
        return artistsController.getArtistsCount();
    }

    @Override
    public Artist getItem(int i) {
        return artistsController.getArtistsItem(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        ArtistViewHolder holder;
        Artist artist = artistsController.getArtistsItem(i);
        if (convertView == null) {
            view = linf.inflate(R.layout.activity_main_list_item, viewGroup, false);
            holder = new ArtistViewHolder( artist.id, i,
                    (TextView) view.findViewById(R.id.artist_name),
                    (TextView) view.findViewById(R.id.artist_ganres),
                    (TextView) view.findViewById(R.id.artist_albums_tracks),
                    (ImageView) view.findViewById(R.id.artist_image)
            );
            view.setOnClickListener(listClickListener);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ArtistViewHolder)view.getTag();
            holder.id = artist.id;
            holder.index = i;
        }

        ITextCompositor textCompositor = TextCompositorSingle.getTextCompositor(context);

        // С картинкой отдельный подход.
        // Грузить их все заранее для всего списка долго.
        // Грузить по мере скроллирования синхронно в UI потоке нельзя.
        // Поэтому будем грузить по мере скроллирования но асинхронно,
        // передавая загрузчику callback ILoadBitmapListener.
        // При этом учитываем что панели элементов списка используются повторно,
        // поэтому чтобы пока идет загрузка не "светилась" картинка предыдущего исполнителя удаляем ее,
        // А еще пока картинка грузится список может проскроллироваться больше чем количество
        // convertView в пуле и convertView уже будет занят новым исполнителем, поэтому сверка id
        // делается прежде чем закачанный Bitmap положить в ImageView
        holder.image.setImageBitmap(null);
        // Сохраняем
        final ArtistViewHolder saveHolder = holder;
        final int saveId = artist.id;
        File imgFile = new File(new File(context.getFilesDir(), "imgs"), "img" + artist.id);
        artistsController.loadImage(i, IArtistController.IMAGE_SMALL, new IArtistController.ILoadBitmapListener() {
            @Override
            public void onLoadBitmap(Bitmap bitmap) {
                // Если пока картинка грузилась экран не промотали так что строка ушла за край, то пишем
                if(saveHolder.id == saveId){
                    saveHolder.image.setImageBitmap(bitmap);
                }
            }
        });

        // Остальные данные пишем без асинхронных заморочек

        holder.name.setText(artist.name);
        holder.genres.setText(getGenresString(artist.genres));
        holder.albums.setText(textCompositor.getStringForAlbums(artist.albums) + ", "
                + textCompositor.getStringForTracks(artist.tracks));
        return view;
    }

    /**
     * Хранитель ссылок на поля с данными, чтобы каждый раз их не искать.
     */
    private static class ArtistViewHolder {
        public TextView name;
        public TextView genres;
        public TextView albums;
        public ImageView image;
        int id;
        int index;

        public ArtistViewHolder(int id, int index, TextView name, TextView genres, TextView albums, ImageView image) {
            this.id = id;
            this.index = index;
            this.name = name;
            this.genres = genres;
            this.albums = albums;
            this.image = image;
        }
    }

    public static String getGenresString(List<String> genres){
        StringBuilder builder = new StringBuilder();
        boolean isBeg = true;
        for (String genre : genres) {
            if (isBeg) {
                isBeg = false;
            } else {
                builder.append(", ");
            }
            builder.append(genre);
        }
        return builder.toString();
    }

}
