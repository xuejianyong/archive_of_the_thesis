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
		
		
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("ddMMyyyy_HHmmss");
        Date date = new Date();
        //sdf.format(date)
		String filename = "D:\\eclipse\\workspace\\maze_optimize_svg-2_abstract_multi_th1\\src\\dl\\results\\result_"+threshold+"_"+loopLimit+".txt";
		System.out.println(filename);
		File writename = new File(filename); // 相对路径，如果没有则要建立一个新的output。txt文件
		writename.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		
		
		
		while(loopNum < loopLimit) {
			//interaction starts here
			System.out.println("------------------------------------------------------------------------");
			System.out.println("loopNum: "+loopNum);
			if(loopNum == 1) {
				initial(5,-10,-3,-1,-2);
				randomPosition = getInitialPosition();//这里需要好好想想
				direction = randomPosition.getDirection();//这里需要好好想想
			}
			
			List<Anticipation> anticipations = anticipate();
			Anticipation selectedAnticipation = selectInteraction(anticipations);
			
			if(selectedAnticipation.isPrimitive()) {//得到的default anticipation当中anticipationList为空，直接执行动作即可
				System.out.println("The selected aticipaiton is primitive");
				intendedInteraction = selectedAnticipation.getExperience().getIntendedInteraction();
			}else {
				//there is something in the anticipaitons
				Anticipation optimalAnticipation = selectedAnticipation.getAnticipationList().get(0);
				System.out.println(optimalAnticipation.getExperience().getIntendedInteraction());
				if(optimalAnticipation.isInteractionPrimitive()) {//anticipationList当中选中的为single interaction的情形
					System.out.println("The anticipation interaction is Primitive");
					intendedInteraction = addOrGetPrimitiveInteraction(optimalAnticipation.getExperience(),optimalAnticipation.getInteractionResult(),0);//得到原始的primitive postInteraction
				}else {//anticipationList 选中的为 multiple interaction的的情形
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
			//得到了intendedInteraction,enact之后得到enactedInteractionCurrent
			
			
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
			
			//显示环境
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
			out.write(recordString); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			
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
		//整体的defaultAnticipation按照其各自的proclivity进行排序
		Collections.sort(anticipations);
		for(Anticipation anti:anticipations) {
			//每个defaultAnticipation当中的anticipationList也进行一次排序
			if(!anti.isPrimitive())Collections.sort(anti.getAnticipationList());
		}
		Anticipation selectedAnticipation = (Anticipation)anticipations.get(0);
		//给出的selectedAnticipationyou两种情况，一种是原始的default anticipation，即为原始的动作
		//另一种是激活后的队列，这里也有两种情况，一种是enacted primitive interaction，一种是enact multiInteraction。 
		if(!selectedAnticipation.isPrimitive()) {
			System.out.println("****************************************");
			System.out.println("the proposed anticipation's anticipationList has something");
		}
		return selectedAnticipation;
		//return getIntendedInteraction(selectedAnticipation.getInteraction());
	}
	
	public List<Anticipation> anticipate(){
		//List<Anticipation> anticipations = getDefaultAnticipations(); //准备默认的anticipation 类似的动作组
		
		List<Anticipation> defaultAnticipations = getDefaultAnticipations(); //默认的准备组，动作组
		List<Interaction> activatedInteractions =  getActivatedInteractions();// 获得所有的 activated composite interaction with <pre,post>
		List<Anticipation> anticipationTemporary = new ArrayList<Anticipation>();//用来临时保存 除default anticipation以外的post interaction
		
		//第一个条件主要用于第一次交互的情况，第二个用于后续激活出现activatedInteraction的情况
		if(this.getEnactedInteraction() != null && activatedInteractions.size()>0) {
			//加上activatedInteraction.size>0,只有在获得激活交互的情况下，以下的处理过程才能进行
			//创建初始的anticipation list，准备好相应的proclivity的创建和更新
			for(Interaction activatedInteraction : activatedInteractions) {
				int proclivitytem = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence();
				//System.out.println("proclivitytem is: "+proclivitytem);
				
				//生成初步的 anticipation
				Anticipation anticipationForAction = new Anticipation(activatedInteraction.getPostInteraction().getExperience(),proclivitytem);
				
				//在生成的anticipation当中需要记录其原有的 activated composite interaction
				//这里有一个问题，如果在后面合并的过程当中，有同样的anticipation，但是activated composite interaction不同 的情况，可能有多个
				anticipationForAction.setActivatedInteraction(activatedInteraction);
				
				
				//对于activatedInteraction当中的postInteraction是primitive Interaction的情况,需要对其result的记录，以便于在enact enacted primitive interaction当中可以找回之前的结果。
				if(activatedInteraction.getPostInteraction().isPrimitive()) {
					anticipationForAction.setInteractionPrimitive(true);//这个anticipation当中的interaction是一个单独的interaction，给出标记
					anticipationForAction.setInteractionResult(activatedInteraction.getPostInteraction().getResult());
				}
				anticipationForAction.setCompositeWeight(activatedInteraction.getWeight());
				
				
				//根据激活的activated interaction的post interaction形成不同的anticipations,并将其保存在anticipationTemporary当中
				int index = anticipationTemporary.indexOf(anticipationForAction);
				if (index < 0) {//新生成的 anticipation
					if(anticipationForAction.isInteractionPrimitive()) {
						//System.out.println("created new temporary anticipation: "
						//		+ ""+addOrGetPrimitiveInteraction(anticipationForAction.getExperience(),anticipationForAction.getInteractionResult(),0));
					}else {
						//System.out.println("created new temporary anticipation: "+anticipationForAction.getExperience().getIntendedInteraction());
					}
					
					//在第一次发现anticipation的时候，在其activated interaction当中加上activated interaction
					anticipationForAction.getActivatedInteractionList().add(activatedInteraction);
					
					anticipationTemporary.add(anticipationForAction);
				}else {//之前有同样的anticipation
					if(anticipationForAction.isInteractionPrimitive()) {
						//System.out.println("adding  the temporary proclivitytem: "
						//		+ ""+addOrGetPrimitiveInteraction(anticipationForAction.getExperience(),anticipationForAction.getInteractionResult(),0));
					}else {
						//System.out.println("adding  the temporary proclivitytem: "+anticipationForAction.getExperience().getIntendedInteraction());
					}
					//System.out.println("adding  the temporary proclivitytem: "+anticipationForAction.getExperience().getIntendedInteraction());
					
					//发现之前有的anticipation的activated interaction list当中添加activated interaction
					anticipationTemporary.get(index).getActivatedInteractionList().add(activatedInteraction);
					
					
					//这块的设计和实现细节需要好好想想
					//其实 activated composite interaction的post interaction的valence 一直是不发生变化的。
					//先给出所有的anticipation，其中出现可以检索到的相同的情况，
					//对于相同的post interaction的情况，对应的权重也会进行相加，由于在后面的计算当中，
					//对于出现不同结果的情况会出现相互抵消的情况
					anticipationTemporary.get(index).addProclivity(proclivitytem);//直接加上该proclivity值即可
					anticipationTemporary.get(index).addCompositeWeight(anticipationForAction.getCompositeWeight());
					
					//发现之前存在这个anticipation，那么查看是否需要更新这个anticipation的weight
					/*
					if(anticipationTemporary.get(index).getCompositeWeight()<anticipationForAction.getCompositeWeight()) {
						anticipationTemporary.get(index).setCompositeWeight(anticipationForAction.getCompositeWeight());
					}*/
				}
			}
			
			//完成对于上一阶段的anticipation当中动作序列的构建
			for(Anticipation antiForActions : anticipationTemporary) {
				List<Integer> anticipationActionList = getAnticipationActionList(antiForActions);
				antiForActions.setActionList(anticipationActionList);
			}
			
			//根据anticipation的action List当中第一个动作，开始对activated interaction进行分类
			//来表征之前的交互经验在action selection当中的作用，主要在这一块当中体现
			for(Anticipation defaultAnticipation:defaultAnticipations) {
				//主要用来设置defaultAnticipation的两个相关属性，一个是关联该动作的所有anticipationList，同时更新相应的proclivity
				List<Anticipation> defaultAnti = new ArrayList<Anticipation>();
				int defaultProclivity = 0;
				//依次从anticipationTemporary遍历，将其归类到指定的defaultAnticipation当中
				for(Anticipation antiForCategorizing:anticipationTemporary) {
					if(!antiForCategorizing.isCategorized()) {//为了减少每次遍历的次数，专注于没有分类的detail anticipation
						if(defaultAnticipation.getExperience().getAction() == antiForCategorizing.getActionList().get(0)) {
							defaultAnti.add(antiForCategorizing);
							defaultProclivity = defaultProclivity+antiForCategorizing.getProclivity();
							antiForCategorizing.setCategorized(true);//该anticipation已经被访问过了，所以在下次访问时会直接跳过。
						}
					}//!isCategorized
				}
				
				//执行一次遍历anticipationTemporary之后，更新相应的anticipationList和proclivity
				defaultAnticipation.setAnticipationList(defaultAnti);
				defaultAnticipation.setProclivity(defaultProclivity);
				if(!defaultAnti.isEmpty())defaultAnticipation.setPrimitive(false);//如果该anticipation当中有anticipationList，那么需要enact composite interaction
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
		//由于只是用来获取每个anticipation当中的anticipationList当中的actionList,所以这一步可以省略，但是加上也没有什么影响
		
		//System.out.println("postInteraction is: "+postInteraction);
		
		List<Integer> anticipationActionList = new ArrayList<Integer>();//用于保存动作序列的list
		
		//采用先序遍历的方式，按照compositeInteraction当中的interaction的顺序获取动作序列
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
		
		//show 显示所有激活的composite interaction
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
		//默认的动作组，这样可以在intendedInteraction与enactedInteraction的比较当中知道动作与interaction的区别
		//Interaction i0a = addOrGetPrimitiveInteraction(e0, result, valence);
		//初始化每个experience的intended interaction，这样减少对于INTERACTIONS的遍历, 这里存在问题
		
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
