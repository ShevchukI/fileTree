package com;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        Stack<Path> result = new Stack<>();

        System.out.println("Enter root path:");
        Path rootPath = Paths.get(input.nextLine());

        System.out.println("Enter depth:");
        int depth = input.nextInt();

        System.out.println("Enter mask:");
        input.nextLine();
        String mask = input.nextLine();
        
        Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), depth, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(file.getFileName().toString().contains(mask)){
                    result.push(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        while (!result.empty()){
            System.out.println(result.pop().toAbsolutePath());
        }

    }
}
