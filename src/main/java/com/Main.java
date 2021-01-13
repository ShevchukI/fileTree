package com;

import com.threads.ResultThread;
import com.threads.SearchThread;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Exchanger<File> exchanger = new Exchanger<>();

        File rootPath = inputPath(input);

        int depth = inputDepth(input);

        input.nextLine();
        String mask = inputMask(input);

        new Thread(new SearchThread(rootPath, depth, mask, exchanger)).start();
        new Thread(new ResultThread(exchanger)).start();

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
