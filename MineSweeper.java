package FinalGame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class MineSweeper extends MainMethod {

    // กำหนดค่าพื้นฐานสำหรับขนาดของช่องระเบิด
    private int tileSize = 70; // ขนาดของช่องระเบิด
    
    // กำหนดค่าพื้นฐานสำหรับขนาดของกระดานเกม
    private int numRows = 10; // จำนวนแถวของกระดาน
    private int numCols = numRows; // จำนวนคอลัมน์ของกระดา
    private int boardWidth = numCols * tileSize; // ความกว้างของกระดาน
    private int boardHeight = numRows * tileSize; // ความสูงของกระดาน

    // จำนวนระเบิดในกระดาน
    private int mineCount; 
    
    // ตัวแปรที่ใช้เก็บข้อมูลของกระดานและระเบิด
    private MineTile[][] board; // กระดานเกมแบบ 2 มิติ
    private ArrayList<MineTile> mineList; // รายการของช่องที่มีระเบิด
    private Random random = new Random(); // ใช้สุ่มตำแหน่งระเบิด
    
    // ตัวแปรสำหรับองค์ประกอบ GUI
    private JFrame frame; // หน้าต่างของเกม
    private JLabel textLabel; // ป้ายข้อความแสดงสถานะเกม
    private JPanel textPanel, boardPanel; // แผงแสดงผลข้อความและกระดานเกม
    private JButton replayButton; // ปุ่มสำหรับเริ่มเกมใหม่

    // ตัวแปรสถานะของเกม
    private int tilesClicked; // จำนวนช่องที่ถูกเปิด
    private boolean gameOver; // ตัวแปรเก็บสถานะว่าเกมจบแล้วหรือไม่

    @Override
    public void initGame() {
        // คำนวณจำนวนลูกระเบิดให้เป็น 15% ของจำนวนช่องทั้งหมด
        mineCount = (int) Math.round(numRows * numCols * 0.15);
        System.out.println("numRow: " + numRows + " numCol: " + numCols);
        System.out.println("Number of mines: " + mineCount); // เพิ่มการแสดงผลจำนวนระเบิด
    
        // กำหนดขนาดฟอนต์ให้ลดลงเมื่อจำนวนแถวมากขึ้น
        int fontSize = Math.max(10, 50 - numRows);
    
        // ตั้งค่าหน้าต่างของเกม
        frame = new JFrame("Minesweeper");
        frame.setSize(boardWidth, boardHeight + 50);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        // แสดงจำนวนระเบิดที่เหลือ
        textLabel = new JLabel("Minesweeper: " + mineCount, JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setOpaque(true);
        
        // สร้างแผงสำหรับข้อความ และเพิ่มป้ายข้อความเข้าไป
        textPanel = new JPanel(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        frame.add(textPanel, BorderLayout.NORTH);
    
        // สร้างตารางของเกมโดยใช้ GridLayout
        boardPanel = new JPanel(new GridLayout(numRows, numCols));
        frame.add(boardPanel);
        
        // สร้างปุ่มเริ่มเกมใหม่
        replayButton = new JButton("Replay");
        replayButton.setFont(new Font("Arial", Font.BOLD, 16));
        replayButton.setVisible(false);
        replayButton.addActionListener(e -> {
            frame.dispose();
            initGame(); // เริ่มเกมใหม่
        });
        textPanel.add(replayButton, BorderLayout.EAST);
    
        // สร้างบอร์ดเกมและเก็บตำแหน่งของระเบิด
        board = new MineTile[numRows][numCols];
        mineList = new ArrayList<>();
        tilesClicked = 0;
        gameOver = false;
        
        // วนลูปสร้างช่อง MineTile ให้ครบทุกช่องบนกระดาน
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, fontSize));

                // เพิ่มการตรวจจับการคลิกของผู้เล่น
                tile.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) return;
                        MineTile tile = (MineTile) e.getSource();
                        
                        if (e.getButton() == MouseEvent.BUTTON1) { // คลิกซ้าย
                            if (tile.getText().equals("")) {
                                if (mineList.contains(tile)) {
                                    revealMines(); // ถ้าคลิกโดนระเบิด
                                } else {
                                    checkMine(tile.getRow(), tile.getColumn()); // ตรวจสอบพื้นที่
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) { // คลิกขวา
                            if (tile.getText().equals("") && tile.isEnabled()) {
                                tile.setText("🚩"); // ตั้งธง
                            }
                            else if (tile.getText().equals("🚩")) {
                                tile.setText(""); // เอาธงออก
                            }
                        }
                    }
                });

                boardPanel.add(tile);
            }
        }
        
        frame.setVisible(true);
        setMines(); // สุ่มตำแหน่งระเบิด
    }

    @Override
    public void setMines() {
        int mineLeft = mineCount;
        while (mineLeft > 0) {
            int r = random.nextInt(numRows);
            int c = random.nextInt(numCols);
            MineTile tile = board[r][c];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft--;
            }
        }
    }

    @Override
    public void revealMines() {
        // แสดงตำแหน่งของระเบิดทั้งหมด
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }
        
        gameOver = true;
        textLabel.setText("Game Over!"); // แจ้งว่าเกมจบแล้ว
        replayButton.setVisible(true); // แสดงปุ่มเล่นใหม่
    }

    @Override
    public void checkMine(int r, int c) {
        // ตรวจสอบว่าอยู่ในขอบเขตของกระดานหรือไม่
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) return;
        
        MineTile tile = board[r][c];
        if (!tile.isEnabled()) return;
        tile.setEnabled(false);
        tilesClicked++;

        // ตรวจสอบจำนวนระเบิดรอบๆ ช่องนี้
        int minesFound = countMinesAround(r, c);
        if (minesFound > 0) {
            tile.setText(String.valueOf(minesFound));
        } else {
            tile.setText(""); // ถ้าไม่มีระเบิดรอบข้างให้เปิดช่องว่างต่อไป
            spreadClear(r, c);
        }
        
        // เช็กว่าผู้เล่นชนะเกมหรือไม่
        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true;
            textLabel.setText("Mines Cleared!"); // แจ้งว่าชนะเกมแล้ว
            replayButton.setVisible(true);
        }
    }
    
    // ตรวจสอบจำนวนระเบิดรอบๆ ช่องที่ระบุ
    private int countMinesAround(int r, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (r + i >= 0 && r + i < numRows && c + j >= 0 && c + j < numCols) {
                    if (mineList.contains(board[r + i][c + j])) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public void spreadClear(int r, int c) {
        // เปิดช่องว่างรอบข้างหากไม่มีระเบิด
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                checkMine(r + i, c + j);
            }
        }
    }

    // Getter สำหรับจำนวนแถว
    public int getNumRows() {
        return numRows;
    }

    // Getter สำหรับจำนวนคอลัมน์
    public int getNumCols() {
        return numCols;
    }

    // Setter สำหรับจำนวนแถว
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    // Setter สำหรับจำนวนคอลัมน์
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }
    
    // Getter สำหรับรายการของช่องที่มีระเบิด
    public int getMineCount() {
        return mineCount;
    }
}