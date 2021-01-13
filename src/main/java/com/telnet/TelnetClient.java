package com.telnet;

import com.threads.ResultThread;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class TelnetClient extends Thread {
    private TelnetServer telnetServer;
    private Socket connection;

    private int depth;
    private String mask;
    private Exchanger<File> exchanger;

    private BufferedReader clientInput;
    private PrintStream clientOutput;

    public TelnetClient(TelnetServer telnetServer, Socket connection) {
        this.telnetServer = telnetServer;
        this.connection = connection;
        depth = 0;
        mask = "";
        exchanger = new Exchanger<>();
    }

    @Override
    public void run() {
        try {
            InputStream clientInputStream = connection.getInputStream();
            OutputStream clientOutputStream = connection.getOutputStream();

            clientInput = new BufferedReader(new InputStreamReader(clientInputStream));
            clientOutput = new PrintStream(clientOutputStream);
            clientOutput.println("Telnet server on port:" + telnetServer.getServerPort());
            clientOutput.println("Root directory: " + telnetServer.getRootAbsolutePath());
            clientOutput.println(showMenu(depth, mask));
            clientOutput.flush();

            String line;
            boolean stop = false;
            while (!stop && (line = clientInput.readLine()) != null) {
                switch (line) {
                    case "1":
                        inputDepth();
                        break;
                    case "2":
                        inputMask();
                        break;
                    case "3":
                        searchFile();
                        break;
                    case "0":
                        clientOutput.println("exit");
                        connection.close();
                        stop = true;
                        break;
                    default:
                        clientOutput.println("Try again");
                        break;
                }
                clientOutput.println(showMenu(depth, mask));

                clientOutput.flush();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void searchFile() throws InterruptedException {
        if (mask.equals("")) {
            clientOutput.println("You need set mask!");
        } else {
            new Thread(new ResultThread(exchanger, clientOutput)).start();

            telnetServer.searchFiles(depth, mask, exchanger);
        }
    }

    private void inputMask() throws IOException {
        clientOutput.println("Enter mask:");
        mask = clientInput.readLine();
    }

    private void inputDepth() throws IOException {
        do {
            clientOutput.println("Enter depth:");
            depth = Integer.parseInt(clientInput.readLine());
        } while (depth < 0);
    }

    private String showMenu(int depth, String mask) {

        String menu = "\r\n" + "Menu:\r\n" +
                "1. Set depth" + " (current: " + depth + ")\r\n" +
                "2. Set mask" + " (current: " + mask + ")\r\n" +
                "3. Search\r\n" +
                "0. Exit";
        return menu;
    }
}
