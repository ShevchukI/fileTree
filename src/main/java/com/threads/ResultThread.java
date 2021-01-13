package com.threads;

import java.io.File;
import java.io.PrintStream;
import java.util.concurrent.Exchanger;

public class ResultThread implements Runnable {
    private Exchanger<File> exchanger;
    private PrintStream printStream;

    public ResultThread(Exchanger<File> exchanger, PrintStream printStream) {
        this.exchanger = exchanger;
        this.printStream = printStream;
    }

    @Override
    public void run() {
        try {
            boolean isDone = false;
            do {
                File file = exchanger.exchange(null);

                if (file != null) {
                    printStream.println(file.getAbsolutePath());
                } else {
                    isDone = true;
                }
            } while (!isDone);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
