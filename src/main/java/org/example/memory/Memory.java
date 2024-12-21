package org.example.memory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Memory {
    private int MEMORY_SIZE;
    private ArrayList memory = new ArrayList<String>();

    public Memory(int memorySize) {
        this.MEMORY_SIZE = memorySize;
    }

    public void run(){
        for (int i = 0; i < MEMORY_SIZE; i++) {
            memory.add(null);
        }



        JButton allocateButton = new JButton("Alocar Processo");
        JButton freeButton = new JButton("Liberar Processo");
//
//        allocateButton.addActionListener(e -> allocateProcess());
//        freeButton.addActionListener(e -> freeProcess());
//
//        controlPanel.add(allocateButton);
//        controlPanel.add(freeButton);
//
//        memoryPanel.setLayout(new GridLayout(1, MEMORY_SIZE, 5, 5));
//        updateMemoryDisplay();
//
//        memoryFrame.add(controlPanel, BorderLayout.SOUTH);
//        memoryFrame.add(memoryPanel, BorderLayout.CENTER);
//
//        memoryFrame.setVisible(true);
    }
}
