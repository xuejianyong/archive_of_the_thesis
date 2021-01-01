package level2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class MainFunction2d extends JFrame{
	
	/* array[i][j]  j={actionType,resultNumber,Value,colorIndex} 
	 * 0:feel left
	 * 1:swap left
	 * 2:feel both
	 * 3:feel right
	 * 4:swap right
	 */
	public int width = 200; 
	public int rate = 8;
	public int intervalExt = width/3;
	public int intervalInn = width/5;
	public int intervalUpAndDown = width/4;
	
	public int x_index = 500;
	public int y_index = 500;
	public MainFunction2d() {
		setSize(100,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(Color.white);
	}
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
	
	public void drawFeelLeft(int x,int y,int result,Graphics2D g) {
		int rate = 8;
		int x1 = x;
		int y1 = y;
		int x2 = x+width;
		int y2 = y+width/rate;
		int x3 = x+width;
		int y3 = y+width-width/rate;
		int x4 = x;
		int y4 = y+width;
		int x5 = x;
		int y5 = y;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		g.fillPolygon(x_array, y_array, p);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}

	public void drawFlipleft(int x,int y,int result,Graphics2D g) {
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		int bias = width/2;
		int x_rect = x+bias;
		g.fillArc(x, y, width, width, 90, 180);
		g.fillRect(x_rect, y, width/2, width);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, width, width, 90, 180);
		g.drawLine(x_rect, y, x_rect+bias, y);
		g.drawLine(x_rect+bias, y, x_rect+bias, y+width);
		g.drawLine(x_rect, y+width, x_rect+bias, y+width);
	}
	
	public void drawFeelBoth(int x,int y,int result,Graphics2D g) {
		if(result == 2) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		g.fillRect(x, y, width, width);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, width);
	}

	public void drawFeelright(int x,int y,int result,Graphics2D g) {
		int x1 = x;
		int y1 = y+width/rate;
		int x2 = x+width;
		int y2 = y;
		int x3 = x+width;
		int y3 = y+width;
		int x4 = x;
		int y4 = y+width-width/rate;
		int x5 = x;
		int y5 = y+width/rate;
		int x_array[] = {x1,x2,x3,x4,x5};
		int y_array[] = {y1,y2,y3,y4,y5};
		int p = y_array.length;
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		g.fillPolygon(x_array, y_array, p);
		g.setColor(Color.BLACK);
		g.drawPolygon(x_array, y_array, p);
	}

	public void drawFlipRight(int x,int y,int result,Graphics2D g) {
		if(result == 1) {
			g.setColor(Color.GREEN);
		}else {
			g.setColor(Color.RED);
		}
		int bias = width/2;
		int x_rect = x-bias;
		g.fillRect(x, y, width/2, width);
		g.fillArc(x, y, width, width, -90, 180);
		g.setColor(Color.BLACK);
		g.drawArc(x, y, width, width, -90, 180);
		g.drawLine(x, y, x+bias, y);
		g.drawLine(x, y, x, y+width);
		g.drawLine(x, y+width, x+bias, y+width);
	}
	
	public void drawFunctionInitial(int x,int y,Graphics2D g) {
		System.out.println(x+" "+y+" "+width);
		int x_rect = x-width-width/4;
		int y_rect = y-(width/8)*7;
		g.setColor(Color.WHITE);
		g.fillRect(x_rect, y_rect, (width/4)*3, (width/4)*3);
		g.setFont(new Font("Arial", Font.PLAIN, (width/4)*3));
		int x_font = x_rect+width/8;
		int y_font = y_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("1" ,x_font,y_font);
		g.drawRect(x_rect, y_rect, (width/4)*3, (width/4)*3);
		
		int x2_rect = x_rect-width/4;
		int y2_rect = y_rect+width-width/4+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x2_rect, y2_rect, (width/4)*3, (width/4)*3);
		int x2_font = x2_rect+width/8;
		int y2_font = y2_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("2" ,x2_font,y2_font);
		g.drawRect(x2_rect, y2_rect, (width/4)*3, (width/4)*3);
		
		int x3_rect = x_rect;
		int y3_rect = y_rect+width+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x3_rect, y3_rect, (width/4)*3, (width/4)*3);
		int x3_font = x3_rect+width/8;
		int y3_font = y3_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("3" ,x3_font,y3_font);
		g.drawRect(x3_rect, y3_rect, (width/4)*3, (width/4)*3);
		
		int x4_rect = x3_rect;
		int y4_rect = y3_rect+width+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x4_rect, y4_rect, (width/4)*3, (width/4)*3);
		int x4_font = x4_rect+width/8;
		int y4_font = y4_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("4" ,x4_font,y4_font);
		g.drawRect(x4_rect, y4_rect, (width/4)*3, (width/4)*3);
		
		int x5_rect = x4_rect;
		int y5_rect = y4_rect+width+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x5_rect, y5_rect, (width/4)*3, (width/4)*3);
		int x5_font = x5_rect+width/8;
		int y5_font = y5_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("5" ,x5_font,y5_font);
		g.drawRect(x5_rect, y5_rect, (width/4)*3, (width/4)*3);
		
		int x6_rect = x5_rect;
		int y6_rect = y5_rect+width+intervalUpAndDown;
		g.setColor(Color.WHITE);
		g.fillRect(x6_rect, y6_rect, (width/4)*3, (width/4)*3);
		int x6_font = x6_rect+width/8;
		int y6_font = y6_rect+(width/8)*5;
		g.setColor(Color.BLACK);
		g.drawString("6" ,x6_font,y6_font);
		g.drawRect(x6_rect, y6_rect, (width/4)*3, (width/4)*3);
		
	}
	
	public void drawFunction(int x, int y,Experience exp,Graphics2D g) {
		int actionType = exp.action;
		int result = exp.result;
		switch(actionType) {
		case 0:drawFeelLeft(x,y,result,g);break;
		case 1:drawFlipleft(x,y,result,g);break;
		case 2:drawFeelBoth(x,y,result,g);break;
		case 4:drawFeelright(x,y,result,g);break;
		case 5:drawFlipRight(x,y,result,g);break;
		}
	}
	
	public void drawFunctionFont(int x, int y,Integer countNum,Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, (width/4)*3));
		int widthPont=0;
		int heightPont=y-width;
		if(countNum<10) {
			widthPont = x+width/3;
		}else widthPont = x+width/5-15;
		g.drawString(countNum.toString() ,widthPont,heightPont);
	}
	
	public void drawFunctionLine(int x,int y,Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawLine(x ,y-width/2,x+width+intervalExt,y-width/2);
	}
	
	public void drawFunctionValence(int x,int y,Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x_index+intervalInn, y-(width/4)*3, width, width/4);
	}
	
	public void drawFunctionPointRect(int x,int y,Graphics2D g) {
		int x1 = x;
		int y1 = y+width/2;
		int x2 = x+width/2;
		int y2 = y+width/4;
		int x3 = x+width;
		int y3 = y+width/2;
		int x_array[] = {x1,x2,x3,x1};
		int y_array[] = {y1,y2,y3,y1};
		int p = y_array.length;
		g.setColor(Color.GRAY);
		g.fillPolygon(x_array, y_array, p);
		g.fillRect(x1, y1, width, width/4);
		g.setColor(Color.BLACK);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x3, y3);
		g.drawLine(x1, y1, x1, y1+width/4);
		g.drawLine(x1, y+width*3/4, x+width, y+width*3/4);
		g.drawLine(x+width, y+width/2, x+width, y+width*3/4);
	}
	
	public void drawFunctionExcited(int x,int y,int excitementCount,Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, (width/6)*excitementCount);
	}
	
	public void drawFunctionCurious(int x,int y,Graphics2D g) {
		int x_font = x+width/4;
		int y_font = y+width- width/4;
		g.setColor(Color.BLACK);
		g.drawString("?" ,x_font,y_font);
		
		
		
	}
	
	public void paint(Graphics graphic) {
		Graphics2D g2d = (Graphics2D)graphic;
		BufferedImage targetImg = new BufferedImage(28000, 1500, BufferedImage.TYPE_INT_ARGB);
		//graphic = targetImg.createGraphics();
		g2d = targetImg.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(7,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND));
		drawFunctionInitial(x_index,y_index,g2d);
		
		
		/*
		int min = 0;
		int max = 4;
		int historicalDepth = 10; // I have no idea about this variate
		*/
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HHmmss");
		String fileName = "ExperimentResult"+df.format(new Date()).toString();
		try {
			System.setOut(new PrintStream(new FileOutputStream("./Result/"+fileName+".txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		Integer countNum = 0;
		int result = 0;
		int mark;
		int recentCount = 20;
		List<List<Integer>> numScoreList = new ArrayList<List<Integer>>();// to show the countNum and Score
		//********************************************** start the algorithm *****************************************
		while(countNum++ < 100) {
			//the function of this switch is to chose the next experience
			System.out.print(countNum);
			drawFunctionFont(x_index,y_index,countNum,g2d);
			drawFunctionLine(x_index,y_index,g2d);
			//System.out.println("the BeliefState is:"+currentBeliefState.toString());
			//System.out.println("the Mood  State is:"+mood);
			switch(mood){
			case 0://curious
				if(isInitial){
					intendedExperience = exp00;
					isInitial = false;
				}else{
					intendedExperience = currentBeliefState.leastTriedExperience();
				}break;
			case 1://hedonist
				//System.out.println("=====================================================");
				//parameters(perPerSpoMap)try to get the max valence with the map of (persistent,persistent Sporadic action)
				intendedExperience = currentBeliefState.intentionWithMaxExpectedOutcomeValence(perSpoPerList);
				//intendedExperience = beliefState.intentionWithMaxExpectedOutcomeValence(currentBeliefState,
				//unknownBeliefList,sporaticBeliefList,persistentBeliefList);
				break;
			case 2://excited------------------------------------------------------------
				intendedExperience = enactedExperience;
				break;
			}//end switch
			System.out.print(" "+intendedExperience.toString());
			drawFunction(x_index,y_index,intendedExperience,g2d);
			result = env.getResult(intendedExperience.action);
			enactedExperience = Experiences[intendedExperience.action][result];
			System.out.print(" "+enactedExperience.toString());
			drawFunction(x_index+intervalInn,y_index+intervalInn,intendedExperience,g2d);
			System.out.println("the x_index value is"+x_index+" and the y_value is"+y_index);
			score = score + enactedExperience.valence;
			if(enactedExperience.valence>0) {
				drawFunctionValence(x_index,y_index,g2d);
			}
			System.out.print(" "+score);
			experienceList.add(intendedExperience);
			currentBeliefState.updateTriedNumberofExperience(intendedExperience);
			if(mood == 2) {
				if(!(enactedExperience.action == intendedExperience.action && 
						enactedExperience.result == intendedExperience.result)) {
					for(Experience key : experienceMarkedMap.keySet()) {
						if(key.action == intendedExperience.action  && key.result == intendedExperience.result) {
							experienceMarkedMap.put(intendedExperience,1);
							//System.out.println("update the experienceMarkedMap 1 and enter the marked function");
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
							//System.out.println("update the experienceMarkedMap 2 and enter the marked function");
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
			BeliefState nextBeliefState = currentBeliefState.updateAndGetBelief(enactedExperience);//该函数的作用使用来构建各个节点之间的相互关系的。
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
			//the dual analysis(这里双重分析) consider the current situation of the environment(state of phenomenonLeft and phenomenonRight)
			boolean isChanged = false;
			if(isMarked) {
				//System.out.println();
				//System.out.println("Enter the connected procedure process");
				if(sporadicActionList.size()>0 && newCreatedBeliefList.size()>0) {
					loop:for(int sporadicIndex=0;sporadicIndex<sporadicActionList.size();sporadicIndex++) {
						int sporadicAction = sporadicActionList.get(sporadicIndex);
						//System.out.println("sporadicAction is:"+sporadicAction);
						for(int persistentIndex=0;persistentIndex<newCreatedBeliefList.size();persistentIndex++) {
							Experience observer = newCreatedBeliefList.get(persistentIndex).persistentExperience;
							//System.out.println("observer is:"+observer.toString());
							//System.out.println("The state of the environment is:  left:"+env.phenomenonLeft+"  right:"+env.phenomenonRight);
							int observerResultPre = env.getResult(observer.action);
							Experience enactedExperiencePre = Experiences[observer.action][observerResultPre];
							//System.out.println("enactedExperiencePre is:"+enactedExperiencePre.toString());
							if(observerResultPre != observer.result) {// the persistent experience is not the current persistent experience
								//System.out.println("enter the function if(observerResultPre != observer.result)");
								int persistentMark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperiencePre);
								if(persistentMark == 0) {
									//System.out.println("persistentMark is 0, so the agent need to check the mark of this enactedExperience");
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
									//System.out.println("the enactedExperienceSpo is :"+enactedExperienceSpo.toString());
									int observerResultPos = env.getResult(observer.action);
									Experience enactedExperiencePos = Experiences[observer.action][observerResultPos];
									//System.out.println("the enactedExperiencePos is :"+enactedExperiencePos.toString());
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
							}else {//当前状态
								//System.out.println("enter the same result function");
								int observedResult = env.getResult(sporadicAction);
								Experience enactedExperienceSpo = Experiences[sporadicAction][observedResult];
								//System.out.println("the enactedExperienceSpo is :"+enactedExperienceSpo.toString());
								int observerResultPos = env.getResult(observer.action);
								Experience enactedExperiencePos = Experiences[observer.action][observerResultPos];
								//System.out.println("enactedExperiencePos is:"+enactedExperiencePos.toString());
								if(observerResultPos != observer.result) {// shows the sporadic action works on current persistent experience
									//System.out.println("enter the function if(observerResultPos != observer.result)");
									int persistentMark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperiencePos);
									if(persistentMark == 0) {
										//System.out.println("persistentMark is 0, so the agent need to check the mark of this enactedExperience");
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
			
			
			//System.out.println("isChanged is:"+isChanged);
			if(isChanged)recentExperience.add(1);
			else recentExperience.add(0);
			//System.out.println();
			// get the Marked-State of the EnactedExperience  *should check this state for every EnactedExperience
			mark = currentBeliefState.getMarkFromMap(experienceMarkedMap,enactedExperience);
			if(!isUpdate) {
				if(mark == 0) {
					//System.out.println("This EnactedExperience Marked is: "+mark);
					mood = 2;
					//excitementCount++;
					currentBeliefState = unknownBeliefState;
					
				}else {
					//System.out.println("This EnactedExperience is already Marked ! mark is: "+mark);
				}
			}

			
			// defined the status for agent become hedonist
			isAllTried = currentBeliefState.isAllTried();
			if(isAllTried) {
				//if(isAllTried)mood  = 0;
				//System.out.println("The experiences are AllTried in this belief!");
			}
			isAllMarked = currentBeliefState.isAllMarked(experienceMarkedMap);
			if(isAllMarked) {
				//System.out.println("The experiences are isAllMarked!");
			}
			boolean ischange = currentBeliefState.isRecentExperienceChanged(recentExperience,recentCount);
			//System.out.println("ischange is:"+recentExperience);
			if(isAllTried && isAllMarked && (!ischange)) {
				mood = 1;
			}
			
			
			if(newCreatedBeliefList.size() == 0) {
				//System.out.println("The newCreatedBeliefList is NULL");
			}else {
				//System.out.println("The createNewBeliefStateList is:");
				for(int index = 0;index < newCreatedBeliefList.size();index ++) {
					//System.out.println(newCreatedBeliefList.get(index).persistentExperience.toString());
				}
			}
			//System.out.println();
			//System.out.println("the experience marked map is as follows:");
			for(Experience key : experienceMarkedMap.keySet()) {
				//System.out.println(key.toString()+" Marked:"+experienceMarkedMap.get(key));
			}
			//System.out.println();
			System.out.print(" "+currentBeliefState);
			System.out.print(" "+mood);
			//System.out.println();
			//System.out.println("The currentBeliefState numberOfTriedMap");
			for(Experience key : currentBeliefState.numberOfTriedMap.keySet()) {
				//System.out.println(key.toString()+" number:"+currentBeliefState.numberOfTriedMap.get(key));
			}
			//System.out.println();
			//System.out.println("The sporadicActionList is:");
			for(int sporadicIndex=0;sporadicIndex<sporadicActionList.size();sporadicIndex++) {
				//System.out.println(sporadicActionList.get(sporadicIndex));
			}
			//System.out.println("The persistent persistent sporadic Map is:");
			for(List<Experience> key : perPerSpoMap.keySet()) {
				//System.out.println(key.toString()+" "+perPerSpoMap.get(key));
			}
			//System.out.println("The persistent sporadic persistent list is:");
			for(int showIndex = 0;showIndex<perSpoPerList.size();showIndex++) {
				//System.out.println(perSpoPerList.get(showIndex));
			}
			//System.out.println("The state of the environment is:  left:"+env.phenomenonLeft+"  right:"+env.phenomenonRight);
			List<Integer> numScoreSmall = new ArrayList<Integer>();
			numScoreSmall.add(score);
			numScoreSmall.add(countNum);
			
			numScoreList.add(numScoreSmall);
			//System.out.println();
			//System.out.println("numScoreList is:");
			//System.out.println(numScoreList);
			//System.out.println("--------------------------------------------------");
			System.out.print(" "+newCreatedBeliefList);
			System.out.println();
		
			if(currentBeliefState.equals(unknownBeliefState)) {
				drawFunctionPointRect(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn,g2d);
			}else {
				drawFunction(x_index+intervalInn,y_index+width+intervalUpAndDown+intervalInn,
						currentBeliefState.persistentExperience,g2d);
			}
			
			if(mood == 2 && excitementCount >0){
				drawFunctionExcited(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn,
						excitementCount,g2d);
			}else if(mood == 0)drawFunctionCurious(x_index+intervalInn,y_index+(width+intervalUpAndDown)*2+intervalInn,g2d);
			x_index=x_index+width+intervalExt;
			
			
		}//end while
		graphic.dispose();
		try {
			ImageIO.write(targetImg, "PNG", new File("D:\\\\11.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		new MainFunction2d();
	}//end main

}
