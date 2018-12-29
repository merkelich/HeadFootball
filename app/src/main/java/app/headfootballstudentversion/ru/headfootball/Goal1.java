package app.headfootballstudentversion.ru.headfootball;

/*
TODO: Класс, отвечающий за изменение цифры, отражающей количество голов у правого игрока, на экране
 */

public class Goal1 extends GoalClass {

    protected int goal1; //количество голов правого игрока

    public Goal1() { //конструктор класса
        bitmapId = R.drawable.noll; //в начале игры у всех 0 голов

        y1 = 4; //размер цифры по игрек
        x1 = 1; //размер цифры по икс

        x = GameView.maxX - (float)9.5; //координаты цифры
        y = 1;
    }

    public void goals() { //за счет количества голов обновляем цифру на экране
        switch (goal1) {
            case 1:
                bitmapId = R.drawable.one; //цифра 1
                break;
            case 2:
                bitmapId = R.drawable.two; //цифра 2
                break;
            case 3:
                bitmapId = R.drawable.three; //цифра 3
                break;
            case 4:
                bitmapId = R.drawable.four; //цифра 4
                break;
            case 5:
                bitmapId = R.drawable.five; //цифра 5
                break;
            case 6:
                bitmapId = R.drawable.six; //цифра 6
                break;
            case 7:
                bitmapId = R.drawable.seven; //цифра 7
                break;

            default:
                bitmapId = R.drawable.noll; //цифра ноль
                break;
        }
    }

    @Override
    public void update(int goal, boolean goals) { //если есть флаг о забитом голе, то меняем картинку с цифрой
        if (goals) {
            goal1 = goal;
            goals();
        }
    }
}
