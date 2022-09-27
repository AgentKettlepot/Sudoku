package Genetics_1;

public class Individual implements Comparable<Individual> {

    static int[][] board;
    static int score;

    public Individual(int[][] puz, int sc) {
        board = puz;
        score = sc;
    }

    public void solve() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 0) {
                    int random = (int) (Math.random() * 9 + 1);
                    board[r][c] = random;
                }
            }
        }
    }

    public int Fitness_Score(int[][] board) { // fitness score is number wrong / total number
        int counter = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                int[] pos = { r, c };
                if (Check(board, pos, board[r][c]) == false) {
                    counter++;
                }
            }
        }
        return (int) ((double) counter / (double) (board.length * board[0].length) * 10000);
    }

    public int getScore() {
        return score;
    }

    public int[][] getBoard() {
        return board;
    }

    public static boolean Check(int[][] board, int[] position, int possible_num) {
        // Check columns
        for (int r = 0; r < board.length; r++) {
            if (board[r][position[1]] == possible_num && r != position[0]) { // there is a repeat
                return false;
            }
        }
        // Check rows
        for (int c = 0; c < board.length; c++) {
            if (board[position[0]][c] == possible_num && c != position[1]) { // there is a repeat
                return false;
            }
        }
        // Check box
        if (Check_Box_Helper(board, position, possible_num) == false)
            return false;

        return true; // number is valid
    }

    public static boolean Check_Box_Helper(int[][] board, int[] position, int possible_num) {
        int row = position[0];
        int col = position[1];
        int[] mini_box_row = new int[3];
        int[] mini_box_column = new int[3];

        if (row <= 2) { // FINDING ROW RANGE FOR MINI BOX
            mini_box_row[0] = 0;
            mini_box_row[1] = 1;
            mini_box_row[2] = 2;
        } else if (row <= 5) {
            mini_box_row[0] = 3;
            mini_box_row[1] = 4;
            mini_box_row[2] = 5;
        } else {
            mini_box_row[0] = 6;
            mini_box_row[1] = 7;
            mini_box_row[2] = 8;
        }

        if (col <= 2) { // FINDING COL RANGE FOR MINI BOX
            mini_box_column[0] = 0;
            mini_box_column[1] = 1;
            mini_box_column[2] = 2;
        } else if (col <= 5) {
            mini_box_column[0] = 3;
            mini_box_column[1] = 4;
            mini_box_column[2] = 5;
        } else {
            mini_box_column[0] = 6;
            mini_box_column[1] = 7;
            mini_box_column[2] = 8;
        }

        for (int r : mini_box_row) {
            for (int c : mini_box_column) {
                if (board[r][c] == possible_num
                        && ((r == position[0] && c != position[1]) || (r != position[0] && c == position[1])))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Individual o) {
        int compareage = ((Individual) o).getScore();

        return this.getScore() - compareage;
    }

}
