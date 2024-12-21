package org.example.memory;
import java.util.*;


public class QuickFitAllocator {
    private final Map<Integer, Queue<MemoryBlock>> blockLists;
    private final int[] predefinedSizes;
    private int nextStartIndex = 0;

    public QuickFitAllocator(int[] predefinedSizes) {
        this.predefinedSizes = predefinedSizes;
        this.blockLists = new HashMap<>();
        initializeBlockLists();
    }

    private void initializeBlockLists() {
        for (int size : predefinedSizes) {
            blockLists.put(size, new LinkedList<>());
        }
    }

    public void addBlocks(int size, int count) {
        if (!blockLists.containsKey(size)) {
            throw new IllegalArgumentException("Size not predefined: " + size);
        }
        Queue<MemoryBlock> list = blockLists.get(size);
        for (int i = 0; i < count; i++) {
            list.add(new MemoryBlock(size, nextStartIndex));
            nextStartIndex += size;
        }
    }


    public MemoryBlock allocate(int size) {
        Queue<MemoryBlock> list = blockLists.get(size);
        if (list != null && !list.isEmpty()) {
            MemoryBlock block = list.poll();
            block.allocate();
            return block;
        }
        throw new OutOfMemoryError("No block available for size: " + size);
    }

    public void deallocate(MemoryBlock block) {
        int size = block.getSize();
        if (!blockLists.containsKey(size)) {
            throw new IllegalArgumentException("Invalid block size: " + size);
        }
        block.free();
        blockLists.get(size).offer(block);
    }
}
