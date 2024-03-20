import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N,M,C;
    static int[][] board;
    static int NX,NY;
    static ArrayList<Person> person;
    static int[] dx ={0,0,-1,1};
    static int[] dy ={1,-1,0,0};
    static int answer;
    public static class Person implements Comparable<Person>
    {
        int sx;
        int sy;
        int ex;
        int ey;

        public Person(int sx, int sy, int ex, int ey) {
            this.sx = sx;
            this.sy = sy;
            this.ex = ex;
            this.ey = ey;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "sx=" + sx +
                    ", sy=" + sy +
                    ", ex=" + ex +
                    ", ey=" + ey +
                    '}';
        }

        @Override
        public int compareTo(Person o) {
            return this.sx-o.sx;
        }
    }
    public static void main(String[] args) throws IOException{

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        N=Integer.parseInt(st.nextToken());
        M=Integer.parseInt(st.nextToken());
        C=Integer.parseInt(st.nextToken());

        board = new int[N][N];
        person = new ArrayList<>();
        for(int i=0;i<N;i++)
        {
            st=new StringTokenizer(bf.readLine());
            for(int j=0;j<N;j++)
            {
                int t=Integer.parseInt(st.nextToken());

                if(t==1)
                {
                    board[i][j]=-1;
                }
                else
                {
                    board[i][j]=0;
                }
            }
        }

        st=new StringTokenizer(bf.readLine());
        NX=Integer.parseInt(st.nextToken())-1;
        NY=Integer.parseInt(st.nextToken())-1;
        for(int i=0;i<M;i++)
        {
            st= new StringTokenizer(bf.readLine());

            int sx=Integer.parseInt(st.nextToken())-1;
            int sy=Integer.parseInt(st.nextToken())-1;
            int ex=Integer.parseInt(st.nextToken())-1;
            int ey=Integer.parseInt(st.nextToken())-1;


            person.add(new Person(sx,sy,ex,ey));
        }

        while(true)
        {
            if(person.size()==0)
            {
    answer=C;
                break;
            }

            int[] info = findNearPerson();
            int personIdx = info[0];
            int d = info[1];

            if(C-d<0)
            {
                answer=-1;
                break;
            }

            C-=d;
            NX=person.get(personIdx).sx;
            NY=person.get(personIdx).sy;

            d = bfs(person.get(personIdx).ex,person.get(personIdx).ey);
            if(C-d<0)
            {
                answer=-1;
                break;
            }

            C+= d;
            NX=person.get(personIdx).ex;
            NY=person.get(personIdx).ey;
            person.remove(personIdx);

        }

        System.out.println(answer);
    }

    public static int[] findNearPerson()
    {

        int minD=Integer.MAX_VALUE;
        int idx=0;
        Collections.sort(person);

        for(int i=0;i<person.size();i++)
        {
            Person p = person.get(i);


            int minNum=bfs(p.sx,p.sy);

            if(minNum<minD)
            {
                minD=minNum;
                idx=i;
            }




        }
        return new int[]{idx,minD};
    }
    public static int bfs(int ax,int ay)
    {

        int[][] tempBoard = new int[N][N];

        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                tempBoard[i][j]=board[i][j];
            }
        }

        boolean[][] v= new boolean[N][N];

        Queue<int[]> q = new LinkedList<>();

        q.add(new int[]{NX,NY,0});
        tempBoard[NX][NY]=1;
        v[NY][NY]=true;
        int num=0;
        while(!q.isEmpty())
        {
            int[] now =q.poll();

            int nowx = now[0];
            int nowy = now[1];
            //System.out.println(nowx);
            int cnt= now[2];
            if(nowx==ax && nowy==ay)
            {
                num= now[2];
                break;
            }


            for(int i=0;i<4;i++)
            {
                int nx= nowx+dx[i];
                int ny= nowy+dy[i];

                if(isBoundary(nx,ny) && tempBoard[nx][ny]==0)
                {
                    q.add(new int[]{nx,ny,cnt+1});

                    v[nx][ny]=true;
                }
            }



        }
return num;
    }
    public static boolean isBoundary(int x,int y)
    {
        return x>=0 && x<N && y>=0 && y<N;
    }
}