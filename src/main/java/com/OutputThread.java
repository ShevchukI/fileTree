package com;

import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

class OutputThread implements Runnable {
    private final BlockingQueue<Path> lane;
    private volatile boolean running = true;

    OutputThread(BlockingQueue<Path> lane) {
        this.lane = lane;
    }

    public void run() {
        while (running) {
            Path path = lane.peek();
            if (path != null) {
                consume(lane.poll());
            }
        }
    }

    private void consume(Path path) {
        System.out.println(path.toAbsolutePath());
    }

    public void terminate(){
        running = false;
    }

}