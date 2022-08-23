#Joshua Hsin
#ID 13651420
#project4_initialize_board.py
import copy
class State:
    '''Representation of board state'''
    def __init__(self, board: [[]]):
        '''Initialization of board state object'''
        self._board = board

    def test_bottom(self):
        '''Pushes board elements to bottom of board after a match'''
        new_board = []
        for column in self._board:
            col = []
            for element in column:
                if element != '   ':
                    col.append(element)
            new_board.append(col)

        for column in new_board:
            while len(column) != len(self._board[0]):
                column.insert(0, '   ')
        self._board = new_board

    def horizontal_check(self):
        '''Checks for horizontal matches in board'''
        list_by_rows = self.lst_rows(self._board)
        new_list_by_rows = copy.deepcopy(list_by_rows)
        width = len(list_by_rows[0])
        for row in range(len(list_by_rows)):
            for element in range(len(list_by_rows[0]) - 2):
                if list_by_rows[row][element] != '   ':
                    if list_by_rows[row][element][1] == list_by_rows[row][element + 1][1] and list_by_rows[row][element][1] == list_by_rows[row][element + 2][1]:
                        new_list_by_rows[row][element] = '*' + list_by_rows[row][element][1] + '*'
                        for range1 in range(element, width):
                            if range1 > 0:
                                if list_by_rows[row][range1][1] == list_by_rows[row][range1 - 1][1] and new_list_by_rows[row][range1 - 1][0] == '*':
                                    new_list_by_rows[row][range1] = '*' + list_by_rows[row][range1][1] + '*'
        self._board = self.lst_rows(new_list_by_rows)

    def vertical_check(self):
        '''Checks for vertical matches in board'''
        height = len(self._board[0])
        new_board = copy.deepcopy(self._board)
        for column in range(len(self._board)):
            for element in range(height - 2):
                if self._board[column][element] != '   ':
                    if self._board[column][element][1] == self._board[column][element + 1][1] and self._board[column][element][1] == self._board[column][element + 2][1]:
                        new_board[column][element] = '*' + self._board[column][element][1] + '*'
                        for range1 in range(element, height):
                            if range1 > 0:
                                if self._board[column][range1][1] == self._board[column][range1 - 1][1] and new_board[column][range1 - 1][0] == '*':
                                    new_board[column][range1] = '*' + self._board[column][range1][1] + '*'
        self._board = new_board

    def diagonal_right_check(self):
        '''Checks for diagonal right down matches in board'''
        height = len(self._board[0])
        width = len(self._board)
        #lst = [height, width]
        new_board = copy.deepcopy(self._board)
        for column in range(width - 2):
           for row in range(height - 2):
               if self._board[column][row] != '   ':
                   if self._board[column][row][1] == self._board[column + 1][row + 1][1] and self._board[column][row][1] == self._board[column + 2][row + 2][1]:
                        for range1 in range(width):
                            if (row + range1) <= (height - 1) and (column + range1) <= (width - 1):
                                if self._board[column + range1][row + range1][1] == self._board[column][row][1]:
                                    new_board[column + range1][row + range1] = '*' + self._board[column + range1][row + range1][1] + '*'
        self._board = new_board

    def diagonal_left_check(self):
        '''Checks for diagonal left down matches in board'''
        height = len(self._board[0])
        width = len(self._board) - 1
        new_board = copy.deepcopy(self._board)
        for column in range(width, 1, -1):
           for row in range(height - 2):
               if self._board[column][row] != '   ':
                   if self._board[column][row][1] == self._board[column - 1][row + 1][1] and self._board[column][row][1] == self._board[column - 2][row + 2][1]:
                        for range1 in range(width + 1):
                            if 0 <= (row + range1) <= (height - 1) and 0 <= (column - range1) <= (width):
                                if self._board[column - range1][row + range1][1] == self._board[column][row][1]:
                                    new_board[column - range1][row + range1] = '*' + self._board[column - range1][row + range1][1] + '*'
        self._board = new_board

    def lst_rows(self, board: [[]]) -> [[]]:
        '''Changes board representation from columns to rows'''
        final_lst = []
        for row1 in range(len(board[0])):
            row_lst = []
            for column in range(len(board)):
                row_lst.append(board[column][row1])
            final_lst.append(row_lst)
        return final_lst
        


def new_board(rows: int, columns: int) -> [[]]:
    '''Creates a new board of certain size'''
    big_lst = []
    for column in range(columns):
        small_lst = []
        for row in range(rows):
            small_lst.append('   ')
        big_lst.append(small_lst)
    return big_lst

def test_bottom(board: [[]]) -> [[]]:
    '''Pushes board elements to bottom of board after a match'''
    board = State(board)
    board.test_bottom()
    return board._board

