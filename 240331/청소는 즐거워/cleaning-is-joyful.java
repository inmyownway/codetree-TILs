import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N,nx,ny;
    static int[][] board;
    static int[] dx= {0,1,0,-1};
    static int[] dy={-1,0,1,0};
    static int[] leftX={-1,1,-1,1, -1,1,-2,2,0};
    static int[] leftY={-1,-1,0,0,  1,1, 0,0,-2};


    static int[] rightX={-1,1,-1,1,  -1,1 , -2,2 ,0};
    static int[] rightY={1,1,0,0,     -1,-1 ,0,0,2};

    static int[] upX={-1,-1,0,0,1,1,  0,0,-2};
    static int[] upY={-1,1,-1,1,1,-1,  -2,2,0};


    static int[] downX={1,1,0,0,-1,-1,0,0,2};
    static int[] downY={-1,1,-1,1,-1,1,-2,2,0};

    static int[] per={10,10,7,7,1,1,2,2,5};
    static int answer;

    public static void main(String[] args) throws IOException {

        BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N= Integer.parseInt(bf.readLine());

        // ######
        // 초기화 부분

        board= new int[N][N];

        for(int i=0;i<N;i++)
        {
            st= new StringTokenizer(bf.readLine());
            for(int j=0;j<N;j++)
            {
                board[i][j]=Integer.parseInt(st.nextToken());
            }
        }
        nx= N/2;
        ny=N/2;

        // #######

        rotate();
        System.out.println(answer);
    }
    public static void rotate()
    {
        int round=0;
        int idx=1;
        int count=0;
        int dir=0;
        while(true)
        {

          //  round++;
         //   System.out.println();
       //     System.out.println("방향 : "+dir%4);
          //  System.out.println(dx[dir%4]+" "+dy[dir%4]);
            for(int i=0;i<idx;i++)
            {
                nx+=dx[dir%4];
                ny+=dy[dir%4];

              //  System.out.println("nx,ny " +nx+" "+ny);
                clean(nx,ny,dir%4);
                board[nx][ny]=0;
                //print();
                if(nx==0 && ny==0)
                    break;

            }

//            if (round==5)
//                break;
           count++;
           if(count%2==0)
               idx++;
           dir++;





    //             종료조건
            if(nx==0 && ny==0)
            {
                break;
            }

        }
    }

    private static void clean(int x,int y,int d) {
        int curr= board[x][y];

        //System.out.println(curr);
        if(d==0)
        {
           // System.out.println("left");
            left(x,y,d);

        }
        else if(d==1)
        {
         //   System.out.println("down");
            down(x,y,d);
        }
        else if(d==2)
        {
           // System.out.println("right");
            right(x,y,d);
        }
        else if(d==3)
        {
           // System.out.println("up");
            up(x,y,d);
        }
    }

    private static void left(int x,int y,int d) {
        int curr=board[x][y];
        int tx=0;
        int ty=0;
        int sum=0;

        for(int i=0;i<9;i++)
        {
            tx=x+leftX[i];
            ty=y+leftY[i];

            sum+=Math.floor( curr*per[i]*0.01);

            if(isBoundary(tx,ty))
            {
                board[tx][ty]+= curr*(per[i]*0.01);
            }
            else {
                answer+=curr*(per[i]*0.01);
            }

        }

        if(isBoundary(x,y-1))
        {
            board[x][y-1]+=curr-sum;
        }

        else
        {
            answer+=curr-sum;
        }

    }


    private static void right(int x,int y,int d) {
        int curr=board[x][y];
        int tx=0;
        int ty=0;
        int sum=0;

        for(int i=0;i<9;i++)
        {
            tx=x+rightX[i];
            ty=y+rightY[i];

            sum+=Math.floor( curr*per[i]*0.01);

            if(isBoundary(tx,ty))
            {
                board[tx][ty]+= curr*(per[i]*0.01);
            }
            else {
                answer+=curr*(per[i]*0.01);
            }

        }

        if(isBoundary(x,y+1))
        {
            board[x][y+1]+=curr-sum;
        }

        else
        {
            answer+=curr-sum;
        }

    }

    private static void up(int x,int y,int d) {
        int curr=board[x][y];
        int tx=0;
        int ty=0;
        int sum=0;

        for(int i=0;i<9;i++)
        {
            tx=x+upX[i];
            ty=y+upY[i];

            sum+=Math.floor( curr*per[i]*0.01);

            if(isBoundary(tx,ty))
            {
                board[tx][ty]+= curr*(per[i]*0.01);
            }
            else {
                answer+=curr*(per[i]*0.01);
            }

        }

        if(isBoundary(x-1,y))
        {
            board[x-1][y]+=curr-sum;
        }

        else
        {
            answer+=curr-sum;
        }

    }

    private static void down(int x,int y,int d) {
        int curr=board[x][y];
        //System.out.println("curr "+curr);
        int tx=0;
        int ty=0;
        int sum=0;

        for(int i=0;i<9;i++)
        {
            tx=x+downX[i];
            ty=y+downY[i];
            // System.out.println(tx+ " "+ty);

            sum+=Math.floor( curr*per[i]*0.01);

            if(isBoundary(tx,ty))
            {
                board[tx][ty]+= curr*(per[i]*0.01);
            }
            else {
                answer+=curr*(per[i]*0.01);
            }

        }

        if(isBoundary(x+1,y))
        {
            board[x+1][y]+=curr-sum;
        }

        else
        {
            answer+=curr-sum;
        }
    }

    public static boolean isBoundary(int x,int y)
    {
        return x>=0 && x<N && y>=0 && y<N;
    }
    public static void print()
    {
        for(int[] b:board)
        {
            System.out.println(Arrays.toString(b));
        }
        System.out.println();
    }

}