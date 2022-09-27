import java.awt.*;
import javax.swing.*;

/*
 * Inductive: Backtracking Algorithm
 */
public class Inductive {
    final static int[] possibleDigits = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    final static String[] headers = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
    static int[][] board;
    static JFrame f = new JFrame();
    static JTable jt = new JTable();
    static Integer[][] array;
    static JScrollPane sp;

    public Inductive(int[][] puzzle) {
        board = puzzle;
    }

    public void Inductive_Solve() {
        array = new Integer[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                array[r][c] = Integer.valueOf(board[r][c]);
            }
        }
        jt = new JTable(array, headers);
        sp = new JScrollPane(jt);

        PrintBoard(board);
        System.out.println("\n\n");
        Solve(board);
        if (IsComplete(board)) {
            System.out.println("Board is done!");
            System.out.println("\n\n\n");
        }
        System.out.println("---------\n");
        PrintBoard(board);
        Draw(board);
    }

    public static Boolean Solve(int[][] board) {
        int[] white = FindEmptySpace(board, board.length, board[0].length);
        if (white == null) {
            return true;
        }
        // Atempt to place digits 1-9 in white
        for (int i : possibleDigits) {
            boolean guess = Check(board, white, i);
            if (guess) {
                Draw(board);
                wait(100);
                board[white[0]][white[1]] = i;
                if (Solve(board))
                    return true;
                board[white[0]][white[1]] = 0;
            }
        }
        return false;
    }

    public static void PrintBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[i][c] + " ");
            }
            System.out.println(" ");
        }
    }

    public static boolean IsComplete(int[][] board) {
        boolean IsWhite = false;
        for (int i = 0; i < board.length; i++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[i][c] == 0)
                    IsWhite = true;
            }
        }
        if (IsWhite)
            return false; // there are still white spaces
        else
            return true;
    }

    public static int[] FindEmptySpace(int[][] board, int rows, int cols) { // returns (row, column) --> position
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 0) { // if empty
                    int[] res = { r, c };
                    return res;
                }
            }
        }
        // No empty spaces:
        System.out.println("Board is Finished!!!!!");
        return null;
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

    public static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private static void Draw(int[][] in) {

        f.remove(sp);
        array = new Integer[in.length][in[0].length];
        for (int r = 0; r < in.length; r++) {
            for (int c = 0; c < in[0].length; c++) {
                array[r][c] = Integer.valueOf(in[r][c]);
            }
        }

        jt = new JTable(array, headers);
        jt.setBounds(30, 40, 200, 500);
        jt.setRowHeight(50);
        jt.setFont(new Font("", 1, 42));

        sp = new JScrollPane(jt);
        f.add(sp);
        f.setSize(800, 800);
        f.setVisible(true);
        f.revalidate();
    }
}
