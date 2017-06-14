package pt.ren.mercado;

import java.util.Map;
import java.util.Scanner;

public class Main {
	private static final String PATH = "http://www.mercado.ren.pt/EN/Electr/MarketInfo/Interconnections/CapForecast/Pages/Daily.aspx";
	private static String[] strMonths = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
			"Dec" };

	public static void main(String[] args) {
		DataProvider data = new DataProvider();
		CapacityCalculator etc = new CapacityCalculator();

		// Creating a map of months and days from URL address and printing to console

		Map<Integer, String> dayMonthMap = data.getDayAndMonth();
		data.printDayAndMonth(dayMonthMap);

		System.out.println("\nType number to calculate daily capacity: \n");

		Scanner sc = new Scanner(System.in);
		Integer number = sc.nextInt();
		
		// Creating a map of months used to print data for results
		
		Map<String, Integer> mapMonths = data.createMonthsMap(strMonths);

		String monthStr = data.getNewMonth(dayMonthMap, number);
		String month = data.printActualCapacity(mapMonths, monthStr);
		String day = data.getNewDay(dayMonthMap, number);
		
		int forecastCapacity = etc.calcForecastCapacity(number, PATH);
		int actualCapacity = etc.calcActualCapacity(number, PATH);

		System.out.println("Actual: 2017" + "-" + month + "-" + day + ": " + "<" + actualCapacity + ">");
		System.out.println("Forecast: 2017" + "-" + month + "-" + day + ": " + "<" + forecastCapacity + ">");

		sc.close();
	}

}
