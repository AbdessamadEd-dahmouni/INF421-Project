import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ADN {

	public static void longestsubsequence(String a,String b) {
		int n=a.length();
		int m=b.length();
		int[][] C= new int[n+1][m+1];
		String s="";
		for(int i=0;i<=m;i++)
			C[0][i]=0;
		for(int i=1;i<=n;i++){   
			C[i][0]=0;
			for(int j=1;j<=m;j++){
				if(a.charAt(i-1)!=b.charAt(j-1))
					C[i][j]=Math.max(C[i-1][j], C[i][j-1]);
				else 
					C[i][j]=C[i-1][j-1]+1;
			}
		}
		while(n>0 && m>0){
			if(C[n][m]==C[n-1][m-1]+1){   
					s=b.charAt(m-1)+s;
				    n--;m--;
			}
			else{
				if(C[n][m]>C[n][m-1]) n--;
				else m--;
			}
		}
		System.out.println(s);
	}

	public static void editiondistance(String a,String b) {
		int n=a.length();
        int m=b.length();
        int[][] C=new int[n+1][m+1];
        for (int j = 0; j <= b.length(); j++)
            C[0][j] = j;
        for (int i = 1; i <= a.length(); i++) {
            C[i][0] = i;
            for (int j = 1; j <= b.length(); j++) {
                C[i][j] = Math.min(1 + Math.min(C[i-1][j], C[i][j-1]), a.charAt(i - 1) == b.charAt(j - 1) ? C[i-1][j-1] : C[i-1][j-1] + 1);
            }
        }
		String aPath = "";
		String bPath = "";
		while(n>0 && m>0){
			if (C[n][m] == (a.charAt(n - 1) == b.charAt(m - 1) ? C[n-1][m-1] : C[n-1][m-1]+1)) {
			    n--;m--;
			    aPath=a.charAt(n)+aPath;
				bPath=b.charAt(m)+bPath;
			}else if (C[n][m] == 1 + C[n][m-1]) {
			    m--;
			    aPath='-'+aPath;
			    bPath=b.charAt(m)+bPath;
			}else if (C[n][m] == 1 + C[n-1][m]) {
				n--;
				aPath=a.charAt(n)+aPath;
				bPath='-'+bPath;
			}
		}
		while(n>0){
			aPath=a.charAt(n-1)+aPath;
			bPath='-'+bPath;
			n--;
		}
		while(m>0){
			aPath='-'+aPath;
			bPath=b.charAt(m-1)+bPath;m--;
		}
		System.out.println(aPath);
		System.out.println(bPath);
		       
	}

	public static void substitutionmatrices(String a,String b) {
		int n=a.length();
        int m=b.length();
        float[][] C=new float[n+1][m+1];
        C[0][0]=0;
        for (int j = 1; j <= b.length(); j++)
            C[0][j] = C[0][j-1] + Blosum50.getScore('-',b.charAt(j-1));
        for (int i = 1; i <= a.length(); i++) {
            C[i][0] = C[i-1][0] + Blosum50.getScore('-',a.charAt(i-1));
            for (int j = 1; j <= b.length(); j++) {
                C[i][j] = Math.max(Math.max(C[i-1][j]+Blosum50.getScore(a.charAt(i-1),'-'), C[i][j-1]+Blosum50.getScore('-',b.charAt(j-1)))
                		, C[i-1][j-1] + Blosum50.getScore(a.charAt(i-1),b.charAt(j-1)));
            }
        }
		String aPath = "";
		String bPath = "";
		while(n>0 && m>0){
			    if (C[n][m] ==  C[n-1][m-1] + Blosum50.getScore(a.charAt(n-1),b.charAt(m-1))) {
				n--;m--;
				aPath=a.charAt(n)+aPath;
				bPath=b.charAt(m)+bPath;
			    }else if (C[n][m] == Blosum50.getScore('-',b.charAt(m-1)) + C[n][m-1]) {
				m--;
				aPath='-'+aPath;
				bPath=b.charAt(m)+bPath;
				}else if (C[n][m] == Blosum50.getScore(a.charAt(n-1),'-') + C[n-1][m]) {
				n--;
				aPath=a.charAt(n)+aPath;
				bPath='-'+bPath;
				}
			}
		while(n>0){
			aPath=a.charAt(n-1)+aPath;
			bPath='-'+bPath;
			n--;
		}
		while(m>0){
			aPath='-'+aPath;
			bPath=b.charAt(m-1)+bPath;
			m--;
		}
		System.out.println(aPath);
		System.out.println(bPath);
		       
	} 

	public static void affinepenalty(String a,String b,float op,float in) { 
		int n=a.length();
        int m=b.length();
        float[][] C=new float[n+1][m+1];
        float[][] A=new float[n+1][m+1];
        float[][] B=new float[n+1][m+1];
        C[0][0]=0; A[0][0]=0; B[0][0]=0;
        for(int i=1;i<a.length();i++)
          {  A[i][0]=-op-in*i;
        	 B[i][0]=Float.MIN_VALUE;
          }
        for(int i=1;i<b.length();i++)
          {   B[0][i]=-op-in*i; 
              A[0][i]=Float.MIN_VALUE;
          }
        for (int j = 1; j <= b.length(); j++){
        	 C[0][j]=Float.MIN_VALUE;
             for (int i = 1; i <= a.length(); i++) {
            	 C[i][0]=Float.MIN_VALUE;
            	 C[i][j]=Math.max(Math.max(C[i-1][j-1],A[i-1][j-1]), B[i-1][j-1])+Blosum50.getScore(a.charAt(i-1),b.charAt(j-1));
        	     A[i][j]=Math.max(Math.max(C[i-1][j],B[i-1][j])-op, A[i-1][j])-in+Blosum50.getScore(a.charAt(i-1),'-');
        	     B[i][j]=Math.max(Math.max(C[i][j-1],A[i][j-1])-op, B[i][j-1])-in+Blosum50.getScore(b.charAt(j-1),'-');
             }
        }
        String aPath = "";
		String bPath = "";
        while(m>0 && n>0){
        	float max=Math.max(Math.max(C[n][m],A[n][m]),B[n][m]);
        	if(max==C[n][m]){
        		n--;m--;
			    aPath=a.charAt(n)+aPath;
				bPath=b.charAt(m)+bPath;
        	}
        	else if(max==A[n][m]){
        		n--;
 			    aPath=a.charAt(n)+aPath;
 			    bPath='-'+bPath;
        	}
        	else{
        		m--;
			    aPath='-'+aPath;
			    bPath=b.charAt(m)+bPath;
          }
        }
    	while(n>0){
    		n--;
			aPath=a.charAt(n)+aPath;
			bPath='-'+bPath;
		}
		while(m>0){
			m--;
			aPath='-'+aPath;
			bPath=b.charAt(m)+bPath;
		}
		System.out.println(aPath);
		System.out.println(bPath);
	}

	public static void localalignement(String a,String b,float op,float in) { 
		int n=a.length();
        int m=b.length();
        float[][] C=new float[n+1][m+1];
        float[][] A=new float[n+1][m+1];
        float[][] B=new float[n+1][m+1];
        for(int i=0;i<a.length();i++)
          {  C[i][0]=0;
             A[i][0]=Float.MIN_VALUE;
        	 B[i][0]=Float.MIN_VALUE;
          }
        for(int i=0;i<b.length();i++)
          {  C[0][i]=0;   
        	 B[0][i]=Float.MIN_VALUE;
             A[0][i]=Float.MIN_VALUE;
          }
        for (int j = 1; j <= b.length(); j++){
             for (int i = 1; i <= a.length(); i++) {
            	 C[i][j]=Math.max(Math.max(Math.max(C[i-1][j-1],A[i-1][j-1]), B[i-1][j-1])+ Blosum50.getScore(a.charAt(i-1),b.charAt(j-1)),0);
            	 A[i][j]=Math.max(Math.max(C[i-1][j],B[i-1][j])-op, A[i-1][j])-in+Blosum50.getScore(a.charAt(i-1),'-');
        	     B[i][j]=Math.max(Math.max(C[i][j-1],A[i][j-1])-op, B[i][j-1])-in+Blosum50.getScore(b.charAt(j-1),'-');
             }
        }
        String aPath = "";
		String bPath = "";
	    int n1=n;int m1=m;
	    float max1=C[n][m];
        for (int j = m; j >= 1; j--){
           for (int i = n; i >= 1; i--) {
        	 if(C[i][j]>max1){
        		 max1=C[i][j];
        		 n1=i;m1=j;
        	 }
         } }
        n=n1;m=m1;
        while(n>0 && m>0){
        	float max=Math.max(Math.max(C[n][m],A[n][m]),B[n][m]);
        	if(C[n][m]==0) break;
        	if(max==C[n][m]){
        		n--;m--;
			    aPath=a.charAt(n)+aPath;
				bPath=b.charAt(m)+bPath;
        	}
        	else if(max==A[n][m]){
        		n--;
 			    aPath=a.charAt(n)+aPath;
 			    bPath='-'+bPath;
        	}
        	else{
        		m--;
			    aPath='-'+aPath;
			    bPath=b.charAt(m)+bPath;
          }
        }
        System.out.println(C[n1][m1]);
		System.out.println(aPath);
		System.out.println(bPath);
	}

	public static double Score(String a, String b) { //on suppose qu'ils ont la meme longueur
		if(a.length()!=b.length())
			throw new IllegalArgumentException("the strings should have the same length.");
		double score=0;
		for(int j=0;j<a.length();j++) score+= Blosum50.getScore(a.charAt(j),b.charAt(j));
		return score;
	}

	private static void generate(String str, String input, String w, double th,double score, Tree dup, int N) {
	    if ( str.length() == N)
	    	if(Score(w,str)>=th*score)
	    		dup.add(str);
	    	else
	    		return;
	    else
	        for (int i = 0; i < input.length(); i++)
	            generate(str + input.charAt(i), input, w, th, score, dup, N);
	}

	public static Tree generate(String input, String g, double th, int N) {
		Tree t = new Tree();
		for(int i=0;i<=g.length()-N;i++){
			String w=g.substring(i,i+N);
			generate("", input, w, th, Score(w,w), t, N);
		}
		return t;
	}
	
	public static List<Integer> indices(String g,String t, double th, int k)
	{   
		String input="ARNDCQEGHILKMFPSTWYV";
        Tree tree = generate(input, g, th, k); // the tree stores the seeds
		List<Integer> L=new ArrayList<Integer>();
		for(int i=0; i<=t.length()-k;i++){
			if(tree.contains(t.substring(i,i+k))) // we look for perfect matches
				L.add(i);
		}
		return L;
	}
	
	public static Set<Align> BLAST(String g, String t, double th, double thl, int k){
		List<Integer> L=indices(g,t,th,k);
		Set<Align> A= new HashSet<Align>();
		String w="";
		String s="";
		Align e;
		double tmp=0;
		for(Integer i : L){
			s=t.substring(i,i+k);
			for(int j=0;j<=g.length()-k;j++){
				w=g.substring(j,j+k);
				if(Score(w,w)*th<=Score(w,s)){
					e=new Align(i,k,Score(w,s));
					while(e.first>Math.max(i-j, 0)){
						tmp=Blosum50.getScore(t.charAt(e.first-1),g.charAt(e.first+j-i-1)); // the score of aligning the elements at the left of the currently extending alignment
						if(tmp>=0){
							e.first--;
							e.len++;
							e.score+=tmp;
						}
						else break;
					}
					while(e.first+e.len<Math.min(g.length()+i-j,t.length())){
						tmp=Blosum50.getScore(t.charAt(e.first+e.len),g.charAt(e.first+e.len+j-i)); // the score of aligning the elements at the right
						if(tmp>=0){
							e.len++;
							e.score+=tmp;
						}
						else break;
					}
					if(e.score>=thl*Score(g,g))
						A.add(e);
				}
			}
		}
		return A;
	}
	
	public static void main (String[] args)
	    {    String a="CACTAAGCATCAGC";
	         String b="ATAGGGCAATCT";
	         //longestsubsequence(a,b);
	         //editiondistance(a,b);
	         substitutionmatrices(a,b);
	         affinepenalty(a,b,10,1);
	         localalignement(a,b,0,0);
	         indices("ADCRGHC","BDADCRGNRADACRGHC",0.9,4);
	         Set<Align> L=BLAST("ADCRGHC","BDADCRGNRADACRGHC",1,0.1,4);
	         for(Align e : L)
	        	 System.out.println(e);
	         return ;
	    }
}