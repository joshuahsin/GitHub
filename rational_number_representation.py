# Submitter: jhsin1(Hsin, Joshua)
from goody import irange, type_as_str
import math
import decimal

class Rational:
    @staticmethod
    # Called as Rational._gcd(...); no self parameter
    # Helper method computes the Greatest Common Divisor of x and y
    def _gcd(x : int, y : int) -> int:
        assert type(x) is int and type(y) is int and x >= 0 and y >= 0,\
          'Rational._gcd: x('+str(x)+') and y('+str(y)+') must be integers >= 0'
        while y != 0:
            x, y = y, x % y
        return x
    
    @staticmethod
    # Called as Rational._validate_arithmetic(..); static, so no self parameter
    # Helper method raises exception with appropriate message if type(v) is not
    #   in the set of types t; the message includes the values of the strings
    #   op (operator), lt (left type) and rt (right type)
    # An example call (from my __add__ method), which checks whether the type of
    #   right is a Rational or int is...
    # Rational._validate_arithmetic(right, {Rational,int},'+','Rational',type_as_str(right))
    def _validate_arithmetic(v : object, t : {type}, op : str, left_type : str, right_type : str):
        if type(v) not in t:
            raise TypeError('unsupported operand type(s) for '+op+
                            ': \''+left_type+'\' and \''+right_type+'\'')        

    @staticmethod
    # Called as Rational._validate_relational(..); static, so no self parameter
    # Helper method raises exception with appropriate message if type(v) is not
    #   in the set of types t; the message includes the values of the strings
    #   op (operator), and rt (right type)
    def _validate_relational(v : object, t : {type}, op : str, right_type : str):
        if type(v) not in t:
            raise TypeError('unorderable types: '+
                            'Rational() '+op+' '+right_type+'()') 
                   

   # Put all other methods here
    def __init__(self, numerator, denominator):
        try:
            negative = False
            if int(numerator) == 0:
                _denominator = 1
            elif (int(denominator) < 0 and int(numerator) < 0) or (int(denominator) < 0 and int(numerator) > 0):
                if int(denominator) < 0 and int(numerator) > 0: 
                    negative = True
                numerator = numerator * -1
                denominator = denominator * -1
            else:
                numerator = numerator
                denominator = denominator
            if numerator < 0:
                negative = True
            gcd = Rational._gcd(abs(numerator), abs(denominator))
            if gcd != 1:
                if negative == True:
                    self._numerator = int(numerator / gcd)
                    self._denominator = int(denominator / gcd)
                else:
                    self._numerator = int(numerator / gcd)
                    self._denominator = int(denominator / gcd)
            else:
                self._numerator = numerator
                self._denominator = denominator
            if int(denominator) == 0:
                raise AssertionError('Rational.__init__ denominator cannot be zero')
        except TypeError:
            raise AssertionError('Rational.__init__ numerator and denominator must be of type int')
        
    def __str__(self):
        return str(self._numerator) + '/' + str(self._denominator)
    
    def __repr__(self):
        return 'Rational(' + str(self._numerator) + ',' + str(self._denominator) + ')'
    
    def __bool__(self):
        if self._numerator == 0 and self._denominator == 1:
            return False
        else:
            return True

    def __getitem__(self, index):
        if type(index) == int:
            if index == 0:
                return self._numerator
            elif index == 1:
                return self._denominator
            else:
                raise TypeError('Must be int of 0 or 1')
        elif type(index) == str:
            index = index.lower()
            print(index)
            a = 'numerator'
            b = 'denominator'
            numerator = None
            denominator = None
            if index.startswith('n'):
                numerator = True
                if index == a[:len(index)]:
                    pass
                else:
                    numerator = False
            elif index.startswith('d'):
                denominator = True
                if index == b[:len(index)]:
                    pass
                else:
                    numerator = False
            else:
                raise TypeError('Prefix must be numerator or denominator (case doesn\'t matter) not', index)
            
            if numerator != None:
                if numerator == True:
                    return self._numerator
                elif numerator == False:
                    raise TypeError('Prefix must be numerator or denominator (case doesn\'t matter) not', index)
            if denominator != None:
                if denominator == True:
                    return self._denominator
                elif denominator == False:
                    raise TypeError('Prefix must be numerator or denominator (case doesn\'t matter) not', index)
                
    def __pos__(self):
        return Rational(self._numerator, self._denominator)
    
    def __neg__(self):
        new_numerator = self._numerator * -1
        return Rational(new_numerator, self._denominator)
    
    def __abs__(self):
        new_numerator = int(math.fabs(self._numerator))
        new_denominator = int(math.fabs(self._denominator))
        return Rational(new_numerator, new_denominator)
    
    def __add__(self, second_element):
        Rational._validate_arithmetic(self, {int, Rational}, '+', 'Rational, int', 'Rational, int')
        if type(second_element) == Rational:
            if second_element._denominator == self._denominator:
                final_numerator = second_element._numerator + self._numerator
                return Rational(final_numerator, self._denominator)
            else:
                denom1 = self._denominator 
                denom2 = second_element._denominator
                second_numerator = second_element._numerator * denom1
                second_denominator = second_element._denominator * denom1
                numerator = self._numerator * denom2
                denominator = self._denominator * denom2
                final_numerator = numerator + second_numerator
                a = Rational(final_numerator, denominator)
                return a
        elif type(second_element) == int:
            a = Rational(second_element, 1)
            denom1 = self._denominator 
            denom2 = a._denominator
            second_numerator = a._numerator * denom1
            second_denominator = a._denominator * denom1
            numerator = self._numerator * denom2
            denominator = self._denominator * denom2
            final_numerator = numerator + second_numerator
            a = Rational(final_numerator, denominator)
            return a
        else:
            raise TypeError('Both elements need to be of type Rational or type int, not type', type_as_str(second_element))
            
    def __radd__(self, second_element):
        Rational._validate_arithmetic(self, {int, Rational}, '+', 'Rational, int', 'Rational, int')
        if type(second_element) == Rational:
            if second_element._denominator == self._denominator:
                final_numerator = second_element._numerator + self._numerator
                return Rational(final_numerator, self._denominator)
            else:
                denom1 = self._denominator 
                denom2 = second_element._denominator
                second_numerator = second_element._numerator * denom1
                second_denominator = second_element._denominator * denom1
                numerator = self._numerator * denom2
                denominator = self._denominator * denom2
                final_numerator = numerator + second_numerator
                a = Rational(final_numerator, denominator)
                return a
        elif type(second_element) == int:
            a = Rational(second_element, 1)
            denom1 = self._denominator 
            denom2 = a._denominator
            second_numerator = a._numerator * denom1
            second_denominator = a._denominator * denom1
            numerator = self._numerator * denom2
            denominator = self._denominator * denom2
            final_numerator = numerator + second_numerator
            a = Rational(final_numerator, denominator)
            return a
        else:
            raise TypeError('Both elements need to be of type Rational or type int, not type', type_as_str(second_element))
            
    def __sub__(self, second_element):
        Rational._validate_arithmetic(self, {int, Rational}, '-', 'Rational, int', 'Rational, int')
        if type(second_element) == Rational:
            if second_element._denominator == self._denominator:
                final_numerator = self._numerator - second_element._numerator
                return Rational(final_numerator, self._denominator)
            else:
                denom1 = self._denominator 
                denom2 = second_element._denominator
                second_numerator = second_element._numerator * denom1
                second_denominator = second_element._denominator * denom1
                numerator = self._numerator * denom2
                denominator = self._denominator * denom2
                final_numerator = numerator - second_numerator
                a = Rational(final_numerator, denominator)
                return a
        elif type(second_element) == int:
            a = Rational(second_element, 1)
            denom1 = self._denominator 
            denom2 = a._denominator
            second_numerator = a._numerator * denom1
            second_denominator = a._denominator * denom1
            numerator = self._numerator * denom2
            denominator = self._denominator * denom2
            final_numerator = numerator - second_numerator
            a = Rational(final_numerator, self._denominator)
            return a
        else:
            raise TypeError('Both elements need to be of type Rational or type int, not type', type_as_str(second_element))
        
    def __rsub__(self, second_element):
        Rational._validate_arithmetic(self, {int, Rational}, '-', 'Rational, int', 'Rational, int')
        if type(second_element) == Rational:
            if second_element._denominator == self._denominator:
                final_numerator = self._numerator - second_element._numerator
                return Rational(final_numerator, self._denominator)
            else:
                denom1 = self._denominator 
                denom2 = second_element._denominator
                second_numerator = second_element._numerator * denom1
                second_denominator = second_element._denominator * denom1
                numerator = self._numerator * denom2
                denominator = self._denominator * denom2
                final_numerator = numerator - second_numerator
                a = Rational(final_numerator, self._denominator)
                return a
        elif type(second_element) == int:
            a = Rational(second_element, 1)
            denom1 = self._denominator 
            denom2 = a._denominator
            second_numerator = a._numerator * denom1
            second_denominator = a._denominator * denom1
            numerator = denom2
            denominator = denom2
            final_numerator = second_numerator - numerator
            a = Rational(final_numerator, self._denominator)
            return a
        else:
            raise TypeError('Both elements need to be of type Rational or type int, not type', type_as_str(second_element))
        
    def __mul__(self, second_element):
        Rational._validate_arithmetic(self, {int, Rational}, '*', 'Rational, int', 'Rational, int')
        if type(second_element) == Rational:
            final_denom = self._denominator * second_element._denominator
            final_num = self._numerator * second_element._numerator
            return Rational(final_num, final_denom)
        elif type(second_element) == int:
            final_num = self._numerator * second_element
            return Rational(final_num, self._denominator)
        else:
            raise TypeError('Both elements need to be of type Rational or type int, not type', type_as_str(second_element))
        
    def __rmul__(self, second_element):
        Rational._validate_arithmetic(self, {int, Rational}, '*', 'Rational, int', 'Rational, int')
        if type(second_element) == Rational:
            final_denom = self._denominator * second_element._denominator
            final_num = self._numerator * second_element._numerator
            return Rational(final_num, final_denom)
        elif type(second_element) == int:
            final_num = self._numerator * second_element
            return Rational(final_num, self._denominator)
        else:
            raise TypeError('Both elements need to be of type Rational or type int, not type', type_as_str(second_element))
        
    def __truediv__(self, second_element):
        if type(second_element) == Rational:
            final_num = self._numerator * second_element._denominator
            final_denom = self._denominator * second_element._numerator
            return Rational(final_num, final_denom)
        elif type(second_element) == int:
            final_denom = self._denominator * second_element
            return Rational(self._numerator, final_denom)
        else:
            raise TypeError('Both elements need to be of type Rational or type int, not type', type_as_str(second_element))
        
    def __rtruediv__(self, second_element):
        if type(second_element) == Rational:
            final_num = self._numerator * second_element._denominator
            final_denom = self._denominator * second_element._numerator
            return Rational(final_num, final_denom)
        elif type(second_element) == int:
            final_denom = self._denominator * second_element
            return Rational(final_denom, self._numerator)
        else:
            raise TypeError('Both elements need to be of type Rational or type int, not type', type_as_str(second_element))
        
    def __pow__(self, power):
        if type(power) == int:
            if int(power) > 0:
                final_num = int(self._numerator ** power)
                final_denom = int(self._denominator ** power)
                return Rational(final_num, final_denom)
            elif int(power) < 0:
                power = power * -1
                final_num = int(self._denominator ** power)
                final_denom = int(self._numerator ** power)
                return Rational(final_num, final_denom)
            elif int(power) == 0:
                return Rational(1, 1)
        else:
            raise TypeError('Element needs to be of type int, not type', type(power))
        
    def __eq__(self, second_element):
        if type(second_element) == Rational:
            if self._numerator == second_element._numerator and self._denominator == second_element._denominator:
                return True
            else:
                return False
        elif type(second_element) == int:
            second = Rational(second_element, 1)
            if self._numerator == second._numerator and self._denominator == second._denominator:
                return True
            else:
                return False
        else:
            raise TypeError('Element that is being compared must be of type Rational or int, not', type_as_str(second_element))
    
    def __lt__(self, second_element):
        Rational._validate_relational(self, {int, Rational}, '<', 'int, Rational')
        if type(second_element) == Rational:
            first_num = self._numerator / self._denominator
            second_num = second_element._numerator / second_element._denominator
            if first_num < second_num:
                return True
            else:
                return False
        elif type(second_element) == int:
            first_num = self._numerator / self._denominator
            if first_num < float(second_element):
                return True
            else:
                return False
        else:
            raise TypeError('Must compare types int or Rational, not', type_as_str(second_element))
        
    def __le__(self, second_element):
        Rational._validate_relational(self, {int, Rational}, '<', 'int, Rational')
        if type(second_element) == Rational:
            first_num = self._numerator / self._denominator
            second_num = second_element._numerator / second_element._denominator
            if first_num <= second_num:
                return True
            else:
                return False
        elif type(second_element) == int:
            first_num = self._numerator / self._denominator
            if first_num <= float(second_element):
                return True
            else:
                return False
        else:
            raise TypeError('Must compare types int or Rational, not', type_as_str(second_element))
        
    def __gt__(self, second_element):
        Rational._validate_relational(self, {int, Rational}, '<', 'int, Rational')
        if type(second_element) == Rational:
            first_num = self._numerator / self._denominator
            second_num = second_element._numerator / second_element._denominator
            if first_num > second_num:
                return True
            else:
                return False
        elif type(second_element) == int:
            first_num = self._numerator / self._denominator
            if first_num > float(second_element):
                return True
            else:
                return False
        else:
            raise TypeError('Must compare types int or Rational, not', type_as_str(second_element))
        
    def __ge__(self, second_element):
        Rational._validate_relational(self, {int, Rational}, '<', 'int, Rational')
        if type(second_element) == Rational:
            first_num = self._numerator / self._denominator
            second_num = second_element._numerator / second_element._denominator
            if first_num >= second_num:
                return True
            else:
                return False
        elif type(second_element) == int:
            first_num = self._numerator / self._denominator
            if first_num >= float(second_element):
                return True
            else:
                return False
        else:
            raise TypeError('Must compare types int or Rational, not', type_as_str(second_element))
        
    def __call__(self, places):
        final_str = ''
        a = self.__str__()
        negative_symbol = None
        if a[0] == '-':
            negative_symbol = a.pop(0)
        integer = self._numerator // self._denominator
        remainder = self._numerator - (integer * self._denominator)
        decimal = '.'
        new_num = 0
        for element in range(places):
            if element == 0:
                new_num = int(str(remainder) + '0')
                if new_num > self._denominator:
                    subtract_factor = new_num // self._denominator
                    new_num = new_num % self._denominator
                    #new_num = new_num - (subtract_factor * self._denominator)
                    decimal += str(subtract_factor)
                else:
                    decimal += '0'
            else:
                new_num = int(str(new_num) + '0')
                if new_num > self._denominator:
                    subtract_factor = new_num // self._denominator
                    #new_num = new_num - (subtract_factor * self._denominator)
                    new_num = new_num % self._denominator
                    decimal += str(subtract_factor)
                else:
                    decimal += '0'
                    
        if negative_symbol != None:
            return negative_symbol + str(integer) + decimal
        else:
            return str(integer) + decimal

    def __setattr__(self, name, value):
        if name == '_numerator' or name == '_denominator':
            if name not in self.__dict__:
                self.__dict__[name] = value
            elif name in self.__dict__:
                raise NameError
        else:
            raise NameError

    
# e ~ 1/0! + 1/1! + 1/2! + 1/3! + ... + 1/n!
def compute_e(n):
    answer = Rational(1)
    for i in irange(1,n):
        answer += Rational(1,math.factorial(i))
    return answer

# Newton: pi = 6*arcsin(1/2); see the arcsin series at http://mathforum.org/library/drmath/view/54137.html
# Check your results at http://www.geom.uiuc.edu/~huberty/math5337/groupe/digits.html
#   also see http://www.numberworld.org/misc_runs/pi-5t/details.html
def compute_pi(n):
    def prod(r):
        answer = 1
        for i in r:
            answer *= i
        return answer
    
    answer = Rational(1,2)
    x      = Rational(1,2)
    for i in irange(1,n):
        big = 2*i+1
        answer += Rational(prod(range(1,big,2)),prod(range(2,big,2)))*x**big/big       
    return 6*answer


if __name__ == '__main__':
    #Run simple tests before running driver

    x = Rational(8,29) 
    print(x+x)
    print(2*x)
    print(x(30))
    
    print()
    import driver    
    driver.default_file_name = 'bscp22S19.txt'
#     driver.default_show_exception=True
#     driver.default_show_exception_message=True
#     driver.default_show_traceback=True
    driver.driver()
