"""
Student code for Word Wrangler game
"""

import urllib2
import codeskulptor
import poc_wrangler_provided as provided

WORDFILE = "assets_scrabble_words3.txt"


# Functions to manipulate ordered word lists

def remove_duplicates(list1):
    """
    Eliminate duplicates in a sorted list.

    Returns a new sorted list with the same elements in list1, but
    with no duplicates.

    This function can be iterative.
    """
    local_list1 = list1[:];
    
    index1 = 0;
    for element1 in local_list1:
        index2 = index1+1;
        while(index2 < len(local_list1)):
            if local_list1[index1]== local_list1[index2]:
                del local_list1[index2]
                index2 -= 1;
            index2 += 1;
        index1 += 1;
        
    return local_list1

def intersect(list1, list2):
    """
    Compute the intersection of two sorted lists.

    Returns a new sorted list containing only elements that are in
    both list1 and list2.

    This function can be iterative.
    """
    intersect_list = [];
    
    for element1 in list1:
        for element2 in list2:
            print element1,element2
            if element1 == element2:
                intersect_list.append(element1);
    
    return intersect_list

# Functions to perform merge sort

def merge(list1, list2):
    """
    Merge two sorted lists.

    Returns a new sorted list containing all of the elements that
    are in either list1 and list2.

    This function can be iterative.
    """   
    
    auxilaryList = []
    for element1 in list1:
        auxilaryList.append(element1);
    for element2 in list2:
        auxilaryList.append(element2);
    mergedList = auxilaryList[:];
        
    i=0;
    mid=len(list1)-1;
    j=mid+1;
    hi=len(auxilaryList)-1;
    
    for k in range(len(auxilaryList)):
        if(i > mid):
            mergedList[k] = auxilaryList[j];
            j+=1;
        elif(j > hi):
            mergedList[k] = auxilaryList[i];
            i+=1;
        elif(auxilaryList[i] > auxilaryList[j]):
            mergedList[k] = auxilaryList[j];
            j+=1;
        else:
            mergedList[k] = auxilaryList[i];
            i+=1;
        
    return mergedList
    
def merge_sort(list1):
    """
    Sort the elements of list1.

    Return a new sorted list with the same elements as list1.

    This function should be recursive.
    """
    local_list = list1[:];
    length=len(local_list)
    
    if(length <= 1):
        return local_list
    
    return merge(merge_sort(local_list[:length/2]),merge_sort(local_list[length/2:]))


# Function to generate all strings for the word wrangler game

def gen_all_strings(word):
    """
    Generate all strings that can be composed from the letters in word
    in any order.

    Returns a list of all strings that can be formed from the letters
    in word.

    This function should be recursive.
    """
    
    #part1
    if word == "":
         return [""]
    
    first = word[0]
    rest = word[1:]
    
    #part 2
    rest_strings = gen_all_strings(rest)
    
    #part 3 and 4
    word_list = []
    for rest in rest_strings:
        word_list.append(rest)
        
    for rest in rest_strings:
        for position in range(0,len(rest)+1):
            word_list.append(rest[:position]+first+rest[position:]);
    
    
    return word_list

# Function to load words from a file

def load_words(filename):
    """
    Load word list from the file named filename.

    Returns a list of strings.
    """
    return []

def run():
    """
    Run game.
    """
    words = load_words(WORDFILE)
    wrangler = provided.WordWrangler(words, remove_duplicates, 
                                     intersect, merge_sort, 
                                     gen_all_strings)
    provided.run_game(wrangler)

# Uncomment when you are ready to try the game
# run()



#print merge_sort([9,3,5,1])
print gen_all_strings("aab")    
    

