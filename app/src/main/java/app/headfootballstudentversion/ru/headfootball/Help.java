package app.headfootballstudentversion.ru.headfootball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Help  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //задает начальную установку параметров при инициализации активности
        setContentView(R.layout.help);
        final Button menu = (Button)findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Help.this, MenuFootball.class); //перекидывает из этого класс в класс, где обрабатывается персонаж, поле и т.д.
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
