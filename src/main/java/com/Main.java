package com;

import com.telnet.TelnetServer;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int serverPort = inputServerPort(input);

        File rootPath = inputRootPath(input);

        new TelnetServer(serverPort, rootPath).start();

        System.out.println("Server started on port " + serverPort);
        System.out.println("Root directory: " + rootPath);
    }

    private static int inputServerPort(Scanner input) {
        int serverPort = -1;
        do {
            try {

                System.out.println("Enter serverPort:");
                serverPort = input.nextInt();

            } catch (InputMismatchException e) {
                input.nextLine();
            }

        } while (serverPort < 0);

        return serverPort;
    }

    private static File inputRootPath(Scanner input) {
        System.out.println("Enter root path:");
        input.nextLine();
        return new File(input.nextLine());
    }
}
