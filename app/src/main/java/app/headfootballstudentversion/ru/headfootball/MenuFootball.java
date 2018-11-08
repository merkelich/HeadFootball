package app.headfootballstudentversion.ru.headfootball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuFootball extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_football);
        final Button start = (Button)findViewById(R.id.start);
        Button multiplayer = (Button)findViewById(R.id.multiplayer);
        Button developers = (Button)findViewById(R.id.developers);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MenuFootball.this, Field.class);
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
