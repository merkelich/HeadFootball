package app.headfootballstudentversion.ru.headfootball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/*
TODO: Класс для прорисовки картинок цифр, отвечающих за количество голов игрока
 */

public class GoalClass {
    protected float x; //координаты картинки гола
    protected float y;

    protected float x1; //размер картинки гола
    protected float y1;

    protected int bitmapId; //ID картинки гола
    protected Bitmap bitmap; //картинка гола

    void init(Context context) { //сжимаем картинку до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(x1 * GameView.unitW), (int)(y1 * GameView.unitH), false);
        cBitmap.recycle();
    }

    void update(int goal1, boolean goals){ //обновление ID картинки гола
    }

    void drow(Paint paint, Canvas canvas){ //рисуем картинку гола
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }
}
