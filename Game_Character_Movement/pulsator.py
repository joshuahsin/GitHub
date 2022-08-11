# A Pulsator is a Black_Hole; it updates as a Black_Hole
#   does, but also by growing/shrinking depending on
#   whether or not it eats Prey (and removing itself from
#   the simulation if its dimension becomes 0), and displays
#   as a Black_Hole but with varying dimensions


from blackhole import Black_Hole

class Pulsator(Black_Hole): 
    def __init__(self, x, y):
        Black_Hole.__init__(self,x,y)
        self._counter = 0
        
    def update(self, test_set):
        self._counter += 1
        remove_set = set()
        for ball in test_set:
            if self.contains(ball):
                remove_set.add(ball)
        if len(remove_set) != 0:
            self.radius += 0.5
            self.change_dimension(1,1)
            self._counter = 0
        elif self._counter == 30:
            self.radius -= 5
            self.change_dimension(-1,-1)
            self._counter = 0
        return remove_set

