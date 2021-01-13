package com.fileSearchers;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public class FileSearcher {
    private Path root;
    private int depth;
    private String mask;
    private List<Path> searchResults;

    public FileSearcher(Path root, int depth, String mask) {
        this.root = root;
        this.depth = depth;
        this.mask = mask;

        searchResults = new LinkedList<>();
    }

    public void search() throws IOException {
        FileWalker fileWalker = new FileWalker(mask, searchResults::add);

        Files.walkFileTree(root, EnumSet.noneOf(FileVisitOption.class), depth, fileWalker);
    }

    public List<Path> getResults() {
        return new LinkedList<>(searchResults);
    }
}
