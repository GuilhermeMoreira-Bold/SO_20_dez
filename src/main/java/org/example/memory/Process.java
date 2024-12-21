package org.example.memory;

public class Process {
    private String name;
    private int startIndex;
    private int size;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public Process(String name, int size) {
        this.name = name;
        this.size = size;
    }
}
