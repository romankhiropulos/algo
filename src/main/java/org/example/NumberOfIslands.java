package org.example;

import java.util.Arrays;

/**
 * Given an m x n 2D binary grid which represents a map
 * of '1's (land) and '0's (water), return the number of islands.
 * <p>
 * An island is surrounded by water and is formed by connecting
 * adjacent lands horizontally or vertically.
 * You may assume all four edges of the grid are all surrounded by water.
 * <p>
 * Solution from <a href="https://cheonhyangzhang.gitbooks.io/leetcode-solutions/content/200-number-of-islands-medium.html">link</a>
 */
public class NumberOfIslands {

    public int numIslands(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        // m - кол-во строк, n - кол-во столбцов грида
        int m = grid.length, n = grid[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, m, n, i, j);
                }
            }
        }
        // todo - for remove
        Arrays.stream(grid).map(Arrays::toString).forEach(System.out::println);

        return count;
    }

    private void dfs(char[][] grid, int m, int n, int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = 'X';
        dfs(grid, m, n, i - 1,    j);
        dfs(grid, m, n, i + 1,    j);
        dfs(grid, m, n,   i,      j - 1);
        dfs(grid, m, n,   i,      j + 1);
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '1'}
        };
        System.out.println(new NumberOfIslands().numIslands(grid));
    }
}
