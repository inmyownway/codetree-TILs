import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    public static class Fish implements Comparable<Fish> {
        int x;
        int y;
        int num;
        int dir;

        public Fish(int x, int y, int num, int dir) {
            this.x = x;
            this.y = y;
            this.num = num;
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "Fish{" +
                    "x=" + x +
                    ", y=" + y +
                    ", num=" + num +
                    ", dir=" + dir +
                    '}';
        }

        @Override
        public int compareTo(Fish o) {
            return this.num - o.num;
        }
    }

    static int N = 4;
    static ArrayList<Fish> fish;
    static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
    static int answer = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        fish = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(bf.readLine());
            for (int j = 0; j < 4; j++) {
                int n = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken()) - 1;
                fish.add(new Fish(i, j, n, d));
            }
        }

        int tx = 0;
        int ty = 0;
        int td = fish.get(0).dir;
        fish.get(0).x = -1;
        fish.get(0).y = -1;

        int score = fish.get(0).num;
        Collections.sort(fish);

        dfs(tx, ty, td, fish, score);
        System.out.println(answer);
    }

    public static void dfs(int cx, int cy, int cd, ArrayList<Fish> fishes, int s) {
        ArrayList<Fish> tempFish = new ArrayList<>();

        for (Fish f : fishes) {
            tempFish.add(new Fish(f.x, f.y, f.num, f.dir));
        }

        // 물고기 이동
        moveFish(tempFish);

        // 술래 이동
        for (int c = 1; c <= 3; c++) {
            int currentX = cx + dx[cd] * c;
            int currentY = cy + dy[cd] * c;

            if (!isBoundary(currentX, currentY)) {
                break;
            }

            for (Fish f : tempFish) {
                if (f.x == currentX && f.y == currentY) {
                    f.x = -1;
                    f.y = -1;
                    dfs(currentX, currentY, f.dir, tempFish, s + f.num);
                    f.x = currentX;
                    f.y = currentY;
                    break;
                }
            }
        }

        answer = Math.max(answer, s);
    }

    public static void moveFish(ArrayList<Fish> tempFish) {
        for (Fish f : tempFish) {
            if (f.x == -1 && f.y == -1) continue;

            for (int i = 0; i < 8; i++) {
                int nx = f.x + dx[f.dir];
                int ny = f.y + dy[f.dir];

                if (isBoundary(nx, ny)) {
                    boolean found = false;
                    for (Fish other : tempFish) {
                        if (other.x == nx && other.y == ny && other.num != -1) {
                            int tempX = f.x;
                            int tempY = f.y;
                            f.x = other.x;
                            f.y = other.y;
                            other.x = tempX;
                            other.y = tempY;
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        f.x = nx;
                        f.y = ny;
                    }
                } else {
                    // 방향 전환
                    f.dir = (f.dir + 1) % 8;
                }
            }
        }
    }

    public static boolean isBoundary(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}