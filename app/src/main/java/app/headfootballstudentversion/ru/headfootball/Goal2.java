package app.headfootballstudentversion.ru.headfootball;

public class Goal2 extends GoalClass {

    protected int goal2;

    public Goal2() { //конструктор
        bitmapId = R.drawable.noll; // определяем начальные параметры
        y1 = 4; //размер по игрек футболиста
        x1 = 1; //размер по икс
        x = (float)8.7;
        y = 1;
    }

    public void goals() {
        switch (goal2) {
            case 1:
                bitmapId = R.drawable.one;
                break;
            case 2:
                bitmapId = R.drawable.two;
                break;
            case 3:
                bitmapId = R.drawable.three;
                break;
            case 4:
                bitmapId = R.drawable.four;
                break;
            case 5:
                bitmapId = R.drawable.five;
                break;
            case 6:
                bitmapId = R.drawable.six;
                break;
            case 7:
                bitmapId = R.drawable.seven;
                break;

            default:
                bitmapId = R.drawable.noll;
                break;
        }
    }

    @Override
    public void update(int goal, boolean goals) { // перемещаем футболиста в зависимости от нажатой кнопки
        if (goals) {
            goal2 = goal;
            goals();
        }
    }
}
