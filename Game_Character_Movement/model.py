import controller, sys
import model   # Pass a reference to model to each update call inside update_all
import copy
#Use the reference to this module to pass it to update methods

from ball      import Ball
from floater   import Floater
from blackhole import Black_Hole
from pulsator  import Pulsator
from hunter    import Hunter
from prey import Prey
from special import Special
from simulton import Simulton


# Global variables: declare them global in functions that assign to them: e.g., ... = or +=
running     = False
cycle_count = 0
balls       = set()


#return a 2-tuple of the width and height of the canvas (defined in the controller)
def world():
    return (controller.the_canvas.winfo_width(),controller.the_canvas.winfo_height())

#reset all module variables to represent an empty/stopped simulation
def reset ():
    global running,cycle_count,balls
    running     = False
    cycle_count = 0
    balls       = set()


#start running the simulation
def start ():
    global running
    running = True


#stop running the simulation (freezing it)
def stop ():
    global running
    running = False

#tep just one update in the simulation
def step ():
    global cycle_count
    cycle_count += 1
    for b in list(balls):
        if isinstance(b, Black_Hole):
            remove_set = b.update(find(Ball))
            for ball in remove_set:
                remove(ball)
        else:
            b.update()


#remember the kind of object to add to the simulation when an (x,y) coordinate in the canvas
#  is clicked next (or remember to remove an object by such a click)   
def select_object(kind):
    global typ
    if kind == 'Ball':
        typ = 'Ball'
    elif kind == 'Remove':
        typ = 'Remove'
    elif kind == 'Floater':
        typ = 'Floater'
    elif kind == 'Black_Hole':
        typ = 'Black_Hole'
    elif kind == 'Pulsator':
        typ = 'Pulsator'
    elif kind == 'Hunter':
        typ = 'Hunter'
    elif kind == 'Special':
        typ = 'Special'

#add the kind of remembered object to the simulation (or remove all objects that contain the
#  clicked (x,y) coordinate
def mouse_click(x,y):
    global typ
    if typ == 'Remove':
        ball_inside = False
        for ball in balls:
            if ((ball._x - ball.radius) <= x <= (ball._x + ball.radius)) and ((ball._y - ball.radius) <= y <= (ball._y + ball.radius)):
                ball_inside = True
                b = ball
        if ball_inside == True:
            remove(b)
    else:
        b = eval(typ + '(' + str(x) + ',' + str(y) + ')')
        add(b)

#add simulton s to the simulation
def add(s):
    balls.add(s)
    

# remove simulton s from the simulation    
def remove(s):
    balls.remove(s)
    

#find/return a set of simultons that each satisfy predicate p    
def find(p):
    final_set = set()
    for ball in balls:
        if isinstance(ball, p):
            final_set.add(ball)
    return final_set


#call update() for all simultons in the simulation (pass model as its argument)
#this function should loop over one set containing all the simultons
#  and should not call type or isinstance: let each simulton do the
#  right thing for itself, without this function knowing what kinds of
#  simultons are in the simulation
def update_all():
    global cycle_count
    if running:
        cycle_count += 1
        balls_copy = copy.deepcopy(balls)
        for b in list(balls):
            if isinstance(b, Black_Hole):
                if isinstance(b, Special):
                    test_lst = find(Simulton)
                    kill_lst = find(Prey)
                    test_lst.remove(b)
                    remove_set = b.update(test_lst)
                    kill = False
                    for ball in kill_lst:
                        if b.radius >= b.distance((ball._x, ball._y)):
                            kill = True
                            balls.remove(b)
                    if kill == False: 
                        for ball in remove_set:
                            remove(ball)
                elif isinstance(b, Hunter):
                    remove_set = b.update(find(Prey))
                    if len(remove_set) > 0:
                        remove_set = b.update(find(Prey))
                        for ball in remove_set:
                            remove(ball)
                    remove_set = b.update(find(Prey))
                    for ball in remove_set:
                        remove(ball)
                elif isinstance(b, Pulsator):
                    if b.radius == 0:
                        remove(b)
                    if b in balls:
                        remove_set = b.update(find(Prey))
                        for ball in remove_set:
                            remove(ball)
                else:
                    remove_set = b.update(find(Prey))
                    for ball in remove_set:
                        remove(ball)
            else:
                b.update()

#delete from the canvas all simultons being simulated; then call display on each
#  simulton being simulated to add it back to the canvas, possibly in a new location, to
#  animate it; also, update the progress label defined in the controller
#this function should loop over one set containing all the simultons
#  and should not call type or isinstance: let each simulton do the
#  right thing for itself, without this function knowing what kinds of
#  simultons are in the simulation
def display_all():
    for o in controller.the_canvas.find_all():
        controller.the_canvas.delete(o)
    
    for b in balls:
        b.display(controller.the_canvas)
    
    controller.the_progress.config(text=str(str(cycle_count)+" cycles/" + str(len(balls)))+" Simultons")

