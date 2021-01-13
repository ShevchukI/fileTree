package com.telnet;

import com.fileVisitors.CustomFileVisitor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Exchanger;
import java.util.concurrent.atomic.AtomicInteger;

public class TelnetServer extends Thread {
    private final static int MAX_CONNECTIONS = 10;

    private int serverPort;
    private Path rootPath;

    private ServerSocket serverSocket = null;
    private TelnetClient client = null;
    private AtomicInteger numberOfConnections = new AtomicInteger(0);
    private Map<String, TelnetClient> activeConnections = new HashMap<String, TelnetClient>();

    public TelnetServer(int serverPort, Path rootPath) {
        this.serverPort = serverPort;
        this.rootPath = rootPath;
    }

    @Override
    public void run() {
        openConnections();
    }

    private void openConnections() {
        try {
            serverSocket = new ServerSocket(serverPort);

            while (true) {
                try {

                    Socket connection = serverSocket.accept();

                    if (getNumberOfConnections() >= MAX_CONNECTIONS) {

                        PrintStream pout = new PrintStream(connection.getOutputStream());
                        pout.println("Too many users");
                        connection.close();
                        continue;
                    } else {
                        client = new TelnetClient(this, connection, getUniqueID());
                        client.start();
                    }
                } catch (SocketException ee) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNumberOfConnections() {
        return numberOfConnections.intValue();
    }

    private String getUniqueID() {
        return (UUID.randomUUID().toString());
    }

    public int clientConnected(TelnetClient clientObj) {
        addToActiveConnection(clientObj);
        return numberOfConnections.addAndGet(1);
    }

    private void addToActiveConnection(TelnetClient clientObj) {
        activeConnections.put(clientObj.getUniqueId(), clientObj);
    }

    public int clientDisconnected(TelnetClient clientObj) {
        removeFromActiveConnection(clientObj);
        return numberOfConnections.decrementAndGet();
    }

    private void removeFromActiveConnection(TelnetClient clientObj) {
        activeConnections.remove(clientObj.getUniqueId());
    }

    public String getRootAbsolutePath() {
        return rootPath.toAbsolutePath().toString();
    }

    public int getServerPort() {
        return serverPort;
    }

    public void searchFiles(int depth, String mask, Exchanger<Path> exchanger) throws IOException {

        CustomFileVisitor fileVisitor = new CustomFileVisitor(rootPath, mask, file -> {
            try {
                exchanger.exchange(file);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), depth, fileVisitor);

    }
}
