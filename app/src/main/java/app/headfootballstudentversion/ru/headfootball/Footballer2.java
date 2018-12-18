package app.headfootballstudentversion.ru.headfootball;

public class Footballer2 extends FootballerBody2 {
    protected float h1; //шаг, на который прыгает футболист при каждом обновлении
    protected float h; //максимальная высота
    protected float y2; //запоминаем начальную координату, с которой прыгает футболист
    protected boolean flying = false; //флаг полета
    protected boolean falling = false; //флаг падения
    protected boolean ball = false; //флаг падения
    protected boolean move = true; //флаг, говорящий о том, что кнопка прыжка уже была нажата
    protected boolean kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата
    protected int swing;

    public Footballer2() { //конструктор
        bitmapId =  R.drawable.head_two_still; // определяем начальные параметры
        y1 = 7; //размер по игрек футболиста
        x1 = 3; //размер по икс
        h1 = 1;
        h = 6;
        x = 3;
        y = GameView.maxY - y1 - 4;
        speed = (float) 0.4;
    }

    public boolean getMove() {
        return move;
    }

    public boolean getKick() {
        return kick;
    }

    private void restart() {
        x = 3;
        y = GameView.maxY - y1 - 4;
        flying = false; //флаг полета
        falling = false; //флаг падения
        ball = false; //флаг падения
        move = true; //флаг, говорящий о том, что кнопка прыжка уже была нажата
        kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата
        swing = 0;
        bitmapId = R.drawable.head_two_still;
    }

    @Override
    public void update(float x3, float y3, float x4, float y4, boolean fly, boolean fall, boolean goal) { // перемещаем футболиста в зависимости от нажатой кнопки

        if (goal) {
            restart();
        }
        else {
            if (kick == false && x >= GameView.maxX - 6) { //удар
                bitmapId = R.drawable.head_two_halfmove;
                swing = 1;
                kick = true;
            }

            if (falling && ball) {
                if (y == y2) {
                    x -= speed;
                    falling = false;
                    ball = false;
                } else {
                    y = y + ((float) 1.5 * h1);
                    x -= speed;
                }
            }

            if (flying && ball) { //прыжок
                if ((y2 - y) == h) {
                    y = y + ((float) 1.5 * h1);
                    x -= speed;
                    falling = true;
                    flying = false;
                } else {
                    y -= h1;
                    x -= speed;
                }
            }


            if (x > x4 && flying == false && !ball && !fly && !fall) { //прыжки
                y2 = y;
                y -= h1;
                flying = true;
                ball = true;
            }

            if (x > x4 && x >= 1 && !ball && move && x == GameView.maxX - 4) { //остановка движения
                move = false;
            }

            if (x > x4 && x >= 1 && !ball) { //назад
                x -= speed;
            }

            if (x < x4 && x <= GameView.maxX - 4 && move && !falling && !flying && !fall) { //бег с мячом
                x += speed;
            }

            if (kick == true) { // анимация удара
                switch (swing) {
                    case 1:
                        bitmapId = R.drawable.head_two_halfhalfmove;
                        swing++;
                        break;
                    case 2:
                        bitmapId = R.drawable.head_two_move;
                        swing++;
                        break;
                    case 3:
                        bitmapId = R.drawable.head_two_halfhalfmove;
                        swing++;
                        break;
                    case 4:
                        bitmapId = R.drawable.head_two_halfmove;
                        swing++;
                        break;
                    case 5:
                        bitmapId = R.drawable.head_two_still;
                        swing = 0;
                        kick = false;
                        break;
                }
            }
        }

        if(Field.isRestartPressed) { //удар
            x = 3;
            y = GameView.maxY - y1 - 4;
            flying = false; //флаг полета
            falling = false; //флаг падения
            ball = false; //флаг падения
            move = true; //флаг, говорящий о том, что кнопка прыжка уже была нажата
            kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата
            swing = 0;
            bitmapId = R.drawable.head_two_still;
        }
    }
}
