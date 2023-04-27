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
    private JButton startEndBtn;
    private JButton historyBtn;
    private JTextArea textArea;
    private JProgressBar progressBar;
    private JLabel progressLabel;
    private MySwingWorker worker;
    private DBHelperUI dbh = new DBHelperUI();

    public Page() {
        setTitle("Optimize Samples System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("./icon.png");
        setIconImage(icon.getImage());

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
        c.gridwidth = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 0, 10);
        startEndBtn = new JButton("Start/End");
        startEndBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (worker != null && !worker.isDone()) {
                    UIManager.put("OptionPane.okButtonText", "OK");
                    JOptionPane.showMessageDialog(null, "Please reopen the programme to restart!", "Notice", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                    return;
                }
                worker = new MySwingWorker();
                worker.execute();
            }
        });
        panel.add(startEndBtn, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 0, 10);
        historyBtn = new JButton("History");
        historyBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                List<String> files = dbh.readAllDBFiles();
                JPanel historyPanel = new JPanel();
                historyPanel.setLayout(new BorderLayout());
                JScrollPane scrollPanel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5, true)); // 设置边框样式
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new GridLayout(files.size(), 3, 10, 10));
                for (String file : files) {
                    JPanel rowPanel = new JPanel();
                    rowPanel.setLayout(new GridLayout(1, 3, 10, 10));
                    JLabel fileLabel = new JLabel(file);
                    rowPanel.add(fileLabel);
                    JButton check = new JButton("Check");
                    check.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String data = dbh.load(file);
                            UIManager.put("OptionPane.okButtonText", "OK");
                            JOptionPane.showMessageDialog(null, data, "History Result", JOptionPane.PLAIN_MESSAGE);
                        }
                    });
                    rowPanel.add(check);
                    JButton remove = new JButton("Remove");
                    remove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dbh.remove(file);
                            contentPanel.remove(rowPanel);
                            contentPanel.revalidate();
                        }
                    });
                    rowPanel.add(remove);
                    contentPanel.add(rowPanel);
                }
                scrollPanel.setViewportView(contentPanel);
                historyPanel.add(scrollPanel, BorderLayout.CENTER);
                JButton back = new JButton("Back");
                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        historyPanel.setVisible(false);
                        panel.setVisible(true);
                    }
                });
                historyPanel.add(back, BorderLayout.SOUTH);
                add(historyPanel, BorderLayout.CENTER);
                historyPanel.setVisible(true);
            }
        });
        panel.add(historyBtn, c);

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
            List<Integer> chosenSamples = sh.generateChosenSamples(m, n);
            List<List<Integer>> possibleResults = sh.generatePossibleResults(chosenSamples, k);
            List<List<Integer>> coverList = sh.generateCoverList(chosenSamples, j);
            Map<List<Integer>, List<List<Integer>>> coverListMap = sh.generateCoverListMap(coverList, s);

            double initSize = coverListMap.size();
            if (n <= 10) {
                while (!coverListMap.isEmpty()) {
                    List<Integer> candidateResult = sh.getCandidateResultSingleProcess(possibleResults, coverListMap);
                    result.add(candidateResult);
                    sh.removeCoverListMapKey(candidateResult, coverListMap);
                    possibleResults.remove(candidateResult);
                    double percent = (1 - coverListMap.size() / initSize) * 100;
                    publish((int) percent);
                    setProgress((int) percent);
                }
            } else {
                while (!coverListMap.isEmpty()) {
                    List<Integer> candidateResult = sh.getCandidateResult(possibleResults, coverListMap);
                    result.add(candidateResult);
                    sh.removeCoverListMapKey(candidateResult, coverListMap);
                    possibleResults.remove(candidateResult);
                    double percent = (1 - coverListMap.size() / initSize) * 100;
                    publish((int) percent);
                    setProgress((int) percent);
                }
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

            dbh.save(m + "-" + n + "-" + k + "-" + j + "-" + s, resultStr);
            return result;
        }

        @Override
        protected void process(List<Integer> chunks) {
            int value = chunks.get(chunks.size() - 1);
            progressBar.setValue(value);
            progressLabel.setText("Progress: " + value + "%");
        }

        @Override
        protected void done() {
            progressBar.setValue(100);
            progressLabel.setText("Progress: 100%");
        }
    }

    public static void main(String[] args) {
        new Page();
    }
}