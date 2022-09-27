public class Tester {
    public static void main(String[] args) {

        int[][] test_case_1 = {
                { 7, 8, 0, 4, 0, 0, 1, 2, 0 },
                { 6, 0, 0, 0, 7, 5, 0, 0, 9 },
                { 0, 0, 0, 6, 0, 1, 0, 7, 8 },
                { 0, 0, 7, 0, 4, 0, 2, 6, 0 },
                { 0, 0, 1, 0, 5, 0, 9, 3, 0 },
                { 9, 0, 4, 0, 6, 0, 0, 0, 5 },
                { 0, 7, 0, 3, 0, 0, 0, 1, 2 },
                { 1, 2, 0, 0, 0, 7, 4, 0, 0 },
                { 0, 4, 9, 2, 0, 6, 0, 0, 7 }
        };
        int[][] test_case_2 = {
                { 7, 8, 0, 4, 0, 0, 1, 2, 0 },
                { 6, 0, 0, 0, 7, 5, 0, 0, 9 },
                { 0, 0, 0, 6, 0, 1, 0, 7, 8 },
                { 0, 0, 7, 0, 4, 0, 2, 6, 0 },
                { 0, 0, 1, 0, 5, 0, 9, 3, 0 },
                { 9, 0, 4, 0, 6, 0, 0, 0, 5 },
                { 0, 7, 0, 3, 0, 0, 0, 1, 2 },
                { 1, 2, 0, 0, 0, 7, 4, 0, 0 },
                { 0, 4, 9, 2, 0, 6, 0, 0, 7 }
        };
        Inductive solver = new Inductive(test_case_1);
        solver.Inductive_Solve();

        Deductive d_solver = new Deductive(test_case_2);
        d_solver.Deductive_solve();
    }
}