"""
Cookie Clicker Simulator
"""

import poc_clicker_provided as provided

# Constants
SIM_TIME = 10000000000.0

class ClickerState:
    """
    Simple class to keep track of the game state.
    """
    
    
    def __init__(self):
        self._total_cookies = 0.0;
        self._current_cookies = 0.0;
        self._current_time = 0.0;
        self._current_cps = 1.0;
        self._history = [(0.0, None, 0.0, 0.0)];
        
    def __str__(self):
        """
        Return human readable state
        """
        return "Time: %s Current Cookies: %s CPS: %s Total Cookies: %s History: (length: %s): %s" % (self.get_time(), self.get_cookies(), self.get_cps(), self._total_cookies, len(self.get_history()), self.get_history());
        
        
    def get_cookies(self):
        """
        Return current number of cookies 
        (not total number of cookies)
        
        Should return a float
        """
        return float(self._current_cookies);
    
    def get_cps(self):
        """
        Get current CPS

        Should return a float
        """
        return float(self._current_cps);
    
    def get_time(self):
        """
        Get current time

        Should return a float
        """
        return float(self._current_time);
    
    def get_history(self):
        """
        Return history list

        History list should be a list of tuples of the form:
        (time, item, cost of item, total cookies)

        For example: [(0.0, None, 0.0, 0.0)]

        Should return a copy of any internal data structures,
        so that they will not be modified outside of the class.
        """
        local_history = self._history[:];
        return local_history;

    def time_until(self, cookies):
        """
        Return time until you have the given number of cookies
        (could be 0.0 if you already have enough cookies)

        Should return a float with no fractional part
        """

        #necessary time is calculated
        time = (cookies - self.get_cookies()) / self.get_cps();

        #if current_cookies greater than cookies, in this case we already have enough cookies
        if(self.get_cookies() >= cookies):
            return 0.0

        if(time < 1.0 and time >= 0.0):
            return 1;

        #checks whether fractional part of the time variable is zero
        #if it is, it returns it
        if(time / int(time) == 1.0):
            return float(time);

        #if its fractional part is not zero. we cannot return for fractional time
        #time is incremented by 1 and returned int that way
        else:
            time = int(time)+1;
            return float(time);
        #52.60869 ~ 53.0
    
    def wait(self, time):
        """
        Wait for given amount of time and update state
        Should do nothing if time <= 0.0
        """
        #if there is no time nothing to do
        if(time == 0.0 or time < 0.0):
            return;
            
        #increasing total, current time and cookies
        self._current_time += time;
        self._total_cookies += self.get_cps() * time
        self._current_cookies += self.get_cps() * time;
    
    def buy_item(self, item_name, cost, additional_cps):
        """
        Buy an item and update state
        Should do nothing if you cannot afford the item
        """
        
        #!!!!!DiKKAT!!!!!!
        #cost'un ayri bir parametre olarak belirlenmesi bir kerede
        #birden fazla item satin alinirken ortaya cikan maliyet olabilir

        cookies = self.get_cookies();
        
        #if cost of the item is greater than current cookies 
        #there is nothing to do
        if(cost > cookies):
            #self.history.append(tuple(_current_time, None, itemCost, self.get_cookies()));
            return;

        self._current_cookies -= cost;
        self._current_cps += additional_cps;
        self._history.append((self.get_time(), item_name, cost, self._total_cookies));


