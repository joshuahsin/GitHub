import SudokuBoard
import Variable
import Domain
import Trail
import Constraint
import ConstraintNetwork
import time
import random

class BTSolver:

    # ==================================================================
    # Constructors
    # ==================================================================

    def __init__ ( self, gb, trail, val_sh, var_sh, cc ):
        self.network = ConstraintNetwork.ConstraintNetwork(gb)
        self.hassolution = False
        self.gameboard = gb
        self.trail = trail

        self.varHeuristics = var_sh
        self.valHeuristics = val_sh
        self.cChecks = cc

    # ==================================================================
    # Consistency Checks
    # ==================================================================

    # Basic consistency check, no propagation done
    def assignmentsCheck ( self ):
        for c in self.network.getConstraints():
            if not c.isConsistent():
                return False
        return True

    """
        Part 1 TODO: Implement the Forward Checking Heuristic

        This function will do both Constraint Propagation and check
        the consistency of the network

        (1) If a variable is assigned then eliminate that value from
            the square's neighbors.

        Note: remember to trail.push variables before you assign them
        Return: a tuple of a dictionary and a bool. The dictionary contains all MODIFIED variables, mapped to their MODIFIED domain.
                The bool is true if assignment is consistent, false otherwise.
    """
    def forwardChecking ( self ):
        d = {}
        if(self.assignmentsCheck() == False):
            return (d, False)
        for i in self.network.variables:
            if i.isAssigned():
                for var in self.network.getNeighborsOfVariable(i):
                    if (i.getAssignment() in var.domain.values) and (var.isAssigned() == False):
                        self.trail.push(var)
                        var.removeValueFromDomain(i.getAssignment())
                        if(len(var.domain.values) == 0):
                            d.clear()
                            return (d, False)
                        if(len(var.domain.values) == 1):
                            var.assignValue(var.domain.values[0])
                        var.setModified(True)
                        self.arcConsistency()
                        d[var] = var.domain
        return (d, True)
    
    
    # =================================================================
	# Arc Consistency
	# =================================================================
    def arcConsistency( self ):
        assignedVars = []
        for c in self.network.constraints:
            for v in c.vars:
                if v.isAssigned():
                    assignedVars.append(v)
        while len(assignedVars) != 0:
            av = assignedVars.pop(0)
            for neighbor in self.network.getNeighborsOfVariable(av):
                if neighbor.isChangeable and not neighbor.isAssigned() and neighbor.getDomain().contains(av.getAssignment()):
                    self.trail.push(neighbor)
                    neighbor.removeValueFromDomain(av.getAssignment())
                    if neighbor.domain.size() == 1:
