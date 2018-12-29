package app.headfootballstudentversion.ru.headfootball;


public class Ball2 extends BallClass2 {
    protected float h1; //шаг,на который меняется координата по Y при полете
    protected float h; //максимальная высота подлета мяча

    protected float y2; //запоминаем начальную координату, с которой прыгает взлетает мяч
    protected boolean flying = false; //флаг полета при ударе от игрока, которым играем
    protected boolean flying1 = false; //флаг полета при ударе от противника
    protected boolean falling = false; //флаг падения при ударе от игрока, которым играем
    protected boolean falling1 = false; //флаг падения при ударе от противника

    protected boolean kick = false; //флаг, говорящий о том, что кнопка удара уже была нажата

    protected boolean move = false; //флаг, отвечающий за обновление картинки при движении мяча
    protected int swing; //флаг, отвечающий за обновление картинки при движении мяча

    protected boolean goal = false; //флаг, выставляемый при забивании одним из футболистов гола
    protected int goal1 = 0; //количество голов, изменяемое при забивании правым футболистом гола
    protected int goal2 = 0; //количество голов, изменяемое при забивании левым футболистом гола

    public static boolean isLeftPressed = false; // нажата левая кнопка
    public static boolean isRightPressed = false; // нажата правая кнопка
    public static boolean isJumpPressed = false; // нажата кнопка прыжка
    public static boolean isKickPressed = false; // нажата кнопка удар

    public static boolean toSynch = false; //флаг синхронизации

    public Ball2() { //конструктор
        bitmapId = R.drawable.ball; //ID картинки мяча

        y1 = 3; //размер по Y мяча
        x1 = 1; //размер по X мяча

        h1 = 1; //шаг,на который меняется координата по Y при полете
        h = 6; //максимальная высота подлета мяча

        x = (float)((int)(GameView.maxX / 2)); //координаты мяча
        y = GameView.maxY  - 7;


        y2 =  GameView.maxY - 7; //уровень земли

        swing = 1; //для смены картинок
        speed = (float) 0.4; //скорость движения по полю
    }

    public void moving() {
        if(move == true) {
            switch (swing) {
                case 1:
                    bitmapId = R.drawable.ball1;
                    swing++;
                    break;
                case 2:
                    bitmapId = R.drawable.ball5;
                    swing++;
                    break;
                case 3:
                    bitmapId = R.drawable.ball2;
                    swing++;
                    break;
                case 4:
                    bitmapId = R.drawable.ball6;
                    swing++;
                    break;
                case 5:
                    bitmapId = R.drawable.ball3;
                    swing++;
                    break;
                case 6:
                    bitmapId = R.drawable.ball7;
                    swing++;
                    break;
                case 7:
                    bitmapId = R.drawable.ball4;
                    swing++;
                    break;
                case 8:
                    bitmapId = R.drawable.ball8;
                    swing++;
                    break;

                default:
                    bitmapId = R.drawable.ball;
                    swing = 0;
                    swing++;
                    break;
            }
        }
    }

    private void restart() {
        x = (float)((int)(GameView.maxX / 2));
        y = GameView.maxY  - 7;

        flying = false;
        flying1 = false;
        falling = false;
        falling1 = false;
        kick = false;
        move = false;
        swing = 1;

        bitmapId = R.drawable.ball;
    }

    public boolean getGoal() { return goal; }

    public int getGoal1() { return goal1; }

    public int getGoal2() { return goal2; }

