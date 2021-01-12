package com.telnet;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class TelnetClient extends Thread {
    private TelnetServer telnetServer = null;
    private Socket connection = null;

    private String uniqueId = null;
    private int depth = 0;
    private String mask = "";
    private Exchanger<Path> exchanger;

    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private boolean stop = false;

    private BufferedReader bufferedReader = null;
    private PrintStream printStream = null;


    public TelnetClient(TelnetServer telnetServer, Socket connection, String uniqueId) {
        this.telnetServer = telnetServer;
        this.connection = connection;
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public void run() {
        try {
            telnetServer.clientConnected(this);

            inputStream = connection.getInputStream();
            outputStream = connection.getOutputStream();

            exchanger = new Exchanger<>();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            printStream = new PrintStream(outputStream);
            printStream.println("Telnet server on port:" + telnetServer.getServerPort());
            printStream.println("Root directory: " + telnetServer.getRootAbsolutePath());
            printStream.println(showMenu(depth, mask));
            printStream.flush();

            String cmd = null;
            while (!stop && (cmd = bufferedReader.readLine()) != null) {
                switch (cmd) {
                    case "1":
                        inputDepth();
                        break;
                    case "2":
                        inputMask();
                        break;
                    case "3":
                        searchFile();

                        break;
                    case "4":
                        printStream.println("exit");
                        break;
                    default:
                        printStream.println("Try again");
                        break;
                }
                printStream.println(showMenu(depth, mask));

                printStream.flush();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void searchFile() throws IOException, InterruptedException {

        if (mask.equals("") || mask == null) {
            printStream.println("You need set mask!");
        } else {
           // boolean isDone = false;
            telnetServer.searchFiles(depth, mask, getUniqueId());
//            do {
//                Path path = exchanger.exchange(null);
//                if (path != null) {
//                    printStream.println(path.toAbsolutePath());
//                } else {
//                    isDone = true;
//                }
//            } while (!isDone);

        }
    }

    private void inputMask() throws IOException {
        printStream.println("Enter mask:");
        mask = bufferedReader.readLine();
    }

    private void inputDepth() throws IOException {
        do {
            printStream.println("Enter depth:");
            depth = Integer.parseInt(bufferedReader.readLine());
        } while (depth < 0);
    }

    private String showMenu(int depth, String mask) {
        StringBuilder menu = new StringBuilder("\r\n");
        menu.append("Menu:\r\n");
        menu.append("1. Set depth").append(" (current: ").append(depth).append(")\r\n");
        menu.append("2. Set mask").append(" (current: ").append(mask).append(")\r\n");
        menu.append("3. Search\r\n");
        menu.append("0. Exit");

        return menu.toString();
    }

    public void printMenu(){
        printStream.println(showMenu(depth, mask));
    }

    public void send(Path file) {
        printStream.println(file.toAbsolutePath());
    }
}
