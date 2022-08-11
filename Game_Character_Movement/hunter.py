# Hunter is derived from each of the Mobile_Simulton and Pulsator classes:
#   updating/displaying like a Pulsator, but also moving (either in a straight
#   line or in pursuit of Prey).


from prey import Prey
from pulsator import Pulsator
from mobilesimulton import Mobile_Simulton
from math import atan2


class Hunter(Pulsator, Mobile_Simulton):
    def __init__(self,x,y):
        self.set_speed(5)
        self.randomize_angle()
        Mobile_Simulton.__init__(self,x,y,20,20,self._angle,self._speed)
        
    def update(self, test_set):
        self.move()
        for b in list(test_set):
            if self.distance((b._x, b._y)) > 200:
                test_set.remove(b)
        if len(test_set) != 0:
            closest = sorted(test_set, key = lambda b: self.distance((b._x, b._y)))[0]
            self.set_angle(atan2(closest._y - self._y, closest._x - self._x))
        remove_set = set()
        for ball in test_set:
            if self.contains(ball):
                remove_set.add(ball)
        return remove_set
            
        
