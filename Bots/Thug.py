import Bot
import sys

import random

class Thug(Bot.Bot):
    def getMove(self, board):
        row,col = self.getPlayerPos(board)
        most = 0
        best = []
        for d in [1,2,3,4]:
            r = reachable(d,row,col,board)
            if r>most:
                most = r
                best = [d]
            if r==most:
                best.append(d)
            i = random.randint(0,len(best)-1)
        return best[i]

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

thug = Thug("Thug")
thug.run(sys.argv[1])
