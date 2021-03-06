
import java.util.Scanner;
import java.util.Arrays;
import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class Array_ope {
	
	//単位行列を生成する。（引数は行数）
	public static double[][] eye(int n){
	    double ans[][] = new double[n][n];
	    for (int i=0;i < n;i++){
	        ans[i][i] = 1;
	    }
	    return ans;
	}
	
	//行列を横向きに連結する（引数右側が右につく）
	public static double[][] con_side(double a[][],double b[][]){
	    if (a.length != b.length){
	        throw new IllegalArgumentException("左右の行数がちがいます。");
	    }
	    double ans[][] = new double[a.length][a[0].length + b[0].length];
	    for (int i = 0;i < a.length;i++){
	        for (int j = 0;j < a[0].length;j ++){
	            ans[i][j] = a[i][j];
	        }
	        for (int j = 0;j < b[0].length;j++){
	            ans[i][j] = b[i][j];
	        }
	    }
	    return ans;
	}
	//行列を縦向きに連結する
	public static double[][] con_port(double a[][],double b[][]){
	    if (a[0].length != b[0].length){
	        throw new IllegalArgumentException("列の数が違います");
	    }
	    double ans[][] = new double[a.length + b.length][a[0].length];
	    for (int i = 0;i < a.length;i++){
	        for (int j = 0;j < a[0].length;j ++){
	            ans[i][j] = a[i][j];
	        }
	    }
	    for (int i = 0;i < b.length;i++){
	        for (int j = 0;j < b[0].length;j++){
	            ans[i+a.length][j] = b[i][j];
	        }
	    }
	    return ans;
	}
	//一次元配列の最大値
	public static double Max_vec(double a[]){
	    double ans = 0;
	    for (int i = 0;i < a.length;i++){
	        ans = ans < a[i] ? a[i] : ans;
	    }
	    return ans;
	}
	//一次元配列の最小値
	public static double Min_vec(double a[]){
	    double ans = a[0];
	    for (int i = 0;i < a.length;i ++){
	        ans = ans > a[i] ? a[i] : ans;
	    }
	    return ans;
	}
	//一次元配列の平均値
	public static double Ave_vec(double a[]){
	    double ans = 0;
	    double count = 0;
	    for (int i = 0;i < a.length;i++){
	        count ++;
	        ans += a[i];
	    }
	    return (double)ans/count;
	}
	//一次元配列の中央値
	public static double Mean_vec(double a[]){
	    Arrays.sort(a);
	    double index = a.length % 2 == 0 ? (double)(a[(a.length/2)-1] + a[a.length/2])/2 : a[a.length/2];
	    return index;
	}
	//一次元配列の最頻値
	public static double Mode_vec(double a[]){
	    Map<String, String> map = new HashMap<String,String>();
	    for (int i = 0;i < a.length;i ++){
	        if (map.containsKey(String.valueOf(a[i]))){
	            int num = Integer.valueOf(map.get(String.valueOf(a[i])));
	            map.put(String.valueOf(a[i]),Integer.toString(num+1));
	        }else{
	            map.put(String.valueOf(a[i]),Integer.toString(1));
	        }
	    }
	    double ans = 0;int check = 0;
	    for (String key:map.keySet()){
	        if (check < Integer.parseInt(map.get(key))){
	            ans = Double.valueOf(key);
	            check = Integer.parseInt(map.get(key));
	        }
	    }
	    return ans;
	}
	//一次元配列のそれぞれの分散を返す
	public static double variance(double a[]){
	    double ave = Array_ope.Ave_vec(a);
	    double ans = 0;
	    for (int i = 0;i < a.length;i++){
	        ans += Math.pow((a[i]-ave),2);
	    }
	    ans = (double) ans / a.length;
	    return ans;
	}
	//一次元配列の要素を平均値0分散1で標準化する
	public static double[] standardize_vec(double a[]){
	    double ave = Array_ope.Ave_vec(a);
	    double vari = Array_ope.variance(a);
	    double ans[] = new double[a.length];
	    for (int i = 0;i < a.length;i++){
	        ans[i] = (double) (a[i]-ave) / vari;
	    }
	    return ans;
	}
	//最大値を1最小値を0にする正規化。
	public static double[] normalize_vec(double a[]){
	    double max = Array_ope.Max_vec(a);
	    double min = Array_ope.Min_vec(a);
	    double ans[] = new double[a.length];
	    for (int i = 0;i < a.length;i++){
	        ans[i] = (double) (a[i] - min) / (max - min);
	    }
	    return ans;
	}
	//列での欠損値補完（第二引数は1は平均値2は中央値3は最頻値4は列の最大値
	//5は列の最小値で補完）欠損値には予めNaNを代入しておいて
	//[1,3,4,2]みたいにその列ごとに補完したい代表値を示す番号を格納した配列を
	//引数に渡す。
	public static double[][] Completion(double a[][],int type[]){
	    if (a[0].length != type.length){
	        throw new IllegalArgumentException("行列の列の数とtypeの次元数が一致してません。");
	    }
	    int count[] = new int[a[0].length];
	    for (int i = 0;i < a.length;i++){
	        for (int j = 0;j < a[0].length;j++){
	            if (Double.isNaN(a[i][j]))count[j]++;
	        }
	    }
	    double elem[] = new double[type.length];
	    for (int i = 0;i < type.length;i ++){
	        double check[] = new double[a.length - count[i]];
	        int c = 0;
	        for (int j = 0;j < a[0].length;j ++){
	            if (! Double.isNaN(a[i][j])){
	                check[c] = a[i][j];
	                c++;
	            }
	        }
	        double ho = 0;
	        if (type[i] == 1)ho = Array_ope.Ave_vec(check);
	        else if (type[i] == 2)ho = Array_ope.Mean_vec(check);
	        else if (type[i] == 3)ho = Array_ope.Mode_vec(check);
	        else if (type[i] == 4)ho = Array_ope.Max_vec(check);
	        else if (type[i] == 5)ho = Array_ope.Min_vec(check);
	        elem[i] = ho;
	    }
	    for (int i = 0;i < a.length;i++){
	        for (int j = 0;j < a[0].length;j++){
	            if (Double.isNaN(a[i][j])){
	                a[i][j] = elem[j];
	            }
	        }
	    }
	    return a;
	}
}
