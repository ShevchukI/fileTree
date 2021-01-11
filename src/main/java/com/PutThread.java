package com;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.concurrent.Exchanger;

public class PutThread implements Runnable {
    private Exchanger<Path> exchanger;
    private Path rootPath;
    private int depth;
    private String mask;

    public PutThread(Path rootPath, int depth, String mask, Exchanger<Path> exchanger) {
        this.exchanger = exchanger;
        this.rootPath = rootPath;
        this.depth = depth;
        this.mask = mask;
    }

    @Override
    public void run() {
        try {

            Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), depth, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    try {
                        if (file.getFileName().toString().contains(mask)) {
                            exchanger.exchange(file);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return FileVisitResult.CONTINUE;
                }
            });

            exchanger.exchange(null);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
