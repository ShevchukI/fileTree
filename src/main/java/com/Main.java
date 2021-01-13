package com;

import com.fileSearchers.FileSearcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);

        Path rootPath = inputPath(input);
        int depth = inputDepth(input);

        input.nextLine();
        String mask = inputMask(input);

        FileSearcher fileSearcher = new FileSearcher(rootPath, depth, mask);
        fileSearcher.search();

        for(Path path:fileSearcher.getResults()){
            System.out.println(path.toAbsolutePath());
        }

    }

    private static Path inputPath(Scanner input) {
        System.out.println("Enter root path:");
        return Paths.get(input.nextLine());
    }

    private static int inputDepth(Scanner input) {
        int depth = -1;
        do {
            try {

                System.out.println("Enter depth:");
                depth = input.nextInt();

            } catch (InputMismatchException e) {
                input.nextLine();
            }

        } while (depth < 0);

        return depth;
    }

    private static String inputMask(Scanner input) {
        System.out.println("Enter mask:");
        return input.nextLine();
    }
}
