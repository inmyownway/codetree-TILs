import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.StringTokenizer;




public class Main {

    static int T,M;
    static int px,py;
    static int[][] board;
    static ArrayList<Monster> liveMonster;
    static ArrayList<Monster> eggMonster;
    static ArrayList<int[]> moves;
    static int[] dir = {0,1,2,3};
    static int[] result;
    static int[] dx = {-1,-1,0,1  ,  1,1,0,-1};
    static int[] dy = {0, -1,-1,-1  ,0,1,1,1};
    static int[][] monsterPosBoard;
    public static class Monster{
        int x;
        int y;
        int d;

        public Monster(int x, int y, int d) {
            super();
            this.x = x;
            this.y = y;
            this.d = d;
        }

        @Override
        public String toString() {
            return "Monster [x=" + x + ", y=" + y + ", d=" + d + "]";
        }

    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st= new StringTokenizer(bf.readLine());

        M=Integer.parseInt(st.nextToken());
        T=Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        px=Integer.parseInt(st.nextToken())-1;
        py=Integer.parseInt(st.nextToken())-1;


        liveMonster= new ArrayList<>();
        eggMonster= new ArrayList<>();
        moves = new ArrayList<>();
        result= new int[3];
        board= new int[4][4]; // 시체 확인용
        monsterPosBoard= new int[4][4];
        for(int i=0;i<M;i++)
        {

            st= new StringTokenizer(bf.readLine());
            int x=Integer.parseInt(st.nextToken())-1;
            int y=Integer.parseInt(st.nextToken())-1;
            int d=Integer.parseInt(st.nextToken())-1;

            liveMonster.add(new Monster(x,y,d));
        }

        // 0 1 2 3 으로 중복 순열 만들어주기
        perm(0);

