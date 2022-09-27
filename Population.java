
import java.util.*;

public class Population {
    static int population_size;
    static int[][] board;
    List<List<individual>> all_generation = new ArrayList<>();

    public Population(int[][] br, int pop_size) {
        population_size = pop_size;
        board = br;
    }

    public void solve() {
        while (population_size > 0 && Board_Check(board) == false) {

            if (all_generation.size() == 0) { // THIS IS FOR FIRST GENERATION
                List<individual> generation = new ArrayList<>();
                for (int i = 0; i <= population_size; i++) {
                    generation.add(new individual(board.clone(), 0));

                }

                for (individual i : generation) {
                    // System.out.println(i);
                    i.solve();
                    // PrintBoard(i.GetBoard());
                    // System.out.println("\n\n");
                }
                // ----------------------------------------------------------------------
                all_generation.add(generation);
                // Sort generation by fitness score
                Collections.sort(generation);
                board = generation.get(0).GetBoard();

                // Always add the first few (or just first) individual into generation n+1 -
                // optional for now, necessary later

                // Swap genomes in generation n
                for (int i = 0; i < generation.size() - 1; i++) {
                    individual one = generation.get(i);
                    individual two = generation.get(i + 1);
                    one.swap(two);
                }

                // Mutate? - optional for now
                population_size /= 2;
            }

            else { // THIS IS FOR AL GENERATIONS AFTER FIRST ONE
                List<individual> generation = new ArrayList<>();
                generation.addAll(all_generation.get(all_generation.size() - 1)); // becomes children of prev.
                                                                                  // generation

                while (generation.size() > population_size) { // removes all the ill chilren w/ bad fitness
                    generation.remove(generation.get(generation.size() - 1));
                }
                all_generation.add(generation);
                // Always add the first few (or just first) individual into generation n+1 -
                // optional for now, necessary later

                // Swap genomes in generation n
                for (int i = 0; i < generation.size() - 1; i++) {
                    individual one = generation.get(i);
                    individual two = generation.get(i + 1);

                    one.swap(two);

                }

                // Mutate? - optional for now
                // Maybe add in a few new individuals just in case we get a good random sample
                population_size /= 2;
                System.out.println(all_generation.get(all_generation.size() - 1).get(0).Fitness_Score(board));
                // we need more b/c rn the fitness scores will be the same after 1 gen b/c just
                // first 2 boards are continously being swapped
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
