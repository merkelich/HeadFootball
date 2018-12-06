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
    private Footballer2 footballer2;
    private Ball ball;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    protected int bitmapId; // id картинки
    protected int gatesId; // id картинки
    protected Bitmap bitmap; // картинка
    protected Bitmap cBitmap; // картинка непреобразованная
    protected Bitmap gates; // картинка
    protected Bitmap cGates; // картинка непреобразованная

    public GameView(Context context) {
        super(context);
        //инициализируем обьекты для рисования
        bitmapId = R.drawable.background3;
        cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);

        gatesId = R.drawable.gates;
        cGates = BitmapFactory.decodeResource(context.getResources(), gatesId);

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
            float x = footballer.getX();
            float y = footballer.getY();
            float x1 = footballer2.getX();
            float y1 = footballer2.getY();
            float x2 = ball.getX();
            float y2 = ball.getY();
            boolean fly = ball.getFly();
            boolean fall = ball.getFall();
            boolean move = footballer2.getMove();
            boolean kick = footballer2.getKick();
            ball.update(x,y,x1,y1,move,kick);
            footballer.update();
            footballer2.update(x, y, x2, y2, fly, fall);
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
                gates = Bitmap.createScaledBitmap(cGates, x, y, false); //картинка фоновая

                footballer = new Footballer(); // добавляем футболиста
                footballer2 = new Footballer2(); // добавляем футболиста
                ball = new Ball();
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawBitmap(bitmap,0,0,paint); // заполняем фон картинкой

            footballer.init(getContext()); // рисуем футболиста
            footballer.drow(paint, canvas); // рисуем футболиста

            footballer2.init(getContext()); // рисуем футболиста
            footballer2.drow(paint, canvas); // рисуем футболиста

            ball.init(getContext());
            ball.drow(paint, canvas);

            canvas.drawBitmap(gates,0,0,paint); // заполняем фон картинкой

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
