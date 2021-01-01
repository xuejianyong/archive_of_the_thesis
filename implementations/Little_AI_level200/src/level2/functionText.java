package level2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class functionText {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<1000;i++) {
			list.add(i);
			list.add(1);
		}
		System.out.println(list);
		
		Iterator<Integer> it = list.iterator();
		while (it.hasNext()) {
		System.out.print(" "+it.next());
		
		}
		
		System.out.println("*******************************************************************");
		
		
		//HashMap map = new HashMap();
		
		Map<Integer,List<Integer>> map1 = new HashMap<Integer, List<Integer>>(); 
		
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
		map.put(0, 0);
		map.put(1, 1);
		map.put(2, 2);
		map.put(3, 3);
		map.put(4, 4);
		
		int max = 0;
		for (Integer key : map.keySet()){
			Integer value = map.get(key); 
			System.out.println("key:"+key+" value"+value);
		}
		
		Experience intendedExperience = null;
		System.out.println(intendedExperience.status);
		
		Map<Integer, List<Integer>> experienceMarkedMap = new HashMap<Integer, List<Integer>>(); 
		List<Integer> listInitialValue = new ArrayList<Integer>();
		listInitialValue.add(0);
		listInitialValue.add(0);
		experienceMarkedMap.put(0, listInitialValue);
		experienceMarkedMap.put(1, listInitialValue);
		experienceMarkedMap.put(2, listInitialValue);
		experienceMarkedMap.put(3, listInitialValue);
		experienceMarkedMap.put(4, listInitialValue);
		
		List<Integer> tempUpdateValue = new ArrayList<Integer>();
		tempUpdateValue.add(0);
		tempUpdateValue.add(2);
		experienceMarkedMap.put(0, tempUpdateValue);
		System.out.println(experienceMarkedMap);
		
		List<Integer> tempList = new ArrayList<Integer>();//for update the marked state
		tempList.clear();
		tempList.add(2);
		tempList.add(1);
		System.out.println(tempList);
		tempList.clear();
		tempList.add(1);
		tempList.add(1);
		System.out.println(tempList);
		System.out.println(tempList.get(0));
		
		
		
		int count1 = 0;
		int count2 = 0;
		
		Experience exp00 = new Experience(0,0,0);
		Experience exp01 = new Experience(0,1,0);
		Experience exp10 = new Experience(1,0,0);
		Experience exp11 = new Experience(1,1,0);
		Experience exp20 = new Experience(2,0,0);
		Experience exp21 = new Experience(2,1,0);
		Experience exp22 = new Experience(2,2,10);
		Experience exp30 = new Experience(3,0,0);
		Experience exp31 = new Experience(3,1,0);
		Experience exp40 = new Experience(4,0,0);
		Experience exp41 = new Experience(4,1,0);
		Experience Experiences[][] = {{exp00,exp01},{exp10,exp11},{exp20,exp21,exp22},{exp30,exp31},{exp40,exp41}};
		List<Experience> unknownBeliefList = new ArrayList<Experience>(); 
		unknownBeliefList.add(exp00);
		unknownBeliefList.add(exp01);
		unknownBeliefList.add(exp10);
		unknownBeliefList.add(exp11);
		unknownBeliefList.add(exp20);
		unknownBeliefList.add(exp21);
		unknownBeliefList.add(exp22);
		unknownBeliefList.add(exp30);
		unknownBeliefList.add(exp31);
		unknownBeliefList.add(exp40);
		unknownBeliefList.add(exp41);
		List<Experience> sporaticBeliefList = new ArrayList<Experience>(); 
		List<Experience> persistentBeliefList = new ArrayList<Experience>(); 
		
		Map<Experience,Integer> experienceMarkedMap = new HashMap<Experience, Integer>();
		experienceMarkedMap.put(exp00, 0);
		experienceMarkedMap.put(exp01, 0);
		experienceMarkedMap.put(exp10, 0);
		experienceMarkedMap.put(exp11, 0);
		experienceMarkedMap.put(exp20, 0);
		experienceMarkedMap.put(exp21, 0);
		experienceMarkedMap.put(exp22, 0);
		experienceMarkedMap.put(exp30, 0);
		experienceMarkedMap.put(exp31, 0);
		experienceMarkedMap.put(exp40, 0);
		experienceMarkedMap.put(exp41, 0);
		
		for (Experience key : experienceMarkedMap.keySet()){
			Integer value = experienceMarkedMap.get(key);
			System.out.println(" value : "+value);
		}
		
		for(int i=0;i<experienceMarkedMap.size();i++) {
			//System.out.println(experienceMarkedMap.get(i). + " state ");
			System.out.println(count1);
			count1++;
		}
		
		
		System.out.println("******************************************");
		exp00.status = 2;
		experienceMarkedMap.put(exp00,2);
		for(int i=0;i<experienceMarkedMap.size();i++) {
			System.out.println(experienceMarkedMap.get(i));
			count2++;
			//System.out.println(experienceMarkedMap.get(i) + " state ");
		}
		
		for (Experience key : experienceMarkedMap.keySet()){
			Integer value = experienceMarkedMap.get(key);
			System.out.println(" value : "+value);
		}
		
		
		/*
		Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
		map.put(0, 0);
		map.put(1, 1);
		map.put(2, 2);
		map.put(3, 3);
		map.put(4, 4);
		
		int max = 0;
		for (Integer key : map.keySet()){
			Integer value = map.get(key); 
			System.out.println("key:"+key+" value"+value);
		}
		
		
		Map<Integer,Integer> intMap = new HashMap<Integer,Integer>();
		intMap.put(1, 0);
		intMap.put(2, 0);
		intMap.put(3, 0);
		intMap.put(4, 0);
		
		System.out.println(intMap);
		intMap.put(3, 3);
		System.out.println(intMap);
		
		int value = intMap.get(3);
		System.out.println(value);
		*/
		
		
		
		
		
		
		
		/**************************************************** success 1 **********************************************************
		Experience exp00 = new Experience(0,0,0);
		Experience exp01 = new Experience(0,1,0);
		Experience exp10 = new Experience(1,0,0);
		Experience exp11 = new Experience(1,1,0);
		Experience exp20 = new Experience(2,0,0);
		Experience exp21 = new Experience(2,1,0);
		Experience exp22 = new Experience(2,2,10);
		Experience exp30 = new Experience(3,0,0);
		Experience exp31 = new Experience(3,1,0);
		Experience exp40 = new Experience(4,0,0);
		Experience exp41 = new Experience(4,1,0);
		Experience Experiences[][] = {{exp00,exp01},{exp10,exp11},{exp20,exp21,exp22},{exp30,exp31},{exp40,exp41}};
		List<Experience> beliefList = new ArrayList <Experience>();
		beliefList.add(exp00);
		beliefList.add(exp01);
		beliefList.add(exp00);
		beliefList.add(exp11);
		beliefList.add(exp00);
		beliefList.add(exp11);
		beliefList.add(exp00);
		beliefList.add(exp30);
		beliefList.add(exp30);
		beliefList.add(exp30);
		beliefList.add(exp11);
		Map<Experience,Integer> map = new HashMap<Experience,Integer>();
		for(Experience i :beliefList){
			if (map.get(i)== null) {
				map.put(i, 1);
			}else{
				map.put(i, map.get(i)+1);
			}
		}
		int leastTryCount = 1000;
		System.out.println(beliefList);
		System.out.println(map);
		Experience exp = null;
		for (Experience key : map.keySet()){
			int value = (int)map.get(key); 
			if(value < leastTryCount) {
				exp = key;
				leastTryCount = value;
			}
			System.out.println("key:"+key.action+key.result+" value:"+value);
		}
		System.out.println(exp); 
		int maxValence = 0;
		Experience exp2 = null;
		for (Experience key : map.keySet()){
			int valence = (int)key.valence; 
			if(valence > maxValence) {
				exp2 = key;
				maxValence = valence;
			}
			System.out.println("key:"+key.action+key.result+" value:"+valence);
		}
		System.out.println(exp2); 
		**************************************************** success 1 ***********************************************************/
		
		
		/*
		Experience exp00 = new Experience(0,0,0);
		Experience exp01 = new Experience(0,1,0);
		Experience exp10 = new Experience(1,0,0);
		Experience exp11 = new Experience(1,1,0);
		Experience exp20 = new Experience(2,0,0);
		Experience exp21 = new Experience(2,1,0);
		Experience exp22 = new Experience(2,2,10);
		Experience exp30 = new Experience(3,0,0);
		Experience exp31 = new Experience(3,1,0);
		Experience exp40 = new Experience(4,0,0);
		Experience exp41 = new Experience(4,1,0);
		Experience Experiences[][] = {{exp00,exp01},{exp10,exp11},{exp20,exp21,exp22},{exp30,exp31},{exp40,exp41}};
		List<Experience> beliefList = new ArrayList <Experience>();
		beliefList.add(exp00);
		beliefList.add(exp01);
		beliefList.add(exp00);
		beliefList.add(exp11);
		beliefList.add(exp00);
		beliefList.add(exp11);
		beliefList.add(exp00);
		beliefList.add(exp30);
		beliefList.add(exp30);
		beliefList.add(exp30);
		beliefList.add(exp11);
		Map<Experience,Integer> map = new HashMap<Experience,Integer>();
		for(Experience i :beliefList){
			if (map.get(i)== null) {
				map.put(i, 1);
			}else{
				map.put(i, map.get(i)+1);
			}
		}
		int leastTryCount = 1000;
		System.out.println(beliefList);
		System.out.println(map);
		Experience exp = null;
		for (Experience key : map.keySet()){
			int value = (int)map.get(key); 
			if(value < leastTryCount) {
				exp = key;
				leastTryCount = value;
			}
			System.out.println("key:"+key.action+key.result+" value:"+value);
		}
		System.out.println(exp); 
		int maxValence = 0;
		Experience exp2 = null;
		for (Experience key : map.keySet()){
			int valence = (int)key.valence; 
			if(valence > maxValence) {
				exp2 = key;
				maxValence = valence;
			}
			System.out.println("key:"+key.action+key.result+" value:"+valence);
		}
		System.out.println(exp2); 
		
		
		
		Map<Experience,Integer> experienceMarkedMap = new HashMap<Experience, Integer>();
		experienceMarkedMap.put(exp00, 0);
		experienceMarkedMap.put(exp01, 0);
		experienceMarkedMap.put(exp10, 0);
		experienceMarkedMap.put(exp11, 0);
		experienceMarkedMap.put(exp20, 0);
		experienceMarkedMap.put(exp21, 0);
		experienceMarkedMap.put(exp22, 0);
		experienceMarkedMap.put(exp30, 0);
		experienceMarkedMap.put(exp31, 0);
		experienceMarkedMap.put(exp40, 0);
		experienceMarkedMap.put(exp41, 0);
		
		for (Experience key : experienceMarkedMap.keySet()){
			Integer value = experienceMarkedMap.get(key);
			System.out.println(" value : "+value);
		}
		
		for(int i=0;i<experienceMarkedMap.size();i++) {
			//System.out.println(experienceMarkedMap.get(i). + " state ");
			System.out.println(count1);
			count1++;
		}
		
		
		System.out.println("******************************************");
		exp00.status = 2;
		experienceMarkedMap.put(exp00,2);
		for(int i=0;i<experienceMarkedMap.size();i++) {
			System.out.println(experienceMarkedMap.get(i));
			count2++;
			//System.out.println(experienceMarkedMap.get(i) + " state ");
		}
		
		for (Experience key : experienceMarkedMap.keySet()){
			Integer value = experienceMarkedMap.get(key);
			System.out.println(" value : "+value);
		}
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		Experience exp00 = new Experience(0,0,0);
		Experience exp01 = new Experience(0,1,0);
		Experience exp10 = new Experience(1,0,0);
		Experience exp11 = new Experience(1,1,0);
		Experience exp20 = new Experience(2,0,0);
		Experience exp21 = new Experience(2,1,0);
		Experience exp22 = new Experience(2,2,10);
		Experience exp30 = new Experience(3,0,0);
		Experience exp31 = new Experience(3,1,0);
		Experience exp40 = new Experience(4,0,0);
		Experience exp41 = new Experience(4,1,0);
		Experience Experiences[][] = {{exp00,exp01},{exp10,exp11},{exp20,exp21,exp22},{exp30,exp31},{exp40,exp41}};
		List<Experience> beliefList = new ArrayList <Experience>();
		beliefList.add(exp00);
		beliefList.add(exp01);
		beliefList.add(exp00);
		beliefList.add(exp11);
		beliefList.add(exp00);
		beliefList.add(exp11);
		beliefList.add(exp00);
		beliefList.add(exp30);
		beliefList.add(exp30);
		beliefList.add(exp30);
		beliefList.add(exp11);
		Map<Experience,Integer> map = new HashMap<Experience,Integer>();
		for(Experience i :beliefList){
			if (map.get(i)== null) {
				map.put(i, 1);
			}else{
				map.put(i, map.get(i)+1);
			}
		}
		int leastTryCount = 1000;
		System.out.println(beliefList);
		System.out.println(map);
		Experience exp = null;
		for (Experience key : map.keySet()){
			int value = (int)map.get(key); 
			if(value < leastTryCount) {
				exp = key;
				leastTryCount = value;
			}
			System.out.println("key:"+key.action+key.result+" value:"+value);
		}
		System.out.println(exp); 
		int maxValence = 0;
		Experience exp2 = null;
		for (Experience key : map.keySet()){
			int valence = (int)key.valence; 
			if(valence > maxValence) {
				exp2 = key;
				maxValence = valence;
			}
			System.out.println("key:"+key.action+key.result+" value:"+valence);
		}
		System.out.println(exp2); 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}//end main

}
