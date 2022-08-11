# Black_Hole is derived from Simulton only, updating by finding+removing
#   any Prey whose center is contained within its radius
#  (returning a set of all eaten simultons), and
#   displays as a black circle with a radius of 10
#   (width/height 20).
# Calling get_dimension for the width/height (for
#   containment and displaying) will facilitate
#   inheritance in Pulsator and Hunter

from simulton import Simulton
from prey import Prey


class Black_Hole(Simulton):
    radius = 10
    def __init__(self, x, y):
        Simulton.__init__(self,x,y,20,20)
    
    def update(self,test_set):
        remove_set = set()
        for ball in test_set:
            if self.contains(ball):
                remove_set.add(ball)
        return remove_set
                
    def contains(self, ball):
        return self.radius >= self.distance((ball._x, ball._y))
        #return self._x - 10 <= ball._x <= self._x + 10 and self._y - 10 <= ball._y <= self._y + 10
    
    def display(self, canvas):
        canvas.create_oval(self._x-self.radius      , self._y-self.radius,
                                self._x+self.radius, self._y+self.radius,
                                fill='black')
        
        