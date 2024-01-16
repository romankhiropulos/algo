package org.interview.generic;

import java.util.ArrayList;
import java.util.List;

public class CovarianceAndContravarianceInGenericExample {

    static class Fruit {
        int weight;
    }

    static class Citrus extends Fruit {
        int weight;
    }

    static class Orange extends Citrus {
        String color;
    }

    static class RedOrange extends Orange {
        String color = "Red";
    }

    /**
     *
     * @param citruses <b>Ковариантный</b> тип принимаемого методом параметра. <br>
     *                 Вставлять параметром в метод можно списки со всеми наследниками Citrus, но читать внутри метода
     *                 можно из списка citruses только в переменные типа родителя Citrus или самого Citrus. При желании
     *                 чтения в переменные с типом наследников нужно явное приведение типа
     * @return - суммарный вес списка цитрусов
     */
    private static int totalCitrusWeight(List<? extends Citrus> citruses) {
        int weight = 0;
        for (int i = 0; i < citruses.size(); i++) {
            weight = weight + citruses.get(i).weight;
        }
        // Так как мы знаем, что тип принимаемого параметра точно наследует Citrus,
        // значит, мы можем объект этого типа записать и в переменную с родительским типом для Citrus:
        Fruit fruit = citruses.get(0);
        Citrus citrus = citruses.get(0);

        // Такая конструкция не сработает, так как мы не знаем точный тип списка citruses, поэтому нет возможности
        // в него вставить какой-либо объект. Вставляя сюда объект, мы можем не угадать, какой там на самом деле класс.
        // Это могло бы нарушить типобезопасность и привести к сбоям приложения
        // citruses.add(new Citrus());

        Orange orange = (Orange) citruses.get(1);

        return weight;
    }

    /**
     * @param citruses <b>Контрвариантный</b> тип принимаемого методом параметра.<br>
     *                 Параметр может принимать листы с типом Citrus и всеми родителями Citrus.<br>
     *                 Внутри метода вставлять в лист можно только объекты типа Citrus и его наследников.<br>
     *                 Читать из списка внутри метода можно только в переменную класса Object
     */
    private static void add10Citruses(List<? super Citrus> citruses) {
        for (int i = 0; i < 8; i++) {
            citruses.add(new Orange());
        }
        citruses.add(new RedOrange());
        citruses.add(new Citrus());
       // citruses.add(new Fruit());

        Object fruit = citruses.get(0);
    }

    public static void main(String[] args) {
        List<Orange> orangeList = new ArrayList<>();
        List<Citrus> citrusList = new ArrayList<>();
        // citrusList = orangeList;  // ! Ошибка  !
        // - в Java листу с определенным типом можно присвоить лист только с точно таким же типом. Тип наследников
        // и потомков тоже не допускается. Это называется ИНВАРИАНТНОСТЬ

        // КОВАРИАНТНОСТЬ
        //
        totalCitrusWeight(citrusList);
        totalCitrusWeight(orangeList);

        List<Fruit> fruitList = new ArrayList<>();
//        totalCitrusWeight(fruitList);

        add10Citruses(fruitList);
        add10Citruses(citrusList);
        // add10Citruses(orangeList); // ! Ошибка  !
    }
}