#                        self.trail.push(neighbor)
                        neighbor.setModified(True)
                        neighbor.assignValue(neighbor.domain.values[0])
                        assignedVars.append(neighbor)

    
    """
        Part 2 TODO: Implement both of Norvig's Heuristics

        This function will do both Constraint Propagation and check
        the consistency of the network

        (1) If a variable is assigned then eliminate that value from
            the square's neighbors.

        (2) If a constraint has only one possible place for a value
            then put the value there.

        Note: remember to trail.push variables before you assign them
        Return: a pair of a dictionary and a bool. The dictionary contains all variables 
		        that were ASSIGNED during the whole NorvigCheck propagation, and mapped to the values that they were assigned.
                The bool is true if assignment is consistent, false otherwise.
    """
    def norvigCheck ( self ):
        d = {}
        if(self.assignmentsCheck() == False):
            return (d, False)
        for i in self.network.variables:
            if i.isAssigned():
                for var in self.network.getNeighborsOfVariable(i):
                    if (i.getAssignment() in var.domain.values) and (var.isAssigned() == False):
                        self.trail.push(var)
                        var.removeValueFromDomain(i.getAssignment())
                        if(len(var.domain.values) == 0):
                            d.clear()
                            return (d, False)
                        #if(len(var.domain.values) == 1):
                            #var.assignValue(var.domain.values[0])
                            #d[var] = var.domain.values[0]
                        var.setModified(True)
        for i in range(len(self.gameboard.board)):
            start = i * len(self.gameboard.board[0]) - 1
            if(i == 0):
                start = 0
            end = start + len(self.gameboard.board[0]) - 1
            for j in range(start, end):
                counter = [0] * len(self.gameboard.board)
                for k in range(len(self.gameboard.board)):
                    for var in self.network.variables[j].domain.values:
                        counter[var - 1] += 1
            for j in range(len(counter)):
                if(counter[j] == 1):
                    for j in range(start, end):
                        var = self.network.variables[j]
                        if(j in var.domain.values):
                            self.trail.push(var)
                            var.assignValue(j)
                            d[var] = j
        return (d, True)

    """
         Optional TODO: Implement your own advanced Constraint Propagation

         Completing the three tourn heuristic will automatically enter
         your program into a tournament.
     """
    def getTournCC ( self ):
        return False

    # ==================================================================
    # Variable Selectors
    # ==================================================================

    # Basic variable selector, returns first unassigned variable
    def getfirstUnassignedVariable ( self ):
        for v in self.network.variables:
            if not v.isAssigned():
                return v
        return None

    """
        Part 1 TODO: Implement the Minimum Remaining Value Heuristic

        Return: The unassigned variable with the smallest domain
    """
    def getMRV ( self ):
        a = None
        for v in self.network.variables:
            if not v.isAssigned():
                a = v
                break
        for v in self.network.variables:
            if not v.isAssigned():
                if(len(v.domain.values) < len(a.domain.values)):
                    a = v
        return a

    """
        Part 2 TODO: Implement the Degree Heuristic

        Return: The unassigned variable with the most unassigned neighbors
    """
    '''
    def getDegree ( self ):
        returnvar = None
        mostUnassignedNeighbors = 0
        for var in self.network.variables:
            if(var.isAssigned == False):
                numUnsigned = 0
                for i in getNeighborsOfVariable(var):
                    if(i.isAssigned == False):
                        numUnsigned += 1
                if(numUnsigned > mostUnassignedNeighbors):
                    mostUnassignedNeighbors = numUnsigned
                    returnvar = var
        return returnvar
    '''
    """
        Part 2 TODO: Implement the Minimum Remaining Value Heuristic
                       with Degree Heuristic as a Tie Breaker

        Return: The unassigned variable with the smallest domain and affecting the  most unassigned neighbors.
                If there are multiple variables that have the same smallest domain with the same number of unassigned neighbors, add them to the list of Variables.
                If there is only one variable, return the list of size 1 containing that variable.
    """
    def MRVwithTieBreaker ( self ):
        a = []
        for v in self.network.variables:
            if(v.isAssigned() == False):
                a.append(v)
                break
        for v in self.network.variables:
            if(v.isAssigned() == False):
                if(len(v.domain.values) < len(a[0].domain.values)):
                    del a[:]
                    a.append(v)
                elif(len(v.domain.values) == len(a[0].domain.values)):
                    a.append(v)
        b = []
        mostUnassignedNeighbors = 0
        for var in a:
            if(var.isAssigned() == False):
                numUnsigned = 0
                for i in self.network.getNeighborsOfVariable(var):
                    if(i.isAssigned() == False):
                        numUnsigned += 1
                if(numUnsigned > mostUnassignedNeighbors):
                    del b[:]
                    b.append(var)
                    mostUnassignedNeighbors = numUnsigned
                elif(numUnsigned == mostUnassignedNeighbors):
                    b.append(var)
                    mostUnassignedNeighbors = numUnsigned
        return b

    """
         Optional TODO: Implement your own advanced Variable Heuristic

         Completing the three tourn heuristic will automatically enter
         your program into a tournament.
     """
    def getTournVar ( self ):
        return None

    # ==================================================================
    # Value Selectors
    # ==================================================================

    # Default Value Ordering
    def getValuesInOrder ( self, v ):
        values = v.domain.values
        return sorted( values )

    """
        Part 1 TODO: Implement the Least Constraining Value Heuristic

        The Least constraining value is the one that will knock the least
        values out of it's neighbors domain.

        Return: A list of v's domain sorted by the LCV heuristic
                The LCV is first and the MCV is last
    """
    def getValuesLCVOrder ( self, v ):
        d = {}
        x = list.copy(v.domain.values)
        for b in v.domain.values:
            valid_value = True
            conflicts = 0
            for a in self.network.getNeighborsOfVariable(v):
                if a.assigned and (len(a.domain.values) == 1):
                    if (a.domain.values[0] == b):
                        valid_value = False
                        x.remove(b)
                        break
                elif (b in a.domain.values):
                    conflicts += 1
            if(valid_value):
                d[b] = conflicts
        a = sorted(d.items(), key = lambda x: x[1])
        g = []
        for i in a:
            g.append(i[0])
        return g

    """
         Optional TODO: Implement your own advanced Value Heuristic

         Completing the three tourn heuristic will automatically enter
         your program into a tournament.
     """
    def getTournVal ( self, v ):
        return None

    # ==================================================================
    # Engine Functions
    # ==================================================================

    def solve ( self, time_left=600):
        if time_left <= 60:
            return -1

        start_time = time.time()
        if self.hassolution:
            return 0

        # Variable Selection
        v = self.selectNextVariable()

        # check if the assigment is complete
        if ( v == None ):
            # Success
            self.hassolution = True
            return 0

        # Attempt to assign a value
        for i in self.getNextValues( v ):

            # Store place in trail and push variable's state on trail
            self.trail.placeTrailMarker()
            self.trail.push( v )

            # Assign the value
            v.assignValue( i )

            # Propagate constraints, check consistency, recur
            if self.checkConsistency():
                elapsed_time = time.time() - start_time 
                new_start_time = time_left - elapsed_time
                if self.solve(time_left=new_start_time) == -1:
                    return -1
                
            # If this assignment succeeded, return
            if self.hassolution:
                return 0

            # Otherwise backtrack
            self.trail.undo()
        
        return 0

    def checkConsistency ( self ):
        if self.cChecks == "forwardChecking":
            return self.forwardChecking()[1]

        if self.cChecks == "norvigCheck":
            return self.norvigCheck()[1]

        if self.cChecks == "tournCC":
            return self.getTournCC()

        else:
            return self.assignmentsCheck()

    def selectNextVariable ( self ):
        if self.varHeuristics == "MinimumRemainingValue":
            return self.getMRV()

        if self.varHeuristics == "Degree":
            return self.getDegree()

        if self.varHeuristics == "MRVwithTieBreaker":
            return self.MRVwithTieBreaker()[0]

        if self.varHeuristics == "tournVar":
            return self.getTournVar()

        else:
            return self.getfirstUnassignedVariable()

    def getNextValues ( self, v ):
        if self.valHeuristics == "LeastConstrainingValue":
            return self.getValuesLCVOrder( v )

        if self.valHeuristics == "tournVal":
            return self.getTournVal( v )

        else:
            return self.getValuesInOrder( v )

    def getSolution ( self ):
        return self.network.toSudokuBoard(self.gameboard.p, self.gameboard.q)
