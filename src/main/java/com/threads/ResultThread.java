package com.threads;

import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class ResultThread implements Runnable {
    private Exchanger<Path> exchanger;

    public ResultThread(Exchanger<Path> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            boolean isDone = false;
            do {
                Path path = exchanger.exchange(null);

                if (path != null) {
                    System.out.println(path.toAbsolutePath());
                } else {
                    isDone = true;
                }
            } while (!isDone);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
