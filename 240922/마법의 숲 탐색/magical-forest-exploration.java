import java.io.*;
import java.util.*;

public class Main {
    static int R, C, K;
    static int[][] board;
    static int sx, sy;
    static int nd;
    static boolean isMove;
    static int answer = 0;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        R = Integer.parseInt(st.nextToken()) + 2;
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[R][C];

        for (int k = 1; k < K + 1; k++) {
            st = new StringTokenizer(bf.readLine());

            int y = Integer.parseInt(st.nextToken()) - 1;
            nd = Integer.parseInt(st.nextToken());
            sx = 0;
            sy = y;

            while (true) {

                isMove = false;
                if (sx == R - 2) {
                    break;
                }
                // System.out.println(sx + " " + sy);
                if (moveDown(sx, sy)) {
                    isMove = true;
                    sx += 1;
                    continue;

                }
                if (moveLeft(sx, sy) && moveDown(sx, sy - 1)) {
                    isMove = true;
                    sx += 1;
                    sy -= 1;
                    nd = (nd + 3) % 4;
                    continue;

                }
                if (moveRight(sx, sy) && moveDown(sx, sy + 1)) {
                    isMove = true;
                    sx += 1;
                    sy += 1;
                    nd = (nd + 1) % 4;
                    continue;

                }
                if (isMove == false) {
                    break;
                }


            }
            if (outOfForest()) {
                board = new int[R + 2][C];
            } else {
                board[sx][sy] = k;
                board[sx - 1][sy] = k;
                board[sx][sy - 1] = k;
                board[sx][sy + 1] = k;
                board[sx + 1][sy] = k;
                if (nd == 0) {
                    board[sx - 1][sy] = -k;
                } else if (nd == 1) {
                    board[sx][sy + 1] = -k;
                } else if (nd == 2) {
                    board[sx + 1][sy] = -k;
                } else if (nd == 3) {
                    board[sx][sy - 1] = -k;

                }
                find(sx, sy);
            }

          
        }
        System.out.println(answer);

    }

    public static void find(int x, int y) {

        Queue<int[]> q = new LinkedList<>();
        int m = -1;

        boolean[][] v = new boolean[R][C];
        q.add(new int[]{x, y});
        v[x][y] = true;

        while (!q.isEmpty()) {
            int[] now = q.poll();

            int currentNum = board[now[0]][now[1]];
            //System.out.println(Arrays.toString(now) + " " + currentNum);
            for (int idx = 0; idx < 4; idx++) {
                int nx = now[0] + dx[idx];
                int ny = now[1] + dy[idx];
                //System.out.println("nx ny" + nx + " " + ny);

                if (isBoundaryFind(nx, ny) && !v[nx][ny]) {

                    // System.out.println("next" + board[nx][ny]);
                    if (currentNum < 0 && board[nx][ny] != 0) {
                        q.add(new int[]{nx, ny});
                        v[nx][ny] = true;
                        m = Math.max(m, nx);
                    }
                    if (currentNum == Math.abs(board[nx][ny])) {
                        q.add(new int[]{nx, ny});
                        v[nx][ny] = true;
                        m = Math.max(m, nx);
                    }
                }
            }
        }
        answer += (m - 1);
        //System.out.println(m - 1);
    }

    public static boolean moveDown(int sx, int sy) {
        int[] canLeft = {sx + 1, sy - 1};
        int[] canRight = {sx + 1, sy + 1};
        int[] canDown = {sx + 2, sy};

        if (isBoundary(canLeft[0], canLeft[1])
                && isBoundary(canRight[0], canRight[1])
                && isBoundary(canDown[0], canDown[1])
                && isCanMove(canLeft[0], canLeft[1])
                && isCanMove(canRight[0], canRight[1])
                && isCanMove(canDown[0], canDown[1])) {

            return true;
        }
        return false;
    }

    public static boolean moveLeft(int sx, int sy) {
        int[] canLeft = {sx, sy - 2};
        int[] canRight = {sx - 1, sy - 1};
        int[] canDown = {sx + 1, sy - 1};

        if (isBoundary(canLeft[0], canLeft[1])
                && isBoundary(canRight[0], canRight[1])
                && isBoundary(canDown[0], canDown[1])
                && isCanMove(canLeft[0], canLeft[1])
                && isCanMove(canRight[0], canRight[1])
                && isCanMove(canDown[0], canDown[1])) {

            // 0 1 2 3           isMove = true;
            // 북 동 남 서
            return true;

            //      nd = (nd + 3) % 4;
        }
        return false;

    }

    public static boolean moveRight(int sx, int sy) {
        int[] canLeft = {sx - 1, sy + 1};
        int[] canRight = {sx, sy + 2};
        int[] canDown = {sx + 1, sy + 1};

        if (isBoundary(canLeft[0], canLeft[1])
                && isBoundary(canRight[0], canRight[1])
                && isBoundary(canDown[0], canDown[1])
                && isCanMove(canLeft[0], canLeft[1])
                && isCanMove(canRight[0], canRight[1])
                && isCanMove(canDown[0], canDown[1])) {

            // 0 1 2 3
            // 북 동 남 서

            //   nd = (nd + 1) % 4;
            return true;

        }
        return false;

    }


    public static boolean isBoundary(int x, int y) {
        return x >= 0 && x < R && y >= 0 && y < C;
    }

    public static boolean isCanMove(int x, int y) {
        if (board[x][y] == 0) {
            return true;
        }
        return false;
    }

    public static boolean outOfForest() {
        if (sx >= 0 && sx <= 2) {
            return true;

        }
        if (sy == 0 && sy == C - 1) {
            return true;
        }
        return false;
    }

    public static boolean isBoundaryFind(int x, int y) {
        return x >= 3 && x < R && y >= 0 && y < C;
    }
}