def simulate_clicker(build_info, duration, strategy):
    """
    Function to run a Cookie Clicker game for the given
    duration with the given strategy.  Returns a clicker_state
    object corresponding to the final state of the game.
    """
    #creating clone of BuildInfo and a new clicker_state
    local_bi = build_info.clone();
    clicker_state = ClickerState();
    
    #setting current time from clicker_state object
    _current_time = clicker_state.get_time();
    
    while(_current_time < duration):
        #if current time has passed duration simulation ends
        if(clicker_state > duration):
            break;
            
        #selected strategy method return name of the item that will be purchased
        item_name = strategy(clicker_state.get_cookies(), clicker_state.get_cps(), clicker_state.get_history(), duration - _current_time, local_bi);

        #if strategy function return None, no item will be purchased
        if(item_name == None):
            clicker_state.wait(1);
            _current_time = clicker_state.get_time();
            continue;

        #cost of the item and cps
        item_cost = local_bi.get_cost(item_name);
        item_cps = local_bi.get_cps(item_name);

        #required time to store enough cookies in order to buy the item
        required_time = clicker_state.time_until(item_cost);

        #in order to prevent passing duration time, we check whether the time pass duration time
        if(clicker_state.get_time() + required_time > duration):
            remaining_time = duration - clicker_state.get_time();
            if(remaining_time > 0):
                clicker_state.wait(remaining_time);
            else:
                break;
        else:
            #it will be waited until having enough cookie to purchase the item
            #wait() function will increase current_cookies, totalCookies and _current_time
            clicker_state.wait(required_time);

            #updating current time
            _current_time = clicker_state.get_time();

            #calculating how many times the item will be buyed
            times_buy = int(clicker_state.get_cookies() / item_cost);
            
            index=0;
            while(index < times_buy):
                #in every iteration we update cost of the item by the new one
                item_cost = local_bi.get_cost(item_name);
                #buying the item and updating cost of the item
                clicker_state.buy_item(item_name,item_cost,item_cps);
                local_bi.update_item(item_name);
                index += 1;
                
    return clicker_state;


def strategy_cursor_broken(cookies, cps, history, time_left, build_info):
    """
    Always pick Cursor!

    Note that this simplistic (and broken) strategy does not properly
    check whether it can actually buy a Cursor in the time left.  Your
    simulate_clicker function must be able to deal with such broken
    strategies.  Further, your strategy functions must correctly check
    if you can buy the item in the time left and return None if you
    can't.
    """
    return "Cursor"

def strategy_none(cookies, cps, history, time_left, build_info):
    """
    Always return None

    This is a pointless strategy that will never buy anything, but
    that you can use to help debug your simulate_clicker function.
    """
    return None

def strategy_cheap(cookies, cps, history, time_left, build_info):
    """
    Always buy the cheapest item you can afford in the time left.
    """
    #setting first element of the dictionary as the cheapest item
    item_list = build_info.build_items();
    cheapest_item_name = item_list[0];
    cheapest_item_cps = build_info.get_cps(cheapest_item_name);

    #iterating over the dictionary to find cheapest one
    for item in item_list:
        if(cheapest_item_cps > build_info.get_cps(item)):
            cheapest_item_cps = build_info.get_cps(item);
            cheapest_item_name = item;

    if(cookies + cps * time_left >= build_info.get_cost(cheapest_item_name)):
        return cheapest_item_name;
    else:
        return None;

def strategy_expensive(cookies, cps, history, time_left, build_info):
    """
    Always buy the most expensive item you can afford in the time left.
    """
    #setting first element of the dictionary as the most expensive item
    item_list = build_info.build_items();

    upper_limit = cookies + cps * time_left;

    expensive_item_name = "";
    expensive_item_cost = 0.0;

    #iterating over the dictionary to find most expensive one
    for item in item_list:
        if(upper_limit >= build_info.get_cost(item)):
            if(expensive_item_cost < build_info.get_cost(item)):
                expensive_item_cost = build_info.get_cost(item);
                expensive_item_name = item;
           
    if(expensive_item_cost == 0.0):
        return None;
    else:
        return expensive_item_name;
    
    
def strategy_best(cookies, cps, history, time_left, build_info):
    """
    The best strategy that you are able to implement.
    """
    return None
        
def run_strategy(strategy_name, time, strategy):
    """
    Run a simulation for the given time with one strategy.
    """
    state = simulate_clicker(provided.BuildInfo(), time, strategy)
    print strategy_name, ":", state

    # Plot total cookies over time

    # Uncomment out the lines below to see a plot of total cookies vs. time
    # Be sure to allow popups, if you do want to see it

    # history = state.get_history()
    # history = [(item[0], item[3]) for item in history]
    # simpleplot.plot_lines(strategy_name, 1000, 400, 'Time', 'Total Cookies', [history], True)

def run():
    """
    Run the simulator.
    """    
    run_strategy("Cursor", SIM_TIME, strategy_cursor_broken)

    # Add calls to run_strategy to run additional strategies
    # run_strategy("Cheap", SIM_TIME, strategy_cheap)
    # run_strategy("Expensive", SIM_TIME, strategy_expensive)
    # run_strategy("Best", SIM_TIME, strategy_best)
    
run()
    


