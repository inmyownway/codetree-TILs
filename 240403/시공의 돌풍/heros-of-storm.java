import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.print.attribute.standard.Sides;

public class Main {

	static int R,C,T;
	static int[] dx = {0,0,-1,1};
	static int[] dy= {1,-1,0,0};
	static int[][] board;
	static int[][] airmachine;
	
	static int[] ux= {-1,0,1,0};
	static int[] uy= {0,1,0,-1};
	public static void main(String[] args) throws IOException {
	
		
		BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(bf.readLine());
		
		R= Integer.parseInt(st.nextToken());
		C= Integer.parseInt(st.nextToken());
		T= Integer.parseInt(st.nextToken());
		
		board= new int[R][C];
		airmachine= new int[2][2];
		int a=0;
		for(int i=0;i<R;i++)
		{
			st= new StringTokenizer(bf.readLine());
			for(int j=0;j<C;j++)
			{
				board[i][j]=Integer.parseInt(st.nextToken());
			
				if(board[i][j]==-1)
				{
					airmachine[a][0]=i;
					airmachine[a][1]=j;
					a++;
				}
			}
			
		}

		int sum=0;
		for(int time = 0; time<T;time++)
		{
			
			move();
			up();
			down();
			
	
			
			
		}
		for(int i=0;i<R;i++)
		{
			for(int j=0;j<C;j++)
			{
				sum+=board[i][j];
			}
		}
		System.out.println(sum+2);
	}

	private static void up() {
		
		int x = airmachine[0][0];
		int y= airmachine[0][1];
	
		for(int i=x-1;i>-1;i--)
		{
			board[i+1][0]=board[i][0];
			board[i][0]=0;
		}
		
		for(int i=1;i<C;i++)
		{
			board[0][i-1] = board[0][i];
			 board[0][i]=0;
		}

		for(int i=1;i<=x;i++)
		{
			board[i-1][C-1]=board[i][C-1];
			board[i][C-1]=0;
		}
		for(int i=C-2; i>=1;i--)
		{
			board[x][i+1]=board[x][i];
			board[x][i]=0;
		}
		
		board[x][y]=-1;
	}
private static void down() {
		
		int x = airmachine[1][0];
		int y= airmachine[1][1];
	
		for(int i=x+1;i<R;i++)
		{
			board[i-1][0]=board[i][0];
			board[i][0]=0;
		}
		
		for(int i=1;i<C;i++)
		{

			board[R-1][i-1] = board[R-1][i];
			 board[R-1][i]=0;
		}

		for(int i=R-2;i>=x;i--)
		{
			board[i+1][C-1]=board[i][C-1];
			board[i][C-1]=0;
		}
		
		
		for(int i=C-2; i>=1;i--)
		{
			board[x][i+1]=board[x][i];
			board[x][i]=0;
		}
		
		board[x][y]=-1;
	}

	public static void move()
	{
		int[][] temp = new int[R][C];
		
		for(int i=0;i<R;i++)
		{
			for(int j=0;j<C;j++)
			{
				if(board[i][j]>=1)
				{
					int cnt=0;
					
					for(int idx=0;idx<4;idx++)
					{
						int nx= i+dx[idx];
						int ny= j+dy[idx];
						
						if(isBoundary(nx, ny) && board[nx][ny]!=-1)
						{
							cnt++;
							temp[nx][ny]+=board[i][j]/5;
						}
					}
					temp[i][j]+= board[i][j] - (board[i][j]/5)*cnt;
					
				}
			}
		}
		for(int[] now : airmachine)
		{
			temp[now[0]][now[1]]=-1;
		}
		for(int i=0;i<R;i++)
		{
			for(int j=0;j<C;j++)
			{
				board[i][j]=temp[i][j];
			}
		}
	}
	public static boolean isBoundary(int x,int y)
	{
		return x>=0 && x<R && y>=0 && y<C;
		
	}
}