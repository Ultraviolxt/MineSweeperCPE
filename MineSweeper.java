package MineSweeper;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


public class MineSweeper extends OnScreen{

    @Override
    public void initGame() {
        // คำนวณจำนวนลูกระเบิดเป็น 15% ของจำนวนช่อง
        mineCount = (int) Math.round(numRows * numCols * 0.15);
    
        // กำหนดขนาดฟอนต์ตามจำนวนแถวและคอลัมน์
        int fontSize = Math.max(10, 50 - numRows); // ลดขนาดฟอนต์เมื่อ numRows มากขึ้น
    
        frame = new JFrame("Minesweeper");
        frame.setSize(boardWidth, boardHeight + 50);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        textLabel = new JLabel("Minesweeper: " + mineCount, JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setOpaque(true);
        
        textPanel = new JPanel(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        frame.add(textPanel, BorderLayout.NORTH);
    
        boardPanel = new JPanel(new GridLayout(numRows, numCols));
        frame.add(boardPanel);
        
        replayButton = new JButton("Replay");
        replayButton.setFont(new Font("Arial", Font.BOLD, 16));
        replayButton.setVisible(false);
        replayButton.addActionListener(e -> {
            frame.dispose();
            initGame();
        });
        textPanel.add(replayButton, BorderLayout.EAST);
    
        board = new MineTile[numRows][numCols];
        mineList = new ArrayList<>();
        tilesClicked = 0;
        gameOver = false;
        
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, fontSize));
                tile.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) return;
                        MineTile tile = (MineTile) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText().equals("")) {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                } else {
                                    checkMine(tile.getRow(), tile.getColumn());
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText() == "" && tile.isEnabled()) {
                                tile.setText("🚩");
                            }
                            else if (tile.getText() == "🚩") {
                                tile.setText("");
                            }
                        }
                    }
                });
                boardPanel.add(tile);
            }
        }
        
        frame.setVisible(true);
        setMines();
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
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }
        gameOver = true;
        textLabel.setText("Game Over!");
        replayButton.setVisible(true);
    }
    @Override
    public void checkMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) return;
        MineTile tile = board[r][c];
        if (!tile.isEnabled()) return;
        tile.setEnabled(false);
        tilesClicked++;

        int minesFound = countMinesAround(r, c);
        if (minesFound > 0) {
            tile.setText(String.valueOf(minesFound));
        } else {
            tile.setText("");
            spreadClear(r, c);
        }
        
        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true;
            textLabel.setText("Mines Cleared!");
            replayButton.setVisible(true);
        }
    }

    public int countMinesAround(int r, int c) {
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
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                checkMine(r + i, c + j);
            }
        }
    }
}