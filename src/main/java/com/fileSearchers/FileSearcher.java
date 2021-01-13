package com.fileSearchers;

import com.fileSearchers.entities.DepthDirectory;

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
        Queue<DepthDirectory> depthDirectories = new ArrayDeque<>();
        DepthDirectory depthDirectory = new DepthDirectory(0, root);

        do {
            File currentFile = depthDirectory.getFile();
            int currentDepth = depthDirectory.getDepth();
            if (currentFile.isDirectory()) {
                for (File file : currentFile.listFiles()) {
                    if (currentDepth < depth && file.isDirectory()) {
                        depthDirectories.add(new DepthDirectory(currentDepth + 1, file));
                    }
                    if (file.getName().contains(mask)) {
                        exchanger.exchange(file);
                    }
                }
            }

            depthDirectory = depthDirectories.poll();
        } while (depthDirectory != null);

        exchanger.exchange(null);
    }
}
