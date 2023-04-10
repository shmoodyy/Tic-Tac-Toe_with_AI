package tictactoe;

import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);

    public Game() {
        while (true) {
            Board board = new Board();
            User user = new User();
            AI ai = new AI();

            String[] menu = gameMenu();
            if (menu[0].equals("exit")) break;
            gameRound(this, board, user, ai, menu);

            System.out.println(someoneWon(board, board.firstPiece) ? board.firstPiece + "wins"
                    : someoneWon(board, board.secondPiece) ? board.secondPiece + "wins" : "Draw");
        }
    }

    public void gameRound(Game game, Board board, User user, AI ai, String[] menu) {
        board.printGrid();
        while (roundNotFinished(board) && !someoneWon(board, board.secondPiece)) {
            boolean errorFound;
            do {
                try {
                    if (menu[1].equals("user")) {
                        user.makeMove(board, board.firstPiece);
                    } else {
                        ai.makeMove(menu[2], game, board, board.firstPiece);
                    }
                    board.printGrid();
                    errorFound = false;
                } catch (NumberFormatException nFE) {
                    System.out.println("You should enter numbers!");
                    errorFound = true;
                }
            } while (errorFound);

            if (someoneWon(board, board.firstPiece) || !roundNotFinished(board)) {
                break;
            }
            if (menu[2].equals("user")) {
                user.makeMove(board, board.secondPiece);
            } else {
                ai.makeMove(menu[2], game, board, board.secondPiece);
            }
            board.printGrid();
        }
    }

    public String[] gameMenu() {
        String [] menuInput;
        while (true) {
            System.out.print("Input command: ");
            menuInput = scanner.nextLine().toLowerCase().split(" ");

            if (menuInput[0].equals("exit")) {
                break;
            } else if (menuInput.length == 3 && menuInput[0].equals("start")
                    && menuInput[1].matches("(user)|(easy)|(medium)|(hard)")
                    && menuInput[2].matches("(user)|(easy)|(medium)|(hard)")) {
                break;
            } else {
                System.out.println("Bad parameters!");
            }
        }
        return menuInput;
    }

    public boolean someoneWon(Board board, String piece) {
        boolean winnerFound = false;
        for (int i = 0; i < 3; i++) {
            if (board.grid[0][0].equals(piece) && board.grid[1][1].equals(piece) && board.grid[2][2].equals(piece)
                || board.grid[0][2].equals(piece) && board.grid[1][1].equals(piece) && board.grid[2][0].equals(piece)
                || board.grid[i][0].equals(piece) && board.grid[i][1].equals(piece) && board.grid[i][2].equals(piece)
                || board.grid[0][i].equals(piece) && board.grid[1][i].equals(piece) && board.grid[2][i].equals(piece)) {
                winnerFound = true;
                break;
            }
        }
        return winnerFound;
    }

    public boolean roundNotFinished(Board board) {
        boolean notFinished = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.grid[i][j].equals("  ")) {
                    notFinished = true;
                    break;
                }
            }
        }
        return notFinished;
    }

    //The following 2 functions were found in the link provided, and modified in the context of my
    // code to be used in the base case of my recursive minimax function implementation.
    public int evaluate(Board board) {
        // Check for a win or loss
        if (someoneWon(board, board.secondPiece)) {
            return 10;
        } else if (someoneWon(board, board.firstPiece)) {
            return -10;
        }

        // Check for a draw
        if (!roundNotFinished(board)) {
            return 0;
        }

        // Otherwise, calculate the heuristic score
        int score = 0;
        score += evaluateLine(board, 0, 0, 0, 1, 0, 2); // Row 0
        score += evaluateLine(board, 1, 0, 1, 1, 1, 2); // Row 1
        score += evaluateLine(board, 2, 0, 2, 1, 2, 2); // Row 2
        score += evaluateLine(board, 0, 0, 1, 0, 2, 0); // Column 0
        score += evaluateLine(board, 0, 1, 1, 1, 2, 1); // Column 1
        score += evaluateLine(board, 0, 2, 1, 2, 2, 2); // Column 2
        score += evaluateLine(board, 0, 0, 1, 1, 2, 2); // Diagonal
        score += evaluateLine(board, 0, 2, 1, 1, 2, 0); // Reverse diagonal

        return score;
    }

    private int evaluateLine(Board board, int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // Check for two in a row with a potential third
        if (board.grid[row1][col1].equals(board.secondPiece)) {
            score = 1;
        } else if (board.grid[row1][col1].equals(board.firstPiece)) {
            score = -1;
        }

        if (board.grid[row2][col2].equals(board.secondPiece)) {
            if (score == 1) {
                score = 10;
            } else if (score == -1) {
                return 0;
            } else {
                score = 1;
            }
        } else if (board.grid[row2][col2].equals(board.firstPiece)) {
            if (score == -1) {
                score = -10;
            } else if (score == 1) {
                return 0;
            } else {
                score = -1;
            }
        }

        if (board.grid[row3][col3].equals(board.secondPiece)) {
            if (score > 0) {
                score *= 10;
            } else if (score < 0) {
                return 0;
            } else {
                score = 1;
            }
        } else if (board.grid[row3][col3].equals(board.firstPiece)) {
            if (score < 0) {
                score *= 10;
            } else if (score > 1) {
                return 0;
            } else {
                score = 1;
            }
        }
        return score;
    }
}
