package app.headfootballstudentversion.ru.headfootball;

public class Ball extends BallClass {
    protected float h1; //шаг,на который меняется координата по Y при полете
    protected float h; //максимальная высота подлета мяча

    protected float y2; //запоминаем начальную координату, с которой прыгает взлетает мяч

    protected boolean flying = false; //флаг полета при ударе от правого игрока
    protected boolean flying1 = false; //флаг полета при ударе от левого игрока
    protected boolean falling = false; //флаг падения при ударе от правого игрока
    protected boolean falling1 = false; //флаг падения при ударе от левого игрока

    protected boolean kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата

    protected boolean move = false; //флаг, отвечающий за обновление картинки при движении мяча
    protected int swing; //флаг, отвечающий за обновление картинки при движении мяча

    protected boolean goal = false; //флаг, выставляемый при забивании одним из футболистов гола
    protected int goal1 = 0; //количество голов, изменяемое при забивании правым футболистом гола
    protected int goal2 = 0; //количество голов, изменяемое при забивании левым футболистом гола

    public Ball() { //конструктор
        bitmapId = R.drawable.ball; //ID картинки мяча

        y1 = 3; //размер по Y мяча
        x1 = 1; //размер по X мяча

        h1 = 1; //шаг,на который меняется координата по Y при полете
        h = 6; //максимальная высота подлета мяча

        x = (float)((int)(GameView.maxX / 2)); //координаты мяча
        y = GameView.maxY  - (float)((int)(GameView.maxY / 2)) - 5;

        falling = true; //флаг падения при ударе от правого игрока
        move = true; //флаг, отвечающий за обновление картинки при движении мяча
        swing = 1; //флаг, отвечающий за обновление картинки при движении мяча
        y2 =  GameView.maxY - 7; //запоминаем начальную координату, с которой прыгает взлетает мяч
        speed = (float) 0.4; //скорость движения мяча
    }

    public void moving() { //изменение ID картинки мяча (анимация кручения мяча)
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

    private void restart() { //при забитом голе рестарт всей игры
        x = (float)((int)(GameView.maxX / 2)); //восстановление начальных координат
        y = GameView.maxY  - (float)((int)(GameView.maxY / 2)) - 5;

        flying = false; //восстановление флагов к исходному состоянию
        flying1 = false;
        falling = true;
        falling1 = false;
        kick = false;
        move = true;
        swing = 1;

        bitmapId = R.drawable.ball; //начальная картинка
    }

    public boolean getFly() {
        return flying;
    } //get методы

    public boolean getFall() {
        return falling;
    }

    public boolean getGoal() { return goal; }

    public int getGoal1() { return goal1; }

    public int getGoal2() { return goal2; }

    @Override
    public void update(float x3, float y3, float x4, float y4, boolean move2, boolean kick2) { //метод обновления координта мяча
        moving();

        if (goal == true) {
            restart();
            goal = false;
        }

        if (x <= 0) {
            goal = true;
            if (goal1 == 7) {
                goal1 = 0;
            }
            else {
                goal1++;
            }
        }

        if (x >= GameView.maxX) {
            goal = true;
            if (goal2 == 7) {
                goal2 = 0;
            }
            else {
                goal2++;
            }
        }

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
                falling1 = true;
                flying1 = false;
            }
            else {
                y -= h1;
                x += speed;
            }
        }

        if(falling1 && kick) {
            if(y == y2) {
                x += speed;
                falling1 = false;
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

        if(((x >= x3 - 0.4 && x <= x3) && (y >= y3 - 5 && y <= y3 + 5)) && Field.isLeftPressed && x >= 1){
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

        if(falling == true){
            if(y >= (y2 - 1)){
                y = y2;
                falling = false;
                move = false;
            }
            else {
                y = y + (float)(1.5 * h1);
            }
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

        if(Field.isRestartPressed) {
            goal = true;
            goal1 = 0;
            goal2 = 0;
        }
    }
}
