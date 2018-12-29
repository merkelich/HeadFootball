package app.headfootballstudentversion.ru.headfootball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/*
TODO: Класс для прорисовки футболиста, управляемого компьютером, на поле
 */

public class FootballerBody2 {
    protected float x; //координаты футболиста
    protected float y;

    protected float x1; //размер картинки футболиста
    protected float y1;

    protected float speed; //скорость движения футболиста

    protected int bitmapId; //ID картинки футболиста
    protected Bitmap bitmap; //картинка футболиста

    void init(Context context) { //сжимаем картинку до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(x1 * GameView.unitW), (int)(y1 * GameView.unitH), false);
        cBitmap.recycle();
    }

    void update(float x3,float y3, float x4,float y4, boolean fly, boolean fall, boolean goal){ //логика движения футболиста, управляемого компьютером
    }

    void drow(Paint paint, Canvas canvas){ //рисуем картинку футболиста
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }

    public float getX() {
        return x;
    } //метод возврата координаты Х футболиста

    public float getY() {
        return y;
    } //метод возврата координаты Y футболиста
}
