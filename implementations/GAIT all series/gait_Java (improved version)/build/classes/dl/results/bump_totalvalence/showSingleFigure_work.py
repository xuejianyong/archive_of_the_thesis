import matplotlib.pyplot as plt
import os
import numpy as np

loopNums = []
bumpNums = []
valences = []
stepNums = []
stepBumpNum = []

loopNumList = []
totalValenceList = []
averageTotalValenceList = []
bumpNumList = []
surpriseList = []
bigValenceList = []
compositeCountList = []

handles = []
scriptPath = os.getcwd()
datafile = os.path.join(scriptPath,'result_maze_optimize_svg-2_abstract_2020824_1412.txt')
print datafile
print 'start the processing......'
lineIndex = 1
addFlag = False
fp=open(datafile)
lines = fp.readlines()

tempLine = ""
tempLoopNum = 0
for line in lines:
    line=line.strip('\n')
    #print line
    #print lineIndex
    if line != '':
        parameters = line.split(',')
        
        loopNum_parameter = parameters[0]
        bumpNum_parameter = parameters[1]
        step_parameter = parameters[2]
        valence_parameter = parameters[3]
        surprise_parameter = parameters[4]
        bigValence_parameter = parameters[5]
        composite_parameter = parameters[6]
        
        if lineIndex == 1:
            tempLine = line
            tempLoopNum = int(loopNum_parameter.split(':')[1])
        else:
            loopNum = int(loopNum_parameter.split(':')[1])#current loop num
            if loopNum > tempLoopNum:
                tempParameters = tempLine.split(',')
                
                temploopNum_parameter = tempParameters[0]
                tempbumpNum_parameter = tempParameters[1]
                tempstep_parameter = tempParameters[2]
                tempvalence_parameter = tempParameters[3]
                tempsurprise_parameter = tempParameters[4]
                tempbigValence_parameter = tempParameters[5]
                tempcomposite_parameter = tempParameters[6]

                loopNumList.append(int(temploopNum_parameter.split(':')[1]))
                bumpNumList.append(int(tempbumpNum_parameter.split(':')[1]))
                totalValenceList.append(int(tempvalence_parameter.split(':')[1]))
                averageTotalValenceList.append(float(tempvalence_parameter.split(':')[1]) / float(temploopNum_parameter.split(':')[1]))
                surpriseList.append(int(tempsurprise_parameter.split(':')[1]))
                bigValenceList.append(int(tempbigValence_parameter.split(':')[1]))
                compositeCountList.append(int(tempcomposite_parameter.split(':')[1]))

                tempLine = line
                tempLoopNum = int(loopNum_parameter.split(':')[1])
            else:
                tempLine = line
                tempLoopNum = int(loopNum_parameter.split(':')[1])
        lineIndex+=1
        
print list(zip(loopNumList,surpriseList))
#print averageTotalValenceList



plt.figure()
plt.title('Bumping times with decision cycles as the agent interacts with the environment.')
plt.plot(loopNumList,bumpNumList,label='Bumps with decision cycles.')
plt.xlabel('Decision cycles')
plt.ylabel('Bump times')
x_ticks = np.arange(0, 650, 30)
y_ticks = np.arange(0, 40, 5)
plt.xticks(x_ticks)
plt.yticks(y_ticks) 
plt.legend()


plt.figure()
plt.title("Accumulated valence with decision cycles in agent's interactions with the environment.")
plt.plot(loopNumList,totalValenceList,label="Accumulated valence with decision cycles.")
plt.xlabel('Decision cycles')
plt.ylabel('Total valence')
x_ticks = np.arange(0, 650, 30)
plt.xticks(x_ticks)
plt.legend()

plt.figure()
plt.title("Average valence with decision cycles from agent's interactions with the environment.")
plt.plot(loopNumList,averageTotalValenceList,label="Average valence with decision cycles.")
plt.xlabel('Decision cycles')
plt.ylabel('Average valence')
x_ticks = np.arange(0, 650, 30)
plt.xticks(x_ticks)
plt.legend()

plt.figure()
plt.title("The surprises with decision cycles in agent's interactions with the environment.")
plt.plot(loopNumList,surpriseList,label="Surprises with decision cycles.")
plt.xlabel('Decision cycles')
plt.ylabel('Surprises in interactions')
x_ticks = np.arange(0, 650, 30)
plt.xticks(x_ticks)
plt.legend()


plt.figure()
plt.title('The number of composite interactions with decision cycles.')
plt.plot(loopNumList,compositeCountList,label='The number of composite interactions with decision cycles.')
plt.xlabel('Decision cycles')
plt.ylabel('Composite interaction count')
x_ticks = np.arange(0, 650, 30)
plt.xticks(x_ticks)
plt.legend()


plt.legend()
plt.show()