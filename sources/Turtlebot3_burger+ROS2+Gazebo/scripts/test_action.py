#!/usr/bin/env python
import rospy
from std_msgs.msg import String
from sensor_msgs.msg import LaserScan
from geometry_msgs.msg import Twist
import time


class CheckAction:
    def __init__(self):
        rospy.init_node("laserScan_listener",anonymous=True)
        self.laser = LaserScan()
        self.velocity_publisher = rospy.Publisher("/cmd_vel",Twist,queue_size=10) #order the robot to take action
        self.pose_subscriber = rospy.Subscriber("/scan",LaserScan,self.callback)#get the current detected information
        self.rate = rospy.Rate(10)
        #self.move = Twist()
        rospy.spin()
    
    def callback(self, data):#Callback function implementing the pose value received
        print("get data from the sensor")
        self.laser = data
    
    def getState(self):
        iswall = False
        inf = float("inf")
        dat1 = self.laser
        print(dat1)
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
        print(element_mean)

        if element_mean > 1.0:
            iswall = True
        return iswall        

    def move(self, linear_speed=0.0, angular_speed=0.0, duration=1.0):
        vel_msg = Twist()
        vel_msg.linear.x = linear_speed
        vel_msg.linear.y = 0
        vel_msg.linear.z = 0
        vel_msg.angular.x = 0
        vel_msg.angular.y = 0
        vel_msg.angular.z = angular_speed

        t0 = float(rospy.Time.now().to_sec())
        while float(rospy.Time.now().to_sec()) - t0 < duration:
            self.velocity_publisher.publish(vel_msg)
            self.rate.sleep()
        vel_msg.linear.x = 0
        vel_msg.angular.z = 0
        self.velocity_publisher.publish(vel_msg)


    #find the index of the min value    
    #print(dat1.index(min(dat1)))
    
    
    #if msg.ranges[0] < 2:
    #    while float(rospy.Time.now().to_sec()) - t0 < 2.0:
    #        pub.publish(move)
    #else:
    #    move.linear.x = 0.2
    #    move.angular.z = 0.0
    #    while float(rospy.Time.now().to_sec()) - t0 < 2.0:
    #        pub.publish(move)
    #if msg.ranges[0]>0.2:
    #    move.linear.x = 0.2
    #    while float(rospy.Time.now().to_sec()) - t0 < 1.0:
    #        pub.publish(move)
    #        rate.sleep() 
    #else:


if __name__ == "__main__":
    try:
        check = CheckAction()
    except rospy.ROSInterruptException:
        pass