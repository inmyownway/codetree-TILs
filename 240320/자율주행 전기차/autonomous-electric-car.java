import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
    static boolean[][] v;
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


            if(d==-1)
            {
                answer=-1;
                break;
            }


            if(C-d<0)
            {
                answer=-1;
                break;
            }

            C-=d;
            NX=person.get(personIdx).sx;
            NY=person.get(personIdx).sy;

            d = bfs(person.get(personIdx).ex,person.get(personIdx).ey);
            if(d==-1)
            {
                answer=-1;
                break;
            }

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

        int tx=NX;
        int ty=NY;
       v= new boolean[N][N];

       v[tx][ty]=true;

       Queue<int[]> q = new LinkedList<>();
       q.add(new int[]{tx,ty,0});

       int aa=0;

       while(!q.isEmpty())
       {
           int[] now = q.poll();

           if(now[0]==ax && now[1]==ay)
           {
               return now[2];

           }
           for(int i=0;i<4;i++)
           {
               int nx= now[0]+dx[i];
               int ny= now[1]+dy[i];

               if(isBoundary(nx,ny) && v[nx][ny]==false && board[nx][ny]==0)
               {
                   v[nx][ny]=true;
                   q.add(new int[]{nx,ny,now[2]+1});
               }
           }
       }
        return -1;

    }
    public static boolean isBoundary(int x,int y)
    {
        return x>=0 && x<N && y>=0 && y<N;
    }
}