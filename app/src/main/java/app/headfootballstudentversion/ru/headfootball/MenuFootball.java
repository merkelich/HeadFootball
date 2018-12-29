package app.headfootballstudentversion.ru.headfootball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/*
TODO: Класс, отвечающий за Меню. Класс, в котором обрабатываются нажатия кнопок для перехода между режимами игры
 */

public class MenuFootball extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //задает начальную установку параметров при инициализации активности
        setContentView(R.layout.activity_menu_football); //обращаемся к файлу XML, описывающему экран и кнопки в меню

        final Button start = (Button)findViewById(R.id.start); //кнопка Играть для игры в однопользовательском режиме
        final Button client = (Button)findViewById(R.id.client); //кнопка Присоединиться для присоединения пользователя к создателю комнаты в многопользовательском режиме
        final Button server = (Button)findViewById(R.id.server); //кнопка Создать комнату для создания комнаты в многопользовательском режиме
        final Button help = (Button)findViewById(R.id.help); //кнопка Помощь

        start.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) { //слушаем кнопку Играть для создания Activity для однопользовательского режима
                try {
                    Intent intent = new Intent(MenuFootball.this, Field.class); //перекидывает из этого класс в класс, где обрабатывается персонаж, поле и т.д. в однопользовательском режиме
                    intent.putExtra("multiplayer", false); //флаг, отвечающий за определение режима игры в классе Field(однопользовательский или многопользовательский)
                    startActivity(intent); //запуск Activity
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        client.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) { //слушаем кнопку Присоединиться для создания Activity для многопользовательского режима
                try {
                    Intent intent = new Intent(MenuFootball.this, Field.class); //перекидывает из этого класс в класс, где обрабатывается персонаж, поле и т.д. в многопользовательском режиме
                    intent.putExtra("multiplayer", true); //флаг, отвечающий за определение режима игры в классе Field(однопользовательский или многопользовательский)
                    intent.putExtra("isServer", false); //флаг, отвечающий за определение игрока в классе Field и других классах(сервер или клиент)
                    startActivity(intent); //запуск Activity
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        server.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) { //слушаем кнопку Создать комнату для создания Activity для многопользовательского режима
                try {
                    Intent intent = new Intent(MenuFootball.this, Field.class); //перекидывает из этого класс в класс, где обрабатывается персонаж, поле и т.д. в многопользовательском режиме
                    intent.putExtra("multiplayer", true); //флаг, отвечающий за определение режима игры в классе Field(однопользовательский или многопользовательский)
                    intent.putExtra("isServer", true); //флаг, отвечающий за определение игрока в классе Field и других классах(сервер или клиент)
                    startActivity(intent); //запуск Activity
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        help.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) { //слушаем кнопку Помощь для создания Activity для многопользовательского режима
                try {
                    Intent intent = new Intent(MenuFootball.this, Help.class); //перекидывает из этого класс в класс, где описано управление персонажем в игре
                    startActivity(intent); //запуск Activity
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
