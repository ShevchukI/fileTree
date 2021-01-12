package com;

import com.telnet.TelnetServer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int serverPort = -1;

        do {
            System.out.println("Enter server port:");
            serverPort = input.nextInt();
        } while (serverPort < 0);

        input.nextLine();
        System.out.println("Enter root path:");
        Path rootPath = Paths.get(input.nextLine());

        TelnetServer server = new TelnetServer(serverPort, rootPath);
        server.start();

        System.out.println("Server started on port " + serverPort);
        System.out.println("Root directory: " + rootPath);
    }

}
