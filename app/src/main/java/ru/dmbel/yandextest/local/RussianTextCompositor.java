package ru.dmbel.yandextest.local;

import android.content.Context;

import ru.dmbel.yandextest.R;

/**
 * Created by dm on 23.04.16.
 */
public class RussianTextCompositor implements ITextCompositor {

    // Нумеруем формы окончаний множественного числа
    // 1 альбом - форма FORM_1
    // 2,3,4 альбома - форма FORM_2
    // 0,5,6,7,8,9 альбомов - форма FORM_5
    private static final int FORM_1 = 0;
    private static final int FORM_2 = 1;
    private static final int FORM_5 = 2;

    // Массивы индексов текстовых ресурсов

    private int[] albumsForm =
            {
                    R.string.albums_f1,
                    R.string.albums_f2,
                    R.string.albums_f5
            };
    private int[] tracksForm =
            {
                    R.string.tracks_f1,
                    R.string.tracks_f2,
                    R.string.tracks_f5
            };

    private Context context;

    /**
     * По значению числа возвращает какая численная форма будет у окончаний существительных
     * @param n
     * @return
     */
    private int getMultiForm(int n){
        // Числа заканчивающиеся на 11,12,13,14 являются ислючением.
        if((n/10)%10 == 1)return FORM_5;

        switch(n%10){
            case 1: return FORM_1;
            case 2:case 3:case 4:return FORM_2;
        }
        return FORM_5;
    }
    @Override
    public String getStringForAlbums(int count) {
        return count + " " + context.getResources().getString(albumsForm[getMultiForm(count)]);
    }

    @Override
    public String getStringForTracks(int count) {
        return count + " " + context.getResources().getString(tracksForm[getMultiForm(count)]);
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
