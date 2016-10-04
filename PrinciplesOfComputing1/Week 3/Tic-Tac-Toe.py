"""
Monte Carlo Tic-Tac-Toe Player
"""

import random
import poc_ttt_gui
import poc_ttt_provided as provided

# Constants for Monte Carlo simulator
# You may change the values of these constants as desired, but
#  do not change their names.
NTRIALS = 100        # Number of trials to run
SCORE_CURRENT = 1.0 # Score for squares played by the current player
SCORE_OTHER = 1.0   # Score for squares played by the other player
    
# Add your functions here.

def mc_trial(board, player):
    '''
    This function takes a current board and the next player to move. 
    The function should play a game starting with the given player by 
    making random moves, alternating between players. The function should 
    return when the game is over. The modified board will contain 
    the state of the game, so the function does not return anything. 
    In other words, the function should modify the board input.
    '''
    
    #if game continues(check_win() == None) we need to make a new move
    while(board.check_win() == None):
        #current empty positions
        empty_squares = board.get_empty_squares();
        
        #randomly create a number between 0 and length of empty squares
        #to make new move
        length_of_zero_list = len(board.get_empty_squares());
        random_zero_index = random.randrange(0, length_of_zero_list);

        #select one square to be marked by one player    
        row = empty_squares[random_zero_index][0];
        col = empty_squares[random_zero_index][1];
        board.move(row,col,player);

        #switch player to make move by another player
        player = provided.switch_player(player);
        
    
def mc_update_scores(scores,board,player):
    '''
    This function takes a grid of scores (a list of lists) with the same dimensions 
    as the Tic-Tac-Toe board, a board from a completed game, and which player 
    the machine player is. The function should score the completed board and update 
    the scores grid. As the function updates the scores grid directly, it does not return anything,
    '''
    
    #when the result is tie, value of scores are not changed
    if(board.check_win() == 4):
        return;
    
    #setting value for list of scores when someone wins
    row = 0;
    while(row < board.get_dim()):
        col = 0;
        while(col < board.get_dim()):
            if(board.square(row,col) == board.check_win()):
                scores[row][col] += SCORE_CURRENT;
            elif(board.square(row,col) == provided.switch_player(board.check_win())):
                scores[row][col] -= SCORE_OTHER; 
            col += 1;
        row += 1;
    
def get_best_move(board, scores):
    '''
    This function takes a current board and a grid of scores. The function should find 
    all of the empty squares with the maximum score and randomly return one of them 
    as a (row, column) tuple. It is an error to call this function with a board that 
    has no empty squares (there is no possible next move), so your function may do whatever 
    it wants in that case. The case where the board is full will not be tested.
    '''
    
    #when there is no empty square means the game has finished
    #there is no next move
    if(len(board.get_empty_squares()) == 0):
        return;
    
    #best move must be one of the empty square. 
    empty_squares = board.get_empty_squares();
    
    #that's why initial biggest number is first square 
    #that match with empty square
    biggest_value = scores[empty_squares[0][0]][empty_squares[0][1]];
    biggest_value_index = [];
    
    #biggest numbers and indexes that match with empty squares are evaluated
    for empty_square in empty_squares:
        row = empty_square[0];
        col = empty_square[1];
        if(scores[row][col] > biggest_value):
            biggest_value_index = [];
            biggest_value = scores[row][col];
            biggest_value_index.append((row,col));
        elif(scores[row][col] == biggest_value):
            biggest_value_index.append((row,col));
        elif(len(biggest_value_index) == 0):
            biggest_value_index.append((empty_squares[0][0],empty_squares[0][1]));
    
    #randomly one of the biggest square selected
    #(if there is just one, random =0 automatically)
    random_index = random.randrange(0, len(biggest_value_index));
    row = biggest_value_index[random_index][0];
    col = biggest_value_index[random_index][1];
    
    #indexes of best move is returned
    return (row,col);
    
def mc_move(board,player,trials):
    '''
    This function takes a current board, which player the machine player is, and 
    the number of trials to run. The function should use the Monte Carlo simulation described
    above to return a move for the machine player in the form of a (row, column) tuple. 
    Be sure to use the other functions you have written!
    '''
    
    #list of scores created. dimension of scores is same with dimension of board
    scores = [[0 for _ in range(board.get_dim())] for _ in range(board.get_dim())];
    
    index = 0;
    while(index < NTRIALS):
        #local_board will be used for trials, it takes updated board values
        #it takes value of updated board before trial so that using again
        local_board = board.clone();
        
        #makes trials NTRIALS times and updates scores permanently
        mc_trial(local_board, player);
        mc_update_scores(scores,local_board,player);
        index += 1;
    
    #after number of NTRIALS scores sends to get_best_move and best move is returned
    return get_best_move(board, scores);

    
#provided.play_game(mc_move, NTRIALS, False)        
#poc_ttt_gui.run_gui(3, provided.PLAYERX, mc_move, NTRIALS, False)
