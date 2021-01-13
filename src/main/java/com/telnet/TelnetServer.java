package com.telnet;

import com.fileSearchers.FileSearcher;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.concurrent.Exchanger;

public class TelnetServer extends Thread {
    private int serverPort;
    private File rootPath;

    public TelnetServer(int serverPort, File rootPath) {
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
        return rootPath.getAbsolutePath();
    }

    public void searchFiles(int depth, String mask, Exchanger<File> exchanger) throws InterruptedException {

        FileSearcher fileSearcher = new FileSearcher(rootPath, depth, mask);
        fileSearcher.search(exchanger);
    }
}
