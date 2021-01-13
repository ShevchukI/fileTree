package com.fileSearchers;

import java.nio.file.Path;

public interface FileSearcherListener {
    void send(Path file);
}
