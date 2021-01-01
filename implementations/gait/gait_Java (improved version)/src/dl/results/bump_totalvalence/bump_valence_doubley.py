import matplotlib.pyplot as plt
import os
import numpy as np

loopNums = []
bumpNums = []
valences = []
stepNums = []
stepBumpNum = []

handles = []
scriptPath = os.getcwd()
datafile = os.path.join(scriptPath,'result_maze_optimize_svg-2_abstract_2020427_1315.txt')
print datafile
print 'start the processing......'
lineIndex = 1
addFlag = False
fp=open(datafile)
lines = fp.readlines()

for line in lines:
    line=line.strip('\n')
    #print line
    if line != '':
        parameters = line.split(',')
        loopNum_parameter = parameters[0]
        bumpNum_parameter = parameters[1]
        step_parameter = parameters[2]
        valence_parameter = parameters[3]
        
        stepNums.append(int(step_parameter.split(':')[1]))
        stepBumpNum.append(int(loopNum_parameter.split(':')[1]))
        
        if len(loopNums) == 0:
            loopNums.append(int(loopNum_parameter.split(':')[1]))
            bumpNums.append(int(bumpNum_parameter.split(':')[1]))
            valences.append(int(valence_parameter.split(':')[1]))
        elif loopNums[len(loopNums)-1] < int(loopNum_parameter.split(':')[1]) :
            loopNums.append(int(loopNum_parameter.split(':')[1]))
            bumpNums.append(int(bumpNum_parameter.split(':')[1]))
            valences.append(int(valence_parameter.split(':')[1]))


fig = plt.figure()
ax1 = fig.add_subplot(111)
ax1.plot(loopNums,bumpNums,label='Threshold: 3')
ax1.set_xlabel('Decision cycles')
ax1.set_ylabel('Bump times')
#y_ticks = np.arange(0, 50, 5)
#plt.yticks(y_ticks) 

ax2 = ax1.twinx() # this is the important function
ax2.plot(loopNums,valences,'g',label='Threshold: 3')
ax2.set_ylabel('Total valences');

#plt.subplot(211)
#plt.title('Interactions with decision cycles and bumping times')
#plt.plot(loopNums,valences,label='Threshold: 3')
#plt.xlabel('Decision cycles')
#plt.ylabel('Bump times')
#x_ticks = np.arange(0, 600, 30)
#y_ticks = np.arange(0, 50, 5)
#plt.xticks(x_ticks)
#plt.yticks(y_ticks) 
#plt.legend()

#plt.subplot(212)
#plt.title("Interactions' total valences with decision cycles")
#plt.plot(loopNums,valences,label='Threshold: 3')
#plt.xlabel('Decision cycles')
#plt.ylabel('Total valences')
#x_ticks = np.arange(0, 600, 30)
#plt.xticks(x_ticks)

plt.legend()
plt.tight_layout()
plt.savefig('bump_valence_double.eps',dpi=600,format='eps')

plt.show()



#plt.subplot(211)
#plt.plot(loopNums,bumpNums,label='Threshold: 3')
#plt.xlabel('Decision cycles')
#plt.ylabel('Bump times')
#x_ticks = np.arange(0, 800, 100)
#y_ticks = np.arange(0, 50, 5)
#plt.xticks(x_ticks)
#plt.yticks(y_ticks)

#plt.subplot(212)
#plt.plot(loopNums,valences,label='Threshold: 3')
#plt.xlabel('Decision cycles')
#plt.ylabel('valences')
#x_ticks = np.arange(0, 1000, 100)

#plt.xticks(x_ticks)


plt.legend()
plt.show() 
    





#plt.plot(stepNums,bumpNums)
#plt.show()
    