
public class Align {
	public int first;
	public int len;
	public double score;
	Align(int first,int len, double score){
		this.first=first;
		this.len=len;
		this.score=score;
	}
	public String toString(){
		return "First index: "+this.first+" ,last index: "+(this.first+this.len+1)+" ,score: "+this.score ;
	}
	@Override
	public boolean equals(Object obj) {
		Align e=(Align) obj;
		return this.first==e.first && this.len==e.len && this.score==e.score;
	}
	@Override
	public int hashCode() {
		return 7*this.first+13*this.len;
	}
}