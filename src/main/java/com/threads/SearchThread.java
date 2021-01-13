package com.threads;

import com.fileSearchers.FileSearcher;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class SearchThread implements Runnable {
    private Exchanger<Path> exchanger;
    private Path rootPath;
    private int depth;
    private String mask;

    public SearchThread(Path rootPath, int depth, String mask, Exchanger<Path> exchanger) {
        this.exchanger = exchanger;
        this.rootPath = rootPath;
        this.depth = depth;
        this.mask = mask;
    }

    @Override
    public void run() {
        try {
            FileSearcher fileSearcher = new FileSearcher(rootPath, depth, mask);
            fileSearcher.search(exchanger);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
