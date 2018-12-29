package app.headfootballstudentversion.ru.headfootball;

public class Footballer3 extends FootballerBody {

    protected float h1; //шаг, на который прыгает футболист при каждом обновлении
    protected float h; //максимальная высота
    protected float y2; //запоминаем начальную координату, с которой прыгает футболист
    protected boolean flying = false; //флаг полета
    protected boolean falling = false; //флаг падения
    protected boolean pressed = false; //флаг, говорящий о том, что кнопка прыжка уже была нажата
    protected boolean kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата
    protected int swing;

    public Footballer3() { //конструктор
        if (Field.isServer == true) {
            bitmapId = R.drawable.head_one_still; // определяем начальные параметры
        }
        else {
            bitmapId = R.drawable.head_two_still; // определяем начальные параметры
        }
        y1 = 7; //размер по игрек футболиста
        x1 = 3; //размер по икс
        h1 = 1;
        h = 6;

        if (Field.isServer == true) {
            x = GameView.maxX - 4;
            y = GameView.maxY - y1 - 4;
        }
        else {
            x = 1;
            y = GameView.maxY - y1 - 4;
        }
        speed = (float) 0.4;
    }

    private void restart() {
        if (Field.isServer == true) {
            x = GameView.maxX - 4;
            y = GameView.maxY - y1 - 4;
        }
        else {
            x = 1;
            y = GameView.maxY - y1 - 4;
        }
        flying = false; //флаг полета
        falling = false; //флаг падения
        pressed = false; //флаг, говорящий о том, что кнопка прыжка уже была нажата
        kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата
        swing = 0;
        if (Field.isServer == true) {
            bitmapId = R.drawable.head_one_still; // определяем начальные параметры
        }
        else {
            bitmapId = R.drawable.head_two_still; // определяем начальные параметры
        }
    }

    @Override
    public void update(boolean goal) { // перемещаем футболиста в зависимости от нажатой кнопки

        if (goal) {
            restart();
        }

        if(falling == true && !Field.isLeftPressed && !Field.isRightPressed){ //про прыжки
            if(y == y2){
                falling = false;
                pressed = false;
            }
            else {
                y = y + ((float)1.5 * h1);
            }
        }

        if(falling == true && Field.isLeftPressed && !Field.isRightPressed){
            if(y == y2) {
                x -= speed;
                falling = false;
                pressed = false;
            }
            else {
                y = y + ((float)1.5 * h1);
                x -= speed;
            }
        }

        if(falling == true && !Field.isLeftPressed && Field.isRightPressed){
            if(y == y2){
                x += speed;
                falling = false;
                pressed = false;
            }
            else {
                y = y + ((float)1.5 * h1);
                x += speed;
            }
        }

        if(flying == true && !Field.isLeftPressed && !Field.isRightPressed){
            if((y2 - y) == h){
                y = y + ((float)1.5 * h1);
                falling = true;
                flying = false;
            }
            else {
                y -= h1;
            }
        }

        if(flying == true && Field.isLeftPressed && !Field.isRightPressed){
            if((y2 - y) == h){
                y = y + ((float)1.5 * h1);
                x -= speed;
                falling = true;
                flying = false;
            }
            else {
                y -= h1;
                x -= speed;
            }
        }

        if(flying == true && !Field.isLeftPressed && Field.isRightPressed){
            if((y2 - y) == h){
                y = y + ((float)1.5 * h1);
                x += speed;
                falling = true;
                flying = false;
            }
            else {
                y -= h1;
                x += speed;
            }
        }

        if(Field.isLeftPressed && x >= 1){ //движения по икс
            x -= speed;
        }

        if(Field.isRightPressed && x <= GameView.maxX - 4){
            x += speed;
        }

        if(Field.isJumpPressed && pressed == false) { //прыжки
            y2 = y;
            y -= h1;
            flying = true;
            pressed = true;
        }

        if(kick == true) {
            if (Field.isServer == true) {
                switch (swing) {
                    case 1:
                        bitmapId = R.drawable.head_one_halfhalfmove;
                        swing++;
                        break;
                    case 2:
                        bitmapId = R.drawable.head_one_move;
                        swing++;
                        break;
                    case 3:
                        bitmapId = R.drawable.head_one_halfhalfmove;
                        swing++;
                        break;
                    case 4:
                        bitmapId = R.drawable.head_one_halfmove;
                        swing++;
                        break;
                    case 5:
                        bitmapId = R.drawable.head_one_still;
                        swing = 0;
                        kick = false;
                        break;
                }
            }
            else {
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

        if(Field.isKickPressed && kick == false) { //удар
            if (Field.isServer == true) {
                bitmapId = R.drawable.head_one_halfmove; // определяем начальные параметры
            }
            else {
                bitmapId = R.drawable.head_two_halfmove; // определяем начальные параметры
            }
            swing = 1;
            kick = true;
        }
    }
    public String getMyCoordinates() {
        return x + " " + y;
    }
}