"""
Clone of 2048 game.
"""

import poc_2048_gui
import random

# Directions, DO NOT MODIFY
UP = 1
DOWN = 2
LEFT = 3
RIGHT = 4

# Offsets for computing tile indices in each direction.
# DO NOT MODIFY this dictionary.
OFFSETS = {UP: (1, 0),
           DOWN: (-1, 0),
           LEFT: (0, 1),
           RIGHT: (0, -1)}

#it merges all elements in appropriate way at most left
def merge(line):
    new = [];
    for element in line:
        new.append(element);
    counter = 0;
    iterator1=0;
    iterator2=0;
    iterator3=0;
    deleted_items = 0;
    
    #it checks whether all elements of the list is zero
    #if it is, it directly return result list
    for tile in new:
        if(tile == 0):
            counter += 1;
            if(counter == len(new)):
                break;
                
                
    #it checks elements of zeros, and moves zeros to right
    #non-zeros to left
    for passnum in range(len(new)-1,0,-1):
        for iterator1 in range(passnum):
            if new[iterator1]==0:
                new[iterator1] = new[iterator1+1];
                new[iterator1+1] = 0;
    
    #merges same values for just once
    while(iterator2<len(new)-1):
        if((new[iterator2] == new[iterator2+1]) and (new[iterator2] != 0)):
            new[iterator2] *= 2;
            del new[iterator2+1];
            deleted_items += 1;
        iterator2 += 1;
    
    
    #it adds number as we deleted from list 
    while(iterator3 < deleted_items):
        new.append(0);
        iterator3 += 1;
    
    return new;


class TwentyFortyEight:
    """
    Class to run the game logic.
    """
    upTiles = [];
    downTiles = [];
    rightTiles = [];
    leftTiles = [];
    initialTiles = {};
    
    #assigns local values to instance variables and run reset() function
    #assigns initial coordinates for every direction to initialTiles dictionary
    def __init__(self, grid_height, grid_width):
        self.grid_height = grid_height;
        self.grid_width = grid_width;
        self.reset();
        
        for upAndDownIndex in range(0,self.get_grid_width()):
            self.upTiles.insert(upAndDownIndex,(0,upAndDownIndex));
            self.downTiles.insert(upAndDownIndex,(self.get_grid_height()-1,upAndDownIndex));
            
        for leftAndRightIndex in range(0,self.get_grid_height()):
            self.leftTiles.insert(leftAndRightIndex,(leftAndRightIndex,0));
            self.rightTiles.insert(leftAndRightIndex,(leftAndRightIndex, self.get_grid_width()-1));
        
        self.initialTiles[UP] = self.upTiles;
        self.initialTiles[DOWN] = self.downTiles;
        self.initialTiles[LEFT] = self.leftTiles;
        self.initialTiles[RIGHT] = self.rightTiles;
        
        print "initial up tiles : ",self.initialTiles[UP];
        print "initial down tiles : ",self.initialTiles[DOWN];
        print "initial left tiles : ",self.initialTiles[LEFT];
        print "initial right tiles : ",self.initialTiles[RIGHT];
        
    #except two tiles, it assigns 0 to all of rest tiles
    def reset(self):
        self.EXAMPLE_GRID = [[0 for col in range(self.grid_width)]
                                    for row in range(self.grid_height)]
        self.new_tile();
        self.new_tile();
    #prints all tiles to the screen for checking purpose
    def __str__(self):
        print self.grid_height;
        print self.grid_width;
        for row in range(0,self.grid_height):
            for column in range(0, self.grid_width):
                print "(",column,",",row,")"," = ",self.get_tile(row,column);
            print "\n";
        print self.EXAMPLE_GRID;
        
    #returns grid_height
    def get_grid_height(self):
        return self.grid_height;
    
    #returns grid_width
    def get_grid_width(self):
        return self.grid_width;

    def move(self, direction):
        """
        Move all tiles in the given direction and add
        a new tile if any tiles moved.
        """
        if(direction == UP or direction == DOWN):
            heightOrWidth = self.get_grid_height();
        elif(direction == LEFT or direction == RIGHT):
            heightOrWidth = self.get_grid_width();
            
        newValuesOfLine = [];
        if(direction == UP or direction == DOWN or direction == LEFT or direction == RIGHT):
            for index in range(0,len(self.initialTiles[int(direction)])):
                singleLineValues = [];
                row = 0;
                col = 0;
                for index2 in range(0,heightOrWidth):
                    row = self.initialTiles[int(direction)][index][0] + index2 * OFFSETS[int(direction)][0];
                    col = self.initialTiles[int(direction)][index][1] + index2 * OFFSETS[int(direction)][1];
                    singleLineValues.append(self.get_tile(row,col));
                    #print "row : ",row,"col ",col;
                print "singleLineValues",singleLineValues;
                newValuesOfLine = merge(singleLineValues);
                print "merged new values : ",newValuesOfLine;
                for index3 in range(len(newValuesOfLine)):
                    row = self.initialTiles[int(direction)][index][0] + index3 * OFFSETS[int(direction)][0];
                    col = self.initialTiles[int(direction)][index][1] + index3 * OFFSETS[int(direction)][1];
                    self.set_tile(row,col,newValuesOfLine[index3]);
        
        self.new_tile();
    
    #randomly selects two tiles and ramdomly gives 
    #them value of 2 or 4(90% 2,%10 4)
    def new_tile(self):
        coordinatesOfZero = [];
        index = 0;
        for row in range(0,self.grid_height):
            for column in range(0, self.grid_width):
                if(self.get_tile(row, column) == 0):
                    coordinatesOfZero.insert(index,(row,column));
                    index += 1;           
        selectedZeroIndex = random.randint(0, len(coordinatesOfZero)-1);
        
        twoOrFour = [];
        index = 0;
        for index in range(0,100):
            if(index < 90):
                twoOrFour.insert(index, 2);
            else:
                twoOrFour.insert(index, 4);
                
        randomTwoOrFour = random.randint(0, 99);
        valueOfNewTiles = twoOrFour[randomTwoOrFour];
        
        
        rowOfNewTile = coordinatesOfZero[selectedZeroIndex][0];
        columnOfNewTile = coordinatesOfZero[selectedZeroIndex][1];
        self.set_tile(rowOfNewTile, columnOfNewTile, valueOfNewTiles);
            
    def set_tile(self, row, col, value):
        self.EXAMPLE_GRID[row][col] = value;

    def get_tile(self, row, col):
        return self.EXAMPLE_GRID[row][col];
            
    def set_tile(self, row, col, value):
        self.EXAMPLE_GRID[row][col] = value;

    def get_tile(self, row, col):
        return self.EXAMPLE_GRID[row][col];

main = TwentyFortyEight(4, 6);
poc_2048_gui.run_gui(main)

