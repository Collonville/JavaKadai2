import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Car {
	static String keys[] = {"buying", "maint", "doors", "persons", "luggage", "safety", "eval"};
	static String evalLabel[] = {"unacc", "acc", "good", "vgood"};
	private ArrayList<HashMap<String,String>> data;
	private HashMap<String, String> map = new HashMap<String,String>();
	
	public Car (String fileName) {
		data = new ArrayList<HashMap<String,String>> ();
		//car.csvの読み込み
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		try {
            BufferedReader br = new BufferedReader(fileReader);

            //読み込んだファイルを１行ずつ処理する
            String line;
            StringTokenizer token;
            while ((line = br.readLine()) != null) {
                token = new StringTokenizer(line, ",");
               
                //分割した文字を画面出力する
                int cnt = 0;
                while (token.hasMoreTokens()) {
                	map.put(keys[cnt], token.nextToken());
                	data.add(map);
                    //System.out.println(data.toString());
                    cnt++;
                }
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }	
	}
	
	public int [] analyze(String query[]) {
		int results[] = new int [evalLabel.length];
		
		System.out.println(query[0]);
		//andまたはorを区切り文字として設定(正規表現による指定)
        @SuppressWarnings("resource")
		Scanner s = new Scanner(query[0]).useDelimiter("s*ands*|s*ors*");

        while (s.hasNext()) {
            System.out.println(s.next());
        }
        s.close();
		// 検索処理
		return results;
	}
	
	public static void main (String[] args) {
		Car car = new Car ("car.csv");
		int evals [] = car.analyze(args);
		
		for (int i = 0; i < evals.length; i++)
			System.out.printf("%s = %d,\n", evalLabel[i], evals[i]);
	}
}
