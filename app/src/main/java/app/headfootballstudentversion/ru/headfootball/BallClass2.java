package app.headfootballstudentversion.ru.headfootball;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/*
TODO: Класс для прорисовки мяча на поле для многопользовательского режима
 */

public class BallClass2 {
    protected float x; //координаты мяча
    protected float y;
    protected float x1; //размер картинки мяча
    protected float y1;
    protected float speed; //скорость движения мяча
    protected int bitmapId; //ID картинки мяча
    protected Bitmap bitmap; //картинка мяча

    void init(Context context) { //сжимаем картинку мяча до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(x1 * GameView.unitW), (int)(y1 * GameView.unitH), false);
        cBitmap.recycle();
    }

    void update(float x3, float y3, float x4, float y4){ //вычисление положения мяча и обновление ID картинки мяча
    }

    void drow(Paint paint, Canvas canvas){ //рисуем картинку мяча
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }

    public float getX() {
        return x;
    } //метод возврата координаты Х мяча

    public float getY() {
        return y;
    } //метод возврата координаты Y мяча
}
