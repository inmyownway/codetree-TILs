import java.util.*;
import java.io.*;

public class Main {
    static int N, M, K;
    static ArrayList<Integer>[][] gun;
    static int[] dx = new int[]{-1, 0, 1, 0};
    static int[] dy = new int[]{0, 1, 0, -1};

    static class Player {
        int num;
        int x;
        int y;
        int d;
        int stat;
        int gun;
        long score;


        public Player(int num, int x, int y, int d, int stat, int gun, long score) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.d = d;
            this.stat = stat;
            this.gun = gun;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "num=" + num +
                    ", x=" + x +
                    ", y=" + y +
                    ", d=" + d +
                    ", stat=" + stat +
                    ", gun=" + gun +
                    ", score=" + score +
                    '}';
        }
    }

    static ArrayList<Player> player;

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        player = new ArrayList<>();
        gun = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gun[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < N; j++) {
                gun[i][j].add(Integer.parseInt(st.nextToken()));
            }
        }
        //printGun();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(bf.readLine());

            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            player.add(new Player(i, x, y, d, s, 0, 0));


        }

        game();
    }

    public static void game() {
        for (int round = 0; round < K; round++) {
            // 1번꺼 부터 움직임

            for (int idx = 0; idx < player.size(); idx++) {
                Player p = player.get(idx);
                // System.out.println(p);
                int nx = p.x + dx[p.d];
                int ny = p.y + dy[p.d];

                if (!isBoundary(nx, ny)) {

                    p.d = (p.d + 2) % 4;
                }
                nx = p.x + dx[p.d];
                ny = p.y + dy[p.d];

                boolean isPlayer = false;
                Player counter = null;
                p.x = nx;
                p.y = ny;
                for (int i = 0; i < player.size(); i++) {

                    if (i == idx) {
                        continue;
                    }

                    counter = player.get(i);

                    //System.out.println(counter.x + " " + counter.y);
                    if (p.x == counter.x && p.y == counter.y) {
                        isPlayer = true;
                        break;
                    }
                }

                // System.out.println(p.num);
                //System.out.println(p.x + " " + p.y);
                // 칸에 상대방 없는 경우
                if (!isPlayer) {

                    //  System.out.println("상대방없음");
                    selectGun(p);
                }
                // 칸에 상대방 있는경우
                else {
                    fight(p, counter);
                }

            }
            //  printGun();
            //  printPlayer();
        }

        for (int i = 0; i < player.size(); i++) {
            System.out.print(player.get(i).score + " ");
        }
    }

    public static void fight(Player player, Player counter) {
        Player winner = null;
        Player loser = null;

        if (player.stat + player.gun == counter.stat + counter.gun) {
            if (player.stat > counter.stat) {
                winner = player;
                loser = counter;
            } else {
                winner = counter;
                loser = player;
            }

        } else {
            if (player.stat + player.gun > counter.stat + counter.gun) {
                winner = player;
                loser = counter;
            } else {
                winner = counter;
                loser = player;
            }
        }

        winner.score += (winner.stat + winner.gun) - (loser.stat + loser.gun);

        // 진 플레이어 총 두고감
        moveLoser(loser);
        actWinner(winner);

    }

    public static void actWinner(Player p) {
        selectGun(p);
    }

    public static void moveLoser(Player p) {
        gun[p.x][p.y].add(p.gun);
        p.gun = 0;

        //System.out.println("loser " + p);

        int cnt = 0;

        for (int idx = 0; idx < 4; idx++) {
            int nx = p.x + dx[(p.d + idx) % 4];
            int ny = p.y + dy[(p.d + idx) % 4];
            boolean flag = false;

            //System.out.println(nx + " " + ny);
            if (isBoundary(nx, ny)) {
                for (int i = 0; i < player.size(); i++) {
                    if (nx == player.get(i).x && ny == player.get(i).y) {
                        flag = true;
                    }
                }
            } else {
                flag = true;
            }
            //System.out.println(flag);
            if (flag == false) {
                p.x = nx;
                p.y = ny;
                p.d = (p.d + idx) % 4;
                selectGun(p);
                return;
            }

        }


    }

    public static void selectGun(Player p) {
        ArrayList<Integer> guns = gun[p.x][p.y];

        //System.out.println(p.x + " " + p.y + "위치에 총들");
        //System.out.println(guns);
        if (guns.size() >= 1) {
            int maxGun = -1;
            int maxGunIdx = -1;

            for (int i = 0; i < guns.size(); i++) {
                if (maxGun < guns.get(i)) {
                    maxGun = guns.get(i);
                    maxGunIdx = i;
                }
            }
            //  System.out.println(maxGun);
            if (maxGun > p.gun) {
                guns.remove(maxGunIdx);
                if (p.gun != 0) {
                    guns.add(p.gun);
                }
                p.gun = maxGun;
            }
        }

    }

    public static boolean isBoundary(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    public static void printGun() {
        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {
                System.out.print(gun[i][j] + " ");
            }

            System.out.println();
        }
    }

    public static void printPlayer() {
        for (int i = 0; i < player.size(); i++) {
            Player p = player.get(i);
            System.out.println(
                    "num: " + p.num + " (" + p.x + "," + p.y + ")  stat: " + p.stat + " gun: " + p.gun + "  score:"
                            + p.score + " d: " + p.d);
        }
    }
}
