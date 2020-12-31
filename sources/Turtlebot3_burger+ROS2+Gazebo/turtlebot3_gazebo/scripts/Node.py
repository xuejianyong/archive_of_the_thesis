
class Node:
    def __init__(self,interaction):
        self.interaction = interaction
        self.upperNode = None
        self.upperDirection = "center"
        self.leftNode = None
        self.rightNode = None
        self.isVisited = False
        self.nodeId = None
        self.isLast = False
    