package level2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeliefState {

	public Experience persistentExperience;
	public Map<Experience,Integer> numberOfTriedMap = new HashMap<Experience, Integer>();
	public Integer allKnownState = 0;
	public List<Experience>  affordExperienceList = new ArrayList<Experience>();
	
	public void initial() {
		//this map store and update the experiences and the tried number,
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
		numberOfTriedMap.put(exp00, 0);
		numberOfTriedMap.put(exp01, 0);
		numberOfTriedMap.put(exp10, 0);
		numberOfTriedMap.put(exp11, 0);
		numberOfTriedMap.put(exp20, 0);
		numberOfTriedMap.put(exp21, 0);
		numberOfTriedMap.put(exp22, 0);
		numberOfTriedMap.put(exp30, 0);
		numberOfTriedMap.put(exp31, 0);
		numberOfTriedMap.put(exp40, 0);
		numberOfTriedMap.put(exp41, 0);
	}
	
	public BeliefState(){
		initial();
		this.persistentExperience = null;
		//this.affordExperienceList = null;
	}
	
	public BeliefState(Experience persistentExperience){
		initial();
		this.persistentExperience = persistentExperience;
		//this.affordExperienceList = null;
	}
	
	public BeliefState(Integer allKnownState ) {
		this.allKnownState = 1;
	}
	
	@Override
	public String toString() {
		if(this.persistentExperience == null) {
			return "unknown";
		}else {
			return this.persistentExperience.toString();
		}
		
	}
	
	/*
	public Experience getLeastTried(List<Experience> beliefList) {
		//List <Integer> list = new ArrayList <Integer>();
		Map<Experience,Integer> map = new HashMap<Experience,Integer>();
		for(Experience i :beliefList){
			if (map.get(i)== null) {
				map.put(i, 1);
			}else{
				map.put(i, map.get(i)+1);
			}
		}// finish the statistics of the beliefList
		
		//System.out.println(beliefList);
		//System.out.println("The Map of the current beliefList is as below:");
		//System.out.println(map);
		
		int leastTryCount = 1000;
		Experience exp = null;
		for (Experience key : map.keySet()){
			int value = (int)map.get(key); 
			if(value < leastTryCount) {
				exp = key;
				leastTryCount = value;
			}
			System.out.println("key:"+key.action+key.result+" countNum:"+value);
		}
		System.out.println(exp.action+""+exp.result); 
		return exp;
	}
	*/
	
	public Experience leastTriedExperience() {
		//can use the Map to interator the tried number;
		//state of wellknown;
		Experience exp = null ;
		int min = 1000;
		for(Experience key : numberOfTriedMap.keySet()) {
			int value = (int) numberOfTriedMap.get(key);
			if(value < min) {
				min = value;
				exp = key;
			}
		}
		return exp;
	}
	
	
	public Experience intentionWithMaxExpectedOutcomeValence(List<List<Experience>> perSpoPerList) {
		//can use the Map to interator the tried number;
		Experience exp = null;
		int valence = 0;
		for(int perSpoPerIndex=0;perSpoPerIndex<perSpoPerList.size();perSpoPerIndex++) {
			List<Experience> perSpoPerListSmall = perSpoPerList.get(perSpoPerIndex);
			if(!(perSpoPerListSmall.get(2).valence < valence)) {
				exp = perSpoPerListSmall.get(2);
				valence = perSpoPerListSmall.get(2).valence;
			}
		}
		//System.out.println(exp.toString());
		return exp;
	}
	
	
	public Experience getSporadicExperience(List<List<Experience>> perSpoPerList, Experience intendedExperience, Experience enactedExperience) {
		// TODO Auto-generated method stub
		Experience exp = null;
		for(int perSpoPerIndex=0;perSpoPerIndex<perSpoPerList.size();perSpoPerIndex++) {
			List<Experience> perSpoPerListSmall = perSpoPerList.get(perSpoPerIndex);
			Experience persistentPre = perSpoPerListSmall.get(0);
			Experience persistentPos = perSpoPerListSmall.get(2);
			if((persistentPre.action == enactedExperience.action && persistentPre.result == enactedExperience.result) &&
					(persistentPos.action == intendedExperience.action && persistentPos.result == intendedExperience.result)) {
				exp = perSpoPerListSmall.get(1);
			}
		}
		return exp;
	}
	
	
	
	
	
	
	
	public String printBeliefState() {
		return "";
	}
	
	public void updateTriedNumberofExperience(Experience intendedExperience) {
		// TODO Auto-generated method stub
		int value;
		for(Experience key : numberOfTriedMap.keySet()) {
			if(key.action ==intendedExperience.action && key.result == intendedExperience.result){
				value = (int)numberOfTriedMap.get(key);
				numberOfTriedMap.put(key, value+1);
			}
		}
	}
	
	public boolean isAllMarked(Map<Experience, Integer> experienceMarkedMap) {
		// TODO Auto-generated method stub
		boolean isAllMarked = true;
		for(Experience key : experienceMarkedMap.keySet()) {
			if((int)experienceMarkedMap.get(key) == 0)isAllMarked = false;
		}
		return isAllMarked;
	}

	public boolean isAllTried() {
		// TODO Auto-generated method stub
		boolean isAllTried = true;
		for(Experience key : numberOfTriedMap.keySet()) {
			if((int) numberOfTriedMap.get(key) == 0) isAllTried = false;
		}
		return isAllTried;
	}
	
	public BeliefState updateAndGetBelief(Experience enactedExperience) {
		int updataedBeliefState = 0;
		//return updataedBeliefState;
		return null;
	}

	public int getMarkFromMap(Map<Experience, Integer> experienceMarkedMap, Experience enactedExperience) {
		// TODO Auto-generated method stub
		int mark = 0;
		for(Experience key : experienceMarkedMap.keySet()) {
			if(key.action == enactedExperience.action && key.result == enactedExperience.result) {
				mark = (int)experienceMarkedMap.get(key);
			}
		}
		return mark;
	}

	public boolean isRecentExperienceChanged(List<Integer> recentExperience,int recentCount) {
		// TODO Auto-generated method stub
		boolean ischange = false;
		int count = 0;
		//int recentCount = 20;
		if(!(recentExperience.size()<recentCount)) {
			for(int recentIndex=recentExperience.size()-recentCount;recentIndex<recentExperience.size();recentIndex++) {
				count+=recentExperience.get(recentIndex);
			}
			if(count > 0)ischange = true;
		}
		return ischange;
	}
	
	public boolean getIsSame(List<List<Experience>> perSpoPerList, Experience enactedObserverPre,
			Experience enactedExperienceSpo, Experience enactedObserverPos) {
		// TODO Auto-generated method stub
		boolean isSame = false;
		for(int checkIndex=0;checkIndex<perSpoPerList.size();checkIndex++) {
			List<Experience> perSpoPerListSmall = perSpoPerList.get(checkIndex);
			if(enactedObserverPre.action == perSpoPerListSmall.get(0).action &&
			   enactedObserverPre.result == perSpoPerListSmall.get(0).result &&
			   enactedExperienceSpo.action == perSpoPerListSmall.get(1).action &&
			   enactedExperienceSpo.result == perSpoPerListSmall.get(1).result &&
			   enactedObserverPos.action == perSpoPerListSmall.get(2).action &&
			   enactedObserverPos.result == perSpoPerListSmall.get(2).result
		   )isSame = true;
		}
		return isSame;
	}

	
	
}
