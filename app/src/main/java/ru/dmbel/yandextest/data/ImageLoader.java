package ru.dmbel.yandextest.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import ru.dmbel.yandextest.controller.IArtistController;

/**
 * Created by dm on 23.04.16.
 */
public class ImageLoader{

    Map<String, Bitmap> bitmapCach;

    public ImageLoader(File filesDir) {
        bitmapCach = new HashMap<>();
    }


    /**
     * Возвращает Bitmap картинки через ILoadBitmapListener
     * @param urlstr
     * @param listener
     */
    public void loadImageForArtist(final String urlstr, final IArtistController.ILoadBitmapListener listener){

        // Проверка не загружена ли уже эта картинка
        if(bitmapCach.containsKey(urlstr)){
            listener.onLoadBitmap(bitmapCach.get(urlstr));
            return;
        }

        // Это ссылка для передачи Bitmap от потока загрузчика к Handler
        final BitmapRef bitmapRef = new BitmapRef();
        final Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                listener.onLoadBitmap(bitmapRef.bitmap);

                //  TODO
              // Пока не сохраняю, так как нужно доделать лимит на количество Bitmap в кэше
                // старые удалять или скидывать на диск
               // bitmapCach.put(urlstr,bitmapRef.bitmap);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    URL url = new URL(urlstr);
                    is = url.openStream();
                    bitmapRef.bitmap = BitmapFactory.decodeStream(is);
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    h.sendEmptyMessage(1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(is!=null) is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    private static class BitmapRef {
        public Bitmap bitmap;
    }
}
