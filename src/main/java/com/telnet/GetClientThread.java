package com.telnet;

import com.GetThread;

import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class GetClientThread extends GetThread {
    private TelnetClient client;

    public GetClientThread(TelnetClient client, Exchanger<Path> exchanger){
        super(exchanger);
        this.client = client;
    }

    @Override
    public void run() {
        try {
            boolean isDone = false;

            do {
                Path path = exchanger.exchange(null);
                if (path != null) {
                    client.send(path);
                } else {
                    isDone = true;
                }
            } while (!isDone);
            client.printMenu();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
