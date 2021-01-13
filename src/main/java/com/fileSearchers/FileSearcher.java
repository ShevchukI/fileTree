package com.fileSearchers;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.concurrent.Exchanger;

public class FileSearcher {
    private Path root;
    private int depth;
    private String mask;

    public FileSearcher(Path root, int depth, String mask) {
        this.root = root;
        this.depth = depth;
        this.mask = mask;
    }

    public void search(Exchanger<Path> exchanger) throws IOException {
        FileWalker fileWalker = new FileWalker(root, mask, file -> {
            try {
                exchanger.exchange(file);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Files.walkFileTree(root, EnumSet.noneOf(FileVisitOption.class), depth, fileWalker);
    }
}
