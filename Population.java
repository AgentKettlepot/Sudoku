
import java.util.*;

public class Population {
    static int generations;
    static int ORIGINAL_SIZE;
    static int[][] ORIGINAL_BOARD;
    static int[][] board;
    List<List<individual>> all_generation = new ArrayList<>();

    public Population(int[][] br, int pop_size) {
        generations = pop_size;
        board = br;
        ORIGINAL_SIZE = pop_size;
        ORIGINAL_BOARD = br;
    }

    public void solve() {
        while (generations > 0 && Board_Check(board) == false) {
            if (all_generation.size() == 0) { // THIS IS FOR FIRST GENERATION - WORKS GOOD
                List<individual> generation = new ArrayList<>();
                for (int i = 0; i <= generations; i++) {
                    generation.add(new individual(ORIGINAL_BOARD.clone(), 0));

                }
                for (individual i : generation) {
                    i.solve();
                }
                all_generation.add(generation);

                // Sort generation by fitness score
                Collections.sort(generation);
                board = generation.get(0).GetBoard();

                // Swap genomes in generation n
                for (int i = 0; i < generation.size() - 1; i++) {
                    individual one = generation.get(i);
                    individual two = generation.get(i + 1);
                    one.swap(two);
                }
                generations -= 1;
            }

            else { // THIS IS FOR ALL GENERATIONS AFTER FIRST ONE
                List<individual> generation = new ArrayList<>();
                // becomes children of prev. generation
                generation.addAll(all_generation.get(all_generation.size() - 1));
                int temp = generation.size() / 2;
                while (generation.size() > temp) { // removes bad half of generation
                    generation.remove(generation.get(generation.size() - 1));
                }

                // Adding a few new individuals and the top individuals from previous
                // generations as more samples
                int prev_size = generation.size();
                int random = (int) (Math.random() * ORIGINAL_SIZE + 1);
                for (int i = 0; i < random; i++) {
                    generation.add(new individual(ORIGINAL_BOARD.clone(), 0));
                }
                for (int j = prev_size - 1; j < generation.size(); j++) {
                    generation.get(j).solve();
                }

                // Swap genomes in generation n
                for (int i = 0; i < generation.size() - 1; i++) {
                    individual one = generation.get(i);
                    individual two = generation.get(i + 1);
                    one.swap(two);
                }

                // Sort generation by fitness score
                Collections.sort(generation);
                board = generation.get(0).GetBoard();

                // Mutate? - optional for now
                generations -= 1;
                System.out.println(all_generation.get(all_generation.size() - 1).get(0).Fitness_Score(board));
                all_generation.add(generation);

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

    public static boolean Board_Check(int[][] board) {
        boolean res = true;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                int[] pos = { r, c };
                if (Check(board, pos, board[r][c]) == false) {
                    res = false;
                }
            }
        }
        return res;
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
}
