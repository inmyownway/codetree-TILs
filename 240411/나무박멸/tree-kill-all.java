import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import javax.sound.midi.SysexMessage;

public class Main {
    static int N,year,range,stayYear;
    static int[][] board;
    static int[][] poisonBoard;
    static int answer;
    static int[] dx= {0,0,-1,1};
    static int[] dy= {1,-1,0,0};
    public static void main(String[] args) throws IOException {

        BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st= new StringTokenizer(bf.readLine());

        N=Integer.parseInt(st.nextToken());
        year=Integer.parseInt(st.nextToken());
        range=Integer.parseInt(st.nextToken());
        stayYear=Integer.parseInt(st.nextToken());

        board= new int[N][N];
        poisonBoard= new int[N][N];

        for(int i=0;i<N;i++)
        {
            st= new StringTokenizer(bf.readLine());
            for(int j=0;j<N;j++)
            {
                board[i][j]=Integer.parseInt(st.nextToken());
            }
        }

        for(int y=0;y<year;y++)
        {
//            System.out.println();
//            System.out.println("year");

            growTree();


            bunsikTree();
            updatePoisonBoard();
            spread();

//            print();
//            for(int[] c: poisonBoard)
//            {
//                System.out.println(Arrays.toString(c));
//            }

        }
        System.out.println(answer);
    }

    private static void spread() {

        int[] dx= {1,1,-1,-1};
        int[] dy= {1,-1,1,-1};

        int[][] killCoountBoard= new int[N][N];

        int maxCnt= Integer.MIN_VALUE;
        int sx=0;
        int sy=0;
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if(board[i][j]>=1)
                {
                    int sum=board[i][j];


                    for(int idx=0;idx<4;idx++)
                    {
                        int tx=i;
                        int ty=j;
                        for(int k=0;k<range;k++)
                        {
                            tx+=dx[idx];
                            ty+=dy[idx];

                            if(isBoundary(tx,ty) && board[tx][ty]>=1)
                            {
                                sum+=board[tx][ty];
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                    killCoountBoard[i][j]=sum;

                    maxCnt=Math.max(sum,maxCnt);
                }

            }
        }

        boolean flag= false;
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if (killCoountBoard[i][j]==maxCnt)

                {
                    sx=i;
                    sy=j;
                    flag=true;
                    break;

                }
            }
            if(flag)
                break;
        }

answer+=maxCnt;
        board[sx][sy]=-2;
        poisonBoard[sx][sy]=stayYear;

        for(int idx=0;idx<4;idx++)
        {
            int tx= sx;
            int ty= sy;
            for(int k=0;k<range;k++)
            {
                tx+=dx[idx];
                ty+=dy[idx];

                if(!isBoundary(tx,ty) || board[tx][ty]==-1)
                    break;
                else
                {
                    board[tx][ty]=-2;
                    poisonBoard[tx][ty]=stayYear;
                }
            }
        }
    }

    private static void bunsikTree() {
        int[][] tempBoard= new int[N][N];

        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {

                // 벽 , 다른나무, 제초제 모두없어야함

                if(board[i][j]>=1)
                {
                tempBoard[i][j]=board[i][j];
                    int sum=0;
                    for(int idx=0;idx<4;idx++)
                    {
                        int nx= i+dx[idx];
                        int ny= j+dy[idx];
                        if(isBoundary(nx,ny) && board[nx][ny]==0)
                        {
                            sum++;
                        }
                    }
                    for(int idx=0;idx<4;idx++)
                    {
                        int nx= i+dx[idx];
                        int ny= j+dy[idx];
                        if(isBoundary(nx,ny) && board[nx][ny]==0)
                        {

                            tempBoard[nx][ny]+=board[i][j]/sum;

                        }
                    }
                }
                else if(board[i][j]!=0){
                    tempBoard[i][j]=board[i][j];
                }



            }
        }
        copy(tempBoard);
    }

    private static void growTree() {

        int[][] tempBoard= new int[N][N];
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
              if(board[i][j]>=1)
              {
                  int sum=0;
                  for(int idx=0;idx<4;idx++)
                  {
                      int nx= i+dx[idx];
                      int ny= j+dy[idx];
                      if(isBoundary(nx,ny) && board[nx][ny]>=1)
                      {
                          sum++;
                      }
                  }
                  tempBoard[i][j]=board[i][j]+sum;
              }
              else
              {
                  tempBoard[i][j]=board[i][j];
              }
            }
        }

        copy(tempBoard);

    }
    public static void copy(int[][] temp)
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
            board[i][j]=temp[i][j];
            }
        }
    }

    private static void updatePoisonBoard() {

        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if (poisonBoard[i][j]>=1)
                {
                    poisonBoard[i][j]-=1;
                    if(poisonBoard[i][j]==0)
                        board[i][j]=0;
                }
            }
        }
    }
    private static boolean isBoundary(int x,int y)
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