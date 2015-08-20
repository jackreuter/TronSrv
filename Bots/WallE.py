import Bot
import sys

import random

BIKE1 = 1
BIKE2 = 2
TAIL1 = 10
TAIL2 = 20
WALL = 50

class WallE(Bot.Bot):
    def __init__(self, name, wallReached=False, wallComplete=False):
        super(WallE, self).__init__(name)
        self.wallReached = wallReached
        self.wallComplete = wallComplete
        self.wallX = 0
        self.wallY = 0
    
    def getMove(self, board):
        row,col = self.getPlayerPos(board)

        if not self.wallReached:
            wallX,wallY = closestWallPoint(row,col,board)
            self.wallX = wallX
            self.wallY = wallY
            best = directionsToPoint(row,col,wallX,wallY,board)
            self.wallReached = checkWallReached(self.playerNum, board)
        else:
            if not self.wallComplete:
                splitX,splitY = evenSplitPoint(self.playerNum,row,col,board,self.wallX,self.wallY)
                best = directionsToPoint(row,col,splitX,splitY,board)
                self.wallComplete = checkWallComplete(self.playerNum, board)
            else:
                most = 0
                best = []
                for d in [1,2,3,4]:
                    r = reachable(d,row,col,board)
                    if r>most:
                        most = r
                        best = [d]
                    if r==most:
                        best.append(d)
                return best[0]

        if len(best)==1:
            return best[0][0]
        elif len(best)>1:
            i = random.random()
            if i<best[0][1]:
                return best[0][0]
            else:
                return best[1][0]
        else:
            for d in [1,2,3,4]:
                if not isBlocked(row,col,d,board):
                    return d
        return 1

def isBlocked(row,col,d,board):
    newRow = row
    newCol = col
    if d==1:
        newRow -= 1
    elif d==2:
        newCol += 1
    elif d==3:
        newRow += 1
    else:
        newCol -= 1
    return not board[newRow][newCol]==0

def checkWallReached(playerNum,board):
    for x,y in getEdgeIndices(board):
        entry = board[x][y]
        if playerNum==1 and (entry==BIKE1 or entry==TAIL1):
            return True
        if playerNum==2 and (entry==BIKE2 or entry==TAIL2):
            return True

def checkWallComplete(playerNum,board):
    left = False
    right = False
    top = False
    bottom = False
    for x,y in getEdgeIndices(board):
        if playerNum==1:
            if board[x][y]==BIKE1 or board[x][y]==TAIL1:
                if x==1:
                    top = True
                if x==len(board)-2:
                    bottom = True
                if y==1:
                    left = True
                if y==len(board[0])-2:
                    right = True
        else:
            if board[x][y]==BIKE2 or board[x][y]==TAIL2:
                if x==1:
                    top = True
                if x==len(board)-2:
                    bottom = True
                if y==1:
                    left = True
                if y==len(board[0])-2:
                    right = True
    print (left and right) or (top and bottom)
    return (left and right) or (top and bottom)

def closestWallPoint(row,col,board):
    shortest = len(board)
    best = (0,0)
    for x,y in getEdgeIndices(board):
        distance = getDistance(row,col,x,y)
        if distance<shortest:
            shortest = distance
            best = (x,y)
    return best

def evenSplitPoint(playerNum,row,col,board,wallX,wallY):
    if wallX==1:
        return (len(board)-2,len(board[0])-1-wallY)
    if wallX==len(board)-2:
        return (1,len(board[0])-1-wallY)
    if wallY==1:
        return (len(board)-1-wallX,len(board[0])-2)
    if wallY==len(board[0])-2:
        return (len(board)-1-wallX,1)
    return (len(board)/2,len(board[0])/2)

def traceToWall(playerNum,row,col,board):
    return traceToWallRec(playerNum,row,col,board,[])

def traceToWallRec(playerNum,row,col,board,seen):
    if row==1 or col==1 or row==len(board)-2 or col==len(board)-2:
        return (row,col)
    else:
        seen.append((row,col))
        prev = previous(playerNum,row,col,board,seen)
        possible = []
        if len(prev)==0:
            return (-1,-1)
        else:
            for x,y in prev:
                possible = traceToWallRec(playerNum,x,y,board,seen)
                if not possible == (-1,-1):
                    return possible
            return (-1,-1)

def previous(playerNum,row,col,board,seen):
    prev = []
    north = (row-1,col)
    south = (row+1,col)
    east = (row,col+1)
    west = (row,col-1)
    possible = [north,south,east,west]
    for x,y in possible:
        if (x,y) not in seen:
            if playerNum==1:
                if board[x][y]==BIKE1 or board[x][y]==TAIL1:
                    prev.append((x,y))
            else:
                if board[x][y]==BIKE2 or board[x][y]==TAIL2:
                    prev.append((x,y))
    return prev

def getEdgeIndices(board):
    edges = []
    for i,row in enumerate(board):
        if i==1 or i==len(board)-2:
            for j in range(1,len(row)-1):
                edges.append((i,j))
        if i>1 and i<len(board)-2:
            edges.append((i,1))
            edges.append((i,len(row)-2))
    return edges

def getDistance(x,y,x1,y1):
    return abs(x-x1)+abs(y-y1)

def directionsToPoint(row,col,toRow,toCol,board):
    viable = []
    total = float(abs(row-toRow)+abs(col-toCol))
    if row-toRow>0 and not isBlocked(row,col,1,board):
        viable.append((1,(row-toRow)/total))
    if toRow-row>0 and not isBlocked(row,col,3,board):
        viable.append((3,(toRow-row)/total))
    if col-toCol>0 and not isBlocked(row,col,4,board):
        viable.append((4,(col-toCol)/total))
    if toCol-col>0 and not isBlocked(row,col,2,board):
        viable.append((2,(toCol-col)/total))
    return viable

def reachable(d,row,col,board):
    if d==1:
        c,s = reachableRec(row-1,col,board,[])
    elif d==2:
        c,s = reachableRec(row,col+1,board,[])
    elif d==3:
        c,s = reachableRec(row+1,col,board,[])
    else:
        c,s = reachableRec(row,col-1,board,[])
    return c

def reachableRec(row,col,board,seen):
    if not board[row][col]==0:
        return (0,[])
    seen.append((row,col))
    ns = neighbors(row,col)
    count = 1
    for x,y in ns:
        if (x,y) not in seen and board[x][y]==0:
            c,s = reachableRec(x,y,board,seen)
            count += c
            seen = s
    return count,seen

def neighbors(row,col):
    return [(row+1,col),(row-1,col),(row,col+1),(row,col-1)]

walle = WallE("Wall-E",False,False)
walle.run(sys.argv[1])
