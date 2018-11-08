package app.headfootballstudentversion.ru.headfootball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static java.security.AccessController.getContext;

public class GameView extends SurfaceView implements Runnable{

    public static int maxX = 20; // размер по горизонтали
    public static int maxY = 28; // размер по вертикали
    public static float unitW = 0; // пикселей в юните по горизонтали
    public static float unitH = 0; // пикселей в юните по вертикали
    private boolean firstTime = true;
    private boolean gameRunning = true;
    private Footballer footballer;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    protected int bitmapId; // id картинки
    protected Bitmap bitmap; // картинка
    protected Bitmap cBitmap; // картинка непреобразованная

    public GameView(Context context) {
        super(context);
        //инициализируем обьекты для рисования
        bitmapId = R.drawable.background3;
        cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);

        surfaceHolder = getHolder();
        paint = new Paint();

        // инициализируем поток
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { //метод, запускающий поток
        while (gameRunning) {
            update();
            draw();
            control();
        }
    }

    private void update() { //обновление для реакции футболиста на кнопки
        if(!firstTime) {
            footballer.update();
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем валидный ли surface

            if(firstTime){ // инициализация при первом запуске
                firstTime = false;
                int x = surfaceHolder.getSurfaceFrame().width();
                int y = surfaceHolder.getSurfaceFrame().height();
                unitW = x / maxX; // вычисляем число пикселей в юните
                unitH = y / maxY;

                bitmap = Bitmap.createScaledBitmap(cBitmap, x, y, false); //картинка фоновая

                footballer = new Footballer(getContext()); // добавляем футболиста
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawBitmap(bitmap,0,0,paint); // заполняем фон картинкой

            footballer.drow(paint, canvas); // рисуем футболиста

            surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas
        }
    }

    private void control() { // пауза на 0 миллисекунд
        try {
            gameThread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
