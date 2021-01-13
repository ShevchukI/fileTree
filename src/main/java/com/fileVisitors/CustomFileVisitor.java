package com.fileVisitors;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CustomFileVisitor extends SimpleFileVisitor<Path> {
    private String mask;
    private FileVisitorListener listener;
    private Path rootPath;

    public CustomFileVisitor(String mask, FileVisitorListener listener) {
        this.mask = mask;
        this.listener = listener;
    }

    public CustomFileVisitor(Path rootPath, String mask, FileVisitorListener listener) {
        this.rootPath = rootPath;
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

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (rootPath != null) {
            boolean isDone = Files.isSameFile(dir, rootPath);
            if (isDone) {
                listener.send(null);
            }
        }
        return super.postVisitDirectory(dir, exc);
    }
}
