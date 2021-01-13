package com;

import com.fileVisitors.CustomFileVisitor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Exchanger<Path> exchanger = new Exchanger<>();

        Path rootPath = inputPath(input);
        int depth = inputDepth(input);
        String mask = inputMask(input);

        Thread put = new Thread(new PutThread(rootPath, depth, mask, exchanger));
        Thread get = new Thread(new GetThread(exchanger));

        put.start();
        get.start();
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
        input.nextLine();
        return input.nextLine();
    }
}
