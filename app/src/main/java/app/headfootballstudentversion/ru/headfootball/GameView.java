package app.headfootballstudentversion.ru.headfootball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
TODO: Класс, в котором работает поток прорисовки поля, персонажей и других объектов
 */

public class GameView extends SurfaceView implements Runnable{

    public static int maxX = 20; // размер по горизонтали экрана телефона в юнитах
    public static int maxY = 28; // размер по вертикали экрана телефона в юнитах

    public static float unitW = 0; // пикселей в юните по горизонтали
    public static float unitH = 0; // пикселей в юните по вертикали

    private boolean firstTime = true; //запуск в первый раз
    private boolean gameRunning = true; //игра работает, флаг для верного завершения работы потока

    private Footballer footballer; //футболист, которым управлет пользователь в однопользовательском режиме
    private Footballer2 footballer2; //футболист, управляемый компьютером в однопользовательском режиме
    private Footballer3 footballer3; //футболист, которым управлет пользователь в многопользовательском режиме
    private Footballer4 footballer4; //футболист, которым управлет другой пользователь в многопользовательском режиме

    private Ball ball; //мяч
    private Ball2 ball2;//мяч для многопользовательского режима

    private Goal1 goal1; //голы правого игрока
    private Goal2 goal2; //голы левого игрока

    private Thread gameThread = null; //поток прорисовки

    private Paint paint; //кисть
    private Canvas canvas; //объект класса для прорисовки
    private SurfaceHolder surfaceHolder; //объект для определния размеров экрана

    protected int bitmapId; //id картинки поля
    protected int gatesId; //id картинки ворот

    protected Bitmap bitmap; //картинка поля
    protected Bitmap cBitmap; //картинка поля непреобразованная

    protected Bitmap gates; //картинка ворот
    protected Bitmap cGates; //картинка ворот непреобразованная

    public GameView(Context context) { //конструктор класса
        super(context);
        //инициализируем обьекты для рисования
        bitmapId = R.drawable.background3; //получаем ID картинки поля
        cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId); //преобразуем до нужных размеров

        gatesId = R.drawable.gates; //получаем ID картинки ворот
        cGates = BitmapFactory.decodeResource(context.getResources(), gatesId); //преобразуем до нужных размеров

        surfaceHolder = getHolder(); //объект для определния размеров экрана
        paint = new Paint(); //создаем объект кисти

        // запускаем поток прорисовки
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { //метод run потока
        while (gameRunning) {
            update(); //обновляем координаты и значения объектов на экране
            draw(); //прорисовываем
            control(); //задержка
        }
    }

