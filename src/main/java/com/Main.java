package com;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter root path:");
        Path rootPath = Paths.get(input.nextLine());

        System.out.println("Enter depth:");
        int depth = input.nextInt();

        System.out.println("Enter mask:");
        input.nextLine();
        String mask = input.nextLine();
        long time = System.nanoTime();

        BlockingQueue<Path> result = new LinkedBlockingQueue<>();

        OutputThread consumer = new OutputThread(result);
        Thread thread = new Thread(consumer);
        thread.start();

        Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), depth, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().contains(mask)) {
                    try {
                        result.put(file);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    result.push(file);

                }
                return FileVisitResult.CONTINUE;
            }
        });

        consumer.terminate();


        time = System.nanoTime() - time;
        System.out.printf("Elapsed %,9.3f ms\n", time/1_000_000.0);
    }


}
