"""
Student portion of Zombie Apocalypse mini-project
"""

import random
import poc_grid
import poc_queue
import poc_zombie_gui

# global constants
EMPTY = 0 
FULL = 1
FOUR_WAY = 0
EIGHT_WAY = 1
OBSTACLE = 5
HUMAN = 6
ZOMBIE = 7


class Apocalypse(poc_grid.Grid):
    """
    Class for simulating zombie pursuit of human on grid with
    obstacles
    """

    def __init__(self, grid_height, grid_width, obstacle_list = None, 
                 zombie_list = None, human_list = None):
        """
        Create a simulation of given size with given obstacles,
        humans, and zombies
        """
        poc_grid.Grid.__init__(self, grid_height, grid_width)
        if obstacle_list != None:
            for cell in obstacle_list:
                self.set_full(cell[0], cell[1])
        if zombie_list != None:
            self._zombie_list = list(zombie_list)
        else:
            self._zombie_list = []
        if human_list != None:
            self._human_list = list(human_list)  
        else:
            self._human_list = []
        
    def clear(self):
        """
        Set cells in obstacle grid to be empty
        Reset zombie and human lists to be empty
        """
        self._obstacle_list = poc_grid.Grid.clear(self);
        self._human_list = [];
        self._zombie_list = [];
        
    def add_zombie(self, row, col):
        """
        Add zombie to the zombie list
        """
        self._zombie_list.append((row,col));
                
    def num_zombies(self):
        """
        Return number of zombies
        """
        return len(self._zombie_list);
            
    def zombies(self):
        """
        Generator that yields the zombies in the order they were
        added.
        """
        for zombie in self._zombie_list:
            yield zombie;

    def add_human(self, row, col):
        """
        Add human to the human list
        """
        self._human_list.append((row,col));
        
    def num_humans(self):
        """
        Return number of humans
        """
        return len(self._human_list);
    
    def humans(self):
        """
        Generator that yields the humans in the order they were added.
        """
        for human in self._human_list:
            yield human;
        
    def compute_distance_field(self, entity_type):
        """
        Function computes and returns a 2D distance field
        Distance at member of entity_list is zero
        Shortest paths avoid obstacles and use four-way distances
        """
        
        #assigning grid height and grid width values from super class Grid
        grid_height = poc_grid.Grid.get_grid_height(self);
        grid_width = poc_grid.Grid.get_grid_width(self);
        
        #visited is created to determine which grid is visited, not visited or visitable
        #if value of grid is EMPTY(0) it is not visited yet
        #if value of grid is FULL(1) it is visited 
        #if value of grid is OBSTACLE(5) it will never be visited,
        #that means human or zombie cannot use this grid 
        #the list has same range with grid and it assigns empty to all grids
        visited = [[EMPTY for dummy_col in range(grid_width)] 
                       for dummy_row in range(grid_height)]
        
        #distance field is created to determine distance of the closest grid that is full
        #the list has same range with grid and assigning row*col value
        distance_field = [[grid_height*grid_width for dummy_col in range(grid_width)] 
                       for dummy_row in range(grid_height)]
        
        #according to value of entity_type, distance of which entity will be found 
        #is decided here, HUMAN or ZOMBIE.
        #coordinates of selected entity is assigned to local_entity 
        if(entity_type == HUMAN):
            local_entity_type = self._human_list;
        elif(entity_type == ZOMBIE):
            local_entity_type = self._zombie_list;
        
        #boundary is a queue that will hold coordinates of selected entity
        #every single element of local_entity_type is pushed to boundary
        boundary = poc_queue.Queue();
        for entity in local_entity_type:
            boundary.enqueue(entity);
        
        #coordinates of entity is used to assign FULL(1) to same coordinates in visited liste, 
        #and assign same coordinates in distance_field list as 0 
        for entity_in_queue in boundary:
            visited[entity_in_queue[0]][entity_in_queue[1]] = FULL;
            distance_field[entity_in_queue[0]][entity_in_queue[1]] = 0;
        
        #coordinates of obstacles are found and these coordinates  
        #used to mark visited list as OBSTACLE(5) that means will never be visited 
        for height in range(grid_height):
            for width in range(grid_width):
                if poc_grid.Grid.is_empty(self, height, width) == False:
                    visited[height][width] = OBSTACLE;
        
        #implementation of breadth-first search
        #each grid represents distance to closest entity(human or zombie which one is selected)
        #coordinates in the queue is used first and neighbor of these grids push to queue
        #all the visitable grids are visited by this way and distance value assign them
        while(len(boundary) > 0):
            current_cell = boundary.dequeue();
            current_cell_value = distance_field[current_cell[0]][current_cell[1]];
            for neighbor_cell in poc_grid.Grid.four_neighbors(self, current_cell[0], current_cell[1]):
                if(visited[neighbor_cell[0]][neighbor_cell[1]] == EMPTY):
                    visited[neighbor_cell[0]][neighbor_cell[1]] = 1;
                    boundary.enqueue(neighbor_cell);
                    distance_field[neighbor_cell[0]][neighbor_cell[1]] = current_cell_value + 1;
    
        return distance_field;
        
    def move_humans(self, zombie_distance_field):
        """
        Function that moves humans away from zombies, diagonal moves
        are allowed
        """
        #cells to run away from zombies are stored in runing_options list
        #it is two dimensional list. neighbor cells for each human will be stored in second dimension
        runing_options = [];
        index=0;
        
        #iterating human coordinate
        for human_coordinate in self._human_list:
            temporary_list = [];
            #finding neighbor cells of human_coordinate
            for neighbor in poc_grid.Grid.eight_neighbors(self, human_coordinate[0], human_coordinate[1]):
                #if cell is not an obstacle it can be visitable then add this cell to temporary list
                if zombie_distance_field[neighbor[0]][neighbor[1]] != poc_grid.Grid.get_grid_height(self) * poc_grid.Grid.get_grid_width(self):
                    temporary_list.append(neighbor);
            #appending temporary list to real list
            runing_options.append(temporary_list);
            #appending current coordinate of human. because staying in the same position can be best
            runing_options[index].append(human_coordinate);
            index += 1;
        
        #print runing_options
        
        #best cell(s) to run away from zombie(s) is decided
        temp_human_list = [];
        new_human_list = [];
        
        #iterating running options of each human and finding
        #best cells to run away from zombies
        for each_human in runing_options:
            max_value = -1;
            for each_grid in each_human:
                distance_value = zombie_distance_field[each_grid[0]][each_grid[1]];
                #try to find maximum value of grid that's why we compare 
                #value of current grid and max_value
                if(max_value < distance_value):
                    max_value = distance_value;
                    del temp_human_list[:]
                    temp_human_list.append((each_grid[0],each_grid[1]));
                elif(max_value == distance_value):
                    del temp_human_list[:]
                    temp_human_list.append((each_grid[0],each_grid[1]));
            #after finding best options for each human, append it to real list
            for human_move in temp_human_list:
                new_human_list.append((human_move[0],human_move[1]));
        
        self._human_list = new_human_list[:];
        
        
        
    def move_zombies(self, human_distance_field):
        """
        Function that moves zombies towards humans, no diagonal moves
        are allowed
        """
        runing_options = [];
        index=0;
        for zombie_coordinate in self._zombie_list:
            temporary_list = [];
            for neighbor in poc_grid.Grid.four_neighbors(self, zombie_coordinate[0], zombie_coordinate[1]):
                if human_distance_field[neighbor[0]][neighbor[1]] != poc_grid.Grid.get_grid_height(self) * poc_grid.Grid.get_grid_width(self):
                    temporary_list.append(neighbor);
            runing_options.append(temporary_list);
            runing_options[index].append(zombie_coordinate);
            index += 1;
        
        
        temp_zombie_list = [];
        new_zombie_list = [];
        for each_zombie in runing_options:
            min_value = (each_zombie[0][0],each_zombie[0][1]);
            for each_grid in each_zombie:
                distance_value = human_distance_field[each_grid[0]][each_grid[1]];
                if(min_value > distance_value):
                    min_value = distance_value;
                    del temp_zombie_list[:]
                    temp_zombie_list.append((each_grid[0],each_grid[1]));
                elif(min_value == distance_value):
                    del temp_zombie_list[:]
                    temp_zombie_list.append((each_grid[0],each_grid[1]));
            for zombie_move in temp_zombie_list:
                new_zombie_list.append((zombie_move[0],zombie_move[1]));
        
        self._zombie_list = new_zombie_list[:];
              

# Start up gui for simulation - You will need to write some code above
# before this will work without errors

#poc_zombie_gui.run_gui(Apocalypse(30, 40))

