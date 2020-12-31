#!/usr/bin/env python

from __future__ import print_function

from turtlebot3_gazebo.srv import EnactionSrv,EnactionSrvResponse
import rospy
from sensor_msgs.msg import LaserScan

def enaction_server():
    rospy.init_node('enaction_server_node',anonymous=True)
    s = rospy.Service('enaction', EnactionSrv, handle_enaction)
    print("The enaction server is ready!")
    rospy.Subscriber("/scan",LaserScan, callback)
    rospy.spin()

class EnactionServer:
    def __init__(self):
        rospy.init_node('enaction_server_node',anonymous=True)
        self.server = rospy.Service('enaction', EnactionSrv, self.handle_enaction)
        print("The enaction server is ready!")
        self.subscriber = rospy.Subscriber("/scan",LaserScan, self.callback)
        rospy.spin()

    def handle_enaction(self,req):
        print("get the intendedInteraction request: %s "%(req.intendedInteraction))
        print(self.laser)
        return EnactionSrvResponse("you get the enaction from the server")

    def callback(self,req):
        print("get subscriber messages")
        self.laser = req
        #print(req.ranges)

    


if __name__ == "__main__":
    es = EnactionServer()
    