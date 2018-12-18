package app.headfootballstudentversion.ru.headfootball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class GoalClass {
    protected float x; // координаты
    protected float y;
    protected float x1; // размер картинки
    protected float y1;
    protected int bitmapId; // id картинки
    protected Bitmap bitmap; // картинка

    void init(Context context) { // сжимаем картинку до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(x1 * GameView.unitW), (int)(y1 * GameView.unitH), false);
        cBitmap.recycle();
    }

    void update(int goal1, boolean goals){ // тут будут вычисляться новые координаты
    }

    void drow(Paint paint, Canvas canvas){ // рисуем картинку
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }
}
