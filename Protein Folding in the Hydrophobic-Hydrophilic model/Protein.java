import java.util.LinkedList;

public class Protein {
	public LinkedList<Integer> x;
	public LinkedList<Integer> y;
	String hp;
	Protein(LinkedList<Integer> x, LinkedList<Integer> y, String hp){
		this.x=new LinkedList<Integer>();
		this.y=new LinkedList<Integer>();
		this.x.addAll(x);
		this.y.addAll(y);
		this.hp=hp;
	}
	public int score(){
		int s=0;
		for(int i=0;i<hp.length();i++)
			for(int j=i+2;j<hp.length();j++){
				if( hp.charAt(i)=='H' && hp.charAt(j)=='H' && Math.abs(x.get(i)-x.get(j))+Math.abs(y.get(i)-y.get(j))==1)
					s++;
					
			}
		return s;
	}
	public String toString(){
		int xmin=Integer.MAX_VALUE;
		int xmax=Integer.MIN_VALUE;
		int ymin=xmin;
		int ymax=xmax;
		int len=hp.length();
		for(int i : x){
			if(i<xmin)
				xmin=i;
			if(i>xmax)
				xmax=i;
		}
		for(int j : y){
			if(j<ymin)
				ymin=j;
			if(j>ymax)
				ymax=j;
		}
		int n=xmax-xmin+1;
		int m=ymax-ymin+1;
		int x0=0,y0=0;
		char[][] T= new char[2*n][2*m];
		for(int i=0;i<2*n;i++)
			for(int j=0;j<2*m;j++)
				T[i][j]=' ';
		for(int i=0;i<len-1;i++){
			x0=x.get(i)-xmin;
			y0=y.get(i)-ymin;
			T[2*x0][2*y0]=hp.charAt(i);
			T[2*x0+(x.get(i+1)-x.get(i))][2*y0+(y.get(i+1)-y.get(i))]= x.get(i+1)!=x.get(i) ? '-' : '|';
		}
		T[2*(x.get(len-1)-xmin)][2*(y.get(len-1)-ymin)]=hp.charAt(len-1);
		String s="";
		for(int j=0;j<2*m;j++){
			for(int i=0;i<2*n;i++)
				s+=T[i][j];
			s+='\n';
		}
	return s;
	}
}
