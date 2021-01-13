package com.threads;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class ResultThread implements Runnable {
    private Exchanger<Path> exchanger;
    private PrintStream printStream;

    public ResultThread(Exchanger<Path> exchanger, PrintStream printStream) {
        this.exchanger = exchanger;
        this.printStream = printStream;
    }

    @Override
    public void run() {
        try {
            boolean isDone = false;
            do {
                Path path = exchanger.exchange(null);

                if (path != null) {
                    printStream.println(path.toAbsolutePath());
                } else {
                    isDone = true;
                }
            } while (!isDone);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
