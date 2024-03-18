import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int N,M,K;
    static int[][][] board;
    static ArrayList<Person> person;
    static int[] dx={-1,1,0,0};
    static int[] dy={0,0,-1,1};
    static int answer;
    static class  Person implements Comparable<Person>{
        int x;
        int y;
        int num;
        int dir;
        int[][] d;

        public Person(int x, int y, int num, int dir, int[][] d) {
            this.x = x;
            this.y = y;
            this.num = num;
            this.dir = dir;
            this.d = d;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "x=" + x +
                    ", y=" + y +
                    ", num=" + num +
                    ", dir=" + dir +
                    '}';
        }

        @Override
        public int compareTo(Person o) {
            return this.num-o.num;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st= new StringTokenizer(bf.readLine());

        N=Integer.parseInt(st.nextToken());
        M=Integer.parseInt(st.nextToken());
        K=Integer.parseInt(st.nextToken());

        board=new int[N][N][2];
        person= new ArrayList<>();
        for(int i=0;i<M;i++)
        {
           person.add(new Person(0,0,0,0,new int[4][4]));

        }
        for(int i=0;i<N;i++)
        {
            st=new StringTokenizer(bf.readLine());
            for(int j=0;j<N;j++)
            {
                int temp=Integer.parseInt(st.nextToken());

            if(temp!=0)
            {
                person.get(temp-1).x=i;
                person.get(temp-1).y=j;
                person.get(temp-1).num=temp-1;

                board[i][j][0]=K;
                board[i][j][1]=temp;
            }
            }
        }
        st= new StringTokenizer(bf.readLine());
        for(int i=0;i<M;i++)
        {
            int temp = Integer.parseInt(st.nextToken());
         //  System.out.println("t"+temp);


            person.get(i).dir=temp-1;
        }

        for(int i=0;i<M;i++)
        {
            for(int a=0;a<4;a++)
            {   st=new StringTokenizer(bf.readLine());
                for(int b=0;b<4;b++)
                {
                    person.get(i).d[a][b]=Integer.parseInt(st.nextToken())-1;
                }
            }
        }
//        for(Person p : person)
//        {
//            System.out.println(p);
//        }
////
//        for(Person p:person)
//        {
//
//                for(int j=0;j<4;j++)
//                {
//                    System.out.println(Arrays.toString(p.d[j]));
//                }
//            System.out.println();
//
//        }

        int time=0;
//        for(int i=0;i<N;i++)
//        {
//            for(int j=0;j<N;j++)
//            {
//                System.out.print(board[i][j][1]+" ");
//            }
//            System.out.println();
//        }
        while(true)
        {
//            System.out.println("time "+ (time+1));
//            System.out.println("남은거");
////        /*                  for(int q=0;q<N;q++)
////                {
////                    for(int w=0;w<N;w++)
////                    {
////                        System.out.print(board[q][w][0]+" ");
////                    }
////                    System.out.println();
////                }*/
//           / System.out.println("넘버");
//            for(int q=0;q<N;q++)
//            {
//                for(int w=0;w<N;w++)
//                {
//                    System.out.print(board[q][w][1]+" ");
//                }
//                System.out.println();
//            }
          //  System.out.println();
            if((person.size()==1 && person.get(0).num==0)|| time>=1000)
            {
                if(time==1000)
                {
                    answer=-1;
                }
                else
                {
                    answer=time;
                }
                break;
            }


            move();
            //print();
            deleteDuplicate();
            t();
            stayK();
       //     print();


//            if(time<10)
//            {        System.out.println(time);
//                print();
//            }
            //print();
        time++;
        }
        System.out.println(answer);

    }
    public static void t()
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if(board[i][j][0]>=1)
                {
                    board[i][j][0]-=1;
                    if(board[i][j][0]==0)
                    {
                        board[i][j][1]=0;
                    }

                }
            }
        }
    }

    public static void move()
    {
        for(int i=0;i<person.size();i++)
        {
            Person p = person.get(i);


            boolean flag= false;
            int currnetDir = p.dir;





            for(int idx=0;idx<4;idx++)
            {


                int nx = p.x+dx[p.d[currnetDir][idx]];
                int ny = p.y+dy[p.d[currnetDir][idx]];



                if(isBoundary(nx,ny)&& board[nx][ny][1]==0)
                {

                    p.x=nx;
                    p.y=ny;
                    p.dir=idx;
                    flag=true;
                    break;
                }
            }
            if(flag)
            {
                continue;
            }

            for(int idx=0;idx<4;idx++)
            {

                int nx = p.x+dx[p.d[currnetDir][idx]];
                int ny = p.y+dy[p.d[currnetDir][idx]];
                if(isBoundary(nx,ny)&& board[nx][ny][1]==p.num+1)
                {
                    p.x=nx;
                    p.y=ny;
                    p.dir=idx;
                    flag=true;
                    break;
                }
            }
        }

    }
    public static void deleteDuplicate()
    {
        ArrayList<Person> temp = new ArrayList<>();

        boolean[] v= new boolean[person.size()];
       // System.out.println(Arrays.toString(v));
        for(int i=0;i<person.size();i++)
        {

            if(!v[i]) {
                ArrayList<Person> dup = new ArrayList<>();
                dup.add(person.get(i));
                v[i] = true;

                for (int j = 0; j < person.size(); j++) {
                    if (i != j && v[j] == false) {
                        if (person.get(i).x == person.get(j).x && person.get(i).y == person.get(j).y) {
                            v[j] = true;
                           // System.out.println(person.get(j));
                            dup.add(person.get(j));

                        }
                    }

                }
              //  System.out.println(Arrays.toString(v));

                Collections.sort(dup);
              //  System.out.println(dup);
                temp.add(dup.get(0));
            }
        }

        person=new ArrayList<>();
        for(Person ta:temp)
        {
            person.add(ta);
        }

    }
    public static void stayK()
    {
        for(Person p:person)
        {
            board[p.x][p.y][0]=K;
            board[p.x][p.y][1]=p.num+1;
        }

    }
    public static boolean isBoundary(int x,int y)
    {
        return x>=0 && x<N && y>=0 && y<N;
    }
//    public static void print()
//    {
//        for(Person p : person)
//        {
//            System.out.println(p);
//        }
//    }

}