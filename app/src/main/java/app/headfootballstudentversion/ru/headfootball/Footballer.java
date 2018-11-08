package app.headfootballstudentversion.ru.headfootball;

import android.content.Context;

public class Footballer extends FootballerBody {

    protected float h1;
    protected float h;
    protected float y2;
    protected boolean flying = false;
    protected boolean falling = false;
    protected boolean pressed = false;

    public Footballer(Context context) {
        bitmapId = R.drawable.head_one; // определяем начальные параметры
        y1 = 6;
        x1 = 3;
        h1 = 1;
        h = 6;
        x = GameView.maxX - 4;
        y=GameView.maxY - y1 - 1;
        speed = (float) 0.4;

        init(context); // инициализируем корабль
    }

    @Override
    public void update() { // перемещаем корабль в зависимости от нажатой кнопки

        if(falling == true && !Field.isLeftPressed && !Field.isRightPressed){ //проверка на прыжки
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

        if(Field.isLeftPressed && x >= 1){
            x -= speed;
        }

        if(Field.isRightPressed && x <= GameView.maxX - 4){
            x += speed;
        }

        if(Field.isJumpPressed && pressed == false) {
            y2 = y;
            y -= h1;
            flying = true;
            pressed = true;
        }
    }
}
