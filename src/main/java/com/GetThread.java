package com;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class GetThread implements Runnable {
    private Exchanger<Path> exchanger;
    private PrintStream printStream;

    public GetThread(Exchanger<Path> exchanger) {
        this.exchanger = exchanger;
    }

    public GetThread(Exchanger<Path> exchanger, PrintStream printStream){
        this.exchanger = exchanger;
        this.printStream = printStream;
    }

    @Override
    public void run() {
        try {
            receiveFile();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receiveFile() throws InterruptedException {
        boolean isDone = false;
        do {
            Path path = exchanger.exchange(null);
            if (path != null) {
                printStream.println(path.toAbsolutePath());
            } else {
                isDone = true;
            }
        } while (!isDone);
    }

}
