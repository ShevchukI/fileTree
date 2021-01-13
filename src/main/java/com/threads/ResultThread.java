package com.threads;

import java.io.File;
import java.util.concurrent.Exchanger;

public class ResultThread implements Runnable {
    private Exchanger<File> exchanger;

    public ResultThread(Exchanger<File> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            boolean isDone = false;
            do {
                File file = exchanger.exchange(null);

                if (file != null) {
                    System.out.println(file.getAbsolutePath());
                } else {
                    isDone = true;
                }
            } while (!isDone);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