    @Override
    public void update(float x3, float y3, float x4, float y4) { //3 - правый, 4 - левый
        moving();

        if (goal == true) { //гол
            restart();
            goal = false;
        }

        if (x <= 0) { //гооооол
            goal = true;
            if (goal1 == 7) {
                goal1 = 0;
            }
            else {
                goal1++;
            }
        }

        if (x >= GameView.maxX) { //гоооооооооол
            goal = true;
            if (goal2 == 7) {
                goal2 = 0;
            }
            else {
                goal2++;
            }
        }

        if(flying && kick) { //полет и удар от правого игрока
            if((y2 - y) == h){
                y = y + h1;
                x -= speed;
                falling = true;
                flying = false;
                toSynch = true;
            }
            else {
                y -= h1;
                x -= speed;
            }
        }

        if(falling && kick) { //падение и удар от правого игрока
            if(y == y2) {
                x -= speed;
                falling = false;
                move = false;
                kick = false;
                toSynch = true;
            }
            else {
                y = y + h1;
                x -= speed;
            }
        }

        if(flying1 && kick) { //полет и удар от левого игрока
            if((y2 - y) == h){
                y = y + h1;
                x += speed;
                falling1 = true;
                flying1 = false;
                toSynch = true;
            }
            else {
                y -= h1;
                x += speed;
            }
        }

        if(falling1 && kick) { //падение и удар от левого игрока
            if(y == y2) {
                x += speed;
                falling1 = false;
                move = false;
                kick = false;
                toSynch = true;
            }
            else {
                y = y + h1;
                x += speed;
            }
        }

        if (Field.isServer == true) {
            if ((x >= x4 - 0.4 && x <= x4) && (y >= y4 - 5 && y <= y4 + 5) && isLeftPressed && x >= 1) { // движения от левого задом
                x -= speed;
                move = true;
                toSynch = true;
            }

            if ((x >= x4 - 0.4 && x <= x4) && (y >= y4 - 5 && y <= y4 + 5) && !isLeftPressed && x >= 1) { //если не двигаемся
                move = false;
            }

            if (((x <= x4 + 2.4 && x >= x4 + 1) && (y >= y4 - 5 && y <= y4 + 5)) && isRightPressed && x <= GameView.maxX - 4) { //движения от левого передом
                x += speed;
                move = true;
                toSynch = true;
            }

            if (((x <= x4 + 2.4 && x >= x4 + 1) && (y >= y4 - 5 && y <= y4 + 5)) && !isRightPressed && x <= GameView.maxX - 4) { //если не двигаемся
                move = false;
            }

            if (((x >= x3 - 0.4 && x <= x3) && (y >= y3 - 5 && y <= y3 + 5)) && Field.isLeftPressed && x >= 1) { //движения от правого передом
                x -= speed;
                move = true;
                toSynch = true;
            }

            if (((x >= x3 - 0.4 && x <= x3) && (y >= y3 - 5 && y <= y3 + 5)) && !Field.isLeftPressed && x >= 1) { //если не двигаемся
                move = false;
            }

            if (((x <= x3 + 2.4 && x >= x3 + 1) && (y >= y3 - 5 && y <= y3 + 5)) && Field.isRightPressed && x <= GameView.maxX - 4) { //движения от правого задом
                x += speed;
                move = true;
                toSynch = true;
            }

            if (((x <= x3 + 2.4 && x >= x3 + 1) && (y >= y3 - 5 && y <= y3 + 5)) && !Field.isRightPressed && x <= GameView.maxX - 4) { //если не двигаемся
                move = false;
            }

            if (Field.isKickPressed && kick == false && ((x >= x3 - 0.4 && x <= x3 + 0.4) && (y >= y3 - 5 && y <= y3 + 5))) { //удар от правого
                swing = 1;
                move = true;
                flying = true;
                kick = true;
                toSynch = true;
            }

            if (isKickPressed && kick == false && ((x <= x4 + 2.4 && x >= x4 + 1) && (y >= y4 - 5 && y <= y4 + 5))) { //удар от левого
                swing = 1;
                move = true;
                flying1 = true;
                kick = true;
                toSynch = true;
            }

        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        else {

            if ((x >= x3 - 0.4 && x <= x3) && (y >= y3 - 5 && y <= y3 + 5) && Field.isLeftPressed && x >= 1) { // движения от левого задом
                x -= speed;
                move = true;
                toSynch = true;
            }

            if ((x >= x3 - 0.4 && x <= x3) && (y >= y3 - 5 && y <= y3 + 5) && !Field.isLeftPressed && x >= 1) { //если не двигаемся
                move = false;
            }

            if (((x <= x3 + 2.4 && x >= x3 + 1) && (y >= y3 - 5 && y <= y3 + 5)) && Field.isRightPressed && x <= GameView.maxX - 4) { //движения от левого передом
                x += speed;
                move = true;
                toSynch = true;
            }

            if (((x <= x3 + 2.4 && x >= x3 + 1) && (y >= y3 - 5 && y <= y3 + 5)) && !Field.isRightPressed && x <= GameView.maxX - 4) { //если не двигаемся
                move = false;
            }

            if (((x >= x4 - 0.4 && x <= x4) && (y >= y4 - 5 && y <= y4 + 5)) && isLeftPressed && x >= 1) { //движения от правого передом
                x -= speed;
                move = true;
                toSynch = true;
            }

            if (((x >= x4 - 0.4 && x <= x4) && (y >= y4 - 5 && y <= y4 + 5)) && !isLeftPressed && x >= 1) { //если не двигаемся
                move = false;
            }

            if (((x <= x4 + 2.4 && x >= x4 + 1) && (y >= y4 - 5 && y <= y4 + 5)) && isRightPressed && x <= GameView.maxX - 4) { //движения от правого задом
                x += speed;
                move = true;
                toSynch = true;
            }

            if (((x <= x4 + 2.4 && x >= x4 + 1) && (y >= y4 - 5 && y <= y4 + 5)) && !isRightPressed && x <= GameView.maxX - 4) { //если не двигаемся
                move = false;
            }


            if (isKickPressed && kick == false && ((x >= x4 - 0.4 && x <= x4 + 0.4) && (y >= y4 - 5 && y <= y4 + 5))) { //удар от правого
                swing = 1;
                move = true;
                flying = true;
                kick = true;
                toSynch = true;
            }

            if (Field.isKickPressed && kick == false && ((x <= x3 + 2.4 && x >= x3 + 1) && (y >= y3 - 5 && y <= y3 + 5))) { //удар от левого
                swing = 1;
                move = true;
                flying1 = true;
                kick = true;
                toSynch = true;
            }

        }
    }

    public String getBallCoordinates() {
        return x + " " + y;
    }

    public void onNotify(String inputMessage) {
        // Принимаем новые координаты игрока
        String[] wordsArray = inputMessage.split(" ");
        switch(wordsArray[0]) {
            case("rightButtonDown"):
                isRightPressed = true;
                break;
            case("rightButtonUp"):
                isRightPressed = false;
                break;
            case("leftButtonDown"):
                isLeftPressed = true;
                break;
            case("leftButtonUp"):
                isLeftPressed = false;
                break;
            case("jumpButtonDown"):
                isJumpPressed = true;
                break;
            case("jumpButtonUp"):
                isJumpPressed = false;
                break;
            case("kickButtonDown"):
                isKickPressed = true;
                break;
            case("kickButtonUp"):
                isKickPressed = false;
                break;
        }
        if (toSynch) {
            x = Float.parseFloat(wordsArray[3]);
            toSynch = false;
        }

    }
}