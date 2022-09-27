import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Deductive {
    final static int[] possibleDigits = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    final static String[] headers = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
    static int[][] board;
    static HashMap<int[], ArrayList<Integer>> matcher = new HashMap<int[], ArrayList<Integer>>();
    static JFrame f = new JFrame();
    static JTable jt = new JTable();
    static JScrollPane sp;
    static Integer[][] array;

    public Deductive(int[][] puzzle) {
        board = puzzle;
    }

    public void Deductive_solve() {
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
        while (IsComplete(board) == false) {
            Match(board, matcher);
            Solve(board, matcher);
            for (int[] cord : matcher.keySet()) {
                System.out.println(cord[0] + "," + cord[1] + ": " + matcher.get(cord).toString());
            }
            System.out.println("\n\n");
        }
        System.out.println("\n\n");
        PrintBoard(board);
    }

    public static void Solve(int[][] board, HashMap<int[], ArrayList<Integer>> map) {
        for (int[] cord : map.keySet()) {
            int x = cord[0];
            int y = cord[1];
            ArrayList<Integer> potential_nums = map.get(cord);
            deductRow(board, x, y, potential_nums);
            deductCol(board, x, y, potential_nums);
            deductBox(board, x, y, potential_nums);
        }

        for (Iterator<Map.Entry<int[], ArrayList<Integer>>> it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry<int[], ArrayList<Integer>> entry = it.next();
            if (entry.getValue().size() == 1) {
                int row = entry.getKey()[0];
                int col = entry.getKey()[1];
                Draw(board);
                // wait(100);
                board[row][col] = entry.getValue().get(0);
                it.remove();
            }
        }

    }

    public static void deductRow(int[][] board, int x, int y, ArrayList<Integer> potential) {
        for (int c = 0; c < board.length; c++) {
            if (potential.contains(board[x][c])) {
                int indx = potential.indexOf(board[x][c]);
                potential.remove(indx);

            }
        }
    }

    public static void deductCol(int[][] board, int x, int y, ArrayList<Integer> potential) {
        for (int r = 0; r < board.length; r++) {
            if (potential.contains(board[r][y])) {
                int indx = potential.indexOf(board[r][y]);
                potential.remove(indx);
            }
        }
    }

    public static void deductBox(int[][] board, int x, int y, ArrayList<Integer> potential) {
        int[] mini_box_row = new int[3];
        int[] mini_box_column = new int[3];

        if (x <= 2) { // FINDING ROW RANGE FOR MINI BOX
            mini_box_row[0] = 0;
            mini_box_row[1] = 1;
            mini_box_row[2] = 2;
        } else if (x <= 5) {
            mini_box_row[0] = 3;
            mini_box_row[1] = 4;
            mini_box_row[2] = 5;
        } else {
            mini_box_row[0] = 6;
            mini_box_row[1] = 7;
            mini_box_row[2] = 8;
        }

        if (y <= 2) { // FINDING COL RANGE FOR MINI BOX
            mini_box_column[0] = 0;
            mini_box_column[1] = 1;
            mini_box_column[2] = 2;
        } else if (y <= 5) {
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
                if (potential.contains(board[r][c])) {
                    int indx = potential.indexOf(board[r][c]);
                    potential.remove(indx);
                }
            }
        }
    }

    private static void Match(int[][] board, HashMap<int[], ArrayList<Integer>> map) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 0) {
                    int[] cords = { r, c };
                    int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
                    ArrayList<Integer> potential_nums = new ArrayList<Integer>();
                    for (int i : array) {
                        potential_nums.add(i);
                    }
                    map.put(cords, potential_nums);
                }

            }
        }
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

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
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
