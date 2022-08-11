#Pursues and kills Black_Hole objects except itself and is killed when touched by Ball objects
from blackhole import Black_Hole
from pulsator import Pulsator
from mobilesimulton import Mobile_Simulton
from math import atan2
from ball import Ball
import random

class Special(Pulsator, Mobile_Simulton): 
    def __init__(self,x,y):
        self.set_speed(10)
        self.randomize_angle()
        Mobile_Simulton.__init__(self,x,y,20,20,self._angle,self._speed)
        
    def update(self, test_set):
        self.move()
        for x in list(test_set):
            if isinstance(x, Special) or isinstance(x,Ball):
                test_set.remove(x)
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