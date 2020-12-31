import matplotlib.pyplot as plt
import os
import numpy as np

loopNums = []
bumpNums = []
valences = []

scriptPath = os.getcwd()
files= os.listdir(scriptPath)

linestyle = ['-', '--', '-.', ':','-']
lineindex = 0

markerstyle = ['o']
markerindex = 0

for file in files:
    
    if file.split(".")[1] == "txt":
        print lineindex
        filename = file.split("_")[1]
        legendName = "Threshold: "+filename
        
        filepath = os.path.join(scriptPath,file)
        print filepath

        #legendName = file.split('.')[0]
        fp=open(filepath)
        lines = fp.readlines()
        for line in lines:
            if line != '':
                parameters = line.split(',')
                loopNum_parameter = parameters[0]
                bumpNum_parameter = parameters[1]
                valence_parameter = parameters[2]

                loopNums.append(int(loopNum_parameter.split(':')[1]))
                bumpNums.append(int(bumpNum_parameter.split(':')[1]))
                valences.append(int(valence_parameter.split(':')[1]))
        
        
        plt.plot(loopNums,bumpNums,linestyle=linestyle[lineindex], label=legendName)
        plt.xlabel('Decision cycles')
        plt.ylabel('Bump times')
        plt.annotate('The optimal threshold',xy=(600,25),xytext=(700,20),arrowprops = dict(facecolor='red',shrink=0.05,headlength= 10,headwidth = 10))
        
        #x_ticks = np.arange(0, 1000, 100)
        #y_ticks = np.arange(0, 60, 5)
        #plt.xticks(x_ticks)
        #plt.yticks(y_ticks)
        
        #plt.subplot(212)
        #plt.plot(loopNums,valences,linestyle=linestyle[lineindex], label=legendName)
        #plt.xlabel('Decision cycles')
        #plt.ylabel('valences')
        
        #x_ticks = np.arange(0, 1000, 100)
        #plt.xticks(x_ticks)

        loopNums = []
        bumpNums = []
        valences = []
        
        lineindex+=1
        
    

plt.legend()
plt.show()
        