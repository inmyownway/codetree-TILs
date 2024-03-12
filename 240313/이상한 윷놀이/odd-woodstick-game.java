import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
       static int N;
       static int K;
    static int[] dx = new int[]{0, 0, -1, 1};
    static int[] dy = new int[]{1, -1, 0, 0};
    static int[][] board;
    static Node[] node;
    static ArrayList<Node> curNode;
    public static class Node implements Comparable<Node> {
        int x;
        int y;
        int dir;
        int pos;

        public Node(int x, int y, int dir, int pos) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.pos = pos;
        }

        public int compareTo(Node o1) {
            return this.pos - o1.pos;
        }

        public String toString() {
            return "Node [x=" + this.x + ", y=" + this.y + ", dir=" + this.dir + ", pos=" + this.pos + "]";
        }
    }
    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        node = new Node[K];


        board = new int[N][N];

        for(int i=0;i<N;i++)
        {
            st= new StringTokenizer(bf.readLine());
            for(int j=0;j<N;j++)
            {
                board[i][j]=Integer.parseInt(st.nextToken());
            }

        }
        for(int i=0;i<K;i++)
        {
            st= new StringTokenizer(bf.readLine());

            int x= Integer.parseInt(st.nextToken())-1;
            int y= Integer.parseInt(st.nextToken())-1;
            int d= Integer.parseInt(st.nextToken())-1;
            node[i]=new Node(x,y,d,1);
        }

       // System.out.println("!초기 상황");
//        for(Node n: node){
//            System.out.println(n);
//        }

        simulate();

       // System.out.println();

    }
    public static void simulate()
    {
        int round=0;
        boolean flag= false;
        while(true)
        {
            round++;
            //System.out.println("round: "+round);
            if(round>1000) {

             round=-1;   break;
            }


            // 같은 층에있는거 확인하기

            for(int idx=0;idx<K;idx++)
            {
                curNode = new ArrayList<>();
                // node[idx] 는 현재 노드
                curNode.add(node[idx]);
              //  System.out.println("현재 "+node[idx]);

                // 위에있는 애들 넣어주기
                for(int i=0;i<K;i++)
                {
                    if(idx!=i)
                    {
                        if(node[idx].x==node[i].x && node[idx].y==node[i].y && node[idx].pos<node[i].pos)
                        {
                            curNode.add(node[i]);
                        }
                    }
                }
                // 움직일 기둥들
               // System.out.println("움직일 기둥");
                Collections.sort(curNode);
               // print(curNode);

                int nx= node[idx].x+dx[node[idx].dir];
                int ny= node[idx].y+dy[node[idx].dir];

               // System.out.println(nx+" "+ny);
                // 넘어가거나 파란색이면 방향 바꿔줘야함
                if(!isBoundary(nx,ny) || board[nx][ny]==2)
                {
                    node[idx].dir=rotate(node[idx].dir);
                    nx=node[idx].x+dx[node[idx].dir];
                    ny= node[idx].y+dy[node[idx].dir];
                }
                if(!isBoundary(nx,ny)|| board[nx][ny]==2)
                {
                    continue;
                }
             //  System.out.println(nx+" "+ny);
                // 흰색이니까
                if(board[nx][ny]==0)
                {
                    // board[nx][ny]에 몇개가 있나 확인해줘야함

                    int size=0;


                    for(int j=0;j<K;j++)
                        if(nx==node[j].x && ny==node[j].y) size++;
                    //System.out.println("size:"+size);

                    moveWhite(size);
                }
                // 4넘는지 확인
                else if(board[nx][ny]==1)
                {int size=0;

                    for(int j=0;j<K;j++)
                        if(nx==node[j].x && ny==node[j].y) size++;
                    moveRed(size);
                }
                // 4넘는지 확인
                //System.out.println("현재 상황");
//                for(Node n: node){
//                    System.out.println(n);
//                }

                for(int a=0;a<K;a++)
                {
                    if(node[a].pos>=4)
                    {
                        flag=true;
                        break;
                    }
                }


            }
            if(flag)
            {
               // System.out.println("4넘음");
                break;
            }


        }
        System.out.println(round);
    }
    public static void moveWhite(int s)
    {
        int dir= curNode.get(0).dir;
        //System.out.println("white");
        for(int i=1;i<curNode.size()+1;i++)
        {
            curNode.get(i-1).x+=dx[dir];
            curNode.get(i-1).y+=dy[dir];
            curNode.get(i-1).pos=s+i;
        }
    }
    public static void moveRed(int s)
    {
        int dir= curNode.get(0).dir;

       // System.out.println("red");


        for(int i=curNode.size()-1;i>-1;i--)
        {
            curNode.get(i).x+=dx[dir];
            curNode.get(i).y+=dy[dir];
            curNode.get(i).pos=curNode.size()-(i)+s;
        }


    }
    public static void print(ArrayList<Node> nn)
    {
        for(Node n: nn)
        {
            System.out.println(n);
        }
    }
    public static boolean isBoundary(int x,int y)
    {
        return x>=0 && x<N && y>=0 && y<N;

    }
    public static int rotate(int d)
    {
        if(d==0)
            return 1;
        else if(d==1)
            return 0;
        else if(d==2)
            return 3;
        else
            return 2;
    }
}