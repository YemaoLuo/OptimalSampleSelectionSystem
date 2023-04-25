package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Page extends JFrame {
    private JLabel label1, label2, label3, label4, label5;
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JButton button;
    private JTextArea textArea;
    private JProgressBar progressBar;


    public Page() {
        setTitle("Optimize samples system");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        label1 = new JLabel("m：");
        textField1 = new JTextField();
        panel.add(label1);
        panel.add(textField1);

        label2 = new JLabel("n：");
        textField2 = new JTextField();
        panel.add(label2);
        panel.add(textField2);

        label3 = new JLabel("k：");
        textField3 = new JTextField();
        panel.add(label3);
        panel.add(textField3);

        label4 = new JLabel("j：");
        textField4 = new JTextField();
        panel.add(label4);
        panel.add(textField4);

        label5 = new JLabel("s：");
        textField5 = new JTextField();
        panel.add(label5);
        panel.add(textField5);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        panel.add(progressBar);

        textArea = new JTextArea();
        panel.add(textArea);

        button = new JButton("开始");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process();
            }
        });
        panel.add(button);

        add(panel);

        setVisible(true);
    }

    public void process() {
        int m = Integer.parseInt(textField1.getText());
        int n = Integer.parseInt(textField2.getText());
        int k = Integer.parseInt(textField3.getText());
        int j = Integer.parseInt(textField4.getText());
        int s = Integer.parseInt(textField5.getText());

        SolutionHelperUI sh = new SolutionHelperUI();
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

        List<List<Integer>> result = new ArrayList<>();
        double initSize = coverListMap.size();
        long removeCoverListMapKeyTime = 0, getCandidateResultTime = 0;
        double percent = 0;
        while (!coverListMap.isEmpty()) {
            percent = (1 - coverListMap.size() / initSize) * 100;
            progressBar.setValue((int) percent);
            progressBar.repaint();
            long startTime5 = System.currentTimeMillis();
            System.out.println(String.format("%.2f", (1 - coverListMap.size() / initSize) * 100) + "%");
            List<Integer> candidateResult = sh.getCandidateResult(possibleResults, coverListMap);
            getCandidateResultTime += System.currentTimeMillis() - startTime5;
            startTime5 = System.currentTimeMillis();
            result.add(candidateResult);
            sh.removeCoverListMapKey(candidateResult, coverListMap);
            possibleResults.remove(candidateResult);
            removeCoverListMapKeyTime += System.currentTimeMillis() - startTime5;
        }
        System.out.println("Remove cover list time: " + removeCoverListMapKeyTime + " ms");
        System.out.println("Get candidate result time: " + getCandidateResultTime + " ms");
        System.out.println("=========================================");

        System.out.println("Result: " + result);
        System.out.println("Result size: " + result.size());
        System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
        System.out.println("=====================================");

        System.out.println("Total time cost: " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("=====================================");

        progressBar.setValue(100);

        textArea.setText("Result: " + result + "\n"
                + "Reuslt Size: " + result.size() + "\n"
                + "Total time cost: " + (System.currentTimeMillis() - startTime) + " ms\n");
    }

    public static void main(String[] args) {
        new Page();
    }
}