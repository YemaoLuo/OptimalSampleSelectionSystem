package ui;

import algorithm.SolutionHelper1_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class Page extends JFrame {
    private JLabel label1, label2, label3, label4, label5;
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JButton button;
    private JTextArea textArea;

    public Page() {
        setTitle("输入数据");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        label1 = new JLabel("输入框1：");
        panel.add(label1);
        textField1 = new JTextField();
        panel.add(textField1);

        label2 = new JLabel("输入框2：");
        panel.add(label2);
        textField2 = new JTextField();
        panel.add(textField2);

        label3 = new JLabel("输入框3：");
        panel.add(label3);
        textField3 = new JTextField();
        panel.add(textField3);

        label4 = new JLabel("输入框4：");
        panel.add(label4);
        textField4 = new JTextField();
        panel.add(textField4);

        label5 = new JLabel("输入框5：");
        panel.add(label5);
        textField5 = new JTextField();
        panel.add(textField5);

        button = new JButton("开始");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int m = Integer.parseInt(textField1.getText());
                int n = Integer.parseInt(textField2.getText());
                int j = Integer.parseInt(textField3.getText());
                int k = Integer.parseInt(textField4.getText());
                int s = Integer.parseInt(textField5.getText());

                SolutionHelper1_1 sh = new SolutionHelper1_1();
                long startTime = System.currentTimeMillis();
                System.out.println("=====================================");

                long tempTime = System.currentTimeMillis();
                java.util.List<Integer> chosenSamples = sh.generateChosenSamples(m, n);
                System.out.println("Chosen samples: " + chosenSamples);
                System.out.println("Chosen samples size: " + chosenSamples.size());
                System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
                System.out.println("=====================================");

                tempTime = System.currentTimeMillis();
                java.util.List<java.util.List<Integer>> possibleResults = sh.generatePossibleResults(chosenSamples, k);
                //System.out.println("Possible results: " + possibleResults);
                System.out.println("Possible results size: " + possibleResults.size());
                System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
                System.out.println("=====================================");

                tempTime = System.currentTimeMillis();
                java.util.List<java.util.List<Integer>> coverList = sh.generateCoverList(chosenSamples, j);
                Map<java.util.List<Integer>, java.util.List<java.util.List<Integer>>> coverListMap = sh.generateCoverListMap(coverList, s);
                //System.out.println("Cover list map: " + coverListMap);
                System.out.println("Cover list map size: " + coverListMap.size());
                System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
                System.out.println("=====================================");

                tempTime = System.currentTimeMillis();
                List<List<Integer>> result = sh.getResult(possibleResults, coverListMap);
                System.out.println("Result: " + result);
                System.out.println("Result size: " + result.size());
                System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
                System.out.println("=====================================");

                System.out.println("Total time cost: " + (System.currentTimeMillis() - startTime) + " ms");
                System.out.println("=====================================");

                textArea.setText("Result: " + result + "\n"
                        + "Reuslt Size: " + result.size() + "\n"
                        + "Total time cost: " + (System.currentTimeMillis() - startTime) + " ms\n");
            }
        });
        panel.add(button);

        textArea = new JTextArea();
        panel.add(textArea);

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Page();
    }
}