       // System.out.println("처음 ");
        //printMonster(liveMonster);
        for(int time=0;time<T;time++)
        {

           // System.out.println("# "+time);

           // System.out.println("몬스터 이동 후 ");
            monsterCopyTry();
            monsterMove();
            //printMonster(liveMonster);



            // 팩맨 이동
            packmanMove();
           // System.out.println("팩맨 이동 후 상황");
           // printMonster(liveMonster);


            // 몬스터 시체 소멸
            deadbody();

            // 몬스터 복제
            monsterCopyStart();

          //  System.out.println("턴 끝난후 상황");
            //printMonster(liveMonster);


           // System.out.println(px+" "+py);

        }
        System.out.println(liveMonster.size());
    }
    private static void monsterCopyStart() {

        for(Monster m: eggMonster)
        {
            int x= m.x;
            int y= m.y;
            int d= m.d;
            liveMonster.add(new Monster(x,y,d));

        }
        eggMonster= new ArrayList<>();
    }
    private static void deadbody() {

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(board[i][j]>=1)
                    board[i][j]-=1;
            }
        }

    }
    private static void packmanMove() {

        // 상 좌 하 우
        int[] mx = {-1,0,1,0};
        int[] my= {0,-1,0,1};

        // 1 2
        // 1 3 -> 0 3 ->

        ArrayList<Integer> eatCount= new ArrayList<>();


       // System.out.println(px+" "+py);

        //printBoard(monsterPosBoard);
        int maxEat=-1;
        for(int[] move: moves)
        {
            // move는 이동 커맨드 ex [0,1,2];
            int sum=0;
            int nx= px;
            int ny = py;
            boolean[][] tempV =new boolean[4][4];
            tempV[nx][ny]=true;
            boolean flag= false;
            for(int dir : move)
            {
                nx+=mx[dir];
                ny+=my[dir];

                if(!isBoundary(nx, ny) || tempV[nx][ny]) {

                    flag=true;

                    break;
                }
                tempV[nx][ny]=true;
                sum+=monsterPosBoard[nx][ny];


            }

            maxEat=Math.max(sum,maxEat);
           if(flag) {
               eatCount.add(0);
           }
           else
            {
                eatCount.add(sum);
            }


        }
       // System.out.println(maxEat);


        // 최대 먹이를 먹는 루트 ( 이거 나오면 sort해서 0번쨰가 조건에 맞는 루트임)
        ArrayList<int[]> routes =new ArrayList<>();



        for(int i=0;i<64;i++)
        {
            if(eatCount.get(i)==maxEat)
            {
                routes.add(moves.get(i));

            }
        }


        routes.sort(new Comparator<int[]>() {

            @Override
            public int compare(int[] o1, int[] o2) {
                // TODO Auto-generated method stub
                return Arrays.compare(o1, o2);
            }
        });


       // System.out.println(maxEat);
//        for(int[] a:routes)
//        {
//            System.out.println(Arrays.toString(a));
//        }



        int[] route= routes.get(0);

        // 몬스터 제거 ( 여기 루트에 있는 애들)

        int nx=px;
        int ny=py;

        ArrayList<Monster> tempLiveMonster = new ArrayList<>();

        int[][] way= new int[3][2];
        int rr=0;
        for(int dir : route)
        {
            nx+=mx[dir];
            ny+=my[dir];

            way[rr][0]=nx;
            way[rr][1]=ny;
            rr++;
        }

        //System.out.println(nx+" "+ny);
        px=nx;
        py=ny;
        for(Monster m: liveMonster)
        {
            if(
                    !(m.x == way[0][0] && m.y== way[0][1])
                            && 	 !(m.x == way[1][0] && m.y== way[1][1])
                            && 	 !(m.x == way[2][0] && m.y== way[2][1])
            )
            {
                tempLiveMonster.add(m);
            }
            else
            {
                board[m.x][m.y]=3;
            }
        }

        liveMonster = new ArrayList<>();
        for(int i=0;i<tempLiveMonster.size();i++)
        {
            liveMonster.add(tempLiveMonster.get(i));
        }



    }


    private static void monsterCopyTry() {

        for(int i=0;i<liveMonster.size();i++)
        {
            Monster tempMonster= liveMonster.get(i);

            int x= tempMonster.x;
            int y= tempMonster.y;
            int d= tempMonster.d;

            eggMonster.add(new Monster(x,y,d));
        }

    }
    private static void monsterMove() {

        monsterPosBoard= new int[4][4];

       // printBoard(monsterPosBoard);
        boolean flag;
        for(Monster m : liveMonster)
        {
            int x= m.x;
            int y= m.y;
            int d= m.d;

            flag=false;
            for(int i=d;i<d+8;i++)
            {
                if(i>=8)
                    i-=8;

                int nx = x+dx[i];
                int ny= y+dy[i];
                if(isBoundary(nx, ny) && board[nx][ny]==0 && !(nx== px && ny==py))
                {
                    m.x= nx;
                    m.y = ny;
                    m.d = i;

                    monsterPosBoard[nx][ny]+=1;
                    flag=true;
                    break;
                }
            }

            if(flag)
                continue;
            monsterPosBoard[x][y]+=1;
        }

    }
    public static void perm(int depth)
    {
        if(depth==3)
        {

            int[] temp = new int[3];

            for(int i=0;i<3;i++)
            {
                temp[i]=result[i];
            }
            moves.add(temp);
            return;
        }
        for(int i=0;i<4;i++)
        {
            result[depth]=dir[i];
            perm(depth+1);
        }
    }
    public static boolean isBoundary(int x,int y)
    {
        return x>=0 && x<4 && y>=0 && y<4;
    }
    public static void printMonster(ArrayList<Monster> monster)
    {
        for(Monster p:monster)
        {
            System.out.println(p);
        }
        System.out.println();

    }
    public static void printBoard(int[][] board)
    {
        for(int[] b: board)
        {
            System.out.println(Arrays.toString(b));
        }
        System.out.println();
    }

}