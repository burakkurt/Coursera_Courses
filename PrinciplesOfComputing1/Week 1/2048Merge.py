"""
Merge function for 2048 game.
"""

"""returns a list that slides all non-zero tiles to left 
and merge the same ones for once""" 
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

row_data = [0,2,0,2,0,2,2,0,2,0,2];
print merge(row_data);
