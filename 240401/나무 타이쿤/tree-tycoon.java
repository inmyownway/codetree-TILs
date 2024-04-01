import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


import java.io.*;

public class Main {

	static int n,m;
	static int[][] board;
	static ArrayList<int[]> command;
	static int[] dx = {0,0,-1,-1,-1  ,0 , 1,1,1};
	static int[] dy = {0,1,1,0,-1,   -1 ,-1,0,1};
	static boolean[][] visited;
	static Queue<Point> q;
	public static class Point{
		int x;
		int y;
		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}

	public static void main(String[] args) throws IOException {
		
		
BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
StringTokenizer st = new StringTokenizer(bf.readLine());

	n= Integer.parseInt(st.nextToken());
	m=Integer.parseInt(st.nextToken());
	board = new int[n][n];
	command= new ArrayList<>();
	 q= new LinkedList<>();
	for(int i=0;i<n;i++)
	{
		st= new StringTokenizer(bf.readLine());
		for(int j=0;j<n;j++)
		{
			board[i][j]=Integer.parseInt(st.nextToken());
		}
		
	}
	for(int i=0;i<m;i++)
	{
		st=new StringTokenizer(bf.readLine());

		int d= Integer.parseInt(st.nextToken());
		int p= Integer.parseInt(st.nextToken());
		command.add(new int[] {d,p});
		
	}
	
	
	q.add(new Point(n-1,0));
	q.add(new Point(n-1,1));
	q.add(new Point(n-2,0));
	q.add(new Point(n-2,1));

	
	
		for(int[] com : command)
		{
			int d= com[0];
			int p = com[1];
			visited = new boolean[n][n];
			int size= q.size();
			
			for(int i=0;i<size;i++)
			{
				Point point = q.poll();
				
				int nx =point.x;
				int ny= point.y;
				for(int idx=0;idx<p;idx++)
				{
					nx+=dx[d];
					ny+=dy[d];
					
					if(nx<0)
						nx=n-1;
					
					if(nx>=n)
						nx=0;
					
					if(ny<0)
						ny=n-1;
					
					if(ny>=n)
						ny=0;
					
	
				}
				
				visited[nx][ny]=true;
				board[nx][ny]+=1;
			}
			grow();
			add();
		}
	
		int answer=0;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				answer+=board[i][j];
			}
		}
		System.out.println(answer);
			}

	private static void add() {

		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(visited[i][j]==false && board[i][j]>=2)
				{
					board[i][j]-=2;
					
					q.add(new Point(i,j));
					
				}
			}
		}
		
	}

	private static void grow() {
	
		int[][] temp = new int[n][n];
		
		
		
		int[] cx= {-1,-1,1,1};
		int[] cy= {-1,1,-1,1};
		
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				
				temp[i][j]=board[i][j];
				
				int count=0;
				if(visited[i][j])
				{
					for(int idx=0;idx<4;idx++)
					{
						int nx= i+cx[idx];
						int ny= j+cy[idx];
						
						if(isBoundary(nx, ny) && board[nx][ny]>=1)
						{
							count++;
						}
						
					}
				}
				
				temp[i][j]+=count;
			}
		}
		
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				board[i][j]=temp[i][j];
			}
		}
		
	}
	public static boolean isBoundary(int x,int y)
	{
		return x>=0 && x< n && y>=0 && y<n; 
				
	}

}