package app.headfootballstudentversion.ru.headfootball;

import android.content.Context;

public class Footballer extends FootballerBody {

    protected float h1; //шаг, на который прыгает футболист при каждом обновлении
    protected float h; //максимальная высота
    protected float y2; //запоминаем начальную координату, с которой прыгает футболист
    protected boolean flying = false; //флаг полета
    protected boolean falling = false; //флаг падения
    protected boolean pressed = false; //флаг, говорящий о том, что кнопка прыжка уже была нажата

    public Footballer(Context context) { //конструктор
        bitmapId = R.drawable.head_one; // определяем начальные параметры
        y1 = 6; //размер по игрек футболиста
        x1 = 3; //размер по икс
        h1 = 1;
        h = 6;
        x = GameView.maxX - 4;
        y=GameView.maxY - y1 - 1;
        speed = (float) 0.4;

        init(context); // инициализируем футболиста
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
    }
}