    private void update() { //обновление для реакции футболиста на кнопки

        if(!firstTime) {
            if (Field.multiplayer == true) {
                float x = footballer3.getX(); //получаем координаты футболиста, которым играем
                float y = footballer3.getY();

                float x1 = footballer4.getX(); //получаем координаты футболиста, которым играет противник
                float y1 = footballer4.getY();

                boolean goal = ball2.getGoal(); //флаг забитого гола
                int goalOne = ball2.getGoal1(); //количество голов
                int goalTwo = ball2.getGoal2();

                goal1.update(goalOne, goal); //обновление количества голов
                goal2.update(goalTwo, goal);

                ball2.update(x, y, x1, y1); //обновление положения мяча

                footballer3.update(goal); //обновление положения футболистов
                footballer4.update(goal);
            }
            else {
                float x = footballer.getX(); //получаем координаты правого футболиста
                float y = footballer.getY();

                float x1 = footballer2.getX(); //получаем координаты левого футболиста
                float y1 = footballer2.getY();

                float x2 = ball.getX(); //координаты мяча
                float y2 = ball.getY();

                boolean goal = ball.getGoal(); //флаг забитого гола
                int goalOne = ball.getGoal1(); //количество голов
                int goalTwo = ball.getGoal2();

                boolean fly = ball.getFly(); //полет мяча
                boolean fall = ball.getFall(); //падение мяча

                boolean move = footballer2.getMove(); //движения левого футболиста
                boolean kick = footballer2.getKick();

                goal1.update(goalOne, goal); //обновление количества голов
                goal2.update(goalTwo, goal);

                ball.update(x, y, x1, y1, move, kick); //обновление положения мяча

                footballer.update(goal); //обновление положения футболистов
                footballer2.update(x, y, x2, y2, fly, fall, goal);
            }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем действителен ли экран сейчас

            if(firstTime){ // инициализация при первом запуске
                firstTime = false;
                int x = surfaceHolder.getSurfaceFrame().width(); //определяем размеры экрана
                int y = surfaceHolder.getSurfaceFrame().height();

                unitW = x / maxX; // вычисляем число пикселей в юните за счет размеров экрана
                unitH = y / maxY;

                bitmap = Bitmap.createScaledBitmap(cBitmap, x, y, false); //преобразуем картинку поля
                gates = Bitmap.createScaledBitmap(cGates, x, y, false); //преобразуем картинку ворот

                if (Field.multiplayer == true) { //если играем в многопользовательском режиме
                    footballer3 = new Footballer3(); //то добавляем футболиста, которым будем играть
                    footballer4 = new Footballer4(); //футболиста противника по комнате
                    ball2 = new Ball2(); //добавляем мяч для многопользовательского режима
                }
                else { //если играем в однопользовательском режиме
                    footballer = new Footballer(); //добавляем футболиста, управляемого нами
                    footballer2 = new Footballer2(); //добавляем футболиста, управляемого компьютером
                    ball = new Ball(); //добавляем мяч
                }

                goal1 = new Goal1(); //объекты классов голов
                goal2 = new Goal2();
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawBitmap(bitmap,0,0,paint); // заполняем фон картинкой поля

            if (Field.multiplayer == true) {
                footballer3.init(getContext()); //преобразуем картинку футболиста под размеры экрана
                footballer3.drow(paint, canvas); //рисуем футболиста на поле

                footballer4.init(getContext()); //преобразуем картинку футболиста под размеры экрана
                footballer4.drow(paint, canvas); //рисуем футболиста на поле

                ball2.init(getContext()); //преобразуем картинку мяча под размеры экрана
                ball2.drow(paint, canvas); //рисуем мяча на поле
            }
            else {
                footballer.init(getContext()); //преобразуем картинку футболиста под размеры экрана
                footballer.drow(paint, canvas); //рисуем футболиста на поле

                footballer2.init(getContext()); //преобразуем картинку футболиста под размеры экрана
                footballer2.drow(paint, canvas); //рисуем футболиста на поле

                ball.init(getContext()); //преобразуем картинку мяча под размеры экрана
                ball.drow(paint, canvas); //рисуем мяча на поле
            }

            goal1.init(getContext()); //преобразуем картинку цифры количества голов правого игрока под размеры экрана
            goal1.drow(paint, canvas); //рисуем картинку цифры количества голов правого игрока

            goal2.init(getContext()); //преобразуем картинку цифры количества голов левого игрока под размеры экрана
            goal2.drow(paint, canvas); //рисуем картинку цифры количества голов левого игрока

            canvas.drawBitmap(gates,0,0,paint); //рисуем картинку ворот

            surfaceHolder.unlockCanvasAndPost(canvas); //открываем canvas
        }
    }

    private void control() { //задержка потока
        try {
            gameThread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void kill() { //метод убийства потока прорисовки
        gameRunning = false;
        gameThread.interrupt();
        gameThread = null;
    }

    public Footballer3 getMyFootballer() {  //метод получения объекта футболиста для многопользовательского режима
        return footballer3;
    }
    public Footballer4 getOpponentFootballer() { //метод получения объекта другого футболиста для многопользовательского режима
        return footballer4;
    }
    public Ball2 getBall() { //метод получения объекта мяча для многопользовательского режима
        return ball2;
    }
}
