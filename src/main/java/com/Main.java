package com;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        List<Path> result = new LinkedList<>();

        System.out.println("Enter root path:");
        Path rootPath = Paths.get(input.nextLine());

        System.out.println("Enter depth:");
        int depth = input.nextInt();

        System.out.println("Enter mask:");
        input.nextLine();
        String mask = input.nextLine();

        CustomFileVisitor fileVisitor = new CustomFileVisitor(mask, result::add);
        
        Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), depth, fileVisitor);

        for(Path path:result){
            System.out.println(path.toAbsolutePath());
        }

    }
}
