package MineSweeper;
import java.awt.*;
import javax.swing.*;

public class StartMenu extends MineSweeper {
    public void showStartMenu() {
        JFrame startFrame = new JFrame("Minesweeper");
        startFrame.setSize(600, 500);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLocationRelativeTo(null);
        startFrame.setLayout(new BorderLayout(10, 10));
        startFrame.getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Minesweeper", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(new Color(50, 50, 200));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        startFrame.add(titleLabel, BorderLayout.NORTH);

        // Center Panel for Size Selection
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel sizeLabel = new JLabel("Select Board Size:");
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(sizeLabel, gbc);

        String[] sizes = {"8x8", "10x10", "12x12", "15x15"};
        ButtonGroup sizeGroup = new ButtonGroup();
        JRadioButton[] sizeButtons = new JRadioButton[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            sizeButtons[i] = new JRadioButton(sizes[i]);
            sizeButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            sizeButtons[i].setBackground(Color.WHITE);
            sizeGroup.add(sizeButtons[i]);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.gridwidth = 2;
            centerPanel.add(sizeButtons[i], gbc);
        }

        startFrame.add(centerPanel, BorderLayout.CENTER);

        // Play Button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 25));
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(new Color(100, 150, 200));
        playButton.setFocusPainted(false);
        playButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        playButton.addActionListener(e -> {
            for (int i = 0; i < sizes.length; i++) {
                if (sizeButtons[i].isSelected()) {
                    String[] dimensions = sizes[i].split("x");
                    numRows = Integer.parseInt(dimensions[0]);
                    numCols = Integer.parseInt(dimensions[1]);
                    startFrame.dispose();
                    initGame();
                    return;
                }
            }
            JOptionPane.showMessageDialog(startFrame, "Please select a board size before playing.");
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        bottomPanel.add(playButton);

        startFrame.add(bottomPanel, BorderLayout.SOUTH);
        startFrame.setVisible(true);
    }
}
