import java.util.*;
import java.io.*;

public class BOJ_메이즈러너 {

    static int N, M, K;
    static int[][] board;
    static int distance;
    static ArrayList<Route> route;

    static class User {
        int x;
        int y;

        public User(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    static class Route implements Comparable<Route> {
        int sx;
        int sy;
        int distance;
        int dir;

        public Route(int sx, int sy, int distnace, int dir) {
            this.sx = sx;
            this.sy = sy;
            this.distance = distnace;
            this.dir = dir;
        }

        @Override
        public int compareTo(Route o) {

            if (this.distance == o.distance) {
                return Integer.compare(this.dir, o.dir);

            }
            return Integer.compare(this.distance, o.distance);

        }
    }

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int ex, ey;
    static ArrayList<User> user;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        user = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(bf.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            user.add(new User(x - 1, y - 1));
        }
        st = new StringTokenizer(bf.readLine());
        ex = Integer.parseInt(st.nextToken()) - 1;
        ey = Integer.parseInt(st.nextToken()) - 1;

        int time = 0;
        //System.out.println("처음");
        // System.out.println(ex + " " + ey);
        // print();

        while (true) {

            if (time >= K) {
                System.out.println(distance);
                System.out.println((ex + 1) + " " + (ey + 1));

                break;
            }

            if (user.size() == 0) {
                //System.out.println("user");
                System.out.println(distance);
                System.out.println((ex + 1) + " " + (ey + 1));
                break;
            }

            move();
            rotate();

            time++;
            //  System.out.println(time + " 초 회전 후");
            //  print();

        }
    }

    public static void rotate() {

        //  System.out.println("roate");
        int[][] temp = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                temp[i][j] = board[i][j];
            }
        }

        int[][] count = new int[N][N];

        for (int i = 0; i < user.size(); i++) {
            temp[user.get(i).x][user.get(i).y] = -1;
            count[user.get(i).x][user.get(i).y]++;

        }
//        for (int i = 0; i < N; i++) {
//            System.out.println(Arrays.toString(count[i]));
//        }
        temp[ex][ey] = -2;
        //print();
        user = new ArrayList<>();
        for (int size = 2; size <= N; size++) {

            for (int i = 0; i <= N - size; i++) {
                for (int j = 0; j <= N - size; j++) {
                    boolean exit = false;
                    boolean person = false;

                    for (int x = i; x < i + size; x++) {
                        for (int y = j; y < j + size; y++) {
                            if (temp[x][y] == -2) {
                                exit = true;
                            }

                            if (temp[x][y] == -1) {
                                person = true;
                            }
                        }
                    }

                    if (exit && person) {
                        // System.out.println(i + " " + j + " g회전도는ㄷ곳");
                        // (i,j)에서 size만큼 크기의 사각형 rotate
                        // System.out.println("!!!!!!!!!!!!!!!!!!!!");

                        int[][] r = new int[size][size];
                        int[][] cc = new int[size][size];

                        for (int xx = 0; xx < size; xx++) {
                            for (int yy = 0; yy < size; yy++) {

                                r[xx][yy] = temp[i + xx][j + yy];
                                cc[xx][yy] = count[i + xx][j + yy];
                            }
                        }

                        // System.out.println("R");
//                        for (int ii = 0; ii < size; ii++) {
//                            System.out.println(Arrays.toString(r[ii]));
//
//                        }
                        // 돌린거 옮기기
                        //  System.out.println("size " + size);
                        for (int xx = 0; xx < size; xx++) {
                            for (int yy = 0; yy < size; yy++) {

                                temp[i + xx][j + yy] = r[size - 1 - yy][xx];
                                count[i + xx][j + yy] = cc[size - 1 - yy][xx];
                                if (temp[i + xx][j + yy] >= 1) {
                                    temp[i + xx][j + yy] -= 1;
                                }
                            }
                        }

                        for (int xx = 0; xx < N; xx++) {
                            for (int yy = 0; yy < N; yy++) {

                                if (temp[xx][yy] == -2) {
                                    temp[xx][yy] = 0;
                                    ex = xx;
                                    ey = yy;

                                } else if (temp[xx][yy] == -1) {

                                    if (count[xx][yy] >= 2) {
                                        //  System.out.println("@@@@@@@@@@@@@@@@@@@");
                                        for (int ci = 0; ci < count[xx][yy]; ci++) {
                                            user.add(new User(xx, yy));
                                        }
                                    } else {
                                        user.add(new User(xx, yy));
                                    }
                                    ;
                                    temp[xx][yy] = 0;

                                }
                            }
                        }
                        for (int xx = 0; xx < N; xx++) {
                            for (int yy = 0; yy < N; yy++) {

                                board[xx][yy] = temp[xx][yy];
                            }
                        }

                        return;

                    }
                }
            }
        }

    }

    public static void move() {
        ArrayList<User> newUser = new ArrayList<>();

        for (int idx = 0; idx < user.size(); idx++) {
            User u = user.get(idx);

            int minDistance = Math.abs(ex - u.x) + Math.abs(ey - u.y);

            route = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int nx = u.x + dx[i];
                int ny = u.y + dy[i];

                if (isBoundary(nx, ny) && board[nx][ny] == 0) {
                    int minD = Math.abs(ex - nx) + Math.abs(ey - ny);

                    if (minD < minDistance) {

                        route.add(new Route(nx, ny, minD, i));
                    }
                }

            }
            Collections.sort(route);

            if (route.size() == 0) {
                newUser.add(new User(u.x, u.y));
            } else {

                distance++;
                newUser.add(new User(u.x + dx[route.get(0).dir], u.y + dy[route.get(0).dir]));
            }

        }
        user = new ArrayList<>();
        for (int i = 0; i < newUser.size(); i++) {
            //  distance++;
            if (newUser.get(i).x == ex && newUser.get(i).y == ey) {
                continue;
            }
            user.add(newUser.get(i));
        }
    }

    public static boolean isBoundary(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    public static void print() {

        int[][] temp = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                temp[i][j] = board[i][j];
            }
        }

        for (int i = 0; i < user.size(); i++) {
            //    System.out.println(user.get(i).x + " " + user.get(i).y);
            temp[user.get(i).x][user.get(i).y] = -1;

        }
        temp[ex][ey] = -2;

        System.out.println(user.size());
        System.out.println("distance " + distance);
        for (int i = 0; i < N; i++) {
            System.out.println(Arrays.toString(temp[i]));
        }
        System.out.println();
    }


}
