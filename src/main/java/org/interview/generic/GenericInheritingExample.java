package org.interview.generic;

public class GenericInheritingExample {


    public static void main(String[] args) {
        GenericExampleChildOne<GenericInheritingExampleChildTwo> child = new GenericExampleChildOne<>();
        child.setT(new GenericInheritingExampleChildTwo());
        child.getT().print();
        child.getT().logging();
    }

    void doPrint() {
        System.out.println("doPrint");
    }
}

/**
 * Здесь тип T обязательно должен и экстендить GenericExample и имплементить оба указанных
 * интерфейса PrintingInterface и LoggingInterface
 * 
 * @param <T>
 */
class GenericExampleChildOne<T extends GenericInheritingExample & PrintingInterface & LoggingInterface> {

    T t;

    public void setT(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }
}

class GenericInheritingExampleChildTwo extends GenericInheritingExample implements PrintingInterface, LoggingInterface {

}

interface PrintingInterface {

    default void print() {
        System.out.println("printing");
    }
}

interface LoggingInterface {

    default void logging() {
        System.out.println("Logging");
    }
}
