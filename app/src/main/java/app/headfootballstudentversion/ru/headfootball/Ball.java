package app.headfootballstudentversion.ru.headfootball;

public class Ball extends BallClass {
    protected float h1; //шаг, на который прыгает футболист при каждом обновлении
    protected float h; //максимальная высота
    protected float y2; //запоминаем начальную координату, с которой прыгает футболист
    protected boolean flying = false; //флаг полета
    protected boolean flying1 = false; //флаг полета
    protected boolean falling = false; //флаг падения
    protected boolean falling1 = false; //флаг падения
    protected boolean pressed = false; //флаг, говорящий о том, что кнопка прыжка уже была нажата
    protected boolean kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата
    protected boolean move = false;
    protected int swing;

    public Ball() { //конструктор
        bitmapId = R.drawable.ball; // определяем начальные параметры
        y1 = 3; //размер по игрек футболиста
        x1 = 1; //размер по икс
        h1 = 1; //step
        h = 6; //max high
        x = (float)((int)(GameView.maxX / 2));
        y = GameView.maxY  - (float)((int)(GameView.maxY / 2)) - 5;
        falling = true;
        move = true;
        y2 =  GameView.maxY - 7;
        swing = 1;
        speed = (float) 0.4;
    }

    public void moving() {
        if(move == true) {
            switch (swing) {
                case 1:
                    bitmapId = R.drawable.ball1;
                    swing++;
                    break;
                case 2:
                    bitmapId = R.drawable.ball5;
                    swing++;
                    break;
                case 3:
                    bitmapId = R.drawable.ball2;
                    swing++;
                    break;
                case 4:
                    bitmapId = R.drawable.ball6;
                    swing++;
                    break;
                case 5:
                    bitmapId = R.drawable.ball3;
                    swing++;
                    break;
                case 6:
                    bitmapId = R.drawable.ball7;
                    swing++;
                    break;
                case 7:
                    bitmapId = R.drawable.ball4;
                    swing++;
                    break;
                case 8:
                    bitmapId = R.drawable.ball8;
                    swing++;
                    break;

                default:
                    bitmapId = R.drawable.ball;
                    swing = 0;
                    swing++;
                    break;
            }
        }
    }

    public boolean getFly() {
        return flying;
    }

    public boolean getFall() {
        return falling;
    }

    @Override
    public void update(float x3, float y3, float x4, float y4, boolean move2, boolean kick2) { // перемещаем футболиста в зависимости от нажатой кнопки
        moving();

        if(flying && kick) {
            if((y2 - y) == h){
                y = y + h1;
                x -= speed;
                falling = true;
                flying = false;
            }
            else {
                y -= h1;
                x -= speed;
            }
        }

        if(falling && kick) {
            if(y == y2) {
                x -= speed;
                falling = false;
                move = false;
                kick = false;
            }
            else {
                y = y + h1;
                x -= speed;
            }
        }

        if(flying1 && kick) {
            if((y2 - y) == h){
                y = y + h1;
                x += speed;
                falling = true;
                flying = false;
            }
            else {
                y -= h1;
                x += speed;
            }
        }

        if(falling1 && kick) {
            if(y == y2) {
                x += speed;
                falling = false;
                move = false;
                kick = false;
            }
            else {
                y = y + h1;
                x += speed;
            }
        }

        if((x >= x4 - 0.4 && x <= x4) && (y >= y4 - 5 && y <= y4 + 5) && x >= 1) {
            x -= speed;
            move = true;
        }

        if(!move2) {
            move = false;
        }

        if(((x <= x4 + 2.4 && x >= x4 + 1) && (y >= y4 - 5 && y <= y4 + 5)) && x <= GameView.maxX - 4) {
            x += speed;
            move = true;
        }

        if(((x >= x3 - 0.4 && x <= x3) && (y >= y3 - 5 && y <= y3 + 5)) && Field.isLeftPressed && x >= 1){ //движения по икс;
            x -= speed;
            move = true;
        }

        if(((x >= x3 - 0.4 && x <= x3) && (y >= y3 - 5 && y <= y3 + 5)) && !Field.isLeftPressed && x >= 1) {
            move = false;
        }

        if(((x <= x3 + 2.4 && x >= x3 + 1) && (y >= y3 - 5 && y <= y3 + 5)) && Field.isRightPressed && x <= GameView.maxX - 4) {
            x += speed;
            move = true;
        }

        if(((x <= x3 + 2.4 && x >= x3 + 1) && (y >= y3 - 5 && y <= y3 + 5)) && !Field.isRightPressed && x <= GameView.maxX - 4) {
            move = false;
        }

        if(falling == true){ //про прыжки
            if(y >= (y2 - 1)){
                y = y2;
                falling = false;
                move = false;
            }
            else {
                y = y + (float)(1.5 * h1);
            }
        }

        if((x <= x3 + 2.4 && x >= x3 - 0.4) && y == y3 - 5 && !Field.isJumpPressed) {

        }

        if((x <= x3 + 2.4 && x >= x3 - 0.4) && y == y3 - 5 && Field.isJumpPressed) {
            falling = false;
        }

        if(Field.isKickPressed && kick == false  &&  ((x >= x3 - 0.4 && x <= x3 + 0.4) && (y >= y3 - 5 && y <= y3 + 5))) { //удар
            swing = 1;
            move = true;
            flying = true;
            kick = true;
        }

        if(kick2 && kick == false  && ((x <= x4 + 2.4 && x >= x4 + 1) && (y >= y4 - 5 && y <= y4 + 5))) { //удар
            swing = 1;
            move = true;
            flying1 = true;
            kick = true;
        }
    }
}
