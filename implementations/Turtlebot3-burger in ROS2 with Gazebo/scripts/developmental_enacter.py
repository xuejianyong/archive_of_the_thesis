#!/usr/bin/env python

from __future__ import print_function

from sensor_msgs.msg import LaserScan
from geometry_msgs.msg import Twist
from turtlebot3_gazebo.srv import EnactionSrv,EnactionSrvResponse
import rospy

class EnactionServer:
    def __init__(self):
        rospy.init_node('enaction_server_node')
        self.laser = LaserScan()
        self.server = rospy.Service('enaction', EnactionSrv, self.handle_enaction)
        print("The enacter is ready!")
        self.velocity_publisher = rospy.Publisher("/cmd_vel",Twist,queue_size=10)
        self.pose_subscriber = rospy.Subscriber("/scan",LaserScan, self.callback)
        #move = Twist()
        self.rate = rospy.Rate(10)
        rospy.spin()
        #self.velocity_publisher = rospy.Publisher('/turtle1/cmd_vel', Twist, queue_size=10)
        #self.pose_subscriber = rospy.Subscriber('/turtle1/pose', Pose, self.callback)
        #self.pose = Pose()
        #self.rate = rospy.Rate(10)
    
    def handle_enaction(self,req):
        print('--------------------------------------------------')
        interactionString = req.intendedInteraction
        print("Intended interaction is: "+interactionString)
        enactedInteractionString = ""

        eIndex = 0
        eIndexList = []
        interactionList = []

        for i in interactionString:
            if i == 'e':
                eIndexList.append(eIndex)
            eIndex+=1
        for ei in eIndexList:
            interaction = interactionString[ei:ei+3]
            interactionList.append(interaction)
        #each primitive interaction for enacting
        for interaction in interactionList:
            actionType = interaction[1]
            result = interaction[2]
            enactedResult = ""
            #print("Single interaction is: "+interaction)
            if actionType == "0":#move forward
                enactedResult = self.getState("3")
                if enactedResult == "1":
                    self.move(linear_speed=0.3)
            elif actionType == "1":#turn left
                enactedResult = "1"
                self.move(linear_speed=0.0, angular_speed=2)
            elif actionType == "2":#turn right
                enactedResult = "1"
                self.move(linear_speed=0.0, angular_speed=-2)
            elif actionType == "3":#feel front
                enactedResult = self.getState("3")
            elif actionType == "4":#feel left
                enactedResult = self.getState("4")
            elif actionType == "5":#feel right
                enactedResult = self.getState("5")

            enactedInteractionString = enactedInteractionString+"e"+actionType+enactedResult+","
            if result != enactedResult:
                break
        
        #testici
        print("Enacted  interaction is: "+enactedInteractionString)
        return EnactionSrvResponse(enactedInteractionString)

    def getState(self,actionType):
        inf = float("inf")
        dat1 = self.laser
        enactedResult = "0"
        if actionType == "3":
            scan_left = dat1[0:4]
            scan_right = dat1[357:360]
            scan_domain = scan_left+scan_right
            element_num = 0
            element_total = 0.0
            element_mean = 0.0
            for scan_element in scan_domain:
                if scan_element != inf:
                    element_num+=1
                    element_total+=scan_element
            if element_total != 0.0:
                element_mean = element_total/element_num
            if element_mean > 0.8:#is not wall
                enactedResult = "1"
        elif actionType == "4":
            scan_domain = dat1[87:94]
            element_num = 0
            element_total = 0.0
            element_mean = 0.0
            for scan_element in scan_domain:
                if scan_element != inf:
                    element_num+=1
                    element_total+=scan_element
            if element_total != 0.0:
                element_mean = element_total/element_num
            if element_mean > 0.8:
                enactedResult = "1"
        elif actionType == "5":
            scan_domain = dat1[267:274]
            element_num = 0
            element_total = 0.0
            element_mean = 0.0
            for scan_element in scan_domain:
                if scan_element != inf:
                    element_num+=1
                    element_total+=scan_element
            if element_total != 0.0:
                element_mean = element_total/element_num
            if element_mean > 0.8:
                enactedResult = "1"
        return enactedResult

    def move(self, linear_speed=0.0, angular_speed=0.0, duration=0.5):
        """ Enacting a movement and returning the outcome """
        #move = Twist()
        vel_msg = Twist()
        vel_msg.linear.x = linear_speed
        vel_msg.linear.y = 0
        vel_msg.linear.z = 0
        vel_msg.angular.x = 0
        vel_msg.angular.y = 0
        vel_msg.angular.z = angular_speed

        # Setting the current time for duration calculus
        t0 = float(rospy.Time.now().to_sec())

        # Loop to move the turtle during a specific duration
        while float(rospy.Time.now().to_sec()) - t0 < duration:
            # Publish the velocity
            self.velocity_publisher.publish(vel_msg)
            self.rate.sleep()

        # After the loop, stops the robot
        vel_msg.linear.x = 0
        vel_msg.angular.z = 0
        self.velocity_publisher.publish(vel_msg)
        # print(Position x=% 2.1f, y=% 2.1f" % (self.pose.x, self.pose.y))
        

    def callback(self, data):#Callback function implementing the pose value received
        self.laser = data.ranges
        #self.pose.x = round(self.pose.x, 4)
        #self.pose.y = round(self.pose.y, 4)
    

if __name__ == "__main__":
    try:
        es = EnactionServer()
    except rospy.ROSInterruptException as e:
        print(e)