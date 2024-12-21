package org.example.terminal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.example.memory.MemoryBlock;
import org.example.memory.Process;
import org.example.memory.QuickFitAllocator;

public class MemoryTerminal {
    private int memory[];
    private QuickFitAllocator allocator;
    private ArrayList<Process> allocatedProcesses = new ArrayList<>();
    private List<MemoryBlock> blocks = new ArrayList<>();
    private JFrame frame = new JFrame("Simulador de Memória");
    private JPanel panel = new JPanel();

    public enum ALLOCATION_TYPE {
        FIRST_FIT,
        BEST_FIT,
        QUICK_FIT,
        WORST_FIT,
        NEXT_FIT
    }

    public MemoryTerminal(int memorySize) {
        this.memory = new int[memorySize];
       allocator = new QuickFitAllocator(new int[]{16,32,64,memorySize});
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        panel.setLayout(new GridLayout(1, memorySize));

        allocator.addBlocks(16, 5);
        allocator.addBlocks(32, 3);
        allocator.addBlocks(64, 2);
    }

    public void allocateProcess(ALLOCATION_TYPE type,Process process) {
        switch (type) {
            case FIRST_FIT:
                firstFitProcess(process);
                break;
                case BEST_FIT:
                    bestFitProcess(process);
                    break;
                    case QUICK_FIT:
                        quickFitProcess(process);
                        break;
                        case WORST_FIT:
                            worstFitProcess(process);
                            break;
                            case NEXT_FIT:
                                nextFitProcess(process);
                                break;
        }
    }

    private void firstFitProcess(Process process) {
        int processSize = process.getSize();
        int memorySize = memory.length;

        for (int i = 0; i < memorySize; i++) {
            boolean found = true;
            for (int j = 0; j < processSize; j++) {
                if (i + j >= memorySize || memory[i + j] != 0) {
                    found = false;
                    break;
                }
            }

            if (found) {
                for (int j = 0; j < processSize; j++) {
                    memory[i + j] = 1;
                }
                process.setStartIndex(i);

                allocatedProcesses.add(process);
                render();
                JOptionPane.showMessageDialog(frame, "First Fit: Processo " + process.getName() + "criado com sucesso!");
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "First Fit: sem espaço suficiente para o processo: " + process.getName());
    }
    private void bestFitProcess(Process process) {
        int bestBlockIndex = -1;
        int minWaste = Integer.MAX_VALUE;

        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == 0) {
                int j = i;
                int blockSize = 0;

                while (j < memory.length && memory[j] == 0) {
                    blockSize++;
                    j++;
                }

                if (blockSize >= process.getSize()) {
                    int waste = blockSize - process.getSize();

                    if (waste < minWaste) {
                        bestBlockIndex = i;
                        minWaste = waste;
                    }
                }

                i = j - 1;
            }
        }

        if (bestBlockIndex != -1) {
            for (int j = 0; j < process.getSize(); j++) {
                memory[bestBlockIndex + j] = 1;
            }
            process.setStartIndex(bestBlockIndex);

            allocatedProcesses.add(process);
            render();
            JOptionPane.showMessageDialog(frame, "Best Fit: Processo " + process.getName() + " criado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(frame, "Best Fit: sem espaço suficiente para o processo: " + process.getName());
        }
    }
    private void worstFitProcess(Process process) {
        int worstBlockIndex = -1;
        int maxWaste = -1;

        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == 0) {
                int j = i;
                int blockSize = 0;

                while (j < memory.length && memory[j] == 0) {
                    blockSize++;
                    j++;
                }

                if (blockSize >= process.getSize()) {
                    int waste = blockSize - process.getSize();

                    if (waste > maxWaste) {
                        worstBlockIndex = i;
                        maxWaste = waste;
                    }
                }

                i = j - 1;
            }
        }

        if (worstBlockIndex != -1) {
            for (int j = 0; j < process.getSize(); j++) {
                memory[worstBlockIndex + j] = 1;
            }
            process.setStartIndex(worstBlockIndex);

            allocatedProcesses.add(process);
            render();

            JOptionPane.showMessageDialog(frame, "Worst Fit: Processo " + process.getName() + " criado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(frame, "Worst Fit: sem espaço suficiente para o processo: " + process.getName());
        }
    }

    private void quickFitProcess(Process process) {
            try {
                MemoryBlock block = allocator.allocate(process.getSize());
                if (block == null) {
                    throw new OutOfMemoryError("Bloco não encontrado");
                }

                int startIndex = block.getStartIndex();

                for (int i = 0; i < process.getSize(); i++) {
                    if (memory[startIndex + i] != 0) {
                        throw new OutOfMemoryError("Bloco está sobreposto");
                    }
                    memory[startIndex + i] = 1;
                }

                process.setStartIndex(startIndex);
                allocatedProcesses.add(process);

                render();
                JOptionPane.showMessageDialog(frame, "Quick Fit: Processo " + process.getName() + " criado com sucesso!");
            } catch (OutOfMemoryError e) {
                JOptionPane.showMessageDialog(frame, "Quick Fit: sem espaço suficiente para o processo: " + process.getName());
            }
    }
    private int currentIndexForNextFit = 0;
    private void nextFitProcess(Process process) {
        int memorySize = memory.length;
        int processSize = process.getSize();
        for (int i = currentIndexForNextFit; i < memorySize; i++) {
            boolean found = true;
            for (int j = 0; j < processSize; j++) {
                if (i + j >= memorySize || memory[i + j] != 0) {
                    found = false;
                    break;
                }
            }
            if(found){
                for (int j = 0; j < processSize; j++) {
                    memory[i + j] = 1;
                }
                currentIndexForNextFit = i + processSize;
                process.setStartIndex(i);

                allocatedProcesses.add(process);
                render();
                JOptionPane.showMessageDialog(frame, "First Fit: Processo " + process.getName() + "criado com sucesso!");
                return;
            }
            JOptionPane.showMessageDialog(frame, "First Fit: sem espaço suficiente para o processo: " + process.getName());
        }
    }

    public void removeProcess(String process) {
        int index = findProcessIndexByName(process);
        if (index != -1) {
            int allocatedSize = allocatedProcesses.get(index).getSize();
            for (int i = 0; i < allocatedSize; i++) {
                memory[i + allocatedProcesses.get(index).getStartIndex()] = 0;
            }
            allocatedProcesses.remove(index);
            render();
        } else {
            System.out.println("Processo " + process + " não encontrado.");
        }
    }

    private int findProcessIndexByName(String processName) {
        for (int i = 0; i < allocatedProcesses.size(); i++) {
            if (allocatedProcesses.get(i).getName().equals(processName)) {
                return i;
            }
        }
        return -1;
    }

    public void render() {
        panel.removeAll();
        for (int i = 0; i < memory.length; i++) {
            JLabel label = new JLabel(memory[i] == 0 ? "FREE" : String.valueOf(memory[i]), SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(memory[i] == 0 ? Color.BLUE : Color.GREEN);
            label.setForeground(Color.WHITE);
            panel.add(label);
        }
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
