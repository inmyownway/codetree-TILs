import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main {
    static int N,M,C;
    static int[][] board;
    static int NX,NY;
    static ArrayList<Person> person;
    static int[] dx ={-1,0,0,1};
    static int[] dy ={0,-1,1,0};
    static int answer;
    static boolean[][] v;
    public static class Person implements Comparable<Person>
    {
        int sx;
        int sy;
        int ex;
        int ey;
        int num;
        int d;

        public Person(int sx, int sy, int ex, int ey,int num,int d) {
            this.sx = sx;
            this.sy = sy;
            this.ex = ex;
            this.ey = ey;
            this.num=num;
            this.d=d;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "sx=" + sx +
                    ", sy=" + sy +
                    ", ex=" + ex +
                    ", ey=" + ey + " num= "+num+   
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
            board[sx][sy]=i+1;


            person.add(new Person(sx,sy,ex,ey,i+1,0));
        }

        
        while(true)
        {
//        	for(int[] b:board)
//        	
//        {
//        	System.out.println(Arrays.toString(b));
//        }
            if(person.size()==0)
            {
                answer=C;
                break;
            }

            int[] info = findNearPerson();
            if(info[0]==-1)
            {
            	answer=-1;
            	break;
            }
            int personIdx=board[info[0]][info[1]];
            if(personIdx==-1)
            {
            	answer=-1;
            	break;
            }
            for(int a=0;a<person.size();a++)
            {
            	if(person.get(a).num==board[info[0]][info[1]])
            	{
            		
            		personIdx=a;
            		
            		
            	}
            }
            //System.out.println("personIDx: "+personIdx);
        
            //System.out.println("내위치:" +NX+" "+NY);
            board[person.get(personIdx).sx][person.get(personIdx).sy]=0;
            int d = person.get(personIdx).d;
            
          //  System.out.println("personidx: "+person.get(personIdx));
         //   System.out.println("d: "+ d);
          

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
       //     System.out.println(d);
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

    	
    	int tx= NX;
    	int ty= NY;
    	
    	Queue<int[]> q = new LinkedList<>();
    	v= new boolean[N][N];
    	q.add(new int[] {tx,ty,0});
    	
    	
    	ArrayList<int[]> temp= new ArrayList<>();
    	
    	v[tx][ty]=true;
    	
    	while(!q.isEmpty())
    	
    	{	int l = q.size();
    		for(int s=0;s<l;s++)
    		{
    	
    		int[] now = q.poll();
    		if(board[now[0]][now[1]]>0)
    		{
    	
    			
    			for(int z=0;z<person.size();z++)
    			{
    				if(person.get(z).num==board[now[0]][now[1]])
    				{		temp.add(new int[] {now[0],now[1]});
    					person.get(z).d=now[2];
    			
    				}
    			}
    			
    		}
    	
    			for(int i=0;i<4;i++)
    			{
    				int nx= now[0]+dx[i];
    				int ny= now[1]+dy[i];
    				
    				if(isBoundary(nx, ny) && v[nx][ny]==false && board[nx][ny]!=-1)
    				{
    					q.add(new int[] {nx,ny,now[2]+1});
    					v[nx][ny]=true;
    				}
    			}
    		}
    		if(temp.size()>0)
    			break;
    		
    	}
    	
    
    	
    	 Collections.sort(temp, new Comparator<int[]>() {
             @Override
             public int compare(int[] a, int[] b) {
                 return Integer.compare(a[0], b[0]);
             }
         });
    	// System.out.println("거리같은 리스트들");
//    	 for(int[] a:temp)
//    	 {
//    		 System.out.println(Arrays.toString(a));
//    	 }
    	 
    	 if(temp.size()>0)
    	 {
    		 int[] nowTemp = temp.get(0);
    		 
    		 return new int[] {nowTemp[0],nowTemp[1]};
    	 }
    	
    	return new int[] {-1,-1};

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

               if(isBoundary(nx,ny) && v[nx][ny]==false && board[nx][ny]!=-1)
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