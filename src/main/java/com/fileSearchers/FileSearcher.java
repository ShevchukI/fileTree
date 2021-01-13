package com.fileSearchers;


import java.io.File;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileSearcher {
    private File root;
    private int depth;
    private String mask;
    private List<File> searchResults;

    public FileSearcher(File root, int depth, String mask) {
        this.root = root;
        this.depth = depth;
        this.mask = mask;

        searchResults = new LinkedList<>();
    }

    public void search() {
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
                        searchResults.add(file);
                    }
                }
            }
            currentDepth++;
            root = depthDirectories.poll();
        } while (root != null);
    }

    public List<File> getResults() {
        return new LinkedList<>(searchResults);
    }
}
