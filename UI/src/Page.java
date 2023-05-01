import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;
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
    private JLabel progressLabel;
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

        JPanel panel = new JPanel(new GridBagLayout()){
            // Override the paintComponent() method to draw a gradient background
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0,new Color(61, 145, 64),
                        getWidth(), getHeight(),  new Color(255, 255, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.insets = new Insets(10, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        label1 = new JLabel("m: 45 ≤m≤ 54");
        label1.setFont(new Font("Arial", Font.BOLD, 24));
        label1.setForeground(Color.WHITE);
        panel.add(label1, c);

        c.gridx = 1;
        c.gridy = 0;
        textField1 = new JTextField();
        textField1.setBackground(Color.WHITE);
        textField1.setForeground(Color.DARK_GRAY);
        textField1.setFont(new Font("Arial", Font.PLAIN, 14));
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        textField1.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(textField1, c);

        c.gridx = 0;
        c.gridy = 1;
        label2 = new JLabel("n:   7  ≤n≤  25 ");
        label2.setFont(new Font("Arial", Font.BOLD, 24));
        label2.setForeground(Color.WHITE);
        panel.add(label2, c);

        c.gridx = 1;
        c.gridy = 1;
        textField2 = new JTextField();
        textField2.setBackground(Color.WHITE);
        textField2.setForeground(Color.DARK_GRAY);
        textField2.setFont(new Font("Arial", Font.PLAIN, 14));
//        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        textField2.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(textField2, c);

        c.gridx = 0;
        c.gridy = 2;
        label3 = new JLabel("k:   4   ≤k≤   7");
        label3.setFont(new Font("Arial", Font.BOLD, 24));
        label3.setForeground(Color.WHITE);
        panel.add(label3, c);

        c.gridx = 1;
        c.gridy = 2;
        textField3 = new JTextField();
        textField3.setBackground(Color.WHITE);
        textField3.setForeground(Color.DARK_GRAY);
        textField3.setFont(new Font("Arial", Font.PLAIN, 14));
//        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        textField3.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(textField3, c);

        c.gridx = 0;
        c.gridy = 3;
        label4 = new JLabel("j:    3   ≤j≤    k");
        label4.setFont(new Font("Arial", Font.BOLD, 24));
        label4.setForeground(Color.WHITE);
        panel.add(label4, c);

        c.gridx = 1;
        c.gridy = 3;
        textField4 = new JTextField();
        textField4.setBackground(Color.WHITE);
        textField4.setForeground(Color.DARK_GRAY);
        textField4.setFont(new Font("Arial", Font.PLAIN, 14));
//        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        textField4.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(textField4, c);

        c.gridx = 0;
        c.gridy = 4;
        label5 = new JLabel("s:   3   ≤s≤    j");
        label5.setFont(new Font("Arial", Font.BOLD, 24));
        label5.setForeground(Color.WHITE);
        panel.add(label5, c);

        c.gridx = 1;
        c.gridy = 4;
        textField5 = new JTextField();
        textField5.setBackground(Color.WHITE);
        textField5.setForeground(Color.DARK_GRAY);
        textField5.setFont(new Font("Arial", Font.PLAIN, 14));
//        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        textField5.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panel.add(textField5, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.weighty = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(10, 10, 10, 10);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JScrollPane(textArea), c);

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 0, 10);
        startEndBtn = new JButton("Start/End"){
            {
                // Set the button's appearance and behavior
                setContentAreaFilled(false);
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                setForeground(Color.WHITE);
                setFont(new Font("Arial", Font.BOLD, 14));
                setFocusPainted(false);
            }
            // Override the paintComponent() method to draw a rounded green gradient background
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = 80;
                int height = getHeight();
                GradientPaint gradient = new GradientPaint(0, 0, new Color(75, 186, 105),
                        0, height, new Color(0, 129, 69));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, width, height, height, height);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
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

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10, 10, 0, 10);
        historyBtn = new JButton("History"){
            {
                // Set the button's appearance and behavior
                setContentAreaFilled(false);
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                setForeground(Color.WHITE);
                setFont(new Font("Arial", Font.BOLD, 14));
                setFocusPainted(false);
            }

            // Override the paintComponent() method to draw a rounded sky blue gradient background
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = 70;
                int height = getHeight();
                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250),
                        0, height, new Color(0, 191, 255));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, width, height, height, height);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
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
                gbc.fill = GridBagConstraints.VERTICAL;
                gbc.weightx = 1.0;
                gbc.weighty = 0.0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridx = 0;
                gbc.gridy = 0;
                for (String file : files) {
                    JPanel rowPanel = new JPanel();
                    rowPanel.setLayout(new GridLayout(1, 3, 10, 10));
                    JLabel fileLabel = new JLabel(file);
                    rowPanel.add(fileLabel);
                    JButton check = new JButton("Detail"){
                        {
                            // Set the button's appearance and behavior
                            setContentAreaFilled(false);
                            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                            setForeground(Color.WHITE);
                            setFont(new Font("Arial", Font.BOLD, 14));
                            setFocusPainted(false);
                        }

                        // Override the paintComponent() method to draw a rounded sky blue gradient background
                        @Override
                        protected void paintComponent(Graphics g) {
                            Graphics2D g2d = (Graphics2D) g.create();
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            int width = 90;
                            int height = getHeight();
                            GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250),
                                    0, height, new Color(0, 191, 255));
                            g2d.setPaint(gradient);
                            g2d.fillRoundRect(0, 0, width, height, height, height);
                            g2d.dispose();
                            super.paintComponent(g);
                        }
                    };
                    check.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String data = dbh.load(file);
                            UIManager.put("OptionPane.okButtonText", "OK");
                            JOptionPane.showMessageDialog(null, data, "History Result", JOptionPane.PLAIN_MESSAGE);
                        }
                    });
                    rowPanel.add(check);
                    JButton remove = new JButton("Remove"){
                        {
                            // Set the button's appearance and behavior
                            setContentAreaFilled(false);
                            setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
                            setForeground(Color.WHITE);
                            setFont(new Font("Arial", Font.BOLD, 14));
                            setFocusPainted(false);
                        }

                        // Override the paintComponent() method to draw a rounded sky blue gradient background
                        @Override
                        protected void paintComponent(Graphics g) {
                            Graphics2D g2d = (Graphics2D) g.create();
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            int width = 90;
                            int height = getHeight();
                            GradientPaint gradient = new GradientPaint(0, 0, new Color(227,23,13),
                                    0, height, new Color(255, 100, 0));
                            g2d.setPaint(gradient);
                            g2d.fillRoundRect(0, 0, width, height, height, height);
                            g2d.dispose();
                            super.paintComponent(g);
                        }
                    };
                    remove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Object[] options = {"Yes", "No"};
                            int result = JOptionPane.showOptionDialog(null, "Confirm to remove this history data?", "Confirm",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Yes");
                            if (result == JOptionPane.YES_OPTION) {
                                dbh.remove(file);
                                historyPanel.setVisible(false);
                                panel.setVisible(true);
                                historyBtn.doClick();
                            }
                        }
                    });
                    rowPanel.add(remove);
                    gbc.gridy++;
                    contentPanel.add(rowPanel, gbc);
                }
                if (files.size() == 0) {
                    JLabel emptyLabel = new JLabel("No history data!", SwingConstants.CENTER);
                    emptyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    gbc.gridy++;
                    contentPanel.add(emptyLabel, gbc);
                }
                scrollPanel.setViewportView(contentPanel);
                historyPanel.add(scrollPanel, BorderLayout.CENTER);
                JButton back = new JButton("Back");
