package MineSweeperGame1;

// คลาสสำหรับทดสอบฟังก์ชันการทำงานของ MineSweeper
public class MineSweeperTest {

    @Test // กำหนดให้เป็นเมธอดที่ใช้สำหรับทดสอบ
    
    public void testBoardSizeSelection() {
        MineSweeper game = new MineSweeper();

        // ทดสอบการตั้งค่าขนาดบอร์ดเป็น 8x8
        game.setNumRows(8);
        game.setNumCols(8);
        assertEquals(8, game.getNumRows()); // ตรวจสอบว่าจำนวนแถวถูกตั้งค่าตามที่กำหนด
        assertEquals(8, game.getNumCols()); // ตรวจสอบว่าจำนวนคอลัมน์ถูกตั้งค่าตามที่กำหนด
        
        // ทดสอบการตั้งค่าขนาดบอร์ดเป็น 15x15
        game.setNumRows(15);
        game.setNumCols(15);
        assertEquals(15, game.getNumRows()); // ตรวจสอบว่าจำนวนแถวถูกตั้งค่าตามที่กำหนด
        assertEquals(15, game.getNumCols()); // ตรวจสอบว่าจำนวนคอลัมน์ถูกตั้งค่าตามที่กำหนด
    }

    // เมธอดช่วยในการตรวจสอบค่า ว่าตรงกับที่คาดหวังหรือไม่
    /**
     * 
     * @param expected ค่าที่คาดหวัง
     * @param actual ค่าที่ได้รับจากการทดสอบ
     */
    private void assertEquals(int expected, int actual) {
        throw new AssertionError("Expected: " + expected + " Got: " + actual);
    }
}