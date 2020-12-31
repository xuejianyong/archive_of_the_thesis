package level2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SaveCode {

	
	public void saveCode() {
		// actionType has a attribute is that tried numbers , the Maximum Valence.
				// HashMap map = new HashMap()
				HashMap map = new HashMap();
				map.put(0, 0);
				map.put(1, 0);
				map.put(2, 0);
				map.put("Feel Right", 0);
				map.put("Swap Rightt", 0);
				Set set = map.keySet();
				for(Iterator iter = set.iterator(); iter.hasNext();){
					String key = (String)iter.next();
					int value = (int)map.get(key);
					}
	}
			//************************************************************************************************************************
			/**
			Map<Integer, List<Integer>> experienceMarkedMap = new HashMap<Integer, List<Integer>>(); 
			List<Integer> listInitialValue = new ArrayList<Integer>();
			listInitialValue.add(0);// state of the experience    : unknown , sporatic , persistent
			listInitialValue.add(0);// the flag of the experience : isWellKnown : 0 unknown 1:known
			experienceMarkedMap.put(0, listInitialValue);
			experienceMarkedMap.put(1, listInitialValue);
			experienceMarkedMap.put(2, listInitialValue);
			experienceMarkedMap.put(3, listInitialValue);
			experienceMarkedMap.put(4, listInitialValue);
			
			Map<Integer, Integer> experienceMarkedMap1 = new HashMap<Integer, Integer>(); 
			experienceMarkedMap1.put(0, 0);
			experienceMarkedMap1.put(1, 0);
			experienceMarkedMap1.put(2, 0);
			experienceMarkedMap1.put(3, 0);
			experienceMarkedMap1.put(4, 0);
			*/
			
			/*
			Map<Integer, Integer> unknownBeliefMap = new HashMap<Integer, Integer>(); 
			Map<Integer, Integer> sporaticBeliefMap = new HashMap<Integer, Integer>(); 
			Map<Integer, Integer> persistentBeliefMap = new HashMap<Integer, Integer>(); 
			
			List<Integer> unknownBeliefList = new ArrayList<Integer>(); 
			unknownBeliefList.add(0);
			unknownBeliefList.add(1);
			unknownBeliefList.add(2);
			unknownBeliefList.add(3);
			unknownBeliefList.add(4);
			List<Integer> sporaticBeliefList = new ArrayList<Integer>(); 
			List<Integer> persistentBeliefList = new ArrayList<Integer>(); 
			*/
			
			//************************************************************************************************************************
	
	
	/*
	switch(mood){
	case 0://curious
		if(isInitial){
			intendedExperience = exp00;
			isInitial = false;
		}else{
			intendedExperience = beliefState.leastTriedExperience(currentBeliefState,unknownBeliefList,sporaticBeliefList,persistentBeliefList);// the implementation of this function needs the type of current belief and the corresponding belieflist
		}break;
	case 1://hedonist
		intendedExperience = beliefState.intentionWithMaxExpectedOutcomeValence(currentBeliefState,unknownBeliefList,sporaticBeliefList,persistentBeliefList);
		break;
	case 2://excited------------------------------------------------------------
		if(!enactedExperience.equals(intendedExperience)) {
			intendedExperience.status = 1;
			sporaticBeliefList.add(intendedExperience);
			unknownBeliefList.remove(intendedExperience);
			experienceMarkedMap.put(intendedExperience,1);
			//mood = ?
		}else if(excitementCount > excitementThreshold) {
			enactedExperience.status = 2; // current belief is persistent
			unknownBeliefList.remove(enactedExperience);// remove the experience from the unknownList
			persistentBeliefList.add(enactedExperience);// add the experience to the persistentBeliefList
			experienceMarkedMap.put(enactedExperience,2);// update the marked state of the experience
			BeliefState newbeliefState = new BeliefState(enactedExperience);
			beliefStateList.add(newbeliefState); 
			mood = 0;
			excitementCount = 0;
			currentBeliefState = 2;
		}else {
			intendedExperience = enactedExperience;
			excitementCount++;
		}break;
	}//end switch
	
	int result = env.getResult(intendedExperience.action);
	enactedExperience = Experiences[intendedExperience.action][result];
	score = score + enactedExperience.valence;
	experienceList.add(intendedExperience);
	
	int mark = (int)experienceMarkedMap.get(enactedExperience);//get the Marked-State(Belief State) of the experience
	if(mark == 0)mood = 2;//the belief state of this experience is neither yet marked sporadic or persistent, the IS get excited.
	
	
	
	case 2://excited------------------------------------------------------------
				if(!enactedExperience.equals(intendedExperience)) {
					intendedExperience.status = 1;
					sporaticBeliefList.add(intendedExperience);
					unknownBeliefList.remove(intendedExperience);
					experienceMarkedMap.put(intendedExperience,1);
					//mood = ?
				}else if(excitementCount > excitementThreshold) {
					enactedExperience.status = 2; // current belief is persistent
					unknownBeliefList.remove(enactedExperience);// remove the experience from the unknownList
					persistentBeliefList.add(enactedExperience);// add the experience to the persistentBeliefList
					experienceMarkedMap.put(enactedExperience,2);// update the marked state of the experience
					BeliefState newbeliefState = new BeliefState(enactedExperience);
					beliefStateList.add(newbeliefState); 
					
					mood = 0;
					excitementCount = 0;
					currentBeliefState = 2;
				}else {
					intendedExperience = enactedExperience;
					System.out.println("excitementCount is: "+excitementCount);
					excitementCount++;
				}break;
	
	
	
	
							
				//construct the previous and post experience
				if(!preAndPost.containsKey(intendedExperience)) {
					preAndPost.put(intendedExperience, enactedExperience);
				}
				
				// use the persistent experience observe the sporadic experience
				if(newCreatedBeliefList.size()>0) {
					for(int newCreatedBeliefIndex=0;newCreatedBeliefIndex<newCreatedBeliefList.size();newCreatedBeliefIndex++) {
						Experience persistentExperiencePre = newCreatedBeliefList.get(newCreatedBeliefIndex).persistentExperience;
						int resultObserverPre = env.getResult(persistentExperiencePre.action);
						Experience enactedPersistentExperiencePre = Experiences[persistentExperiencePre.action][resultObserverPre];
						int resultObserved = env.getResult(intendedExperience.action);
						int resultObserverPost = env.getResult(persistentExperiencePre.action);
						Experience enactedPersistentExperiencePost = Experiences[persistentExperiencePre.action][resultObserverPost];
						if(resultObserverPre != resultObserverPost) {
							List<Experience> perSpoPerListSmall = new ArrayList<Experience>();
							perSpoPerListSmall.add(enactedExperience);
							perSpoPerListSmall.add(intendedExperience);
							perSpoPerListSmall.add(enactedPersistentExperiencePost);
							perSpoPerList.add(perSpoPerListSmall);
						}
					}//end for
				}//end if
							
							
							
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	*/
	
	

	
	
}
