package tictactoe;

import java.util.Random;

public class AI {

    Random random = new Random();

    public void makeMove(String difficulty, Game game, Board board, String piece) throws NumberFormatException {
        int[] cellAI = new int[]{(random.nextInt(3)), (random.nextInt(3))};
        switch (difficulty) {
            case "easy" -> {
                if (board.isCellEmpty(cellAI[0], cellAI[1])) {
                    System.out.println("Making move level \"easy\"");
                    board.setPiece(cellAI[0], cellAI[1], piece);
                } else {
                    makeMove(difficulty, game, board, piece);
                }
            } case "medium" -> {
                int[] targetLocked = findTarget(board);
                if (board.isCellEmpty(targetLocked[0], targetLocked[1])) {
                    System.out.println("Making move level \"medium\"");
                    board.setPiece(targetLocked[0], targetLocked[1], piece);
                } else {
                    makeMove(difficulty, game, board, piece);
                }
            } case "hard" -> {
                System.out.println("Making move level \"hard\"");
                int bestScore = Integer.MIN_VALUE;
                int bestRow = -1;
                int bestCol = -1;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board.grid[i][j].equals("  ")) {
                            board.grid[i][j] = piece;
                            board.setPiece(i, j, piece);
                            int score = minimax(game, board, 0, false, piece);
                            board.setPiece(i, j, "  ");
                            board.grid[i][j] = "  ";
                            if (score > bestScore) {
                                bestScore = score;
                                bestRow = i;
                                bestCol = j;
                            }
                        }
                    }
                }
                board.grid[bestRow][bestCol] = piece;
            }
        }
    }

    public int minimax(Game game, Board board, int depth, boolean isMaximizingPlayer, String piece) {
        // Check if the game has ended or reached maximum depth
        String enemyPiece = piece.equals("X ") ? "O " : "X ";
        if (depth == 0 || !game.roundNotFinished(board)) return game.evaluate(board);


        if (isMaximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isCellEmpty(i , j)) {
                        board.setPiece(i, j, piece);
                        int value = minimax(game, board, depth + 1, true, piece);
                        board.setPiece(i, j, "  ");
                        bestValue = Math.max(value, bestValue);
                    }
                }
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board.isCellEmpty(i, j)) {
                        board.setPiece(i, j, enemyPiece);
                        int value = minimax(game, board, depth + 1, false, piece);
                        board.setPiece(i, j, "  ");
                        bestValue = Math.min(value, bestValue);
                    }
                }
            }
            return bestValue;
        }
    }

    public int[] findTarget (Board board) {
        int[] randomTarget = new int[]{(random.nextInt(3)), (random.nextInt(3))};
            for (int i = 0; i < 3; i++) {
                if ((board.grid[0][0].equals("X ") && board.grid[1][1].equals("X ")
                        || board.grid[0][0].equals("O ") && board.grid[1][1].equals("O "))
                        && (board.isCellEmpty(2, 2))) {
                    return new int[]{2, 2};
                } //
                if ((board.grid[0][0].equals("X ") && board.grid[2][2].equals("X ")
                        || board.grid[0][0].equals("O ") && board.grid[2][2].equals("O "))
                        && (board.isCellEmpty(1, 1))) {
                    return new int[]{1, 1};
                }
                if ((board.grid[2][2].equals("X ") && board.grid[1][1].equals("X ")
                        || board.grid[2][2].equals("O ") && board.grid[1][1].equals("O "))
                        && (board.isCellEmpty(0, 0))) {
                    return new int[]{0, 0};
                }
                if ((board.grid[0][2].equals("X ") && board.grid[1][1].equals("X ")
                        || board.grid[0][2].equals("O ") && board.grid[1][1].equals("O "))
                        && (board.isCellEmpty(2, 0))) {
                    return new int[]{2, 0};
                } //
                if ((board.grid[0][2].equals("X ") && board.grid[2][0].equals("X ")
                        || board.grid[0][2].equals("O ") && board.grid[2][0].equals("O "))
                        && (board.isCellEmpty(1, 1))) {
                    return new int[]{1, 1};
                }
                if ((board.grid[1][1].equals("X ") && board.grid[2][0].equals("X ")
                        || board.grid[1][1].equals("O ") && board.grid[2][0].equals("O "))
                        && (board.isCellEmpty(0, 2))) {
                    return new int[]{0, 2};
                }
                if ((board.grid[i][0].equals("X ") && board.grid[i][1].equals("X ")
                        || board.grid[i][0].equals("O ") && board.grid[i][1].equals("O "))
                        && (board.isCellEmpty(i, 2))) {
                    return new int[]{i, 2};
                } //
                if ((board.grid[i][0].equals("X ") && board.grid[i][2].equals("X ")
                        || board.grid[i][0].equals("O ") && board.grid[i][2].equals("O "))
                        && (board.isCellEmpty(i, 1))) {
                    return new int[]{i, 1};
                }
                if ((board.grid[i][1].equals("X ") && board.grid[i][2].equals("X ")
                        || board.grid[i][1].equals("O ") && board.grid[i][2].equals("O "))
                        && (board.isCellEmpty(i, 0))) {
                    return new int[]{i, 0};
                }
                if ((board.grid[0][i].equals("X ") && board.grid[1][i].equals("X ")
                        || board.grid[0][i].equals("O ") && board.grid[1][i].equals("O "))
                        && (board.isCellEmpty(2, i))) {
                    return new int[]{2, i};
                } //
                if ((board.grid[0][i].equals("X ") && board.grid[2][i].equals("X ")
                        || board.grid[0][i].equals("O ") && board.grid[2][i].equals("O "))
                        && (board.isCellEmpty(1, i))) {
                    return new int[]{1, i};
                }
                if ((board.grid[1][i].equals("X ") && board.grid[2][i].equals("X ")
                        || board.grid[1][i].equals("O ") && board.grid[2][i].equals("O "))
                        && (board.isCellEmpty(0, i))) {
                    return new int[]{0, i};
                }
            }
        return randomTarget;
    }
}