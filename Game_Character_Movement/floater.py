# A Floater is Prey; it updates by moving mostly in
#   a straight line, but with random changes to its
#   angle and speed, and displays as ufo.gif (whose
#   dimensions (width and height) are computed by
#   calling .width()/.height() on the PhotoImage


#from PIL.ImageTk import PhotoImage
from prey import Prey
import random


class Floater(Prey): 
    radius = 5
    def __init__(self,x,y): 
        self.set_speed(5)
        self.randomize_angle()
        Prey.__init__(self,x,y,10,10,self._angle,self._speed)
        
    def update(self):
        x = random.random()
        if x <= 0.29:
            speed_change = random.uniform(-0.5, 0.5)
            if (self._speed + speed_change < 3) or (self._speed + speed_change > 7):
                while (self._speed + speed_change < 3) or (self._speed + speed_change > 7):
                    speed_change = random.uniform(-0.5, 0.5)
            self._speed += speed_change
            self._angle += random.uniform(-0.5, 0.5)
        self.move()
        
    def display(self, canvas):
        canvas.create_oval(self._x-Floater.radius      , self._y-Floater.radius,
                                self._x+Floater.radius, self._y+Floater.radius,
                                fill='red')
