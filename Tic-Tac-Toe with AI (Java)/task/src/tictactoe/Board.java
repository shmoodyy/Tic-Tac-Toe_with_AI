package tictactoe;

public class Board {
    final String[][] grid = new String[3][3];
    final String firstPiece = "X ";
    final String secondPiece = "O ";

    public Board() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                grid[row][col] = "  ";
            }
        }
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col].equals("  ");
    }

    public void setPiece(int row, int col, String piece) {
        grid[row][col] = piece;
    }

    public void printGrid() {
        System.out.println("–––––––––");

        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("–––––––––");

    }
}