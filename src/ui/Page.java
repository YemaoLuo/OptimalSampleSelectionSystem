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
    private JLabel progressLabel;
    private MySwingWorker worker;

    public Page() {
        setTitle("Optimize Samples System");
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
        c.insets = new Insets(10, 10, 0, 10);
        button = new JButton("Start/End");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (worker != null && !worker.isDone()) {
                    worker.cancel(true);
                    return;
                }
                worker = new MySwingWorker();
                worker.execute();
            }
        });
        panel.add(button, c);

        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 10, 10, 10);
        progressLabel = new JLabel("Progress: 0%", JLabel.CENTER);
        panel.add(progressLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 10, 10, 10);
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        panel.add(progressBar, c);

        add(panel);

        setVisible(true);
    }

    class MySwingWorker extends SwingWorker<List<List<Integer>>, Integer> {
        @Override
        protected List<List<Integer>> doInBackground() {
            textArea.setText("Starting");
            progressLabel.setText("Progress: 0%");
            progressBar.setValue(0);

            List<List<Integer>> result = new ArrayList<>();
            int m = Integer.parseInt(textField1.getText());
            int n = Integer.parseInt(textField2.getText());
            int k = Integer.parseInt(textField3.getText());
            int j = Integer.parseInt(textField4.getText());
            int s = Integer.parseInt(textField5.getText());

            SolutionHelperUI sh = new SolutionHelperUI();
            long startTime = System.currentTimeMillis();
            java.util.List<Integer> chosenSamples = sh.generateChosenSamples(m, n);
            java.util.List<java.util.List<Integer>> possibleResults = sh.generatePossibleResults(chosenSamples, k);
            java.util.List<java.util.List<Integer>> coverList = sh.generateCoverList(chosenSamples, j);
            Map<java.util.List<Integer>, java.util.List<java.util.List<Integer>>> coverListMap = sh.generateCoverListMap(coverList, s);

            double initSize = coverListMap.size();
            while (!coverListMap.isEmpty()) {
                if (isCancelled()) {
                    return null;
                }
                List<Integer> candidateResult = sh.getCandidateResult(possibleResults, coverListMap);
                result.add(candidateResult);
                sh.removeCoverListMapKey(candidateResult, coverListMap);
                possibleResults.remove(candidateResult);
                double percent = (1 - coverListMap.size() / initSize) * 100;
                publish((int) percent);
                setProgress((int) percent);
            }

            String resultStr = "";
            resultStr += "Result: \n";
            for (List<Integer> list : result) {
                resultStr += list.toString() + "\n";
            }
            resultStr += "\n";
            resultStr += "Reuslt Size: " + result.size() + "\n"
                    + "Total time cost: " + (System.currentTimeMillis() - startTime) + "ms";
            textArea.setText(resultStr);

            return result;
        }

        @Override
        protected void process(List<Integer> chunks) {
            if (!isCancelled()) {
                int value = chunks.get(chunks.size() - 1);
                textArea.setText("Cancelled");
                progressBar.setValue(value);
                progressLabel.setText("Progress: " + value + "%");
            }
        }

        @Override
        protected void done() {
            if (isCancelled()) {
                textArea.setText("Cancelling please wait for the programe to stop");
                progressBar.setValue(0);
                progressLabel.setText("Progress: 0%");
            } else {
                try {
                    List<List<Integer>> result = get();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Page();
    }
}