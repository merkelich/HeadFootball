package app.headfootballstudentversion.ru.headfootball;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/*
TODO: Класс, отвечающий за экран Помощь.
 */

public class Help  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //задает начальную установку параметров при инициализации активности
        setContentView(R.layout.help); //обращаемся к файлу XML, описывающему экран помощи игроку
        final Button menu = (Button)findViewById(R.id.menu); //кнопка возвращения в Меню

        menu.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) { //слушаем кнопку возвращения в Меню
                try {
                    Help.this.finish(); //завершаем текущую Activity
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
