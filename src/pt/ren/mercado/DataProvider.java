package pt.ren.mercado;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataProvider {

	private static final String PATH = "http://www.mercado.ren.pt/EN/Electr/MarketInfo/Interconnections/CapForecast/Pages/Daily.aspx";
	private Map<String, Integer> mapMonths;
	private Document doc;

	public DataProvider() {
	}

	public String getNewMonth(Map<Integer, String> map, Integer key) {
		String[] arrMonth = new String[1];
		String mon = map.get(key);
		arrMonth = mon.split(" ");

		return arrMonth[arrMonth.length - 1];
	}

	public String getNewDay(Map<Integer, String> map, Integer key) {
		String[] arrDay = new String[1];
		String mon = map.get(key);
		arrDay = mon.split(" ");
		return arrDay[0];
	}

	public void printDayAndMonth(Map<Integer, String> mapDayAndMonth) {
		for (Map.Entry<Integer, String> entry : mapDayAndMonth.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public Map<String, Integer> createMonthsMap(String[] strMonths) {
		mapMonths = new TreeMap<>();
		for (Integer i = 1; i <= strMonths.length; i++) {
			mapMonths.put(strMonths[i - 1], i);
		}
		return mapMonths;
	}

	public String printActualCapacity(Map<String, Integer> mapMonths, String key) {
		Integer month = mapMonths.get(key);
		String newMonth = "";

		if (month < 10) {
			newMonth = "0" + month.toString();
		} else {
			newMonth = month.toString();
		}
		return newMonth;

	}

	/**
	 * 
	 * @return map of months and days from URL address
	 */
	public Map<Integer, String> getDayAndMonth() {
		Map<Integer, String> map = new HashMap<>();
		try {
			doc = Jsoup.connect(PATH).get();

			for (Element table : doc.select("table.gridALL")) {
				for (Element row : table.select("tr")) {
					Elements tds = row.select("th");
					if (tds.size() >= 16) {
						for (int i = 1; i < 16; i++) {
							String day = tds.get(i).text();
							map.put(i, day);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
