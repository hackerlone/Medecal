package temp;

import java.util.Calendar;

import org.apache.poi.ss.formula.functions.T;



public class Test {
	
	public static void main(String[] args) {
		ThreadLocal<T> t = null;
		Calendar cd = Calendar.getInstance();
		Calendar ld = Calendar.getInstance();
		ld.set(2016, 7, 4);
		System.out.println(cd.get(Calendar.YEAR));
		System.out.println(ld.get(Calendar.YEAR));
		System.out.println(cd.get(Calendar.MONTH));
		System.out.println(ld.get(Calendar.MONTH));
		int monthDiff = ld.get(Calendar.MONTH) - cd.get(Calendar.MONTH);
		
		int yearDiff = ld.get(Calendar.YEAR) - cd.get(Calendar.YEAR);
		//int months = 12 * yearDiff + monthDiff;
		//int years = months/12;
		System.out.println(yearDiff);
		System.out.println(monthDiff);
		
		//test2();
	}
	
	public static void test2(){
		for(int i = 1910; i<3201;i++ ){
			
			System.out.println("<option>"+i+"</option>");
		}
	}
	
	
	
	public static void test1(){
		System.out.println(7%5);
		System.out.println(5%5);
		System.out.println(2%5);
		System.out.println(0%5);
		System.out.println(30%5);
		System.out.println("-----------");
		System.out.println(7/5);
		System.out.println(5/5);
		System.out.println(2/5);
		System.out.println(0/5);
		System.out.println(30/5);
	}

}
