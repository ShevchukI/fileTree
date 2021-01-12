package com.telnet;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;

public class CustomFileVisitor<T> extends SimpleFileVisitor<T> {
    private TelnetClient client;

    public CustomFileVisitor(TelnetClient client){
        this.client = client;
    }

    public void sendFile(Path file){
        client.send(file);
    }
}
