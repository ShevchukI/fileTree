package com.telnet;

import com.fileSearchers.FileSearcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class TelnetServer extends Thread {
    private int serverPort;
    private Path rootPath;

    public TelnetServer(int serverPort, Path rootPath) {
        this.serverPort = serverPort;
        this.rootPath = rootPath;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (true) {
                try {
                    Socket connection = serverSocket.accept();

                    new TelnetClient(this, connection).start();
                } catch (SocketException ee) {
                    ee.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getRootAbsolutePath() {
        return rootPath.toAbsolutePath().toString();
    }

    public void searchFiles(int depth, String mask, Exchanger<Path> exchanger) throws IOException {

        FileSearcher fileSearcher = new FileSearcher(rootPath, depth, mask);
        fileSearcher.search(exchanger);
    }
}
