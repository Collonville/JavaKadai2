import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Car {
	static String keys[] = {"buying", "maint", "doors", "persons", "luggage", "safety", "eval"};
	static String evalLabel[] = {"unacc", "acc", "good", "vgood"};
	private ArrayList<HashMap<String,String>> data;
	
	public Car (String fileName) {
		data = new ArrayList<HashMap<String,String>> ();
		//car.csvの読み込み
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
            BufferedReader br = new BufferedReader(fileReader);

            //読み込んだファイルを１行ずつ処理する
            String line;
            StringTokenizer token;
            while ((line = br.readLine()) != null) {
            	HashMap<String, String> map = new HashMap<String,String>();
                token = new StringTokenizer(line, ",");

                int cnt = 0;
                while (token.hasMoreTokens()) {
                	map.put(keys[cnt], token.nextToken());
                    cnt++;
                }
                data.add(map);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }	
	}
	
	public int [] analyze(String query[]) {
		//引数文字列を全て結合して渡す
		Search search = new Search(data, String.join(" ", query), keys); 
		
		//演算子の解析
		search.findIndependence();
        
		return search.getEval(evalLabel);
	}
	
	public static void main (String[] args) {
		Car car = new Car ("car.csv");
		int evals [] = car.analyze(args);
		
		for (int i = 0; i < evals.length; i++)
			System.out.printf("%s = %d,\n", evalLabel[i], evals[i]);
	}
}
