#Joshua Hsin
#ID 13651420
#project5_faller.py
import project5_model as main
import copy
import unittest
class faller():
    '''Representation of faller object'''
    def __init__(self, element1: str, element2: str, element3: str):
        '''Initialize faller object with brackets'''
        self._lst = ['[' + element1 + ']', '[' + element2 + ']', '[' + element3 + ']']

    def rotate(self):
        '''Rotate faller object'''
        self._lst = [self._lst[2], self._lst[0], self._lst[1]]

    def freeze(self):
        '''Freeze faller objects by changing brackets to lines'''
        self._lst = ['|' + self._lst[0][1] + '|', '|' + self._lst[1][1] + '|', '|' + self._lst[2][1] + '|']

def update_faller(board: [[]], column: int, faller: faller) -> [[]] and int:
    '''Updates board after introducing faller'''
    board[column][0] = faller[-1]
    return board, column

def freeze_faller(board: [[]], faller: faller) -> bool and [[]]:
    '''Freezes faller on board and faller object'''
    faller.freeze()
    for column in board:
        for element in range(len(column)):
            if '[' in column[element]:
                column[element] = '|' + column[element][1] + '|'
    return True, board

def resolve_faller(board: [[]]) -> [[]]:
    '''Resolves faller after reaching bottom and before introducing new faller'''
    for column in board:
        for element in range(len(column)):
            if '|' in column[element]:
                column[element] = ' ' + column[element][1] + ' '
    return board

def rotate_faller(board: [[]], last_faller_index: int, faller: faller, column: int) -> None:
    '''Rotates faller object and faller on board'''
    faller.rotate()
    old_board = copy.deepcopy(board)
    board[column][last_faller_index] = faller._lst[2]
    board[column][last_faller_index - 1] = faller._lst[1]
    board[column][last_faller_index - 2] = faller._lst[0]

def fall(board: [[]], faller: faller, column: int, faller_in_board_count: int) -> [[]]:
    '''Drops faller by one and checks if faller hits bottom of current board'''
    col = board[column][:]
    for element in range(len(col)):
        if element != 0:
            if board[column][element] == '   ' and board[column][element - 1] != '   ':
                col[element] = board[column][element - 1]
            elif board[column][element] != '   ':
                bottom = True
                for iterable in range(element, len(col)):
                    if board[column][iterable] == '   ':
                        bottom = False
                        break
                if bottom == False:
                    col[element] = board[column][element - 1]
                
        else:
            faller_in_board_count = 0
            for element in board[column]:
                if '[' in element:
                    faller_in_board_count += 1
            if faller_in_board_count == 1:
                col[0] = faller[1]
            elif faller_in_board_count == 2:
                col[0] = faller[0]
            elif faller_in_board_count > 2:
                col[0] = '   '
    board[column] = col
    return board
    
def rotate_faller_less_than_3(board: [[]], last_faller_index: int, faller: faller, column: int):
    '''Drops faller by one and checks if faller hits bottom of current board if only one or two faller elements are present'''
    faller.rotate()
    if last_faller_index == 0:
        board[column][last_faller_index] = faller._lst[2]
    elif last_faller_index == 1:
        board[column][last_faller_index] = faller._lst[2]
        board[column][last_faller_index - 1] = faller._lst[1]

