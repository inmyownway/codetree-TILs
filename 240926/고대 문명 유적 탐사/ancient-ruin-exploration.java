import java.util.*;
import java.io.*;

public class Main {
    static int K, M;
    static Queue<Integer> piece;
    static int[][] board;
    static ArrayList<int[]> rotatePos;
    static int[][] tempBoard;
    static int maxCnt;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    static class pNode implements Comparable<pNode> {
        int x;
        int y;

        public pNode(int x, int y) {
            this.x = x;
            this.y = y;


        }

        @Override
        public int compareTo(pNode o) {
            if (this.y == o.y) {
                return o.x - this.x;
            } else {
                return this.y - o.y;
            }
        }

    }

    static class Node implements Comparable<Node> {

        int result;
        int angle;
        int y;
        int x;
        //ArrayList<int[]> pos;

        public Node(int result, int angle, int y, int x) {
            this.result = result;
            this.angle = angle;
            this.y = y;
            this.x = x;
            //t//his.pos = pos;
        }

        @Override
        public int compareTo(Node o) {

            if (this.result == o.result) {
                if (this.angle == o.angle) {
                    if (this.y == o.y) {
                        return this.x - o.x;
                    } else {
                        return this.y - o.y;
                    }
                } else {
                    return this.angle - o.angle;
                }
            } else {
                return o.result - this.result;
            }

        }

        @Override
        public String toString() {
            return "Node{" +
                    "result=" + result +
                    ", angle=" + angle +
                    ", y=" + y +
                    ", x=" + x +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[5][5];
        rotatePos = new ArrayList<>();
        piece = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < 5; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(bf.readLine());

        for (int i = 0; i < M; i++) {
            piece.add(Integer.parseInt(st.nextToken()));
        }

        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                rotatePos.add(new int[]{i, j});
            }
        }

        for (int k = 0; k < K; k++) {

            ArrayList<Node> nodes = new ArrayList<>();
            maxCnt = 0;
            for (int rp = 0; rp < rotatePos.size(); rp++) {
                int[] pos = rotatePos.get(rp);
                // System.out.println(Arrays.toString(pos));
                tempBoard = new int[5][5];

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        tempBoard[i][j] = board[i][j];
                    }
                }

                for (int idx = 0; idx < 3; idx++) {
                    //System.out.println(idx + "도");
                    int x = pos[0];

                    int y = pos[1];
                    rotate(x, y);

//                    for (int aa = 0; aa < 5; aa++) {
//                        System.out.println(Arrays.toString(tempBoard[aa]));
//                    }

                    int num = bfs();
                    //  System.out.println(num);
                    //  System.out.println("\n");

                    nodes.add(new Node(num, idx, y, x));
                }
            }
            Collections.sort(nodes);
            //  System.out.println("1 " + nodes.get(0).result);
            //maxCnt += nodes.get(0).result;
            putPiece(nodes.get(0));

            if (maxCnt != 0) {
                System.out.print(maxCnt + " ");
            } else {
                break;
            }
