import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N,M;
    static int[][] board;
    static boolean[][] v;
    static int[] dx={0,0,-1,1};
    static int[] dy={1,-1,0,0};
    static ArrayList<Bomb> bomb;
    static int answer;
    static boolean flag;
    public static class Bomb{
        ArrayList<int[]> RED;
        ArrayList<int[]> NUM;

        int SIZE;
        int RED_CNT;

        int ROW;
        int COL;
        public Bomb(){}

        public Bomb(int SIZE,ArrayList<int[]> RED, ArrayList<int[]> NUM, int RED_CNT, int ROW, int COL) {
            this.SIZE=SIZE;
            this.RED = RED;
            this.NUM = NUM;
            this.RED_CNT = RED_CNT;
            this.ROW = ROW;
            this.COL = COL;
        }

        @Override
        public String toString() {

            StringBuilder sb= new StringBuilder();
            for(int i=0;i<RED.size();i++)
            {
                System.out.print(Arrays.toString(RED.get(i))+" ");
            }
            for(int i=0;i<NUM.size();i++)
            {
                System.out.print(Arrays.toString(NUM.get(i))+" ");

            }
            return "Bomb{" +
                    ", SIZE=" + SIZE +
                    ", RED_CNT=" + RED_CNT +
                    ", ROW=" + ROW +
                    ", COL=" + COL +
                    '}';
        }

//        @Override
//        public int compare(Bomb o1, Bomb o2) {
//
//            if(o1.SIZE != o2.SIZE)
//            {
//                return Integer.compare(o1.SIZE,o2.SIZE);
//            }
//            else if(o1.RED_CNT != o2.RED_CNT)
//            {
//                return Integer.compare(o1.RED_CNT,o2.RED_CNT);
//            }
//            else if(o1.ROW != o2.ROW)
//            {
//                return Integer.compare(o1.ROW,o2.ROW);
//            }
//            else
//            {
//                return Integer.compare(o1.COL,o2.COL);
//            }
//        }
    }
    public static class BombComparator implements Comparator<Bomb>
    {

        @Override
        public int compare(Bomb o1, Bomb o2) {


                if(o1.SIZE != o2.SIZE)
                {
                    return Integer.compare(o2.SIZE,o1.SIZE);
                }
                else if(o1.RED_CNT != o2.RED_CNT)
                {
                    return Integer.compare(o1.RED_CNT,o2.RED_CNT);
                }
                else if(o1.ROW != o2.ROW)
                {
                    return Integer.compare(o2.ROW,o1.ROW);
                }
                else
                {
                    return Integer.compare(o1.COL,o2.COL);
                }

        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bf =new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N=Integer.parseInt(st.nextToken());
        M= Integer.parseInt(st.nextToken());

        board = new int[N][N];


        for(int i=0;i<N;i++)
        {
            st=new StringTokenizer(bf.readLine());
            for(int j=0;j<N;j++)
            {
                board[i][j]=Integer.parseInt(st.nextToken());
            }
        }

        flag=true;

        while(true)
        {
            bomb =new ArrayList<>();
            v= new boolean[N][N];
            findBomb();

            flag=deleteBomb();

//            System.out.println("삭제후");
//            for(int[] b:board)
//            {System.out.println(Arrays.toString(b));}
//            System.out.println();
//            System.out.println();

            if(flag==false)

            {
                System.out.println(answer);
                break;
            }

            gravity();





            turn();


            gravity();

//            for(int[] b:board)
//            {System.out.println(Arrays.toString(b));}
//            System.out.println();
//            System.out.println();
        }


    }


    private static boolean deleteBomb() {


//        for(int i=0;i< bomb.size();i++)
//        {
//            System.out.println(bomb.get(i));
//        }

        if(bomb.size()>=1)
        {   Collections.sort(bomb, new BombComparator());
            Bomb deleteBomb = bomb.get(0);

            //System.out.println(deleteBomb);
            ArrayList<int[]> num = deleteBomb.NUM;
            ArrayList<int[]> red = deleteBomb.RED;
            for(int[] n : num)
            {
                board[n[0]][n[1]]=-2;
            }
            for(int[] n : red)
            {
                board[n[0]][n[1]]=-2;
            }

            int cnt = num.size()+red.size();

            answer+=cnt*cnt;
            return true;
        }
        else {//(bomb.size()==0) {
            return false;
        }


    }

    private static void findBomb() {

        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if(board[i][j]!= -1 && board[i][j]!=0 && board[i][j]!=-2 && v[i][j]==false)
                {
                    bfs(i,j);
                }
            }
        }

    }
    private static void bfs(int x,int y)
    {
        int NUM= board[x][y];


        ArrayList<int[]> red= new ArrayList<>();
        ArrayList<int[]> num= new ArrayList<>();

        Queue<int[]> q = new LinkedList<>();


        q.add(new int[]{x,y});

        while(!q.isEmpty())
        {
            int[] now= q.poll();

            for(int idx=0;idx<4;idx++) {
                int nx = now[0] + dx[idx];
                int ny = now[1] + dy[idx];


                if(isBoundary(nx,ny) && v[nx][ny]==false)
                {
                    if(board[nx][ny]==NUM || board[nx][ny]==0)
                    {
                        q.add(new int[]{nx,ny});
                        if(board[nx][ny]==NUM)
                        {
                            v[nx][ny]=true;
                            num.add(new int[]{nx,ny});
                        }
                        else{
                            v[nx][ny]=true;
                            red.add(new int[]{nx,ny});
                        }
                    }
                }
            }



        }

        // 폭탄 조건 성공
        if(num.size() >=2 || (num.size()+red.size())>=2)
        {

            num.sort(new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {

                    if(o1[0] !=o2[0])
                    {
                        return Integer.compare(o2[0],o1[0]);
                    }
                    else
                    {
                        return Integer.compare(o1[1],o2[1]);
                    }
                }
            });
            //System.out.println("폭탄 만들어진거");
//            for(int[] a: num){
//                System.out.println(Arrays.toString(a));
//            }
//            System.out.println("----");
//            for(int[] a: red){
//                System.out.println(Arrays.toString(a));
//            }
            //        public Bomb(int SIZE,ArrayList<int[]> RED, ArrayList<int[]> NUM, int RED_CNT, int ROW, int COL) {
            int[] rc= num.get(0);
            bomb.add(new Bomb(num.size()+red.size(),red,num,red.size(),rc[0],rc[1]));



        }

        for(int[] redback: red)
        {
            v[redback[0]][redback[1]]=false;
        }

    }

    private static void turn() {

        int[][] temp= new int[N][N];

        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                temp[i][j]=board[j][N-1-i];
            }
        }

        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                board[i][j]=temp[i][j];
            }
        }
    }

    private static void gravity() {

        for(int i=N-1;i>-1;i--)
        {
            for(int j=0;j<N;j++)
            {
                if (board[i][j]>=0)
                {
                    for(int idx=i+1;idx<N;idx++)
                    {
                        if(board[idx][j]==-2)
                        {
                            board[idx][j]=board[idx-1][j];
                            board[idx-1][j]=-2;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static boolean isBoundary(int x,int y)
    {
        return x>=0 && x<N && y>=0 && y<N;
    }


}