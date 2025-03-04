package MineSweeper;

// คลาสสำหรับทดสอบฟังก์ชันการทำงานของ MineSweeper
public class MineSweeperTest {

    @Test // กำหนดให้เป็นเมธอดที่ใช้สำหรับทดสอบ
    public void testBoardSizeSelection() {
        MineSweeper game = new MineSweeper();

        // ทดสอบการตั้งค่าขนาดบอร์ดเป็น 8x8
        game.numRows = 8;
        game.numCols = 8;
        assertEquals(8, game.numRows); // ตรวจสอบว่าจำนวนแถวถูกตั้งค่าตามที่กำหนด
        assertEquals(8, game.numCols); // ตรวจสอบว่าจำนวนคอลัมน์ถูกตั้งค่าตามที่กำหนด
        
        // ทดสอบการตั้งค่าขนาดบอร์ดเป็น 15x15
        game.numRows = 15;
        game.numCols = 15;
        assertEquals(15, game.numRows); // ตรวจสอบว่าจำนวนแถวถูกตั้งค่าตามที่กำหนด
        assertEquals(15, game.numCols); // ตรวจสอบว่าจำนวนคอลัมน์ถูกตั้งค่าตามที่กำหนด
    }

    // เมธอดช่วยในการตรวจสอบค่า ว่าตรงกับที่คาดหวังหรือไม่
    private void assertEquals(int expected, int actual) {
        throw new AssertionError("คาดหวังค่า: " + expected + " แต่ได้ค่า: " + actual);
    }
}