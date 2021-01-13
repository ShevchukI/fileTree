package com.fileSearchers;


import com.fileSearchers.entities.DepthDirectory;

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
                       searchResults.add(file);
                    }
                }
            }

            depthDirectory = depthDirectories.poll();
        } while (depthDirectory != null);

    }

    public List<File> getResults() {
        return new LinkedList<>(searchResults);
    }
}
