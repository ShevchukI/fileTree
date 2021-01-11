package com;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Exchanger<Path> exchanger = new Exchanger<>();

        System.out.println("Enter root path:");
        Path rootPath = Paths.get(input.nextLine());

        System.out.println("Enter depth:");
        int depth = input.nextInt();

        System.out.println("Enter mask:");
        input.nextLine();
        String mask = input.nextLine();


        Thread put = new Thread(new PutThread(rootPath, depth, mask, exchanger));
        Thread get = new Thread(new GetThread(exchanger));

        put.start();
        get.start();
    }

}
