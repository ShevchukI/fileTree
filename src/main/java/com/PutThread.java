package com;

import com.fileVisitors.CustomFileVisitor;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.concurrent.Exchanger;

public class PutThread implements Runnable {
    private Exchanger<Path> exchanger;
    private Path rootPath;
    private int depth;
    private String mask;


    public PutThread(Path rootPath, int depth, String mask, Exchanger<Path> exchanger) {
        this.rootPath = rootPath;
        this.depth = depth;
        this.mask = mask;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        walkFileTree();

    }

    private void walkFileTree() {
        try {
            CustomFileVisitor fileVisitor = new CustomFileVisitor(rootPath, mask, file -> {
                try {
                    exchanger.exchange(file);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), depth, fileVisitor);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
