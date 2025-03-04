package MineSweeper;

public class MineSweeperTest {
    @Test
    public void testBoardSizeSelection() {
        MineSweeper game = new MineSweeper();

        game.numRows = 8;
        game.numCols = 8;
        assertEquals(8, game.numRows);
                assertEquals(8, game.numCols);
        
                game.numRows = 15;
                game.numCols = 15;
                assertEquals(15, game.numRows);
                assertEquals(15, game.numCols);
            }
        
            private void assertEquals(int i, int numRows) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
            }
}

