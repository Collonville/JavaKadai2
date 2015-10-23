import java.util.ArrayList;
import java.util.HashMap;


public class Search {
	private ArrayList<HashMap<String,String>> data;
	private boolean hitKey[];
	private String pat;
	private int hitEvalNum[];
	private String[] query;
	private ArrayList<String> splitedQuery = new ArrayList<String>();
	
	public Search(ArrayList<HashMap<String,String>> data, String pat, String[] query){
		this.data = data;
		this.pat = pat;
		this.query = query;
		
		hitKey = new boolean[data.size()];
		
        //queryを" "と"="で分割する
		String div1[] = this.pat.split(" ");
        for(int i = 0; i < div1.length; i++){
        	if(i % 2 == 0){
        		String div2[] = div1[i].split("=");
        		splitedQuery.add(div2[0]);
        		splitedQuery.add(div2[1]);
        	}else{
        		splitedQuery.add(div1[i]);
        	}
        }
	}
	
	private void and(final int level){
		boolean leftHit[] = new boolean[data.size()];
		boolean rightHit[] = new boolean[data.size()];
		
		for(int i = 0; i < data.size(); i++){
			HashMap<String, String> map = data.get(i);
			for (String str : map.keySet()) {
				if(level == 2){
					if(str.equals(splitedQuery.get(level - 2)) && map.get(str).equals(splitedQuery.get(level - 1))){
						leftHit[i] = true;
					}
				}
				if(str.equals(splitedQuery.get(level + 1)) && map.get(str).equals(splitedQuery.get(level + 2))){
					rightHit[i] = true;
				}
			}
		}
		
		for(int i = 0; i < data.size(); i++){
			hitKey[i] = hitKey[i] || (leftHit[i] && rightHit[i]);
		}
	}
	
	private void or(final int level){
		boolean leftHit[] = new boolean[data.size()];
		boolean rightHit[] = new boolean[data.size()];
		
		for(int i = 0; i < data.size(); i++){
			HashMap<String, String> map = data.get(i);
			for (String str : map.keySet()) {
				if(level == 2){
					if(str.equals(splitedQuery.get(level - 2)) && map.get(str).equals(splitedQuery.get(level - 1))){
						leftHit[i] = true;
					}
				}
				
				if(str.equals(splitedQuery.get(level + 1)) && map.get(str).equals(splitedQuery.get(level + 2))){
					rightHit[i] = true;
				}
			}
		}
		
		for(int i = 0; i < data.size(); i++){
			hitKey[i] = (hitKey[i] || leftHit[i] || rightHit[i]);
		}
	}
	
	private void not(){
		for(int i = 0; i < data.size(); i++){
			HashMap<String, String> map = data.get(i);
			for (String str : map.keySet()) {
				if(str.equals(splitedQuery.get(0)) && map.get(str).equals(splitedQuery.get(1))){
					hitKey[i] = true;
				}
			}
		}
	}
	
	public int[] getEval(String evalLabel[]){
		hitEvalNum = new int [evalLabel.length];
		
		for(int i = 0; i < data.size(); i++){
			HashMap<String, String> map = data.get(i);
			for (String str : map.keySet()) {
				for(int j = 0; j < evalLabel.length; j++){
					if(hitKey[i] == true && str.equals(query[6]) && map.get(str).equals(evalLabel[j])){
						hitEvalNum[j]++;
					}
				}
			}
		}
		
		return hitEvalNum;
	}
	
	public void findIndependence(){
		int operatorNum = splitedQuery.size() / 3;
		
		//演算子がない時
		if(operatorNum == 0){
			System.out.println("not");
			not();
		}
		
		//演算子が存在する時
		for(int i = 0; i < operatorNum; i++){
			if(splitedQuery.get(i * 3 + 2).equals("or")){
				or(i * 3 + 2);
			}else if(splitedQuery.get(i * 3 + 2).equals("and")){
				and(i * 3 + 2);
			}
		}
	}
}
