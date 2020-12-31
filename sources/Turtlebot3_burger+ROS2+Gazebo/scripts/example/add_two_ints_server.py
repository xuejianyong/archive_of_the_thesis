#!/usr/bin/env python

from __future__ import print_function

from beginner_tutorials.srv import AddTwoInts,AddTwoIntsResponse
import rospy

def handle_add_two_ints(req):
    print("Returning the result: [%s + %s = %s]"%(req.a, req.b, (req.a + req.b)))
    return AddTwoIntsResponse(req.a + req.b)

def add_two_ints_server():
    rospy.init_node('add_two_ints_server')
    s = rospy.Service('add_two_ints', AddTwoInts, handle_add_two_ints)
    # __init__(self, name, service_class, handler,buff_size=DEFAULT_BUFF_SIZE, error_handler=None)
    #service_class --service definition class
    #handler --callback function for processing service request. function takes in a ServieRequest and returns 
    #a ServiceResponse of the appropriate type. Function may also return a list, tuple, or dictionary with
    #arguments to initialize a ServiceResponse instance of the correct type.
    print("Ready to add two ints.")
    rospy.spin()

if __name__ == "__main__":
    add_two_ints_server()