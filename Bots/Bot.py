import getpass
import sys
import telnetlib
import random

class Bot(object):
    def __init__(self, name):
        self.name = name
        self.playerNum = 1

    def getPlayerPos(self, board):
        for i,row in enumerate(board):
            for j,entry in enumerate(row):
                if entry==self.playerNum:
                    return (i,j)
        return (0,0)

    def getOppPos(self, board):
        oppNum = 1
        if self.playerNum==1:
            oppNum = 2
        for i,row in enumerate(board):
            for j,entry in enumerate(row):
                if entry==oppNum:
                    return (i,j)
        return (0,0)
    
    def getMove(self, board):
        return random.choice([1,2,3,4])
        
    def run(self, host):
        tn = telnetlib.Telnet(host,5490)
        done = False
        board = []
        while (not done):
            match = tn.expect(["Are you a human\?","You are Player 1.","You are Player 2.","BOARD","Enter a direction:","You wins","You lose","Tie"])
            if "Are you a human" in match[1].group(0):
                tn.write("no")
                tn.write("\n")
            elif "You are Player" in match[1].group(0):
                data = match[2].strip()
                if "1" in match[1].group(0):
                    self.playerNum = 1
                else:
                    self.playerNum = 2
                print data
            elif match[1].group(0)=="BOARD":
                board = []
                data = match[2].strip()
                boardString = data[0:len(data)-5]
                for i,rowString in enumerate(boardString.split(";")):
                    row = []
                    for j,entry in enumerate(rowString.split(",")):
                        row.append(int(entry))
                    board.append(row)
            elif match[1].group(0)=="Enter a direction:":
                print match[2]
                move = self.getMove(board)
                tn.write(str(move))
                tn.write("\n")
            elif match[1].group(0)=="You wins":
                data = match[2]
                print data + " " + self.name
                done = True
            elif match[1].group(0)=="You lose":
                data = match[2]
                print data + " " + self.name
                done = True
            elif match[1].group(0)=="Tie":
                data = match[2]
                print data
                done = True

