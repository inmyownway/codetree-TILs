import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;



public class Main {

	static int R,C,M;
	static int[][][] board;
	static int king= 0;
	static int[][] numBoard;
	static int[] dx = {0,-1,1,0,0};
	static int[] dy= {0,0,0,  1,-1};
	static int answer;
	public static class Shark implements Comparable<Shark>
	{
		int x;
		int y;
		int s;
		int d;
		int z;
		int num;
		public Shark(int x, int y, int s, int d, int z,int num) {
			super();
			this.x = x;
			this.y = y;
			this.s = s;
			this.d = d;
			this.z = z;
			this.num=num;
		}
		@Override
		public String toString() {
			return "Shark [x=" + x + ", y=" + y + ", s=" + s + ", d=" + d + ", z=" + z + "]";
		}
		@Override
		public int compareTo(Shark o) {
			
			return Integer.compare(o.z, this.z);
		}
		
	}
	static ArrayList<Shark> sharks;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		/*
		 *  1. 낚시왕 이동
		 *  2. 제일 가까운 상어 제거
		 *  3. 상어이동
		 *  	
		 *  정렬하고 이동 tempBoard = 0 에 크기 넣음 
		 *  	- 이동시 tempBoard 
		 *  
		 * 
		 *  if(temp[x][y]==0 ) arr.add(shark)
		 * 
		 * */
		BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(bf.readLine());
		
		R=Integer.parseInt(st.nextToken());
		C=Integer.parseInt(st.nextToken());
		M=Integer.parseInt(st.nextToken());
		
		board =new int[R][C][3];
		
		sharks = new ArrayList<>();
		numBoard= new int[R][C];
		for(int i=0;i<M;i++)
		{
			st= new StringTokenizer(bf.readLine());
			
			int r= Integer.parseInt(st.nextToken())-1;
			int c= Integer.parseInt(st.nextToken())-1;
			int s= Integer.parseInt(st.nextToken());
			int d= Integer.parseInt(st.nextToken());
			int z= Integer.parseInt(st.nextToken());

			board[r][c][0]=s;
			board[r][c][1]=d;
			board[r][c][2]=z;
			
		}
		
		
		for(int a=0;a<C;a++)
		{
			
			// 가까운 상어 제거 
			remove(a);
			// 상어이동 
			sharkMove();
			
//			for(int i=0;i<R;i++)
//			{
//				for(int j=0;j<C;j++)
//				{
//					System.out.print(board[i][j][2]+" ");
//				}
//				System.out.println();
//			}
//			System.out.println();
	
		}
		System.out.println(answer);
		
		
	}

	private static void sharkMove() {
		
	
		
		int[][][] temp = new int[R][C][3];
		

			for(int i=0;i<R;i++)
			{
				for(int j=0;j<C;j++)
				{
					// 상어가 있으면 
					if(board[i][j][2]>0)
					{
						
						
						int nx= i;
						int ny= j;
						
						int ns= board[i][j][0];
						int nd= board[i][j][1];
						int nz= board[i][j][2];
						
						for(int idx=0;idx<ns;idx++)
						{
							nx+=dx[nd];
							ny+=dy[nd];
						
							if(!isBoundary(nx, ny))
							{
								nd= turn(nd);
								nx+= (dx[nd]*2);
								ny+= (dy[nd]*2);
							}		
							
						}
						
						
						// 위치 나옴
						// 상어가 있으면 크기비교해서 큰거 넣어줌
						if(temp[nx][ny][2]>0)
						{
							if(temp[nx][ny][2] < nz)
							{
								temp[nx][ny][0]=ns;
								temp[nx][ny][1]=nd;
								temp[nx][ny][2]=nz;
							}
						}
						else if(temp[nx][ny][2]==0)
						{
							temp[nx][ny][0]=ns;
							temp[nx][ny][1]=nd;
							temp[nx][ny][2]=nz;
						}
						
					}
					
				
				}
			}
			
			board= new int[R][C][3];
			for(int i=0;i<R;i++)
			{
				for(int j=0;j<C;j++) {
					
					board[i][j][0]=temp[i][j][0];
					board[i][j][1]=temp[i][j][1];
					board[i][j][2]=temp[i][j][2];
				}
			}
			
			

		
		
	}
	private static int turn(int d) {
		
		if(d==1)
			return 2;
		else if (d==2)
			return 1;
		else if(d==3)
			return 4;
		return 3;
		
	}
	private static void remove(int i) {

		int num=0;
		//System.out.println("pos: "+i);

		for(int j=0;j<R;j++)
		{
			if(board[j][i][2]>0)
			{
				//System.out.println("j i"+" "+i+" "+j);
				answer+= board[j][i][2];
			
				board[j][i][0]=0;

				board[j][i][1]=0;

				board[j][i][2]=0;
				break;
			}
		}
		

		
	}
	public static boolean isBoundary(int x,int y)
	{
		return x>=0 && x<R && y>=0 && y<C;
	}

	
}