package FinalGame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MineSweeperTest {

    private MineSweeper game;

    // ตั้งค่าสำหรับการทดสอบแต่ละครั้ง
    @BeforeEach
    public void setUp() {
        game = new MineSweeper();
    }

    // ทดสอบการตั้งค่าขนาดบอร์ด
    @Test
    public void testBoardSizeSelection() {
        // ทดสอบการตั้งค่าขนาดบอร์ดเป็น 8x8
        game.setNumRows(8);
        game.setNumCols(8);
        assertEquals(8, game.getNumRows(), "Number of rows should be 8");
        assertEquals(8, game.getNumCols(), "Number of columns should be 8");

        // ทดสอบการตั้งค่าขนาดบอร์ดเป็น 15x15
        game.setNumRows(15);
        game.setNumCols(15);
        assertEquals(15, game.getNumRows(), "Number of rows should be 15");
        assertEquals(15, game.getNumCols(), "Number of columns should be 15");
    }

    // ทดสอบจำนวนระเบิดที่ตั้งค่าในเกม
    @Test
    public void testMineCount() {
        game.setNumRows(10);
        game.setNumCols(10);
        game.initGame();
        int expectedMineCount = (int) Math.round(10 * 10 * 0.15);
        assertEquals(expectedMineCount, game.getMineCount(), "The mine count should be " + expectedMineCount);
    }
}