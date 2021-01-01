#!/usr/bin/env python

from __future__ import print_function

import sys
import rospy
from beginner_tutorials.srv import *

def add_two_ints_client(x, y):
    rospy.wait_for_service('add_two_ints') # service name 'add_two_ints'
    try:
        add_two_ints = rospy.ServiceProxy('add_two_ints', AddTwoInts) #service name, AddTwoInts service_class
        #the class of AddTwiInts comes from the generated python code from srv file
        resp1 = add_two_ints(x, y)#call the service, then at this step to transfer the parameters
        return resp1.sum
    except rospy.ServiceException as e:
        print("Service call failed: %s"%e)

def usage():
    return "%s [x y]"%sys.argv[0]

if __name__ == "__main__":
    print("show the parameters:")
    print(sys.argv)#get parameters from the command
    print()
    if len(sys.argv) == 3:
        x = int(sys.argv[1])
        y = int(sys.argv[2])
    else:
        print(usage())
        sys.exit(1)
    print("Requesting %s+%s"%(x, y))
    print("%s + %s = %s"%(x, y, add_two_ints_client(x, y)))