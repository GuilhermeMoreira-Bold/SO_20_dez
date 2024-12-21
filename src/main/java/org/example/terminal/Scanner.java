package org.example.terminal;

import org.example.memory.Memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import org.example.memory.Process;
public class Scanner {
    private MemoryTerminal memory;
    private JFrame frame = new JFrame("Simulador de Memória");
    private JPanel panel = new JPanel();
    private MemoryTerminal.ALLOCATION_TYPE type;


    public Scanner(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
    }
    public void  welcome(){
        JFrame frame = new JFrame("Simulador de Memória");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton defineMemoryButton = new JButton("1 - Definir o tamanho da memória");
        defineMemoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Digite o tamanho da memória:");
                if (input != null) {
                    memory = new MemoryTerminal(Integer.parseInt(input));
                    memory.render();
                    JOptionPane.showMessageDialog(frame, "Tamanho da memória definido para: " + input + " unidades.");
                }
            }
        });

        JButton chooseAlgorithmButton = new JButton("2 - Escolher algoritmo");
        chooseAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] algorithms = Arrays.stream(MemoryTerminal.ALLOCATION_TYPE.values())
                        .map(Enum::name)
                        .toArray(String[]::new);
                String selectedAlgorithm = (String) JOptionPane.showInputDialog(
                        frame,
                        "Escolha o algoritmo:",
                        "Menu de Algoritmos",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        algorithms,
                        algorithms[0]
                );
                if (selectedAlgorithm != null) {
                    try {
                        type = MemoryTerminal.ALLOCATION_TYPE.valueOf(selectedAlgorithm.toUpperCase());
                        JOptionPane.showMessageDialog(frame, "Algoritmo selecionado: " + type);
                    } catch (IllegalArgumentException er) {
                        JOptionPane.showMessageDialog(frame, "Algoritmo inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton createProcessButton = new JButton("3 - Alocar Processo");
        createProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Digite o nome do processo:");
                String size = JOptionPane.showInputDialog(frame, "Digite o tamanho do processo:");
                if (input != null) {
                    memory.allocateProcess(type, new Process(input, Integer.parseInt(size)));
                    memory.render();
                }
            }
        });

        JButton exitButton = new JButton("4 - Sair");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Tem certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        JButton removePorcessButton = new JButton("5 - Remover Processo");
        removePorcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String processName = JOptionPane.showInputDialog(frame, "Digite o nome do processo para remover:");
                memory.removeProcess(processName);
            }
        });

        panel.add(defineMemoryButton);
        panel.add(chooseAlgorithmButton);
        panel.add(createProcessButton);
        panel.add(removePorcessButton);
        panel.add(exitButton);

        frame.add(panel);

        frame.setVisible(true);
    }
}
