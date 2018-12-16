package app.headfootballstudentversion.ru.headfootball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Field extends AppCompatActivity implements View.OnTouchListener {

    public static boolean isLeftPressed = false; // нажата левая кнопка
    public static boolean isRightPressed = false; // нажата правая кнопка
    public static boolean isJumpPressed = false; // нажата кнопка прыжка
    public static boolean isKickPressed = false; // нажата кнопка удар
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //задает начальную установку параметров при инициализации активности
        setContentView(R.layout.field);

        gameView = new GameView(this); // создаём gameView

        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout); // находим gameLayout
        gameLayout.addView(gameView); // и добавляем в него gameView

        ImageButton leftButton = (ImageButton) findViewById(R.id.leftButton); // находим кнопки в хмл
        ImageButton rightButton = (ImageButton) findViewById(R.id.rightButton);
        ImageButton jumpButton = (ImageButton) findViewById(R.id.jumpButton);
        ImageButton kickButton = (ImageButton) findViewById(R.id.kickButton);
        Button menu = (Button) findViewById(R.id.menu);

        leftButton.setOnTouchListener(this); // и добавляем этот класс как слушателя (при нажатии сработает onTouch)
        rightButton.setOnTouchListener(this);
        jumpButton.setOnTouchListener(this);
        kickButton.setOnTouchListener(this);

        menu.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) {
                try {
                    gameView.kill();
                    Intent intent = new Intent(Field.this, MenuFootball.class); //перекидывает из этого класс в класс, где обрабатывается персонаж, поле и т.д.
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean onTouch(View button, MotionEvent motion) {
        switch(button.getId()) { // определяем какая кнопка
            case R.id.leftButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isLeftPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isLeftPressed = false;
                        break;
                }
                break;
            case R.id.rightButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isRightPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isRightPressed = false;
                        break;
                }
                break;
            case R.id.jumpButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isJumpPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isJumpPressed = false;
                        break;
                }
                break;

            case R.id.kickButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isKickPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isKickPressed = false;
                        break;
                }
                break;
        }
        return true;
    }
}
