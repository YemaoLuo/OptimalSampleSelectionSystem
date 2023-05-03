import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Page extends JFrame {
    private JLabel label1, label2, label3, label4, label5;
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JButton startEndBtn;
    private JButton historyBtn;
    private JTextArea textArea;
    private JProgressBar progressBar;
    private MySwingWorker worker;
    private DBHelperUI dbh = new DBHelperUI();
    private SolutionHelperUI sh = new SolutionHelperUI();

    public Page() {
        setTitle("Optimal Sample Selection System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon("./res/icon.png");
        setIconImage(icon.getImage());

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(10, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        label1 = new JLabel("m：[45, 54]");
        panel.add(label1, c);

        c.gridx = 1;
        c.gridy = 0;
        textField1 = new JTextField();
        panel.add(textField1, c);

        c.gridx = 0;
        c.gridy = 1;
        label2 = new JLabel("n： [7, 25]");
        panel.add(label2, c);

        c.gridx = 1;
        c.gridy = 1;
        textField2 = new JTextField();
        panel.add(textField2, c);

        c.gridx = 0;
        c.gridy = 2;
        label3 = new JLabel("k： [4, 7]");
        panel.add(label3, c);

        c.gridx = 1;
        c.gridy = 2;
        textField3 = new JTextField();
        panel.add(textField3, c);

        c.gridx = 0;
        c.gridy = 3;
        label4 = new JLabel("j：  [s, k]");
        panel.add(label4, c);

        c.gridx = 1;
        c.gridy = 3;
        textField4 = new JTextField();
        panel.add(textField4, c);

        c.gridx = 0;
        c.gridy = 4;
        label5 = new JLabel("s： [3, 7]");
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
                ImageIcon errorIcon = new ImageIcon("./res/error.png");
                if (worker != null && !worker.isDone()) {
                    Object[] options = {"Yes", "No"};
                    int result = JOptionPane.showOptionDialog(null, "Running in progress. Sure to end?", "Confirm",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Yes");
                    if (result == JOptionPane.YES_OPTION) {
                        try {
                            UIManager.put("OptionPane.okButtonText", "OK");
                            JOptionPane.showMessageDialog(null, "The programme will close and restart immediately. If fails, please restart it manually.", "Notice", JOptionPane.INFORMATION_MESSAGE, null);
                            String currentPath = System.getProperty("user.dir");
                            Runtime.getRuntime().exec("taskkill /f /im " + currentPath + "\\OSSS.exe");
                            Runtime.getRuntime().exec(currentPath + "\\OSSS.exe");
                        } catch (Exception ex) {
                        } finally {
                            System.exit(0);
                        }
                    }
                    return;
                }
                try {
                    int m = Integer.parseInt(textField1.getText());
                    int n = Integer.parseInt(textField2.getText());
                    int k = Integer.parseInt(textField3.getText());
                    int j = Integer.parseInt(textField4.getText());
                    int s = Integer.parseInt(textField5.getText());
                    if (!sh.validate(m, n, k, j, s)) {
                        UIManager.put("OptionPane.okButtonText", "OK");
                        JOptionPane.showMessageDialog(null, "Input invalid! \n \n" +
                                "m is within the range of [45, 54]\n" +
                                "n is within the range of [7, 25]\n" +
                                "k is within the range of [4, 7]\n" +
                                "s is within the range of [3, 7]\n" +
                                "j is between the minimum value of s and k", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
                        return;
                    }
                } catch (Exception ex) {
                    UIManager.put("OptionPane.okButtonText", "OK");
                    JOptionPane.showMessageDialog(null, "Input invalid! \n" +
                            "m is within the range of [45, 54], n is within the range of [7, 25]\n" +
                            "k is within the range of [4, 7], s is within the range of [3, 7]\n" +
                            "j is between the minimum value of s and k.", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);
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
                scrollPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5, true));
                GridBagLayout layout = new GridBagLayout();
                JPanel contentPanel = new JPanel(layout);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                gbc.weighty = 0.0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridx = 0;
                gbc.gridy = 0;
                for (String file : files) {
                    historyPanel.setAlignmentY(Component.TOP_ALIGNMENT);
                    JPanel rowPanel = new JPanel();
                    rowPanel.setLayout(new GridLayout(1, 3, 10, 10));
                    JLabel fileLabel = new JLabel(file);
                    rowPanel.add(fileLabel);
                    JButton check = new JButton("Check");
                    check.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String data = dbh.load(file);
                            JScrollPane scrollPanel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                            JTextArea label = new JTextArea(data);
                            scrollPanel.setViewportView(label);
                            JFrame frame = new JFrame(file);
                            frame.setSize(300, 500);
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            frame.add(scrollPanel);
                            frame.setLocationRelativeTo(null);
                            frame.setResizable(false);
                            frame.setVisible(true);
                        }
                    });
                    rowPanel.add(check);
                    JButton remove = new JButton("Remove");
                    remove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Object[] options = {"Yes", "No"};
                            int result = JOptionPane.showOptionDialog(null, "Confirm to remove this history data?", "Confirm",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Yes");
                            if (result == JOptionPane.YES_OPTION) {
                                dbh.remove(file);
                                historyPanel.setVisible(false);
                                contentPanel.remove(rowPanel);
                                historyPanel.setVisible(true);
                            }
                        }
                    });
                    rowPanel.add(remove);
                    gbc.gridy++;
                    contentPanel.add(rowPanel, gbc);
                }
                if (files.size() == 0) {
                    JLabel emptyLabel = new JLabel("No history data", SwingConstants.CENTER);
                    emptyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    gbc.gridy++;
                    contentPanel.add(emptyLabel, gbc);
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
                JButton removeAll = new JButton("Remove All");
                removeAll.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Object[] options = {"Yes", "No"};
                        int result = JOptionPane.showOptionDialog(null, "Confirm to remove all history data?", "Confirm",
                                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Yes");
                        if (result == JOptionPane.YES_OPTION) {
                            dbh.removeAll();
                            contentPanel.removeAll();
                            contentPanel.revalidate();
                            historyPanel.setVisible(false);
                            panel.setVisible(true);
                        }
                    }
                });
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(back);
                buttonPanel.add(removeAll);
                historyPanel.add(buttonPanel, BorderLayout.SOUTH);
                add(historyPanel, BorderLayout.CENTER);
                historyPanel.setVisible(true);
            }
        });
        panel.add(historyBtn, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.weighty = 0;
        c.insets = new Insets(5, 10, 5, 10);
        c.anchor = GridBagConstraints.CENTER;
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setString("0%");
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(progressBar, c);

        add(panel);

        setVisible(true);
    }

    class MySwingWorker extends SwingWorker<List<List<Integer>>, Integer> {
        @Override
        protected List<List<Integer>> doInBackground() {

            textArea.setText("Starting...Please wait...");
            progressBar.setValue(0);

            List<List<Integer>> result = new ArrayList<>();
            int m = Integer.parseInt(textField1.getText());
            int n = Integer.parseInt(textField2.getText());
            int k = Integer.parseInt(textField3.getText());
            int j = Integer.parseInt(textField4.getText());
            int s = Integer.parseInt(textField5.getText());

            long startTime = System.currentTimeMillis();
            List<Integer> chosenSamples = sh.generateChosenSamples(m, n);
            List<List<Integer>> possibleResults = sh.generatePossibleResults(chosenSamples, k);
            List<Set<Integer>> coverList = sh.generateCoverList(chosenSamples, j);

            double initSize = coverList.size();
            if (n <= 12) {
                while (!coverList.isEmpty()) {
                    List<Integer> candidateResult = sh.getCandidateResultSingleThread(possibleResults, coverList, s);
                    result.add(candidateResult);
                    sh.removeCoveredResults(candidateResult, coverList, s);
                    possibleResults.remove(candidateResult);
                    double percent = (1 - coverList.size() / initSize) * 100;
                    publish((int) percent);
                    setProgress((int) percent);
                }
            } else {
                while (!coverList.isEmpty()) {
                    List<Integer> candidateResult = sh.getCandidateResult(possibleResults, coverList, s);
                    result.add(candidateResult);
                    sh.removeCoveredResults(candidateResult, coverList, s);
                    possibleResults.remove(candidateResult);
                    double percent = (1 - coverList.size() / initSize) * 100;
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
            resultStr += "Reuslt Size: " + result.size() + "\n" + "\n"
                    + "Total time cost: " + (System.currentTimeMillis() - startTime) + "ms";
            textArea.setText(resultStr);

            dbh.save(m + "-" + n + "-" + k + "-" + j + "-" + s, resultStr);
            return result;
        }

        @Override
        protected void process(List<Integer> chunks) {
            int value = chunks.get(chunks.size() - 1);
            progressBar.setValue(value);
            progressBar.setString(value + "%");
        }

        @Override
        protected void done() {
            progressBar.setValue(100);
            progressBar.setString(100 + "%");
        }
    }

    public static void main(String[] args) {
        new Page();
    }
}