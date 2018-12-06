package app.headfootballstudentversion.ru.headfootball;

import android.content.Context;

public class Footballer extends FootballerBody {

    protected float h1; //шаг, на который прыгает футболист при каждом обновлении
    protected float h; //максимальная высота
    protected float y2; //запоминаем начальную координату, с которой прыгает футболист
    protected boolean flying = false; //флаг полета
    protected boolean falling = false; //флаг падения
    protected boolean pressed = false; //флаг, говорящий о том, что кнопка прыжка уже была нажата
    protected boolean kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата
    protected int swing;

    public Footballer() { //конструктор
        bitmapId = R.drawable.head_one_still; // определяем начальные параметры
        y1 = 7; //размер по игрек футболиста
        x1 = 3; //размер по икс
        h1 = 1;
        h = 6;
        x = GameView.maxX - 4;
        y = GameView.maxY - y1 - 2;
        speed = (float) 0.4;
    }

    @Override
    public void update() { // перемещаем футболиста в зависимости от нажатой кнопки

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

        if(Field.isKickPressed && kick == false) { //удар
            bitmapId = R.drawable.head_one_halfmove;
            swing = 1;
            kick = true;
        }
    }
}
