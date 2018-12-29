package app.headfootballstudentversion.ru.headfootball;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import java.util.Random;

import app.headfootballstudentversion.ru.headfootball.Scaledrone.MemberData;

public class Field extends AppCompatActivity implements View.OnTouchListener, RoomListener {

    public static boolean isLeftPressed = false; // нажата левая кнопка
    public static boolean isRightPressed = false; // нажата правая кнопка
    public static boolean isJumpPressed = false; // нажата кнопка прыжка
    public static boolean isKickPressed = false; // нажата кнопка удар
    public static boolean isRestartPressed = false; // нажата кнопка удар
    GameView gameView;

    // Переменные определяют состояние мультиплеера
    public static boolean multiplayer = false;
    public static boolean isServer = true;

    //Информация для Scaledrone
    private String channelID = "zMFEgOKkXOu27dhe";
    private String roomName = "observable-room";
    private Scaledrone scaledrone;
    private String incomingMessage;

    private Footballer3 myFootballer;
    private Footballer4 opponentFootballer;
    private Ball2 ball;

    public static Field field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //задает начальную установку параметров при инициализации активности
        Bundle intent = getIntent().getExtras();
        multiplayer = intent.getBoolean("multiplayer");
        isServer = intent.getBoolean("isServer");
        if (multiplayer == true) {
            setContentView(R.layout.fieldmultiplayer);
        }
        else {
            setContentView(R.layout.field);
        }

        gameView = new GameView(this); // создаём gameView

        field = this;

        RelativeLayout gameLayout = (RelativeLayout) findViewById(R.id.gameLayout); // находим gameLayout
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

        if (multiplayer == false) {
            ImageButton restartButton = (ImageButton) findViewById(R.id.restart);
            restartButton.setOnTouchListener(this);
        }
        // Определяем, была ли создана комната, или был запущен клиент

        menu.setOnClickListener(new View.OnClickListener() { //обработчик кнопки старт
            @Override
            public void onClick(View v) {
                //TODO: здесь убеждаемся что все закрыли
                gameView.kill();
                Field.this.finish();
            }
        });

        //Scaledrone connection
        if(multiplayer) {
            MemberData data = new MemberData(getRandomName(), getRandomColor());
            scaledrone = new Scaledrone(channelID, data);
            scaledrone.connect(new Listener() {
                @Override
                public void onOpen() {
                    System.out.println("Scaledrone connection open");
                    // Since the MainActivity itself already implement RoomListener we can pass it as a target
                    scaledrone.subscribe(roomName, Field.this);
                }

                @Override
                public void onOpenFailure(Exception ex) {
                    System.err.println(ex);
                }

                @Override
                public void onFailure(Exception ex) { System.err.println(ex); }

                @Override
                public void onClosed(String reason) {
                    System.err.println(reason);
                }
            });
        }

    }

    public boolean onTouch(View button, MotionEvent motion) {
        // получаем объекты футболистов
        if (multiplayer) {
            myFootballer = gameView.getMyFootballer();
            ball = gameView.getBall();
        }
        switch(button.getId()) { // определяем какая кнопка
            case R.id.leftButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isLeftPressed = true;
                        if(multiplayer) sendMessage("leftButtonDown " + myFootballer.getMyCoordinates() + " " + ball.getBallCoordinates());
                        break;
                    case MotionEvent.ACTION_UP:
                        isLeftPressed = false;
                        if(multiplayer) sendMessage("leftButtonUp " + myFootballer.getMyCoordinates() + " " + ball.getBallCoordinates());
                        break;
                }

                break;
            case R.id.rightButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isRightPressed = true;
                        if(multiplayer) sendMessage("rightButtonDown " + myFootballer.getMyCoordinates() + " " + ball.getBallCoordinates());
                        break;
                    case MotionEvent.ACTION_UP:
                        isRightPressed = false;
                        if(multiplayer) sendMessage("rightButtonUp " + myFootballer.getMyCoordinates() + " " + ball.getBallCoordinates());
                        break;
                }
                break;
            case R.id.jumpButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isJumpPressed = true;
                        if(multiplayer) sendMessage("jumpButtonDown " + myFootballer.getMyCoordinates() + " " + ball.getBallCoordinates());
                        break;
                    case MotionEvent.ACTION_UP:
                        isJumpPressed = false;
                        if(multiplayer) sendMessage("jumpButtonUp " + myFootballer.getMyCoordinates() + " " + ball.getBallCoordinates());
                        break;
                }
                break;

            case R.id.kickButton:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isKickPressed = true;
                        if(multiplayer) sendMessage("kickButtonDown " + ball.getBallCoordinates() + " " + ball.getBallCoordinates());
                        break;
                    case MotionEvent.ACTION_UP:
                        isKickPressed = false;
                        if(multiplayer) sendMessage("kickButtonUp " + ball.getBallCoordinates() + " " + ball.getBallCoordinates());
                        break;
                }
                break;

            case R.id.restart:
                switch (motion.getAction()) { // определяем нажата или отпущена
                    case MotionEvent.ACTION_DOWN:
                        isRestartPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isRestartPressed = false;
                        break;
                }
                break;
        }
        return true;
    }

    // Переопределяем методы RoomListener
    // Successfully connected to Scaledrone room
    @Override
    public void onOpen(Room room) {
        System.out.println("Conneted to room");
    }
    // Connecting to Scaledrone room failed
    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }
    // Received a message from Scaledrone room
    @Override
    public void onMessage(Room room, final JsonNode json, final Member member) {
        opponentFootballer = gameView.getOpponentFootballer();
        ball = gameView.getBall();
        // To transform the raw JsonNode into a POJO we can use an ObjectMapper
        final ObjectMapper mapper = new ObjectMapper();
        try {
            // member.clientData is a MemberData object, let's parse it as such
            final MemberData data = mapper.treeToValue(member.getClientData(), MemberData.class);
            // if the clientID of the message sender is the same as our's it was sent by us
            boolean belongsToCurrentUser = member.getId().equals(scaledrone.getClientID());
            // since the message body is a simple string in our case we can use json.asText() to parse it as such
            // if it was instead an object we could use a similar pattern to data parsing
            if (!belongsToCurrentUser) {
                incomingMessage = json.asText();
                System.out.println(incomingMessage);
                opponentFootballer.onNotify(incomingMessage);
                ball.onNotify(incomingMessage);

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Методы получения флагов мультиплеера
    public boolean getMultiplayer() {
        return multiplayer;
    }


    //Random names creation
    // TODO: make better
    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (adjs[(int) Math.floor(Math.random() * adjs.length)] + "_" + nouns[(int) Math.floor(Math.random() * nouns.length)]);
    }

    //Random colors creation
    // TODO: make better
    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
    // Метод посылает сообщение
    public void sendMessage(String str) {
        String message = str;
        if (message.length() > 0) {
            scaledrone.publish("observable-room", message);
        }
    }
}