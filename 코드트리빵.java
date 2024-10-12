package codingtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.io.*;


public class BOJ_코드트리빵 {

	static int[][] board;
	static int N,M;
	static int[][] conv;
	static boolean[][] v;
	static boolean[][] visited;
	static ArrayList<Base> base;
	static ArrayList<Person> person;
	static int[] dx= {-1,0,0,1};
	static int[] dy= {0,-1,1,0};
	static ArrayList<Base> selectBase;
	static int arriveCnt;
	static class Node{
		int x;
		int y;
		int cnt;
		int startIdx;
		public Node(int x, int y,int cnt,int startIdx) {
			super();
			this.x = x;
			this.y = y;
			
			this.cnt=cnt;
	this.startIdx=startIdx;
		}
		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + ", cnt=" + cnt + "  "+ startIdx;
		}
		
		
	}
	static class Base implements Comparable<Base>{
		int num;
		int x;
		int y;
		int d;
		public Base(int num, int x, int y,int d) {
			super();
			this.num = num;
			this.x = x;
			this.y = y;
			this.d=d;
		}
		
		@Override
		public String toString() {
			return "Base [num=" + num + ", x=" + x + ", y=" + y + ", d=" + d + "]";
		}

		@Override
		public int compareTo(Base o)
		{
			if(o.d==this.d)
			{
			if(this.x==o.x)
				{
					return Integer.compare(this.y,o.y);
				}
			return this.x-o.x;
			}
			return this.d-o.d;
		
		}}
	
	
	static class Person{
		int num;
		int x;
		int y;
		public Person(int num, int x, int y) {
			super();
			this.num = num;
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "Person  num=" + num + " (" + x + "," + y + ")";
		}
		
	}
	public static void main(String[] args) throws IOException
	{

	
		BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(bf.readLine());
		
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		base= new ArrayList<>();
		conv = new int[M][2];
		board= new int[N][N];
		v= new boolean[N][N];
		 visited= new boolean[N][N];
		 person = new ArrayList<>();
		int baseNum= 1;
		for(int i=0;i<N;i++)
		{
			
			st= new StringTokenizer(bf.readLine());
			for(int j=0;j<N;j++)
			{
				board[i][j]= Integer.parseInt(st.nextToken());
				if(board[i][j]==1)
				{
					base.add(new Base(baseNum++,i,j,0));
				}
			}
		}
		
		for(int i=0;i<M;i++)
		{			st= new StringTokenizer(bf.readLine());

			int x= Integer.parseInt(st.nextToken())-1;
			int y= Integer.parseInt(st.nextToken())-1;
			
			conv[i][0]=x;
			conv[i][1]=y;
			
		}
		
		

		
		int time=1;
	
		while(true)
		{	//System.out.println("time "+time);
			if(arriveCnt==M)
			{
				break;
			}
			
			personMove();
			
			if(time<=M)
			{
				int personNum= time;
				findBase(personNum);
			}
			
	
		//	print();
			time++;
			
			
			
		}
		System.out.println(time-1);
		
		
		
		
		
	}
	public static void personMove()
	{
		//System.out.println("personMove");
		
		ArrayList<int[]> arrive= new ArrayList<>();
		for(int i=0;i<person.size();i++)
		{
	
			Person p= person.get(i);	
			if(p.num==-1)
				continue;
			//System.out.println(p);
			int[] cv= conv[i];
			int cx= cv[0];
			int cy= cv[1];
			
	
		
			for(int a=0;a<N;a++)
			{
				for(int b=0;b<N;b++)
				{
					visited[a][b]=false;
				}
			}
		
			Queue<Node> q= new LinkedList<>();
			
			ArrayList<Integer> arr= new ArrayList<>();
			
			q.add(new Node(p.x,p.y,0,0));
			
			
			
			ArrayList<Node> route= new ArrayList<>();
			int cnt= Integer.MAX_VALUE;
			int direction = Integer.MAX_VALUE;
			while(!q.isEmpty())
			{
				Node n = q.poll();
			//	System.out.println(n);
				int x= n.x;
				int y= n.y;
				
				if(x==cx && y==cy)
				{
				
					route.add(n);
					break;
				
				}
				boolean flag= false;
				

				for(int idx=0;idx<4;idx++)
				{
					int nx= x+dx[idx];
					int ny= y+dy[idx];
				
					if(isBoundary(nx,ny) && !v[nx][ny] && !visited[nx][ny])
					{
						
				
							int tempD=0;
							if(n.cnt+1==1)
							{
								tempD=idx;
							}
							q.add(new Node(nx,ny,n.cnt+1,n.startIdx+tempD));
							visited[nx][ny]=true;
					}
				}
				if(flag)
					break;
			}
				
			p.x+=dx[route.get(0).startIdx];
			p.y+=dy[route.get(0).startIdx];
			
			if(p.x== cx && p.y==cy)
			{
				arriveCnt++;
				p.num=-1;
				arrive.add(new int[] {p.x,p.y});
			}
			
		}
		for(int[] a: arrive)
		{
			v[a[0]][a[1]]=true;
		}
			
			
			
		
	}
	public static void findBase(int num)
	{
		int[] cv = conv[num-1];
		
		int cx= cv[0];
		int cy=cv[1];
		//System.out.println("conv: "+cx+" "+cy);
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
				
			{
				visited[i][j]=false;
			}
		}
		selectBase= new ArrayList<>();
	
			
			Queue<int[]> q= new LinkedList<>();
			q.add(new int[] {cx,cy,0});
			visited[cx][cy]=true;
			
			while(!q.isEmpty())
			{
				int[] now = q.poll();
				
				int x= now[0];
				int y= now[1];
				
				for(int idx=0;idx<base.size();idx++)
				{
					
					int bx= base.get(idx).x;
					int by= base.get(idx).y;
				
					if(x==bx && y==by)
					{
						selectBase.add(new Base(idx+1,x,y,now[2]));
						break;
					}
				}
				
				for(int i=0;i<4;i++)
				{
					int nx= x+dx[i];
					int ny= y+dy[i];
					
					if(isBoundary(nx,ny) && !v[nx][ny] && !visited[nx][ny])
					{
						q.add(new int[] {nx,ny,now[2]+1});
						visited[nx][ny]=true;
					}
				}
			}
		//	System.out.println(selectBase.size());
//			for(Base b: selectBase)
//			{
//				System.out.println(b);
		//	}
			Collections.sort(selectBase);
			Base s = selectBase.get(0);
			//System.out.println("seelcet "+s);
			v[s.x][s.y]=true;
			person.add(new Person(num,s.x,s.y));
		
	}
	public static boolean isBoundary(int x,int y)
	{
		return x>=0 && x<N && y>=0 && y<N;
	}
	public static void print()
	{
		int[][] b= new int[N][N];
		
	
		for(Person p: person)
		{
			b[p.x][p.y]=p.num;
		}
		for(int i=0;i<N;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(b[i][j]+" ");
			}
			System.out.println();
		}
//		System.out.println();
//		for(int i=0;i<N;i++)
//		{
//			for(int j=0;j<N;j++)
//			{
//				System.out.print(v[i][j]+" ");
//			}
//			System.out.println();
//		}
		System.out.println();
		System.out.println();

	}
}