//            for (int i = 0; i < 5; i++) {
//                System.out.println(Arrays.toString(board[i]));
//            }
//            System.out.println();
        }


    }

    public static void putPiece(Node node) {
        int x = node.x;
        int y = node.y;
        int angle = node.angle + 1;

        boolean[][] v = new boolean[5][5];

        tempBoard = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
        for (int idx = 0; idx < angle; idx++) {
            rotate(x, y);
        }

        ArrayList<pNode> pnode = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!v[i][j]) {

                    int num = tempBoard[i][j];
                    Queue<int[]> q = new LinkedList<>();
                    q.add(new int[]{i, j});

                    ArrayList<int[]> al = new ArrayList<>();
                    al.add(new int[]{i, j});
                    int cnt = 1;
                    v[i][j] = true;

                    while (!q.isEmpty()) {
                        int[] now = q.poll();
                        int xx = now[0];
                        int yy = now[1];

                        for (int idx = 0; idx < 4; idx++) {
                            int nx = xx + dx[idx];
                            int ny = yy + dy[idx];

                            if (isBoundary(nx, ny) && !v[nx][ny] && tempBoard[nx][ny] == num) {
                                v[nx][ny] = true;
                                q.add(new int[]{nx, ny});
                                cnt++;
                                al.add(new int[]{nx, ny});

                            }
                        }


                    }
                    if (cnt >= 3) {
                        maxCnt += cnt;
                        for (int[] nn : al) {
                            pnode.add(new pNode(nn[0], nn[1]));
                        }
                    }


                }
            }
        }

        Collections.sort(pnode);

        for (int i = 0; i < pnode.size(); i++) {

            int p = piece.poll();
            tempBoard[pnode.get(i).x][pnode.get(i).y] = p;
        }

        while (true) {
            if (check() == false) {
                break;
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = tempBoard[i][j];
            }
        }
    }

    public static boolean check() {

        ArrayList<pNode> pnode = new ArrayList<>();
        boolean[][] v = new boolean[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!v[i][j]) {

                    int num = tempBoard[i][j];
                    Queue<int[]> q = new LinkedList<>();
                    q.add(new int[]{i, j});

                    ArrayList<int[]> al = new ArrayList<>();
                    al.add(new int[]{i, j});
                    int cnt = 1;
                    v[i][j] = true;

                    while (!q.isEmpty()) {
                        int[] now = q.poll();
                        int xx = now[0];
                        int yy = now[1];

                        for (int idx = 0; idx < 4; idx++) {
                            int nx = xx + dx[idx];
                            int ny = yy + dy[idx];

                            if (isBoundary(nx, ny) && !v[nx][ny] && tempBoard[nx][ny] == num) {
                                v[nx][ny] = true;
                                q.add(new int[]{nx, ny});
                                cnt++;
                                al.add(new int[]{nx, ny});

                            }
                        }


                    }
                    if (cnt >= 3) {
                        maxCnt += cnt;
                        for (int[] nn : al) {
                            pnode.add(new pNode(nn[0], nn[1]));
                        }
                    }


                }
            }
        }

        if (pnode.size() == 0) {
            return false;
        }
        Collections.sort(pnode);

        for (int i = 0; i < pnode.size(); i++) {

            int p = piece.poll();
            tempBoard[pnode.get(i).x][pnode.get(i).y] = p;
        }
        return true;
    }


    public static int bfs() {

        boolean[][] v = new boolean[5][5];
        int maxCCnt = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!v[i][j]) {

                    int num = tempBoard[i][j];
                    int cnt = 1;

                    Queue<int[]> q = new LinkedList<>();
                    q.add(new int[]{i, j});
                    v[i][j] = true;

                    while (!q.isEmpty()) {
                        int[] now = q.poll();
                        int x = now[0];
                        int y = now[1];

                        for (int idx = 0; idx < 4; idx++) {
                            int nx = x + dx[idx];
                            int ny = y + dy[idx];

                            if (isBoundary(nx, ny) && !v[nx][ny] && tempBoard[nx][ny] == num) {
                                cnt++;
                                v[nx][ny] = true;
                                q.add(new int[]{nx, ny});
                            }
                        }


                    }
                    if (cnt >= 3) {
                        maxCCnt += cnt;
                    }
                }
            }


        }

        return maxCCnt;
    }

    public static void rotate(int x, int y) {

        int[][] b = new int[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                b[i][j] = tempBoard[i][j];
            }
        }

        b[x - 1][y - 1] = tempBoard[x + 1][y - 1];
        b[x - 1][y] = tempBoard[x][y - 1];
        b[x - 1][y + 1] = tempBoard[x - 1][y - 1];

        b[x][y + 1] = tempBoard[x - 1][y];
        b[x + 1][y + 1] = tempBoard[x - 1][y + 1];

        b[x + 1][y] = tempBoard[x][y + 1];
        b[x + 1][y - 1] = tempBoard[x + 1][y + 1];
        b[x][y - 1] = tempBoard[x + 1][y];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tempBoard[i][j] = b[i][j];
            }
        }

    }

    public static boolean isBoundary(int x, int y) {
        return x >= 0 && x < 5 && y >= 0 && y < 5;
    }

}