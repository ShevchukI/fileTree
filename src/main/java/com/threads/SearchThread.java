package com.threads;

import com.fileSearchers.FileSearcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class SearchThread implements Runnable {
    private Exchanger<File> exchanger;
    private File rootPath;
    private int depth;
    private String mask;

    public SearchThread(File rootPath, int depth, String mask, Exchanger<File> exchanger) {
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

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
