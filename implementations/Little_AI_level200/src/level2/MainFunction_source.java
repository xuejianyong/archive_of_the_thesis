package level2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class MainFunction_source {
	
	/* array[i][j]  j={actionType,resultNumber,Value,colorIndex} 
	 * 0:feel left
	 * 1:swap left
	 * 2:feel both
	 * 3:feel right
	 * 4:swap right
	 */
	public int experiences[][] = {
			{0,0,0,2},{0,1,0,1},
			{1,0,0,2},{1,1,0,1},
			{2,0,0,2},{2,1,0,4},{2,2,10,1},
			{3,0,0,2},{3,1,0,1},
			{4,0,0,2},{4,1,0,1}};
	
	public int getScore(int actionType,int result) {
		// get the score from the experiences
		int score = 0;
		for(int i=0;i<11;i++){
			if(experiences[i][0] == actionType) {
				if(experiences[i][1]==result) {
					score = experiences[i][2];
				};
			}else if(experiences[i][0] > actionType)break;
		}
		return score;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		/*
		int min = 0;
		int max = 4;
		int historicalDepth = 10; // I have no idea about this variate
		*/
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
		String fileName = "ExperimentResult"+df.format(new Date()).toString();
		System.setOut(new PrintStream(new FileOutputStream("./Result/"+fileName+".txt")));
		//**************************************** the preparing work for implementation ****************************************************
		int threshold = 5;
		int winScore = 10;
		int score = 0;
		String experienceLabels[]= {"Feel Left","Swap Left","Feel Both","Feel Right","Swap Right"};
		String states[] = {"unknown","sporadic","persistent"}; // unknown 0, sopradic 1, persistent 2
		String moodLabels[] = {"curious","hedonist","excited"};// curious 0, hedonist 1, excited 2
		int mood = 0;
		
		BeliefState unknownBeliefState = new BeliefState(); // the initial beliefState is unknown
		BeliefState currentBeliefState = unknownBeliefState;
		List<BeliefState> newCreatedBeliefList = new ArrayList<BeliefState>();//use to save the new created belief state
		
		List<Integer> sporadicActionList = new ArrayList<Integer>();
		List<List<Experience>> perSpoPerList = new ArrayList<List<Experience>>();
		Map<List<Experience>,Integer> perPerSpoMap = new HashMap<List<Experience>,Integer>();//experience,experience,sporadicAction
		Map<List<Experience>,Integer> perSpoPerIsConfirmMap = new HashMap<List<Experience>,Integer>();//experience,sporadicExperience,experience,isConfirmed
		
		Environment env = new Environment();
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
		
		// Experience marked code domain
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
		
		boolean isInitial = true;
		boolean isAllTried = false;
		boolean isMarked = false;
		boolean isAllMarked = false;
		boolean isUpdate = false;
		
		List<Experience> experienceList = new ArrayList<Experience>();//record the experienced experience
		List<Integer> recentExperience = new ArrayList<Integer>();
		//************************************************ the initial work *****************************************
		int excitementCount = 1;          //the number of excitement
		int excitementThreshold = 5;
		Experience intendedExperience = null;
		Experience enactedExperience = null;
		int countNum = 0;
		int result = 0;
		int mark;
		int recentCount = 20;
		List<List<Integer>> numScoreList = new ArrayList<List<Integer>>();// to show the countNum and Score
		//********************************************** start the algorithm *****************************************
		while(countNum++ < 81) {
			//the function of this switch is to chose the next experience
			System.out.println("The Current Count Number is:"+countNum);
			System.out.println("the BeliefState is:"+currentBeliefState.toString());
			System.out.println("the Mood  State is:"+mood);
			switch(mood){
			case 0://curious
				if(isInitial){
					intendedExperience = exp00;
					isInitial = false;
				}else{
					intendedExperience = currentBeliefState.leastTriedExperience();
				}break;
			case 1://hedonist
				System.out.println("=====================================================");
				//parameters(perPerSpoMap)try to get the max valence with the map of (persistent,persistent Sporadic action)
				intendedExperience = currentBeliefState.intentionWithMaxExpectedOutcomeValence(perSpoPerList);
				//intendedExperience = beliefState.intentionWithMaxExpectedOutcomeValence(currentBeliefState,
				//unknownBeliefList,sporaticBeliefList,persistentBeliefList);
				break;
			case 2://excited------------------------------------------------------------
				intendedExperience = enactedExperience;
				break;
			}//end switch
			System.out.println("the IntendedExperience is:"+intendedExperience.toString());
			result = env.getResult(intendedExperience.action);
			enactedExperience = Experiences[intendedExperience.action][result];
			System.out.println("the EnactedExperience  is:"+enactedExperience.toString());
			score = score + enactedExperience.valence;
			System.out.println("The current score is:"+score);
			experienceList.add(intendedExperience);
			currentBeliefState.updateTriedNumberofExperience(intendedExperience);
			if(mood == 2) {
				if(!(enactedExperience.action == intendedExperience.action && 
						enactedExperience.result == intendedExperience.result)) {
					for(Experience key : experienceMarkedMap.keySet()) {
						if(key.action == intendedExperience.action  && key.result == intendedExperience.result) {
							experienceMarkedMap.put(intendedExperience,1);
							System.out.println("update the experienceMarkedMap 1 and enter the marked function");
						}
					}
					mood = 0;// refer to step 11
					currentBeliefState = unknownBeliefState; // refer to step 11
					isMarked = true;
					if(!sporadicActionList.contains(intendedExperience.action)) {
						sporadicActionList.add(intendedExperience.action);
					}
				}else if(!(excitementCount < excitementThreshold)) {
					for(Experience key : experienceMarkedMap.keySet()) {
						if(key.action == intendedExperience.action  && key.result == intendedExperience.result) {
							experienceMarkedMap.put(key,2);
							currentBeliefState = new BeliefState(enactedExperience);
							newCreatedBeliefList.add(currentBeliefState);// createNewBeliefSta
							//currentBeliefState = newBeliefState;
							System.out.println("update the experienceMarkedMap 2 and enter the marked function");
						}
					}
					mood = 0;
					excitementCount = 1;
					isMarked = true;
				}else{
					excitementCount++;
				}
			}//end if(mood = 2)
			
			
			/*
			BeliefState nextBeliefState = currentBeliefState.updateAndGetBelief(enactedExperience);//�ú���������ʹ�������������ڵ�֮����໥��ϵ�ġ�
			if(nextBeliefState == null) {
				//currentBeliefState = unknownBeliefState;
			}else {
				currentBeliefState = nextBeliefState;
			}
			*/		
			
			/*
			 *  use the list to learn the arcs between different type experiences, but how to handle with the settings of currentbeliefstate
			 *  this procedure only happy in the situation when the isMarked changed
			 */
			//the dual analysis(����˫�ط���) consider the current situation of the environment(state of phenomenonLeft and phenomenonRight)
			boolean isChanged = false;
			if(isMarked) {
				System.out.println();
				System.out.println("Enter the connected procedure process");
				if(sporadicActionList.size()>0 && newCreatedBeliefList.size()>0) {
					loop:for(int sporadicIndex=0;sporadicIndex<sporadicActionList.size();sporadicIndex++) {
						int sporadicAction = sporadicActionList.get(sporadicIndex);
						System.out.println("sporadicAction is:"+sporadicAction);
						for(int persistentIndex=0;persistentIndex<newCreatedBeliefList.size();persistentIndex++) {
							Experience observer = newCreatedBeliefList.get(persistentIndex).persistentExperience;
							System.out.println("observer is:"+observer.toString());
							System.out.println("The state of the environment is:  left:"+env.phenomenonLeft+"  right:"+env.phenomenonRight);
							int observerResultPre = env.getResult(observer.action);
							Experience enactedExperiencePre = Experiences[observer.action][observerResultPre];
							System.out.println("enactedExperiencePre is:"+enactedExperiencePre.toString());
							if(observerResultPre != observer.result) {// the persistent experience is not the current persistent experience
								System.out.println("enter the function if(observerResultPre != observer.result)");
								int persistentMark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperiencePre);
								if(persistentMark == 0) {
									System.out.println("persistentMark is 0, so the agent need to check the mark of this enactedExperience");
									mood = 2;
									currentBeliefState = unknownBeliefState;
									isUpdate = true;
									enactedExperience = enactedExperiencePre;
									break loop;
								}else {// the persistent experience is already marked
									isUpdate = false;
									//try the sporadic experience.
									int observedResult = env.getResult(sporadicAction);
									Experience enactedExperienceSpo = Experiences[sporadicAction][observedResult];
									System.out.println("the enactedExperienceSpo is :"+enactedExperienceSpo.toString());
									int observerResultPos = env.getResult(observer.action);
									Experience enactedExperiencePos = Experiences[observer.action][observerResultPos];
									System.out.println("the enactedExperiencePos is :"+enactedExperiencePos.toString());
									if(observerResultPos == observer.result) {
										List<Experience> prePos = new ArrayList<Experience>();
										List<Experience> perSpoPerListSmall = new ArrayList<Experience>();
										prePos.add(enactedExperiencePre);
										prePos.add(enactedExperiencePos);
										
										perSpoPerListSmall.add(enactedExperiencePre);
										perSpoPerListSmall.add(enactedExperienceSpo);
										perSpoPerListSmall.add(enactedExperiencePos);
										// should store the beliefstate not just the persistent experience.
										boolean isSame = currentBeliefState.getIsSame(perSpoPerList,enactedExperiencePre,
												enactedExperienceSpo,enactedExperiencePos);
										if(!isSame) {
											perSpoPerList.add(perSpoPerListSmall);
										}
										
										if(!perPerSpoMap.containsKey(prePos)) {
											perPerSpoMap.put(prePos, sporadicAction);
											isChanged = true;
										}
									}
								}
							}else {//��ǰ״̬
								System.out.println("enter the same result function");
								int observedResult = env.getResult(sporadicAction);
								Experience enactedExperienceSpo = Experiences[sporadicAction][observedResult];
								System.out.println("the enactedExperienceSpo is :"+enactedExperienceSpo.toString());
								int observerResultPos = env.getResult(observer.action);
								Experience enactedExperiencePos = Experiences[observer.action][observerResultPos];
								System.out.println("enactedExperiencePos is:"+enactedExperiencePos.toString());
								if(observerResultPos != observer.result) {// shows the sporadic action works on current persistent experience
									System.out.println("enter the function if(observerResultPos != observer.result)");
									int persistentMark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperiencePos);
									if(persistentMark == 0) {
										System.out.println("persistentMark is 0, so the agent need to check the mark of this enactedExperience");
										mood = 2;
										currentBeliefState = unknownBeliefState;
										isUpdate = true;
										enactedExperience = enactedExperiencePos;
										break loop;
									}else {
										isUpdate = false;
										List<Experience> prePos = new ArrayList<Experience>();
										List<Experience> perSpoPerListSmall = new ArrayList<Experience>();
										prePos.add(enactedExperiencePre);
										prePos.add(enactedExperiencePos);
										
										perSpoPerListSmall.add(enactedExperiencePre);
										perSpoPerListSmall.add(enactedExperienceSpo);
										perSpoPerListSmall.add(enactedExperiencePos);
										boolean isSame = currentBeliefState.getIsSame(perSpoPerList,enactedExperiencePre,
												enactedExperienceSpo,enactedExperiencePos);
										if(!isSame) {
											perSpoPerList.add(perSpoPerListSmall);
										}
										if(!perPerSpoMap.containsKey(prePos)) {
											perPerSpoMap.put(prePos, sporadicAction);
											isChanged = true;
										}
									}
								}
							}
						}
					}
				}
				isMarked = false;
				//currentBeliefState = currentBeliefState.updateAndGetBelief(enactedExperience,mark,newCreatedBeliefList,env,Experiences);
			}//end if(isMarked)
			
			
			System.out.println("isChanged is:"+isChanged);
			if(isChanged)recentExperience.add(1);
			else recentExperience.add(0);
			System.out.println();
			// get the Marked-State of the EnactedExperience  *should check this state for every EnactedExperience
			mark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperience);
			if(!isUpdate) {
				if(mark == 0) {
					System.out.println("This EnactedExperience Marked is: "+mark);
					mood = 2;
					currentBeliefState = unknownBeliefState;
				}else {
					System.out.println("This EnactedExperience is already Marked ! mark is: "+mark);
				}
			}

			
			// defined the status for agent become hedonist
			isAllTried = currentBeliefState.isAllTried();
			if(isAllTried) {
				//if(isAllTried)mood  = 0;
				System.out.println("The experiences are AllTried in this belief!");
			}
			isAllMarked = currentBeliefState.isAllMarked(experienceMarkedMap);
			if(isAllMarked) {
				System.out.println("The experiences are isAllMarked!");
			}
			boolean ischange = currentBeliefState.isRecentExperienceChanged(recentExperience,recentCount);
			System.out.println("ischange is:"+recentExperience);
			if(isAllTried && isAllMarked && (!ischange)) {
				mood = 1;
			}
			
			
			if(newCreatedBeliefList.size() == 0) {
				System.out.println("The newCreatedBeliefList is NULL");
			}else {
				System.out.println("The createNewBeliefStateList is:");
				for(int index = 0;index < newCreatedBeliefList.size();index ++) {
					System.out.println(newCreatedBeliefList.get(index).persistentExperience.toString());
				}
			}
			System.out.println();
			System.out.println("the experience marked map is as follows:");
			for(Experience key : experienceMarkedMap.keySet()) {
				System.out.println(key.toString()+" Marked:"+experienceMarkedMap.get(key));
			}
			System.out.println();
			System.out.println("the Updated Belief is:"+currentBeliefState);
			System.out.println("the Updated Mood   is:"+mood);
			System.out.println();
			System.out.println("The currentBeliefState numberOfTriedMap");
			for(Experience key : currentBeliefState.numberOfTriedMap.keySet()) {
				System.out.println(key.toString()+" number:"+currentBeliefState.numberOfTriedMap.get(key));
			}
			System.out.println();
			System.out.println("The sporadicActionList is:");
			for(int sporadicIndex=0;sporadicIndex<sporadicActionList.size();sporadicIndex++) {
				System.out.println(sporadicActionList.get(sporadicIndex));
			}
			System.out.println("The persistent persistent sporadic Map is:");
			for(List<Experience> key : perPerSpoMap.keySet()) {
				System.out.println(key.toString()+" "+perPerSpoMap.get(key));
			}
			System.out.println("The persistent sporadic persistent list is:");
			for(int showIndex = 0;showIndex<perSpoPerList.size();showIndex++) {
				System.out.println(perSpoPerList.get(showIndex));
			}
			System.out.println("The state of the environment is:  left:"+env.phenomenonLeft+"  right:"+env.phenomenonRight);
			List<Integer> numScoreSmall = new ArrayList<Integer>();
			numScoreSmall.add(score);
			numScoreSmall.add(countNum);
			
			numScoreList.add(numScoreSmall);
			System.out.println();
			System.out.println("numScoreList is:");
			System.out.println(numScoreList);
			System.out.println("--------------------------------------------------");
			
		
		}//end while
		
		
		
		
		
	}//end main

}
