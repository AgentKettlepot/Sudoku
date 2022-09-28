public class individual implements Comparable<individual> {
    int[][] board = new int[9][9];
    int score;
    int[][] ORIGINAL_BOARD = new int[9][9];

    public individual(int[][] b, int sc) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = b[r][c];
                ORIGINAL_BOARD[r][c] = b[r][c];
            }
        }

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
        score = Fitness_Score(board);
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
        return (int) ((double) counter / 81.0 * 10000);
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

    public int[][] GetBoard() {
        return board;
    }

    @Override
    public int compareTo(individual o) {
        int compareage = ((individual) o).score;
        return compareage - this.score;
    }

    public void swap(individual second) {
        int[][] temp_array = new int[9][4];
        // Swapping the top half of i1 for top half of i2
        for (int r = 0; r < temp_array.length; r++) {
            for (int c = 0; c < temp_array[0].length; c++) {
                temp_array[r][c] = this.GetBoard()[r][c];
                this.GetBoard()[r][c] = second.GetBoard()[r][c];
                second.GetBoard()[r][c] = temp_array[r][c];
            }
        }
    }
}
