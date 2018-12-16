package app.headfootballstudentversion.ru.headfootball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuFootball extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //задает начальную установку параметров при инициализации активности
        setContentView(R.layout.activity_menu_football); //обращаемся к файлу хмл, описывающему экран и кнопки
        final Button start = (Button)findViewById(R.id.start); //кнопка старт
        Button multiplayer = (Button)findViewById(R.id.multiplayer);
        final Button help = (Button)findViewById(R.id.help);

        help.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MenuFootball.this, Help.class); //перекидывает из этого класс в класс, где обрабатывается персонаж, поле и т.д.
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MenuFootball.this, Field.class); //перекидывает из этого класс в класс, где обрабатывается персонаж, поле и т.д.
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
