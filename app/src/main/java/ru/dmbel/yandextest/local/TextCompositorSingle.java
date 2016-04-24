package ru.dmbel.yandextest.local;

import android.content.Context;

import ru.dmbel.yandextest.R;

/**
 * Автоматически выбирает класс в соотвествии с текущей локалью
 * и возвращает единственный экземпляр.
 * Множество классов реализующих ITextCompositor
 * нужны для формирования динамичных текстов на разных языках.
 * Имя нужного класс берется из строкового ресурса text_generator_class_name
 *
 */
public class TextCompositorSingle {

    private static ITextCompositor instance;

    public static ITextCompositor getTextCompositor(Context context){
        if(instance == null) {
            try {
                instance = (ITextCompositor) Class.forName(
                        context.getResources().getString(R.string.text_generator_class_name)
                ).newInstance();
                instance.setContext(context);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return instance;
    };
    public static void invalidateInstance(){
        instance = null;
    }
}
