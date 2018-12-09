import java.util.LinkedList;

public class ProteinFolding {
	public static LinkedList<Protein> Proteins(String s){
		LinkedList<Protein> L=new LinkedList<Protein>();
		LinkedList<Integer> x0=new LinkedList<Integer>();
		LinkedList<Integer> y0=new LinkedList<Integer>();
		x0.add(0);
		y0.add(0);
		L.add(new Protein(x0,y0,""+s.charAt(0)));
		int n=0,m=0;
		Protein q;
		boolean valid=true;
		int len=1;
		Protein p=L.remove();
		while(len<s.length()){
			n=p.x.peekLast();
			m=p.y.peekLast();
			for(int k=-1;k<2;k+=2){
				valid=true;
				for(int i=0; i<p.hp.length();i++){
					if(n+k==p.x.get(i) && m==p.y.get(i))
						valid=false;
				}
				if(valid){
					q=new Protein(p.x,p.y,p.hp+s.charAt(len));
					q.x.add(n+k);
					q.y.add(m);
					L.add(q);
				}
			}
			for(int k=-1;k<2;k+=2){
				valid=true;
				for(int i=0; i<p.hp.length();i++){
					if(n==p.x.get(i) && m+k==p.y.get(i))
						valid=false;
				}
				if(valid){
					q=new Protein(p.x,p.y,p.hp+s.charAt(len));
					q.x.add(n);
					q.y.add(m+k);
					L.add(q);
				}
			}
			p=L.remove();
			len=p.hp.length();
		}
		return L;
	}
	public static Protein folding(String s){
		LinkedList<Protein> L=Proteins(s);
		int max=Integer.MIN_VALUE;
		int tmp;
		for(Protein p : L){
			tmp=p.score();
			if(tmp>max)
				max=tmp;
		}
		for(Protein p : L)
			if(p.score()==max)
				return p;
		return L.peek();
			
	}
    public static void main(String[] args) {
        String[] examples={"PHHPPHHPPHPH","PHPPHHPPH","PHPHPPHPHP"};
        for(int i=0;i<3;i++){
            System.out.println(examples[i]+':');
            System.out.println(ProteinFolding.folding(examples[i]));
        }
    }
}
