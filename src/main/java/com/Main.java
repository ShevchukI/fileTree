package com;

import com.fileSearchers.FileSearcher;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        File rootPath = inputPath(input);
        int depth = inputDepth(input);

        input.nextLine();
        String mask = inputMask(input);

        FileSearcher fileSearcher = new FileSearcher(rootPath, depth, mask);
        fileSearcher.search();

        for(File file:fileSearcher.getResults()){
            System.out.println(file.getAbsolutePath());
        }

    }

    private static File inputPath(Scanner input) {
        System.out.println("Enter root path:");
        return new File(input.nextLine());
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
