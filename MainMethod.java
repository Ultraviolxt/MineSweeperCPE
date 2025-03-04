package MineSweeperGame;

public abstract class MainMethod {
    // เมธอด abstract ที่ต้องให้คลาสลูก Override เอง
    public abstract void initGame(); // เมธอดสำหรับเริ่มเกม
    public abstract void setMines(); // เมธอดสำหรับวางระเบิด
    public abstract void revealMines(); // เมธอดสำหรับแสดงตำแหน่งระเบิดเมื่อเกมจบ
    public abstract void checkMine(int r, int c); // เมธอดสำหรับตรวจสอบว่าช่องที่คลิกมีระเบิดหรือไม่
    public abstract void spreadClear(int r, int c); // เมธอดสำหรับเปิดช่องที่ไม่มีระเบิดโดยอัตโนมัติ
}