//                    {
//                        // Set the button's appearance and behavior
//                        setContentAreaFilled(true);
//                        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
//                        setForeground(Color.black);
//                        setFont(new Font("Arial", Font.BOLD, 14));
//                        setFocusPainted(false);
//                    }
//
//                    // Override the paintComponent() method to draw a rounded sky blue gradient background
//                    @Override
//                    protected void paintComponent(Graphics g) {
//                        Graphics2D g2d = (Graphics2D) g.create();
//                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                        int width = 100;
//                        int height = getHeight();
//                        GradientPaint gradient = new GradientPaint(0, 0, new Color(128,118,105),
//                                0, height, new Color(118,128,105));
//                        g2d.setPaint(gradient);
//                        g2d.fillRoundRect(0, 0, width, height, height, height);
//                        g2d.dispose();
//                        super.paintComponent(g);
//                    }
//                };
                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        historyPanel.setVisible(false);
                        panel.setVisible(true);
                    }
                });
                JButton removeAll = new JButton("Remove All");
//                    {
//                        // Set the button's appearance and behavior
//                        setContentAreaFilled(true);
//                        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
//                        setForeground(Color.black);
//                        setFont(new Font("Arial", Font.BOLD, 14));
//                        setFocusPainted(false);
//                    }
//
//                    // Override the paintComponent() method to draw a rounded sky blue gradient background
//                    @Override
//                    protected void paintComponent(Graphics g) {
//                        Graphics2D g2d = (Graphics2D) g.create();
//                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                        int width = 100;
//                        int height = getHeight();
//                        GradientPaint gradient = new GradientPaint(0, 0, new Color(227,23,13),
//                                0, height, new Color(255, 100, 0));
//                        g2d.setPaint(gradient);
//                        g2d.fillRoundRect(0, 0, width, height, height, height);
//                        g2d.dispose();
//                        super.paintComponent(g);
//                    }
//                };
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

        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 10, 10, 10);
//        progressLabel = new JLabel(" ", JLabel.CENTER);
//        panel.add(progressLabel, c);

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 10, 10, 10);
        progressBar = new JProgressBar(){
            {
                // Set the progress bar's appearance and behavior
                setStringPainted(true);
                setForeground(new Color(34,139,34));
                setFont(new Font("Arial", Font.BOLD, 16));
            }

            // Override the getPreferredSize() method to set the progress bar's preferred size
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(750, 30);
            }
        };
//        progressBar.setSize(700, 20);
//        progressBar.setLocation((panel.getWidth() - progressBar.getWidth()) / 2, (panel.getHeight() - progressBar.getHeight()) / 2);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        panel.add(progressBar,c);

        add(panel);

        setVisible(true);
    }
    class MySwingWorker extends SwingWorker<List<List<Integer>>, Integer> {
        @Override
        protected List<List<Integer>> doInBackground() {

            textArea.setText("Starting...Please wait...");
//            progressLabel.setText("Progress: 0%");
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
            resultStr += "Reuslt Size: " + result.size() + "\n"+"\n"
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