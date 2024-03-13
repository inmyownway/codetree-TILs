import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {


    public static class Fish implements Comparable<Fish>{
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
            return this.num-o.num;
        }
    }
    static int N=4;
    static ArrayList<Fish> fish;
    static int[] dx={-1,-1,0, 1,  1, 1 ,0, -1};
    static int[] dy={0,-1,-1,-1, 0, 1, 1, 1};
    static int answer=Integer.MIN_VALUE;
    public static void main(String[] args) throws IOException {

        BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        fish= new ArrayList<>();
        int idx=0;
        for(int i=0;i<4;i++)
        {
            st= new StringTokenizer(bf.readLine());
            for(int j=0;j<4;j++)
            {
                int n = Integer.parseInt(st.nextToken());
                int d= Integer.parseInt(st.nextToken())-1;
                fish.add( new Fish(i,j,n,d));
            }
        }
        int tx= 0;
        int ty=0;
        int td= fish.get(0).dir;
        fish.get(0).x=-1;
        fish.get(0).y=-1;

        int score= fish.get(0).num;
        Collections.sort(fish);

        //  print(fish);
        // System.out.println();
        dfs(tx,ty,td,fish,score);
        System.out.println(answer);
    }
    public static void dfs(int cx,int cy,int cd,ArrayList<Fish> fishes,int s)
    {
        ArrayList<Fish> tempFish =fishes;

        for(Fish f:tempFish)
        {
            //System.out.println("움직이는거 "+f);
            //System.out.println(f.x+ " "+f.y+" "+f.dir);
            boolean flag =false;
            for(int i=0;i<8;i++)
            {
                int nx= f.x+dx[(f.dir+i)%8];
                int ny= f.y+dy[(f.dir+i)%8];
                //  System.out.println("i: "+i+" ("+nx+ ","+ny+")");
                if(!isBoundary(nx,ny) || (nx==cx && ny==cy))
                {
                    // 밖이거나 , 술래랑 같으면 반시계로 45도 돌ㄱ;
                    continue;
                }
                // System.out.println(nx+" "+ny);
                // 가려는 칸에 뭐가 있거나 없는지 확인
                boolean isEmpty = true;

                for(Fish check : tempFish)
                {
                    //  System.out.println("@ "+check);
                    if(check.num!=f.num)
                    {
                        if(check.x==nx && check.y==ny)
                        {
                            //  System.out.println("true");
                            // 가려는곳에 뭐가있으면 교환하기

                            check.x=f.x;
                            check.y=f.y;

                            f.x=nx;
                            f.y=ny;
                            f.dir= (f.dir+i)%8;




                            flag=true;
                            isEmpty=false;
                            // System.out.println(f);
                            //System.out.println(check);
                            //System.out.println(" ");
                            break;
                        }
                    }



                } if(isEmpty)
            {
                f.x=nx;
                f.y=ny;
                f.dir= (f.dir+i)%8;


                flag=true;
                break;
            }
                if(flag)
                    break;
            }


            //  print(tempFish);
            //  System.out.println();
            // System.out.println();
        }


        // 술래움직이기
        for(int c=1;c<5;c++)
        {
            int currentX= cx;
            int currentY=cy;


            currentX+= dx[cd]*c;
            currentY+=dy[cd]*c;
            // 애를 먹을거임

            boolean flag= false;
            if(isBoundary(currentX,currentY))
            {
                for(Fish q: tempFish)
                {
                    // 누군가 있는경우
                    if(currentX== q.x && currentY==q.y)
                    {
                        //System.out.println(q.x+" " +q.y);
                        int newX=q.x;
                        int newY=q.y;
                        int newDir= q.dir;

                        q.x=-1;
                        q.y=-1;
                        flag=true;
                        dfs(newX,newY,newDir,tempFish,s+q.num);

                        q.x=currentX;
                        q.y=currentY;

                    }
                }
            }
            if(flag==false)
            {
                answer=Math.max(answer,s);
            }
        }

        // System.out.println("end");
        // System.out.println();
    }

    public static void print(ArrayList<Fish> fa)
    {
        for(Fish ff: fa)
        {
            System.out.println(ff);
        }

    }
    public static boolean isBoundary(int x,int y)
    {
        return x>=0 && x<N && y>=0 && y<N;
    }

}