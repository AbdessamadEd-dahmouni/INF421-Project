// this class can only handle words with the same length, as words with the same prefix are considered elements of
//the tree even if the short one is not added to the tree, which should not be the case in general.
public class Tree{
	public Tree[] child;
	public int count;
	Tree(){
		count = 0;
		child = new Tree[26];
	}
	static int index(int c){
		return c-'A';
	}
	
	static char toChar(int i){
		return (char) (i+'A');
	}
	static void add(Tree root, String s){
		int len = s.length();
		for(int i=0; i<len; i++){
			int key = index(s.charAt(i));
			if(root.child[key]==null){
				root.child[key] = new Tree();
				root.count++;
			}
			root = root.child[key];
		}
	}
	
	void add(String s){
		add(this,s);
	}
	
	static void remove(Tree root, String s){
		if(s == "") return;
		Tree t = root;
		Tree r = root;
		int k = 0;
		int i = 0;
		while( t != null && i<s.length() && t.child[index(s.charAt(i))] != null) {
			if( t.count >= 2 ){ r = t; k = i;}
			t = t.child[index(s.charAt(i))];
			i++;
		}
		if(i == s.length() && t.count==0) r.child[index(s.charAt(k))] = null;
	}
	
	void remove(String s){
		remove( this, s);
	}
	
	public String toString(String r) {
		String s = "";
		for(int i = 0 ; i<26 ; i++){
			if(child[i] != null){
				if(this.child[i].count>=1)
					s += this.child[i].toString(r+toChar(i));
				else
					s+=r+toChar(i)+'\n';
			}
		}
		return s;
	}
	
	public String toString(){
		return toString("");
	}
	
	public boolean contains(String word){
		if (this.count==0) return false;
		Tree t=this;
		int i=0;
		while(i<word.length()){
			if((t=t.child[index(word.charAt(i))])==null) return false;
			i++;
		}
		return true;
	}
	public static void main(String[] args) {
		Tree t=new Tree();
		t.add("ARND");
		t.add("CQEG");
		t.add("AKEG");
		t.remove("ARND");
		System.out.println(t);
		System.out.println(t.contains("AKEG"));
	}
}