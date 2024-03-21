import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, K;
    static ArrayList<Integer> movingWalk = new ArrayList<>();
    static int[] personPos;
    static int[] D;
    static int kCnt,round;

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        D = new int[N * 2];
        st = new StringTokenizer(bf.readLine());

        for (int i = 0; i < N*2; i++) {
            D[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < N * 2; i++) {
            movingWalk.add(i);
        }

        personPos = new int[N];

        while (true) {

            round++;
          //  System.out.println("round: "+round);

            moveMovingWalk();
//
           // System.out.println(movingWalk);

            personMove();
          //  System.out.println(Arrays.toString(personPos));

            personAdd();
          //  System.out.println("로직 끝");
           // System.out.println("D: "+Arrays.toString(D));

            //System.out.println(Arrays.toString(personPos));
            //System.out.println(movingWalk);

            if(check()==false)
            {
                System.out.println(round);
                break;
            }
           // System.out.println();

        }


    }

    private static void personAdd() {

        if (D[movingWalk.get(0)] >= 1 && personPos[0] == 0) {
            personPos[0] = 1;
            D[movingWalk.get(0)] -= 1;
        }
    }

    private static void personMove() {
        for (int idx = N - 1; idx > -1; idx--) {
            if (personPos[idx] == 1) {
                if (idx == N - 1) {
                    personPos[idx] = 0;
                } else {
                    if (D[movingWalk.get(idx + 1)] >= 1 && personPos[idx + 1] == 0) {
                        D[movingWalk.get(idx + 1)] -= 1;
                        personPos[idx + 1] = 1;
                        personPos[idx] = 0;
                    }

                }
            }
        }
    }

    private static void moveMovingWalk() {

        int last = movingWalk.get(movingWalk.size() - 1);

        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(last);
        movingWalk.remove(movingWalk.size() - 1);
        movingWalk.addAll(0, temp);

        for(int i=N-1;i>-1;i--)
        {
            if(personPos[i]==1)
            {
                if(i==N-1)
                {
                    personPos[i]=0;
                }
                else
                {
                    personPos[i]=0;
                    personPos[i+1]=1;
                }

            }

        }

    }

    private static boolean check()
    {
        kCnt=0;
        for (int i = 0; i < N * 2; i++) {
            if (D[i] <= 0) {
                kCnt++;
            }
        }

        if (kCnt >= K)
            return false;
        return true;
    }

}