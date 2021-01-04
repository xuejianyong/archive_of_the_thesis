# -*- coding: utf-8 -*-
"""
Created on Monday  20/05/2019 11:06:01

@author: J.Xue

The implementation of NAO with VREP
"""

#For more informations please contact with: https://liris.cnrs.fr/page-membre/jianyong-xue

import vrep,sys
import threading
import math
import numpy as np
from naoqi import ALProxy
from manage_joints import get_first_handles,JointControl,JointControlfor

print '================ Program Sarted ================'

vrep.simxFinish(-1)
clientID=vrep.simxStart('127.0.0.1',19997,True,True,5000,5)
print clientID
if clientID!=-1:
	print 'Connected to remote API server'
else:
	print 'Connection non successful'
	sys.exit('Could not connect')


print "================ Choregraphe's Initialization ================"
print 'Enter your NAO IP'
#naoIP = raw_input()
naoIP = "127.0.0.1"
#naoIP = map(str,naoIP.split())
print 'Enter your NAO port'
#naoPort = raw_input()
naoPort = 52431
naoPort=int(naoPort)
#naoPort = map(int,naoPort.split())

motionProxy = ALProxy("ALMotion",naoIP, naoPort)
postureProxy = ALProxy("ALRobotPosture", naoIP, naoPort)

print "Wake up the robot"
#motionProxy.wakeUp()

#Go to the posture StandInitZero
posture = 'StandZero'
print 'Posture Initialization : ' + posture

# Send robot to Pose Init
#postureProxy.goToPosture("StandInit", 0.5)


#motionProxy.stiffnessInterpolation('Body', 1.0, 1.0)
#postureProxy.goToPosture(posture,1.0,1.0)

Head_Yaw=[];Head_Pitch=[];
L_Hip_Yaw_Pitch=[];L_Hip_Roll=[];L_Hip_Pitch=[];L_Knee_Pitch=[];L_Ankle_Pitch=[];L_Ankle_Roll=[];
R_Hip_Yaw_Pitch=[];R_Hip_Roll=[];R_Hip_Pitch=[];R_Knee_Pitch=[];R_Ankle_Pitch=[];R_Ankle_Roll=[];
L_Shoulder_Pitch=[];L_Shoulder_Roll=[];L_Elbow_Yaw=[];L_Elbow_Roll=[];L_Wrist_Yaw=[]
R_Shoulder_Pitch=[];R_Shoulder_Roll=[];R_Elbow_Yaw=[];R_Elbow_Roll=[];R_Wrist_Yaw=[]
R_H=[];L_H=[];R_Hand=[];L_Hand=[];
Body = [Head_Yaw,Head_Pitch,L_Hip_Yaw_Pitch,L_Hip_Roll,L_Hip_Pitch,L_Knee_Pitch,L_Ankle_Pitch,L_Ankle_Roll,R_Hip_Yaw_Pitch,R_Hip_Roll,R_Hip_Pitch,R_Knee_Pitch,R_Ankle_Pitch,R_Ankle_Roll,L_Shoulder_Pitch,L_Shoulder_Roll,L_Elbow_Yaw,L_Elbow_Roll,L_Wrist_Yaw,R_Shoulder_Pitch,R_Shoulder_Roll,R_Elbow_Yaw,R_Elbow_Roll,R_Wrist_Yaw,L_H,L_Hand,R_H,R_Hand]

get_first_handles(clientID,Body)
print "================ Handles Initialization ================"

#get the angles of the jointsm getAngles(names,useSensors)
# the parameter are: names of the joints, useSensors if true sensor angles will be returned

commandAngles = motionProxy.getAngles('Body', False)
print '========== NAO is listening =========='


t1 = threading.Thread(target=JointControlfor,args=(clientID,motionProxy,0,Body))
t1.start()


motionProxy.wakeUp()
postureProxy.goToPosture("StandInit", 0.5)

x  = 0
y  = 0
theta  = 0
loopNum = 1
while(loopNum < 5):
    errorCode,sensor_handle = vrep.simxGetObjectHandle(clientID,"Proximity_sensor_front",vrep.simx_opmode_oneshot_wait)
    returnCode,detectionState,detectedPoint,detectedObjectHandle,detectedSurfaceNormalVector=vrep.simxReadProximitySensor(clientID,sensor_handle,vrep.simx_opmode_streaming)
    #print detectedPoint
    distance = np.linalg.norm(detectedPoint)
    print "the distance is"
    print distance
    #print type(distance)
    if distance == 0:
        pass
    if distance > 0.7 or distance == 0.7:
        print "move"
        theta = 0
        x = 0.5
        motionProxy.moveTo(x, y, theta)
    elif distance>0 and distance <0.7 :
        x = 0
        theta = -math.pi/2
        motionProxy.moveTo(x, y, theta)
	loopNum+=1
    print loopNum

#theta  = 0

motionProxy.rest()
print 'the robot will move '

#while(1):
#    pass
#JointControlfor(clientID,motionProxy,0,Body)