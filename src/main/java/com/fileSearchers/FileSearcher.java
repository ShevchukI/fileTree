package com.fileSearchers;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Exchanger;

public class FileSearcher {
    private File root;
    private int depth;
    private String mask;

    public FileSearcher(File root, int depth, String mask) {
        this.root = root;
        this.depth = depth;
        this.mask = mask;
    }

    public void search(Exchanger<File> exchanger) throws InterruptedException {
        Queue<File> depthDirectories = new ArrayDeque<>();
        int currentDepth = 0;

        do {
            File currentFile = root;

            if (currentFile.isDirectory()) {
                for (File file : currentFile.listFiles()) {
                    if (currentDepth < depth && file.isDirectory()) {
                        depthDirectories.add(file);
                    }
                    if (file.getName().contains(mask)) {
                        exchanger.exchange(file);
                    }
                }
            }
            currentDepth++;
            root = depthDirectories.poll();
        } while (root != null);

        exchanger.exchange(null);
    }
}
