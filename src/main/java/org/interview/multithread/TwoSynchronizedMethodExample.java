package org.interview.multithread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwoSynchronizedMethodExample {

    public static synchronized void methodOne() throws InterruptedException {
        log.debug("Зашли в метод 1");
        Thread.sleep(3000);
    }

    public synchronized void methodTwo() throws InterruptedException {
        log.debug("Зашли в метод 2");
        Thread.sleep(3000);
    }
}

class Main {
    public static void main(String[] args) throws InterruptedException {
        TwoSynchronizedMethodExample twoSynchronizedMethodExample = new TwoSynchronizedMethodExample();
        new Thread(() -> {
            try {
                TwoSynchronizedMethodExample.methodOne();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                twoSynchronizedMethodExample.methodTwo();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}