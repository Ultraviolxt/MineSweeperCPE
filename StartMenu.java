package MineSweeperGame;
import java.awt.*;
import javax.swing.*;

public class StartMenu extends MineSweeper {

    // เมธอดสำหรับแสดงเมนูเริ่มต้นของเกม
    public void showStartMenu() {
        // สร้างหน้าต่างเริ่มต้นของเกม
        JFrame startFrame = new JFrame("Minesweeper");
        startFrame.setSize(600, 500);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLocationRelativeTo(null);
        startFrame.setLayout(new BorderLayout(10, 10));
        startFrame.getContentPane().setBackground(Color.WHITE);

        // สร้างป้ายชื่อเกมที่ส่วนบนของหน้าต่าง
        JLabel titleLabel = new JLabel("Minesweeper", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(new Color(50, 50, 200));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        startFrame.add(titleLabel, BorderLayout.NORTH);

        // สร้างแผงกลางสำหรับการเลือกขนาดกระดาน
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        // ตั้งค่าข้อจำกัดของ GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // สร้างป้ายข้อความสำหรับเลือกขนาดกระดาน
        JLabel sizeLabel = new JLabel("Select Board Size:");
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(sizeLabel, gbc);

        // สร้างตัวเลือกขนาดกระดาน
        String[] sizes = {"8x8", "10x10", "12x12", "15x15"};
        ButtonGroup sizeGroup = new ButtonGroup();
        JRadioButton[] sizeButtons = new JRadioButton[sizes.length];

        // วนลูปสร้างปุ่มเลือกขนาดกระดานแต่ละขนาด
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

        // เพิ่มแผงกลางไปยังหน้าต่างเริ่มต้น
        startFrame.add(centerPanel, BorderLayout.CENTER);

        // สร้างปุ่มเล่นเกม
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 25));
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(new Color(100, 150, 200));
        playButton.setFocusPainted(false);
        playButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        // เพิ่มตัวฟังเหตุการณ์เมื่อคลิกปุ่มเล่นเกม
        playButton.addActionListener(e -> {
            for (int i = 0; i < sizes.length; i++) {
                if (sizeButtons[i].isSelected()) {
                    String[] dimensions = sizes[i].split("x");
                    setNumRows(Integer.parseInt(dimensions[1]));
                    setNumCols(Integer.parseInt(dimensions[0]));
                    startFrame.dispose();
                    initGame();
                    return;
                }
            }
            // แสดงข้อความเตือนหากยังไม่ได้เลือกขนาดกระดาน
            JOptionPane.showMessageDialog(startFrame, "Please select a board size before playing.");
        });

        // สร้างแผงล่างและเพิ่มปุ่มเล่นเกมเข้าไป
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        bottomPanel.add(playButton);

        // เพิ่มแผงล่างไปยังหน้าต่างเริ่มต้น
        startFrame.add(bottomPanel, BorderLayout.SOUTH);
        startFrame.setVisible(true); // แสดงหน้าต่างเริ่มต้น
    }
}