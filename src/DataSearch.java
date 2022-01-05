import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class DataSearch {
	Set<String> recordsSet = new HashSet<>();
	Set<String> citiesSet = new HashSet<>();
	Set<String> housesSet = new HashSet<>();	
	Map<String, String> HousesMap = new HashMap<>();
	Map<String, Integer> FloorMap = new HashMap<>();	
	Map<String, List<Integer>> citiesFloorMap = new HashMap<>();
	List<String> readAllLines;	
	int[] fArr = {0};
	int[] againeArr = {0};
	int[] allArr = {0};	
	
	
		
	void collectData(String excelFile) {
		//вызов функции чтения файла
		searchDuplicatedRecords(excelFile);
		citiesSet.remove("\"city\"");		
		//System.out.println("cities: " + citiesSet);
		//System.out.println("HousesMap.size: " + HousesMap.size());
		//System.out.println("FloorMap.size: " + FloorMap.size());	
		//System.out.println("cities.size: " + citiesSet.size());
		//System.out.println("houses.size: " + housesSet.size());		
		System.out.println("Дублирующих записей:  " + againeArr[0]);			
		
		
		for (String s : citiesSet) {				
		//вызов функции фильтрации по городам			
		Map<String, String> filteredMap = sortByCity(s);
					
		//вызов функции	этажности домов
		List<Integer> fList = createFloorList(filteredMap);
				    
		citiesFloorMap.put(s,fList);
		System.out.println("Город:  " + s);
		System.out.println("1-этажных:  " + fList.get(0));
		System.out.println("2-этажных:  " + fList.get(1));
		System.out.println("3-этажных:  " + fList.get(2));
		System.out.println("4-этажных:  " + fList.get(3));
		System.out.println("5-этажных:  " + fList.get(4));   
		}
	}
	
	
	
	public void searchDuplicatedRecords(String excelFile) {
		//String excelFile = "E:\\address.xls";				
	    //System.out.println(excelFile);	
		
		try {
			System.out.println("Searching houses...  Поиск домов...");		
			readAllLines = Files.readAllLines(Paths.get(excelFile));			
		
			readAllLines.stream().forEach(record -> {			
				
				String city = record.split(";")[0];		
				String floor = record.split(";")[3];
				String[] recordSplit= record.split(";");
				String recordHouse = recordSplit[0] + " " + recordSplit[1] + " " + recordSplit[2];		
				//System.out.println(city);
				//System.out.println(house);
				//System.out.println(floor);			
	
				fArr[0] = 0;		
				if (floor.equals("1")) {fArr[0] = 1;}
				if (floor.equals("2")) {fArr[0] = 2;}
				if (floor.equals("3")) {fArr[0] = 3;}
				if (floor.equals("4")) {fArr[0] = 4;}
				if (floor.equals("5")) {fArr[0] = 5;}			
			
				//System.out.println(allArr[0]);
				if (allArr[0] == 1) { 
					HousesMap.put(recordHouse, city);
					FloorMap.put(recordHouse, fArr[0]);
				}
		 
			    if (housesSet.add(recordHouse)) {			  
				    HousesMap.put(recordHouse, city);				  
				    FloorMap.put(recordHouse, fArr[0]);				  
			   }			  
			   else {
				   if (fArr[0]> FloorMap.get(recordHouse)) {				
					FloorMap.put(recordHouse, fArr[0]);				
			        }					
			   }	
			    
			    if (!recordsSet.add(record)) {	   			
			    	againeArr[0]++;						
			    }
			    if (!citiesSet.add(city)) {			
			    }				
			    allArr[0]++;
			});	
	
		} catch (IOException e) {
		System.out.println("Файл не читается. В первую очередь проверьте, верно ли задан путь до файла!\n");
		//e.printStackTrace();
		}		
	}
	
	
	
	private Map<String, String> sortByCity(String s) {
		Map<String, String> filteredMap = new HashMap<>();
		filteredMap = HousesMap.entrySet()
		        .stream().filter(x->s.equals(x.getValue()))
		        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
				//System.out.println("Filtered map: " + filteredMap);
		return filteredMap;
	}
	
	
		
	private List<Integer> createFloorList(Map<String, String> filteredMap) {
				int f1=0;
				int f2=0;
				int f3=0;
				int f4=0;
				int f5=0;					
				List<Integer> fList = Arrays.asList(0, 0, 0, 0, 0);				

				for(String key : filteredMap.keySet()) {
					int floorValue = FloorMap.get(key);
					switch(floorValue) {
					case 1: f1+=1;   	
			    	case 2: f2+=1;
			    	case 3: f3+=1;
			    	case 4: f4+=1;
			    	case 5: f5+=1;
			    	default:
					}
				fList.set(0, f1);
				fList.set(1, f2);
				fList.set(2, f3);
				fList.set(3, f4);
				fList.set(4, f5);					
				}				
				return fList;		
	}	
}

