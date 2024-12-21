package org.example.memory;

public class MemoryBlock {
    private final int size;
    private boolean isFree;
    private int startIndex;

    public MemoryBlock(int startIndex, int size) {
        this.startIndex = startIndex;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public boolean isFree() {
        return isFree;
    }
    public int getStartIndex() {
        return startIndex;
    }

    public void allocate() {
        isFree = false;
    }

    public void free() {
        isFree = true;
    }

}
