package emdp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class EMDP {

	public int env[][] = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,1,1,0,0,1,1},
			{1,0,1,0,0,1,1,0,0,1},
			{1,0,1,1,0,1,1,1,0,1},
			{1,0,0,0,0,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,0,1,1,1,1,1,0,1},
			{1,1,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1}};
	
	public int env_back[][] = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	
	public Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();
	public Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;
	public Position randomPosition;
	public String direction = "";
	public int loopNum = 1;
	public Interaction enactedInteraction = null;
	public Interaction superInteraction = null;
	public Interaction intendedInteraction = null;
	public Interaction enactedInteractionCurrent = null;
	public int bumpTimes = 0;
	public int totalValence = 0;
	
	public int threshold = 4;
	public int loopLimit = 1001;
	
	public Interaction getSuperInteraction() {
		return superInteraction;
	}
	public void setSuperInteraction(Interaction superInteraction) {
		this.superInteraction = superInteraction;
	}
	public Interaction getEnactedInteraction() {
		return enactedInteraction;
	}
	public void setEnactedInteraction(Interaction enactedInteraction) {
		this.enactedInteraction = enactedInteraction;
	}

	public EMDP() throws IOException {
		// TODO Auto-generated constructor stub
		
		
		SimpleDateFormat sdf = new SimpleDateFormat();// ��ʽ��ʱ�� 
        sdf.applyPattern("ddMMyyyy_HHmmss");
        Date date = new Date();
        //sdf.format(date)
		String filename = "D:\\eclipse\\workspace\\maze_optimize_svg-2_abstract_multi_th1\\src\\dl\\results\\result_"+threshold+"_"+loopLimit+".txt";
		System.out.println(filename);
		File writename = new File(filename); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
		writename.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		
		
		
		while(loopNum < loopLimit) {
			//interaction starts here
			System.out.println("------------------------------------------------------------------------");
			System.out.println("loopNum: "+loopNum);
			if(loopNum == 1) {
				initial(5,-10,-3,-1,-2);
				randomPosition = getInitialPosition();//������Ҫ�ú�����
				direction = randomPosition.getDirection();//������Ҫ�ú�����
			}
			
			List<Anticipation> anticipations = anticipate();
			Anticipation selectedAnticipation = selectInteraction(anticipations);
			
			if(selectedAnticipation.isPrimitive()) {//�õ���default anticipation����anticipationListΪ�գ�ֱ��ִ�ж�������
				System.out.println("The selected aticipaiton is primitive");
				intendedInteraction = selectedAnticipation.getExperience().getIntendedInteraction();
			}else {
				//there is something in the anticipaitons
				Anticipation optimalAnticipation = selectedAnticipation.getAnticipationList().get(0);
				System.out.println(optimalAnticipation.getExperience().getIntendedInteraction());
				if(optimalAnticipation.isInteractionPrimitive()) {//anticipationList����ѡ�е�Ϊsingle interaction������
					System.out.println("The anticipation interaction is Primitive");
					intendedInteraction = addOrGetPrimitiveInteraction(optimalAnticipation.getExperience(),optimalAnticipation.getInteractionResult(),0);//�õ�ԭʼ��primitive postInteraction
				}else {//anticipationList ѡ�е�Ϊ multiple interaction�ĵ�����
					System.out.println("The anticipation interaction is composite and its weight is:"+optimalAnticipation.getCompositeWeight());
					if(optimalAnticipation.getCompositeWeight()>threshold && optimalAnticipation.getProclivity()>0) {
						System.out.println("The anticipation interaction's weight plus than threshold and proclivity is positive");
						intendedInteraction = optimalAnticipation.getExperience().getIntendedInteraction();
					}else if(optimalAnticipation.getCompositeWeight()<=threshold && optimalAnticipation.getProclivity()>0){
						System.out.println("The anticipation interaction's weight less or equal than threshold and proclivity is positive");
						intendedInteraction = optimalAnticipation.getExperience().getIntendedInteraction().getPreInteraction();
					}else {
						System.out.println("The anticipation interaction's weight less than threshold or proclivity is negative");
						intendedInteraction = firstInteraction(optimalAnticipation.getExperience().getIntendedInteraction());
					}
				}
			}//not primitive interaction
			//�õ���intendedInteraction,enact֮��õ�enactedInteractionCurrent
			
			
			System.out.println();
			System.out.println("intendedInteraction is: "+intendedInteraction);
			enactedInteractionCurrent = enact(intendedInteraction);
			System.out.println("enactedInteraction  is: "+enactedInteractionCurrent);
			
			
			
			int enactedValence = enactedInteractionCurrent.getValence();
			totalValence=totalValence+enactedValence;
			learnCompositeInteraction(enactedInteractionCurrent);
			this.setEnactedInteraction(enactedInteractionCurrent);
			
			System.out.println();
			System.out.println("bumpTimes is: "+bumpTimes);
			System.out.println("enactedValence is: "+enactedValence);
			System.out.println("totalValence is: "+totalValence);
			
			//��ʾ����
			System.out.println();
			int width = env.length;//rows
			int height = env[0].length;//columes
			System.out.println("the size of env is:"+height+" "+width);
			for(int k=0;k<width;k++) {
				for(int z=0;z<height;z++) {
					if(env[k][z] == 1)System.out.print("#");
					else if(k==randomPosition.getPointX() && z==randomPosition.getPointY()) {
						switch(direction) {
						case "up":System.out.print("^");break;
						case "down":System.out.print("v");break;
						case "left":System.out.print("<");break;
						case "right":System.out.print(">");break;
						}
					}else {
						System.out.print("-");
					}
				}
				System.out.println();
			}
			System.out.println();
			
			String recordString = "loopNum:"+loopNum+","+"bumpTimes:"+bumpTimes+","+"totalValence:"+totalValence+"\r\n";
			out.write(recordString); // \r\n��Ϊ����
			out.flush(); // �ѻ���������ѹ���ļ�
			
			loopNum++;
		}//loopNum < loopLimit
		out.close();
	}
	
	public void learnCompositeInteraction(Interaction enacted) {
		// TODO Auto-generated method stub
		System.out.println("learnCompositeInteraction()");
		Interaction previousInteraction = this.getEnactedInteraction(); 
		Interaction lastInteraction = enacted;
		Interaction previousSuperInteraction = this.getSuperInteraction();
		Interaction lastSuperInteraction = null;
		
		if (previousInteraction != null) {
			lastSuperInteraction = addOrGetAndReinforceCompositeInteraction(previousInteraction, lastInteraction);
		}
		if (previousSuperInteraction != null){
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction.getPreInteraction(), lastSuperInteraction);
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction, lastInteraction);
        }
        this.setSuperInteraction(lastSuperInteraction);
	}
	
	public Interaction addOrGetAndReinforceCompositeInteraction(Interaction previousInteraction,Interaction lastInteraction) {
		// TODO Auto-generated method stub
		String label = "<" + previousInteraction.getLabel() + lastInteraction.getLabel() + ">";
		Interaction interaction = INTERACTIONS.get(label);
		if (interaction == null){
			//isNovel = true;
			interaction = addOrGetInteraction(label);
			interaction.setPreInteraction(previousInteraction);
			interaction.setPostInteraction(lastInteraction);
			interaction.setValence(previousInteraction.getValence() + lastInteraction.getValence());
			
			addOrGetAbstractExperiment(interaction);
			interaction.incrementWeight();
			int preLevel = previousInteraction.getLevel();
			int posLevel = lastInteraction.getLevel();
			if(preLevel >= posLevel) {
				interaction.setLevel(preLevel+1);
			}else {
				interaction.setLevel(posLevel+1);
			}
			System.out.println("LEARN:" + interaction.toString()+" level is:"+interaction.getLevel());
        }else {
        	interaction.incrementWeight();
			System.out.println("REINFORCE: " + interaction.toString());
		}
		return interaction;
	}
	
	public Experience addOrGetAbstractExperiment(Interaction interaction) {
		// TODO Auto-generated method stub
		String label = interaction.getLabel().replace('e', 'E').replace('>', '|');
        if (!EXPERIENCES.containsKey(label)){
        	Experience abstractExperience =  new Experience(label,6);
        	abstractExperience.setIntendedInteraction(interaction);
			interaction.setExperience(abstractExperience);
            EXPERIENCES.put(label, abstractExperience);
        }
        return EXPERIENCES.get(label);
	}
	
	public Interaction addOrGetInteraction(String label) {
		// TODO Auto-generated method stub
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, createInteraction(label));			
		return INTERACTIONS.get(label);
	}
	
	public Interaction firstInteraction(Interaction inter) {
		Interaction first = inter;
		while(!first.isPrimitive()) {
			first = first.getPreInteraction();
		}
		return first;
	}
	
	public Interaction enact(Interaction intended) {
		if(intended.isPrimitive()) {
			int actionType = intended.getExperience().getAction();
			int interactResult = enactAction(actionType,randomPosition);
			return addOrGetPrimitiveInteraction(intended.getExperience(),interactResult,0);
		}else {
			Interaction enactedPreInteraction = enact(intended.getPreInteraction());
			if(!enactedPreInteraction.equals(intended.getPreInteraction())) {
				return enactedPreInteraction; 
			}else {
				Interaction enactedPostInteraction = enact(intended.getPostInteraction());
				return addOrGetAndReinforceCompositeInteraction(enactedPreInteraction,enactedPostInteraction);
			}
		}
	}
	
	public int enactAction(int actionType,Position randomPosition) {
		//boolean isBump = false;
		int interactResult = 0; 
		int poX = randomPosition.getPointX();
		int poY = randomPosition.getPointY();
		switch(actionType) {
		case 0://move forward
			switch(direction) {
			case "up":
				if(env[poX-1][poY] == 0) {
					interactResult = 1;
					randomPosition.setPointX(poX-1);
				}else {
					bumpTimes+=1;
				}break;
			case "right":
				if(env[poX][poY+1] == 0) {
					interactResult = 1;
					randomPosition.setPointY(poY+1);
				}else {
					bumpTimes+=1;
				}break;
			case "down":
				if(env[poX+1][poY] == 0) {
					interactResult = 1;
					randomPosition.setPointX(poX+1);
				}else {
					bumpTimes+=1;
				}break;
			case "left":
				if(env[poX][poY-1] == 0) {
					interactResult = 1;
					randomPosition.setPointY(poY-1);
				}else {
					bumpTimes+=1;
				}break;
			}break;
		case 1://turn left
			switch(direction){
			case "up":
				direction = "left";
				break;
			case "right":
				direction = "up";
				break;
			case "down":
				direction = "right";
				break;
			case "left":
				direction = "down";
				break;
			}
			interactResult = 1;
			break;
		case 2://turn right
			switch(direction){
			case "up":
				direction = "right";
				break;
			case "right":
				direction = "down";
				break;
			case "down":
				direction = "left";
				break;
			case "left":
				direction = "up";
				break;
			}
			interactResult = 1;
			break;
		case 3://feel front
			switch(direction) {
			case "up":
				if(env[poX-1][poY] == 0) {
					interactResult = 1;
				}break;
			case "right":
				if(env[poX][poY+1] == 0) {
					interactResult = 1;
				}break;
			case "down":
				if(env[poX+1][poY] == 0) {
					interactResult = 1;
				}break;
			case "left":
				if(env[poX][poY-1] == 0) {
					interactResult = 1;
				}break;
			}
			break;
		case 4://feel left
			switch(direction) {
			case "up":
				if(env[poX][poY-1] == 0) {
					interactResult = 1;
				}break;
			case "right":
				if(env[poX-1][poY] == 0) {
					interactResult = 1;
				}break;
			case "down":
				if(env[poX][poY+1] == 0) {
					interactResult = 1;
				}break;
			case "left":
				if(env[poX+1][poY] == 0) {
					interactResult = 1;
				}break;
			}
			break;
		case 5://feel right
			switch(direction) {
			case "up":
				if(env[poX][poY+1] == 0) {
					interactResult = 1;
				}break;
			case "right":
				if(env[poX+1][poY] == 0) {
					interactResult = 1;
				}break;
			case "down":
				if(env[poX][poY-1] == 0) {
					interactResult = 1;
				}break;
			case "left":
				if(env[poX-1][poY] == 0) {
					interactResult = 1;
				}break;
			}
			break;
		}
		return interactResult;
	}
	
	public Anticipation selectInteraction(List<Anticipation> anticipations) {
		//�����defaultAnticipation��������Ե�proclivity��������
		Collections.sort(anticipations);
		for(Anticipation anti:anticipations) {
			//ÿ��defaultAnticipation���е�anticipationListҲ����һ������
			if(!anti.isPrimitive())Collections.sort(anti.getAnticipationList());
		}
		Anticipation selectedAnticipation = (Anticipation)anticipations.get(0);
		//������selectedAnticipationyou���������һ����ԭʼ��default anticipation����Ϊԭʼ�Ķ���
		//��һ���Ǽ����Ķ��У�����Ҳ�����������һ����enacted primitive interaction��һ����enact multiInteraction�� 
		if(!selectedAnticipation.isPrimitive()) {
			System.out.println("****************************************");
			System.out.println("the proposed anticipation's anticipationList has something");
		}
		return selectedAnticipation;
		//return getIntendedInteraction(selectedAnticipation.getInteraction());
	}
	
	public List<Anticipation> anticipate(){
		//List<Anticipation> anticipations = getDefaultAnticipations(); //׼��Ĭ�ϵ�anticipation ���ƵĶ�����
		
		List<Anticipation> defaultAnticipations = getDefaultAnticipations(); //Ĭ�ϵ�׼���飬������
		List<Interaction> activatedInteractions =  getActivatedInteractions();// ������е� activated composite interaction with <pre,post>
		List<Anticipation> anticipationTemporary = new ArrayList<Anticipation>();//������ʱ���� ��default anticipation�����post interaction
		
		//��һ��������Ҫ���ڵ�һ�ν�����������ڶ������ں����������activatedInteraction�����
		if(this.getEnactedInteraction() != null && activatedInteractions.size()>0) {
			//����activatedInteraction.size>0,ֻ���ڻ�ü����������£����µĴ�����̲��ܽ���
			//������ʼ��anticipation list��׼������Ӧ��proclivity�Ĵ����͸���
			for(Interaction activatedInteraction : activatedInteractions) {
				int proclivitytem = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence();
				//System.out.println("proclivitytem is: "+proclivitytem);
				
				//���ɳ����� anticipation
				Anticipation anticipationForAction = new Anticipation(activatedInteraction.getPostInteraction().getExperience(),proclivitytem);
				
				//�����ɵ�anticipation������Ҫ��¼��ԭ�е� activated composite interaction
				//������һ�����⣬����ں���ϲ��Ĺ��̵��У���ͬ����anticipation������activated composite interaction��ͬ ������������ж��
				anticipationForAction.setActivatedInteraction(activatedInteraction);
				
				
				//����activatedInteraction���е�postInteraction��primitive Interaction�����,��Ҫ����result�ļ�¼���Ա�����enact enacted primitive interaction���п����һ�֮ǰ�Ľ����
				if(activatedInteraction.getPostInteraction().isPrimitive()) {
					anticipationForAction.setInteractionPrimitive(true);//���anticipation���е�interaction��һ��������interaction���������
					anticipationForAction.setInteractionResult(activatedInteraction.getPostInteraction().getResult());
				}
				anticipationForAction.setCompositeWeight(activatedInteraction.getWeight());
				
				
				//���ݼ����activated interaction��post interaction�γɲ�ͬ��anticipations,�����䱣����anticipationTemporary����
				int index = anticipationTemporary.indexOf(anticipationForAction);
				if (index < 0) {//�����ɵ� anticipation
					if(anticipationForAction.isInteractionPrimitive()) {
						//System.out.println("created new temporary anticipation: "
						//		+ ""+addOrGetPrimitiveInteraction(anticipationForAction.getExperience(),anticipationForAction.getInteractionResult(),0));
					}else {
						//System.out.println("created new temporary anticipation: "+anticipationForAction.getExperience().getIntendedInteraction());
					}
					
					//�ڵ�һ�η���anticipation��ʱ������activated interaction���м���activated interaction
					anticipationForAction.getActivatedInteractionList().add(activatedInteraction);
					
					anticipationTemporary.add(anticipationForAction);
				}else {//֮ǰ��ͬ����anticipation
					if(anticipationForAction.isInteractionPrimitive()) {
						//System.out.println("adding  the temporary proclivitytem: "
						//		+ ""+addOrGetPrimitiveInteraction(anticipationForAction.getExperience(),anticipationForAction.getInteractionResult(),0));
					}else {
						//System.out.println("adding  the temporary proclivitytem: "+anticipationForAction.getExperience().getIntendedInteraction());
					}
					//System.out.println("adding  the temporary proclivitytem: "+anticipationForAction.getExperience().getIntendedInteraction());
					
					//����֮ǰ�е�anticipation��activated interaction list�������activated interaction
					anticipationTemporary.get(index).getActivatedInteractionList().add(activatedInteraction);
					
					
					//������ƺ�ʵ��ϸ����Ҫ�ú�����
					//��ʵ activated composite interaction��post interaction��valence һֱ�ǲ������仯�ġ�
					//�ȸ������е�anticipation�����г��ֿ��Լ���������ͬ�������
					//������ͬ��post interaction���������Ӧ��Ȩ��Ҳ�������ӣ������ں���ļ��㵱�У�
					//���ڳ��ֲ�ͬ��������������໥���������
					anticipationTemporary.get(index).addProclivity(proclivitytem);//ֱ�Ӽ��ϸ�proclivityֵ����
					anticipationTemporary.get(index).addCompositeWeight(anticipationForAction.getCompositeWeight());
					
					//����֮ǰ�������anticipation����ô�鿴�Ƿ���Ҫ�������anticipation��weight
					/*
					if(anticipationTemporary.get(index).getCompositeWeight()<anticipationForAction.getCompositeWeight()) {
						anticipationTemporary.get(index).setCompositeWeight(anticipationForAction.getCompositeWeight());
					}*/
				}
			}
			
			//��ɶ�����һ�׶ε�anticipation���ж������еĹ���
			for(Anticipation antiForActions : anticipationTemporary) {
				List<Integer> anticipationActionList = getAnticipationActionList(antiForActions);
				antiForActions.setActionList(anticipationActionList);
			}
			
			//����anticipation��action List���е�һ����������ʼ��activated interaction���з���
			//������֮ǰ�Ľ���������action selection���е����ã���Ҫ����һ�鵱������
			for(Anticipation defaultAnticipation:defaultAnticipations) {
				//��Ҫ��������defaultAnticipation������������ԣ�һ���ǹ����ö���������anticipationList��ͬʱ������Ӧ��proclivity
				List<Anticipation> defaultAnti = new ArrayList<Anticipation>();
				int defaultProclivity = 0;
				//���δ�anticipationTemporary������������ൽָ����defaultAnticipation����
				for(Anticipation antiForCategorizing:anticipationTemporary) {
					if(!antiForCategorizing.isCategorized()) {//Ϊ�˼���ÿ�α����Ĵ�����רע��û�з����detail anticipation
						if(defaultAnticipation.getExperience().getAction() == antiForCategorizing.getActionList().get(0)) {
							defaultAnti.add(antiForCategorizing);
							defaultProclivity = defaultProclivity+antiForCategorizing.getProclivity();
							antiForCategorizing.setCategorized(true);//��anticipation�Ѿ������ʹ��ˣ��������´η���ʱ��ֱ��������
						}
					}//!isCategorized
				}
				
				//ִ��һ�α���anticipationTemporary֮�󣬸�����Ӧ��anticipationList��proclivity
				defaultAnticipation.setAnticipationList(defaultAnti);
				defaultAnticipation.setProclivity(defaultProclivity);
				if(!defaultAnti.isEmpty())defaultAnticipation.setPrimitive(false);//�����anticipation������anticipationList����ô��Ҫenact composite interaction
			}//dfaultAnticipation
		}
		return defaultAnticipations;
	}
	
	public List<Integer> getAnticipationActionList(Anticipation antiForActions){
		Interaction postInteraction = null;
		if(antiForActions.isInteractionPrimitive()) {
			postInteraction = addOrGetPrimitiveInteraction(antiForActions.getExperience(),
					antiForActions.getInteractionResult(),0);
		}else {
			postInteraction = antiForActions.getExperience().getIntendedInteraction();
		}
		//����ֻ��������ȡÿ��anticipation���е�anticipationList���е�actionList,������һ������ʡ�ԣ����Ǽ���Ҳû��ʲôӰ��
		
		//System.out.println("postInteraction is: "+postInteraction);
		
		List<Integer> anticipationActionList = new ArrayList<Integer>();//���ڱ��涯�����е�list
		
		//������������ķ�ʽ������compositeInteraction���е�interaction��˳���ȡ��������
		Stack<Node> actionNodeStack = new Stack<Node>();
		Node actionMainNode = new Node(postInteraction);
		actionNodeStack.push(actionMainNode);
		while(!actionNodeStack.isEmpty()) {
			Node actionNodeUpper = (Node)actionNodeStack.pop();
			if(!actionNodeUpper.getInteraction().isPrimitive()) {
				Node rightActionNode = new Node(actionNodeUpper.getInteraction().getPostInteraction());
				rightActionNode.setUpperNode(actionNodeUpper);
				rightActionNode.setUpperNodeDirection(2);
				actionNodeStack.push(rightActionNode);
				actionNodeUpper.setRightNode(rightActionNode);
				
				Node leftActionNode = new Node(actionNodeUpper.getInteraction().getPreInteraction());
				leftActionNode.setUpperNode(actionNodeUpper);
				leftActionNode.setUpperNodeDirection(1);
				actionNodeStack.push(leftActionNode);
				actionNodeUpper.setLeftNode(leftActionNode);
			}else {
				actionNodeUpper.setLeftNode(null);
				actionNodeUpper.setRightNode(null);
				anticipationActionList.add(actionNodeUpper.getInteraction().getExperience().getAction());
			}
		}
		//System.out.println(actionNodeList.size());
		//System.out.println("anticipationActionList is:"+anticipationActionList);
		return anticipationActionList;
	}
	
	public List<Anticipation> getDefaultAnticipations(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Experience experience : this.EXPERIENCES.values()){//6 experiences
			if(!experience.isAbstract()) {
				Anticipation anticipation = new Anticipation(experience, 0);
				anticipations.add(anticipation);
			}
		}
		return anticipations;
	}
	
	public List<Interaction> getActivatedInteractions() {
		List<Interaction> contextInteractions = new ArrayList<Interaction>();
		if (this.getEnactedInteraction()!=null) {
			contextInteractions.add(this.getEnactedInteraction());
			
			if (!this.getEnactedInteraction().isPrimitive())
				contextInteractions.add(this.getEnactedInteraction().getPostInteraction());
			if (this.getSuperInteraction() != null)
				contextInteractions.add(this.getSuperInteraction());
		}
		
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : this.INTERACTIONS.values()){
			if (!activatedInteraction.isPrimitive()) {
				if (contextInteractions.contains(activatedInteraction.getPreInteraction())) {
					activatedInteractions.add(activatedInteraction);
					activatedInteraction.incrementActivatedNum();
				}
			}
		}
		
		//show ��ʾ���м����composite interaction
		String activatedString = "\n";
		for (Interaction activatedInteraction : activatedInteractions) {
			activatedString=activatedString+activatedInteraction+"\n";
		}
		//System.out.println("all activated interactions are : "+activatedString);
		return activatedInteractions;
	}
	
	public Position getInitialPosition(){
		List<Position> availablePositionList = new ArrayList<Position>();
		int width = env.length;
		int length = env[0].length;
		for(int i=0;i<width;i++){
			for(int j=0;j<length;j++) {
				if(env[i][j] == 0) {
					Position temp = new Position();
					temp.setPointX(i);
					temp.setPointY(j);
					availablePositionList.add(temp);
				}
			}
		}
		//int randomIndex = random.nextInt(availablePositionList.size());
		Position initialPosition = (Position)availablePositionList.get(0);
		//String tempDirection = directions[random.nextInt(4)];
		initialPosition.setDirection("left");
		return initialPosition;
	}
	
	public void initial(int v_moveSucess,int v_moveFailture,int v_turn,int v_feelEmpty,int v_feelWall) {
		//System.out.println("parameters are{v_moveSucess:"+v_moveSucess+" v_moveFailture:"
		//		+ ""+v_moveFailture+" v_turn:"+v_turn+" v_feelEmpty:"+v_feelEmpty+" v_feelWall:"+v_feelWall);
		Experience e0 = addOrGetExperience("e0",0);e0.resetAbstract();//move forward  1:sucess,0:fail
		Experience e1 = addOrGetExperience("e1",1);e1.resetAbstract();//turn left
		Experience e2 = addOrGetExperience("e2",2);e2.resetAbstract();//turn right
		Experience e3 = addOrGetExperience("e3",3);e3.resetAbstract();//feel front    0:feel empy,1:feel wall
		Experience e4 = addOrGetExperience("e4",4);e4.resetAbstract();//feel left     0:feel empy,1:feel wall
		Experience e5 = addOrGetExperience("e5",5);e5.resetAbstract();//feel right    0:feel empy,1:feel wall
		
		Interaction i01 = addOrGetPrimitiveInteraction(e0, 1, v_moveSucess);
		Interaction i00 = addOrGetPrimitiveInteraction(e0, 0, v_moveFailture);
		Interaction i11 = addOrGetPrimitiveInteraction(e1, 1, v_turn);
		Interaction i21 = addOrGetPrimitiveInteraction(e2, 1, v_turn);
		Interaction i31 = addOrGetPrimitiveInteraction(e3, 1, v_feelEmpty);
		Interaction i30 = addOrGetPrimitiveInteraction(e3, 0, v_feelWall);
		Interaction i41 = addOrGetPrimitiveInteraction(e4, 1, v_feelEmpty);
		Interaction i40 = addOrGetPrimitiveInteraction(e4, 0, v_feelWall);
		Interaction i51 = addOrGetPrimitiveInteraction(e5, 1, v_feelEmpty);
		Interaction i50 = addOrGetPrimitiveInteraction(e5, 0, v_feelWall);
		//Ĭ�ϵĶ����飬����������intendedInteraction��enactedInteraction�ıȽϵ���֪��������interaction������
		//Interaction i0a = addOrGetPrimitiveInteraction(e0, result, valence);
		//��ʼ��ÿ��experience��intended interaction���������ٶ���INTERACTIONS�ı���, �����������
		
		e0.setIntendedInteraction(i01);
		e1.setIntendedInteraction(i11);
		e2.setIntendedInteraction(i21);
		e3.setIntendedInteraction(i30);
		e4.setIntendedInteraction(i40);
		e5.setIntendedInteraction(i50);
	}
	
	public Experience addOrGetExperience(String label,int actionType) {
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, createExperience(label,actionType));
		return EXPERIENCES.get(label);
	}
	
	public Experience createExperience(String label,int actionType){
		return new Experience(label,actionType);
	}
	
	public Interaction addOrGetPrimitiveInteraction(Experience experience, int result, int valence) {
		String label = experience.getLabel()+result;
		if (!INTERACTIONS.containsKey(label)){
			Interaction interaction = createInteraction(label);
			interaction.setExperience(experience);
			interaction.setResult(result);
			interaction.setValence(valence);
			INTERACTIONS.put(label, interaction);			
		}
		Interaction interaction = INTERACTIONS.get(label);
		return interaction;
	}
	
	public Interaction createInteraction(String label) {
		return new Interaction(label);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		EMDP va = new EMDP();
		

	}

}
