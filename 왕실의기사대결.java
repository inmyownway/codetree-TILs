import java.util.*;
import java.io.*;

public class CODETREE_왕실의기사대결 {
    static int L, N, Q;
    static int[][] board;
    static ArrayList<int[]> command;
    static ArrayList<Solider> solider;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static ArrayList<Solider> canMove;
    static int answer;

    static class Solider {
        int num;
        int x1;
        int y1;
        int x2;
        int y2;
        int k;
        int hp;

        public Solider(int num, int x1, int y1, int x2, int y2, int k, int hp) {
            this.num = num;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.k = k;
            this.hp = hp;
        }

        @Override
        public String toString() {
            return "Solider{" +
                    "num=" + num +
                    ", x1=" + x1 +
                    ", y1=" + y1 +
                    ", x2=" + x2 +
                    ", y2=" + y2 +
                    ", k=" + k + " , hp" + hp + " " +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        board = new int[L][L];
        solider = new ArrayList<>();
        command = new ArrayList<>();

        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(bf.readLine());

            for (int j = 0; j < L; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(bf.readLine());
            int x1 = Integer.parseInt(st.nextToken()) - 1;
            int y1 = Integer.parseInt(st.nextToken()) - 1;
            int dx = Integer.parseInt(st.nextToken()) - 1;
            int dy = Integer.parseInt(st.nextToken()) - 1;
            int k = Integer.parseInt(st.nextToken());

            //System.out.println(x1 + " " + y1 + " " + (x1 + dx) + " " + (y1 + dy) + " ");
            solider.add(new Solider(i, x1, y1, x1 + dx, y1 + dy, k, k));
        }

        for (int i = 0; i < Q; i++) {

            st = new StringTokenizer(bf.readLine());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            command.add(new int[]{x, d});
        }

        for (int[] com : command) {

            //System.out.println("____________________________\n" + Arrays.toString(com) + " !!" + solider.size());

            Queue<Solider> q = new LinkedList<>();
            canMove = new ArrayList<>();

            Solider moveSolider = findSolider(com[0]);
            if (moveSolider.num == -1) {
                continue;
            } else {
                q.add(moveSolider);
            }

            canMove.add(moveSolider);

            int d = com[1];

            boolean flag = true;
            while (!q.isEmpty()) {

                Solider s = q.poll();
                //System.out.println("큐에서 나온 솔져 " + s);
                //System.out.println(s.x1 + " " + s.y1 + " " + s.x2 + " " + s.y2);

                int nx1 = s.x1 + dx[d];
                int ny1 = s.y1 + dy[d];
                int nx2 = s.x2 + dx[d];
                int ny2 = s.y2 + dy[d];

                // System.out.println(nx1 + " " + ny1 + " " + nx2 + " " + ny2);
                if (isBoundary(nx1, ny1) && isBoundary(nx2, ny2) && isWall(nx1, ny1, nx2, ny2)) {

                    ArrayList<Solider> findSoliders = find(s.num, nx1, ny1, nx2, ny2);

                    for (Solider findS : findSoliders) {

                        //System.out.println(findS);
                        canMove.add(findS);
                        q.add(findS);
                    }
                } else {
                    flag = false;
                    break;
                }


            }
            // System.out.println("밀기가능??  " + flag);

            if (flag) {
                //System.out.println(canMove);

                for (Solider idx : canMove) {

                    idx.x1 += dx[d];
                    idx.y1 += dy[d];
                    idx.x2 += dx[d];
                    idx.y2 += dy[d];

                }

                for (Solider s : canMove) {
                    if (s.num != com[0]) {

                        int cnt = 0;

                        for (int i = s.x1; i <= s.x2; i++) {
                            for (int j = s.y1; j <= s.y2; j++) {
                                if (board[i][j] == 1) {
                                    cnt++;
                                }
                            }
                        }
                        s.k -= cnt;
                        //  answer += cnt;
                        //   System.out.println(cnt);

                    }
                }

                for (Solider s : solider) {
                    if (s.k <= 0) {
                        s.num = -1;
                    }
                }
                //  System.out.println(" " + solider.size());
                int idx = 1;


            }
            //체력 깍기

            //print();

        }
        for (Solider s : solider) {

            if (s.num != -1) {
                answer += s.hp - s.k;
            }


        }
        System.out.println(answer);

    }

    public static ArrayList<Solider> find(int num, int x1, int y1, int x2, int y2) {

        ArrayList<Solider> result = new ArrayList<>();

        //System.out.println("num " + num);
        for (int i = 1; i < solider.size() + 1; i++) {
            //System.out.println(i + " ");

            if (i != num) {
                Solider s = solider.get(i - 1);
                if (s.num == -1) {
                    continue;
                }

                ArrayList<int[]> a = new ArrayList<>();
                boolean flag = false;
                for (int p = x1; p <= x2; p++) {
                    for (int q = y1; q <= y2; q++) {

                        //  System.out.println(p + " " + q);
                        if ((p >= s.x1 && p <= s.x2) && (q >= s.y1 && q <= s.y2)) {

                            //System.out.println("들어가는 솔져" + s + "들어감");
                            result.add(s);
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }
                }


            }

        }
        return result;
    }

    public static Solider findSolider(int num) {
        Solider nullSolider = new Solider(-1, -1, -1, -1, -1, -1, -1);
        for (int i = 0; i < solider.size(); i++) {
            if (solider.get(i).num == num) {
                return solider.get(i);
            }
        }
        return nullSolider;
    }

    public static boolean isBoundary(int x, int y) {
        return x >= 0 && x < L && y >= 0 && y < L;
    }

    public static boolean isWall(int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                if (board[i][j] == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void print() {
        int[][] b = new int[L][L];
        for (Solider s : solider) {
            if (s.num == -1) {
                continue;
            }
            //System.out.println(s);
            int x1 = s.x1;
            int y1 = s.y1;
            int x2 = s.x2;
            int y2 = s.y2;

            int n = s.num;
            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    b[i][j] = n;
                }
            }
        }
//
//        for (int i = 0; i < L; i++) {
//            System.out.println(Arrays.toString(b[i]));
//        }
        //  System.out.println("");
    }


}
