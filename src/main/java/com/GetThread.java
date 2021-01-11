package com;

import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class GetThread implements Runnable {
    private Exchanger<Path> exchanger;

    public GetThread(Exchanger<Path> exchanger) {
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
