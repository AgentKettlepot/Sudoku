package Genetics_1;

import java.util.*;

public class Genetics {
    static int[][] board;
    static List<List<Individual>> generation_list = new ArrayList<>();
    static int generation_size;

    public Genetics(int[][] puzzle, int size) {
        board = puzzle;
        generation_size = size;
    }

    public void Trial() {
        List<Individual> first_gen = new ArrayList<>();
        Solve(first_gen);
        if (IsValid(board)) {
            System.out.println("Board is done!\n\n\n\n");
            PrintBoard(board);
        } else {
            System.out.println("Board cannot be solved :(");
        }
    };

    public void Solve(List<Individual> generation) {
        // System.out.println(generation_list.size());

        generation_size--;
        List<Individual> new_generation = new ArrayList<>();
        new_generation.addAll(generation);
        System.out.println(generation_list);
        // System.out.println(new_generation);
        if (IsValid(board) == false) {
            generation_list.add(new_generation);
            for (int i = 0; i <= generation_size; i++) {
                new_generation.add(new Individual(board, 0));
                new_generation.get(i).solve();
            }

            // This part gets rid of the weakest link
            int index_of_weakest_indv = 0;
            for (int i = 1; i < new_generation.size(); i++) {
                if (new_generation.get(i).getScore() < new_generation.get(i - 1).getScore()) {
                    index_of_weakest_indv = i;
                }
            }
            new_generation.remove(new_generation.get(index_of_weakest_indv));

            // Traverse new_generation and sort by fitness
            Collections.sort(new_generation);
            board = new_generation.get(0).getBoard();
            // System.out.println(new_generation.get(0).Fitness_Score(board));

            // Traverse new_generation and swap genomes with swap(new_generation)
            for (int i = 0; i < new_generation.size() - 1; i++) {
                Individual i1 = new_generation.get(i);
                Individual i2 = new_generation.get(i + 1);
                swap_genome(i1, i2);
            }
            Solve(new_generation);
        } else {
            System.out.println("board is done");
            return;
        }
    }

    public void swap_genome(Individual i1, Individual i2) {
        int[][] temp_array = new int[9][4];
        // Swapping the top half of i1 for top half of i2
        for (int r = 0; r < temp_array.length; r++) {
            for (int c = 0; c < temp_array[0].length; c++) {
                temp_array[r][c] = i1.getBoard()[r][c];
                i1.getBoard()[r][c] = i2.getBoard()[r][c];
                i2.getBoard()[r][c] = temp_array[r][c];
            }
        }
    }

    public static boolean IsValid(int[][] board) {
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

    public static void PrintBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[i][c] + " ");
            }
            System.out.println(" ");
        }
    }
}
