package com.fileVisitors;

import java.nio.file.Path;

public interface FileVisitorListener {
    void send(Path file);
}
