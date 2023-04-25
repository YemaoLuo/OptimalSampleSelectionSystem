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

    public Page() {
        setTitle("Optimize samples system");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(10, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        label1 = new JLabel("m：");
        panel.add(label1, c);

        c.gridx = 1;
        c.gridy = 0;
        textField1 = new JTextField();
        panel.add(textField1, c);

        c.gridx = 0;
        c.gridy = 1;
        label2 = new JLabel("n：");
        panel.add(label2, c);

        c.gridx = 1;
        c.gridy = 1;
        textField2 = new JTextField();
        panel.add(textField2, c);

        c.gridx = 0;
        c.gridy = 2;
        label3 = new JLabel("k：");
        panel.add(label3, c);

        c.gridx = 1;
        c.gridy = 2;
        textField3 = new JTextField();
        panel.add(textField3, c);

        c.gridx = 0;
        c.gridy = 3;
        label4 = new JLabel("j：");
        panel.add(label4, c);

        c.gridx = 1;
        c.gridy = 3;
        textField4 = new JTextField();
        panel.add(textField4, c);

        c.gridx = 0;
        c.gridy = 4;
        label5 = new JLabel("s：");
        panel.add(label5, c);

        c.gridx = 1;
        c.gridy = 4;
        textField5 = new JTextField();
        panel.add(textField5, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.weighty = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(10, 10, 10, 10);
        textArea = new JTextArea();
        panel.add(new JScrollPane(textArea), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 10, 10);
        button = new JButton("开始");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process();
            }
        });
        panel.add(button, c);

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
        while (!coverListMap.isEmpty()) {
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

        textArea.setText("Result: " + result + "\n"
                + "Reuslt Size: " + result.size() + "\n"
                + "Total time cost: " + (System.currentTimeMillis() - startTime) + " ms\n");
    }

    public static void main(String[] args) {
        new Page();
    }
}