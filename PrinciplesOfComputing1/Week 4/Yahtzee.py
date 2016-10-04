"""
Planner for Yahtzee
Simplifications:  only allow discard and roll, only score against upper level
"""

# Used to increase the timeout, if necessary
import codeskulptor
codeskulptor.set_timeout(10)

def gen_all_sequences(outcomes, length):
    """
    Iterative function that enumerates the set of all sequences of
    outcomes of given length.
    """
    
    answer_set = set([()])
    for dummy_idx in range(length):
        temp_set = set()
        for partial_sequence in answer_set:
            for item in outcomes:
                new_sequence = list(partial_sequence)
                #print "once:",new_sequence
                #print "item:",item
                new_sequence.append(item)
                #print "sonra:",new_sequence
                temp_set.add(tuple(new_sequence))
        answer_set = temp_set
        #print answer_set;
    return answer_set

def gen_sorted_nth_sequences(hand):
    """
    Iterative function that enumerates the set of unique sequences of
    outcomes of until given length.
    """
    length = len(hand);
    temp_answer_set = set([()])
    answer_list = []
    for dummy_idx in range(length):
        temp_set = set()
        for partial_sequence in temp_answer_set:
            for item in hand:
                #print "item:",item,"partial sequence:",partial_sequence;
                new_sequence = list(partial_sequence)
                new_sequence.append(item)
                temp_set.add(tuple(new_sequence))
        temp_answer_set = temp_set
        sorted_sequences = [tuple(sorted(sequence)) for sequence in temp_answer_set]
        temp_answer_set = set(sorted_sequences);
        
        for element in temp_answer_set:
            answer_list.append(element);
     
    return answer_list;

def score(hand):
    """
    Compute the maximal score for a Yahtzee hand according to the
    upper section of the Yahtzee score card.

    hand: full yahtzee hand

    Returns an integer score 
    """
    
    #in order to store maximum score
    max_score = 0;
    
    #dictionary of scores will hold upper section value of dies
    scores = {};
    for value in hand:
        scores[value] = 0;
    
    
    #values of hand will be added to same key of scores dictionary
    #by this way 1s will be added to scores[1], 2s will be added to scores[2]..
    for element in hand:
        scores[element] += element;
    #print scores;
    
    #maximum value will be found in this loop
    for key in scores:
        if(max_score < scores[key]):
            max_score = scores[key];
    #print max_score;
    
    return max_score;
    
def expected_value(held_dice, num_die_sides, num_free_dice):
    """
    Compute the expected value based on held_dice given that there
    are num_free_dice to be rolled, each with num_die_sides.

    held_dice: dice that you will hold
    num_die_sides: number of sides on each die
    num_free_dice: number of dice to be rolled

    Returns a floating point expected value
    """
    #holds value of die sides 
    outcomes = [];
    
    #scores and outcomes are set dynamically according to num_die_sides
    for die_side in range(1,num_die_sides+1):
        outcomes.append(die_side);
    
    #stores all sequences of rolling dice num_free_dice times
    seq_outcomes = gen_all_sequences(outcomes, num_free_dice);
    
    #stores maximum score of all hands
    max_scores = [];
    
    #creates new hand by merging held_dice and seq_outcomes.
    #we get maximum scores for this hands
    for each_tuple in seq_outcomes:
        merged_tuple = held_dice + each_tuple;
        tuple_score = score(merged_tuple);
        max_scores.append(tuple_score);
        
    #summing for this maximum scores
    sum_up = 0;
    for element in max_scores:
        sum_up += element;
    
    #returning average of maximum scores
    average = sum_up*1.0 / len(max_scores);
    return average;


def gen_all_holds(hand):
    """
    Generate all possible choices of dice from hand to hold.

    hand: full yahtzee hand

    Returns a set of tuples, where each tuple is dice to hold
    """
    answer_list = gen_sorted_nth_sequences(hand);
    #print answer_list;
    
    will_delete = [];
    index = 0;
    for element in answer_list:
        repetition = {};
        for value in hand:
            repetition[value] = 0;
        for value in hand:
            repetition[value] += 1;
        #print repetition;
        #print "element",element;
        for number in element:
            #print number,"repetition limit:",repetition[number];
            repetition[number] -= 1;
            #print "repetition",repetition;   
            #print "";
            if(repetition[number] < 0):
                will_delete.append(index);
                break;
        #print "";        
        index += 1;
    #print "answer_list",answer_list;
    
    will_delete.reverse(); 
    for delete in will_delete:
        del answer_list[delete];
    
    answer_list.append(());
    
    #print answer_list;
    return set(answer_list);

def strategy(hand, num_die_sides):
    """
    Compute the hold that maximizes the expected value when the
    discarded dice are rolled.

    hand: full yahtzee hand
    num_die_sides: number of sides on each die

    Returns a tuple where the first element is the expected score and
    the second element is a tuple of the dice to hold
    """
    max_expected_value = 0;
    max_hand_tuple = {};
    length_of_longest_hand = len(hand);
    
    hand_list = list(gen_all_holds(hand));
    for each_hand in hand_list:
        length_of_each_hand = len(each_hand);
        if(length_of_longest_hand == 0):
            length_of_each_hand = -1;
        #print "length_of_longest_hand-length_of_each_hand:",length_of_longest_hand-length_of_each_hand;
        each_expected_value = expected_value(each_hand, num_die_sides, length_of_longest_hand-length_of_each_hand);
        
        #print "each_hand:",each_hand;
        #print "length:",length_of_each_hand;
        #print "length_of_longest_hand-length_of_each_hand:",length_of_longest_hand-length_of_each_hand;
        #print "each_expected_value:",each_expected_value;
        
        if(each_expected_value > max_expected_value):
            #print each_expected_value," > ",max_expected_value;
            max_expected_value = each_expected_value;
            max_hand_tuple = each_hand;
            
    #print max_expected_value;
    #print max_hand_tuple;
    return (max_expected_value, max_hand_tuple);

def run_example():
    """
    Compute the dice to hold and expected score for an example hand
    """
    num_die_sides = 6
    hand = (1, 1, 1, 5, 6)
    hand_score, hold = strategy(hand, num_die_sides)
    print "Best strategy for hand", hand, "is to hold", hold, "with expected score", hand_score
    

#print expected_value((), 6, 1);
#print strategy((1,), 6);
#print gen_all_holds((5,5,5));    
run_example()
#import poc_holds_testsuite
#poc_holds_testsuite.run_suite(gen_all_holds)



