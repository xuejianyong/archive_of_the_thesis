import matplotlib.pyplot as plt
import os
import numpy as np

loopNums = []
bumpNums = []
valences = []

scriptPath = os.getcwd()
datafile = os.path.join(scriptPath,'result_2_1001.txt')
print datafile
print 'start the processing......'
fp=open(datafile)
lines = fp.readlines()

for line in lines:
    line=line.strip('\n')
    #print line
    #print lineIndex
    if line != '':
        parameters = line.split(',')
        
        loopNum_parameter = parameters[0]
        bumpNum_parameter = parameters[1]
        valence_parameter = parameters[2]

        loopNums.append(int(loopNum_parameter.split(':')[1]))
        bumpNums.append(int(bumpNum_parameter.split(':')[1]))
        valences.append(int(valence_parameter.split(':')[1]))
        

plt.figure()
plt.title('Bumping times with decision cycles as the agent interacts with the environment.')
plt.plot(loopNums,bumpNums,label='Bumps with decision cycles.')
plt.xlabel('Decision cycles')
plt.ylabel('Bump times')
#x_ticks = np.arange(0, 650, 30)
#y_ticks = np.arange(0, 40, 5)
#plt.xticks(x_ticks)
#plt.yticks(y_ticks) 
plt.legend()

plt.figure()
plt.title("Accumulated valence with decision cycles in agent's interactions with the environment.")
plt.plot(loopNums,valences,label="Accumulated valence with decision cycles.")
#plt.xlabel('Decision cycles')
#plt.ylabel('Total valence')
#x_ticks = np.arange(0, 650, 30)
#plt.xticks(x_ticks)
plt.legend()


plt.legend()
plt.show()