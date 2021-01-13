package com.fileSearchers;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileWalker extends SimpleFileVisitor<Path> {
    private String mask;
    private FileSearcherListener listener;

    public FileWalker(String mask, FileSearcherListener listener) {
        this.mask = mask;
        this.listener = listener;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.getFileName().toString().contains(mask)) {
            listener.send(file);
        }

        return super.visitFile(file, attrs);
    }
}
