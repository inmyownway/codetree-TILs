package jmalgo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;



public class Main {

	static int T,M;
	static int px,py;
	static int[][] board; 
	static ArrayList<Monster> liveMonster;
	static ArrayList<Monster> eggMonster;
	static ArrayList<int[]> moves;
	static int[] dir = {0,1,2,3};
	static int[] result;
	static int[] dx = {-1,-1,0,1  ,  1,1,0,-1};
	static int[] dy = {0, -1,-1,-1  ,0,1,1,1};
	static int[][] monsterPosBoard;
	public static class Monster{
		int x;
		int y;
		int d;
		
		public Monster(int x, int y, int d) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
		}

		@Override
		public String toString() {
			return "Monster [x=" + x + ", y=" + y + ", d=" + d + "]";
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(bf.readLine());
		
		M=Integer.parseInt(st.nextToken());
		T=Integer.parseInt(st.nextToken());
		st = new StringTokenizer(bf.readLine());
		px=Integer.parseInt(st.nextToken());
		py=Integer.parseInt(st.nextToken());
		
		
		liveMonster= new ArrayList<>();
		eggMonster= new ArrayList<>();
		moves = new ArrayList<>();
		result= new int[3];
		board= new int[4][4]; // 시체 확인용
		monsterPosBoard= new int[4][4];
		for(int i=0;i<M;i++)
		{
			st= new StringTokenizer(bf.readLine());
			int x=Integer.parseInt(st.nextToken())-1;
			int y=Integer.parseInt(st.nextToken())-1;
			int d=Integer.parseInt(st.nextToken())-1;
			
			liveMonster.add(new Monster(x,y,d));
		}
		
		// 0 1 2 3 으로 중복 순열 만들어주기 
		perm(0);
		
		printMonster(liveMonster);
		for(int time=0;time<T;time++)
		{
			monsterCopyTry();
			monsterMove();
			System.out.println("# "+time);
			printMonster(liveMonster);
			System.out.println("egg");
			printMonster(eggMonster);
			printBoard(board);
			
			packmanMove();
			
	
		}
	}
	private static void packmanMove() {
		
		int[] mx = {-1,0,1,0};
		int[] my= {0,-1,0,1};
		
		int maxEat=-1;
		
		for(int[] move: moves)
		{
			// move는 이동 커맨드 
			int sum=0;
			
			
		}
	}
	private static void monsterCopyTry() {
	
		for(int i=0;i<liveMonster.size();i++)
		{
			Monster tempMonster= liveMonster.get(i);
			
			int x= tempMonster.x;
			int y= tempMonster.y;
			int d= tempMonster.d;
			
			eggMonster.add(new Monster(x,y,d));
		}
		
	}
	private static void monsterMove() {
		
		monsterPosBoard= new int[4][4];
		for(Monster m : liveMonster)
		{
			int x= m.x;
			int y= m.y;
			int d= m.d;
			
			for(int i=d;i<d+8;i++)
			{
				if(i>=8)
					i-=8;
				
				int nx = x+dx[i];
				int ny= y+dy[i];
				if(isBoundary(nx, ny) && board[nx][ny]==0 && (nx!= px && ny!=py))
				{
					m.x= nx;
					m.y = ny;
					m.d = d;
					
					monsterPosBoard[nx][ny]+=1;
					
					break;
				}
			}
			monsterPosBoard[x][y]+=1;
		}
		
	}
	public static void perm(int depth)
	{
		if(depth==3)
		{

			int[] temp = new int[3];
			
			for(int i=0;i<3;i++)
			{
				temp[i]=result[i];
			}
			moves.add(temp);
			return;
		}
		for(int i=0;i<4;i++)
		{
			result[depth]=dir[i];
			perm(depth+1);
		}
	}
	public static boolean isBoundary(int x,int y)
	{
		return x>=0 && x<4 && y>=0 && y<4;
	}
	public static void printMonster(ArrayList<Monster> monster)
	{
		for(Monster p:monster)
		{
			System.out.println(p);
		}
		System.out.println();

	}
	public static void printBoard(int[][] board)
	{
		for(int[] b: board)
		{
			System.out.println(Arrays.toString(b));
		}
		System.out.println();
	}

}