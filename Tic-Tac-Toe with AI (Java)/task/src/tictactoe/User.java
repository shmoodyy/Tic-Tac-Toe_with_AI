package tictactoe;

import java.util.Scanner;

public class User {
    Scanner scanner = new Scanner(System.in);

    public void makeMove(Board board, String piece) throws NumberFormatException {
        System.out.print("Enter the coordinates: ");
        String inputCoordinates = scanner.nextLine();
        String[] cell = inputCoordinates.split(" ");
        int cellRow = Integer.parseInt(cell[0]);
        int cellColumn = Integer.parseInt(cell[1]);

        if (cellRow >= 1 && cellColumn >= 1 && cellRow <= 3 && cellColumn <= 3) {
            if (board.isCellEmpty(cellRow - 1, cellColumn - 1)) {
                board.setPiece(cellRow - 1, cellColumn - 1, piece);
            } else {
                System.out.println("This cell is occupied! Choose another one!");
                makeMove(board, piece);
            }
        } else {
            System.out.println("Coordinates should be from 1 to 3!");
            makeMove(board, piece);
        }
    }
}