package com.fileSearchers.entities;

import java.io.File;

public class DepthDirectory {
    private int depth;
    private File file;

    public DepthDirectory(int depth, File file){
        this.depth = depth;
        this.file = file;
    }

    public int getDepth() {
        return depth;
    }

    public File getFile() {
        return file;
    }
}