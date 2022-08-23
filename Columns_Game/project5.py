#project5 - Columns Game View Module
#Joshua Hsin
#ID 13651420
import project5_model as model
import project5_initialize_board as initialize
import pygame
import tkinter
import project5_faller
import project5_match_check as match

class ColumnsGame:
    '''Visualisation of Columns Game'''
    def __init__(self):
        '''Initilization of object'''
        pass
        
    def run(self) -> None:
        '''Loops through game actions, quits when game in invalid running state'''
        pygame.init()

        surface = pygame.display.set_mode((700, 600))

        clock = pygame.time.Clock()
        pygame.time.set_timer(pygame.USEREVENT+1, 500)
        pygame.time.set_timer(pygame.USEREVENT+2, 100)
        pygame.time.set_timer(pygame.USEREVENT+3, 50)
        pygame.time.set_timer(pygame.USEREVENT+4, 10)
        pygame.time.set_timer(pygame.USEREVENT+5, 1)
        running = True
        color_amount = 0
        circle_center_x = 350
        circle_center_y = 300
        rect_x = 700/3
        rect_y = 30
        rect_w = 250
        rect_len = 541 + (2/3)
        rect_fill = 0
        turn = 0
        faller_column = 0
        faller_lst = project5_faller.faller('A', 'B','C')
        bottom = False

        surface.fill(pygame.Color(100, 0, 200))

        pygame.draw.rect(surface, pygame.Color(255,255,255),
                    (rect_x, rect_y, rect_w, rect_len) , rect_fill)

        board = initialize.new_board(13,6)
        
        try:
            while running:
                for event in pygame.event.get():
                    if event.type == pygame.QUIT:
                        running = False
                    elif event.type == pygame.USEREVENT+1:
                        board, turn, faller_column, faller_lst, bottom = model.next_move(board, turn, faller_column, faller_lst)
                        self.draw_board(board, surface, rect_x, rect_y, rect_w, rect_len)
                    elif event.type == pygame.USEREVENT+2:
                        board, faller_column, faller_lst = self._handle_keys(board, faller_column, faller_lst)
                        self.draw_board(board, surface, rect_x, rect_y, rect_w, rect_len)
                    elif event.type == pygame.USEREVENT+3:
                        if bottom == True:
                            board = match.continuous_check(board)
                            self.draw_board(board, surface, rect_x, rect_y, rect_w, rect_len)
                            running = self.check_game_end(board, faller_column)
                    elif event.type == pygame.USEREVENT+4:
                        if bottom == True:
                            board = self.match_protocal(board, surface)
                            running = self.check_game_end(board, faller_column)
                    elif event.type == pygame.USEREVENT+5:
                        last_faller_index = model.get_last_faller_index(board, faller_column)
                        if last_faller_index < 2:
                            if board[faller_column][last_faller_index + 1] != '   ':
                                running = False
                    elif event.type == pygame.VIDEORESIZE:
                        self._resize_surface(event.size)
                pygame.display.flip()
        finally:
            surface.fill(pygame.Color(0, 0, 0))
            pygame.quit()

    def _resize_surface(self, size: (int, int)) -> None:
        pygame.display.set_mode(size, pygame.RESIZABLE)

    def draw_board(self, board:[[]], surface, rect_x:float, rect_y:int, rect_w:int, rect_len:float) -> None:
        '''Draws the current state of the board'''
        jewel_w = rect_w/6
        jewel_len = rect_len/13
        for column in range(len(board)):
            for element in range(len(board[0])):
                color1, color2, color3 = self.find_color(board[column][element])
                pygame.draw.rect(surface, pygame.Color(color1, color2, color3),
                                (rect_x + (jewel_w * column) + 1, rect_y + (jewel_len * element) + 0.75,
                                 (jewel_w - 0.75), (jewel_len - 0.75)), 0)

    def check_game_end(self, board:[[]], column:int) -> bool:
        '''Checks if the game ends by checking if the board is full or faller stops early'''
        running = True
        full = True
        while full == True:
            for element in board[column]:
                if element == '   ':
                    full = False
                break
        last_faller_index = model.get_last_faller_index(board, column)
        if last_faller_index > 2:
            full = True
        if full == True:
            running = False
        return running

            
    def find_color(self, element:str) -> int:
        '''Returns RGB color values based on element type'''
        if element[1] == 'S':
            color1 = 0
            color2 = 0
            color3 = 255
        elif element[1] == 'T':
            color1 = 200
            color2 = 0
            color3 = 200
        elif element[1] == 'V':
            color1 = 255
            color2 = 0
            color3 = 0
        elif element[1] == 'W':
            color1 = 200
            color2 = 200
            color3 = 0
        elif element[1] == 'X':
            color1 = 0
            color2 = 255
            color3 = 0
        elif element[1] == 'Y':
            color1 = 0
            color2 = 200
            color3 = 200
        elif element[1] == 'Z':
            color1 = 200
            color2 = 200
            color3 = 200
        else:
            color1 = 0
            color2 = 0
            color3 = 0
        return color1, color2, color3

    def _handle_keys(self, board:[[]], column:int, faller_lst: project5_faller.faller) -> [[]] and int and project5_faller.faller:
        '''Returns output based on keyboard input'''
        keys = pygame.key.get_pressed()
        if keys[pygame.K_LEFT]:
            board, column = self.move_left(board, column, faller_lst)

        if keys[pygame.K_RIGHT]:
            board, column = self.move_right(board, column)

        if keys[pygame.K_SPACE]:
            board, column, faller_lst = self.rotate(board, column, faller_lst)
        return board, column, faller_lst

    def move_left(self, board:[[]], column: int, faller_lst: project5_faller.faller) -> [[]] and int:
        '''Moves faller to the left if valid move'''
        faller_indexes = []
        for element in range(len(board[column])):
            if '[' in board[column][element] or '|' in board[column][element]:
                faller_indexes.append(element)
        last_faller_index = model.get_last_faller_index(board, column)
        blocked = False
        if column == 0:
            blocked = True
        else:
            for index in faller_indexes:
                if column == (len(board) - 1):
                    if board[column - 1][index] != '   ':
                        blocked = True
                        break
                else:
                    if board[column - 1][index] != '   ':
                        blocked = True
        if blocked == False:
            for index in faller_indexes:
                board[column - 1][index] = board[column][index]
                board[column][index] = '   '
        if column - 1 >= 0:
            column = column - 1
        return board, column

    def move_right(self, board:[[]], column: int) -> [[]] and int:
        '''Moves faller to the right if valid move'''
        faller_indexes = []
        for element in range(len(board[column])):
            if '[' in board[column][element] or '|' in board[column][element]:
                faller_indexes.append(element)
        last_faller_index = model.get_last_faller_index(board, column)
        blocked = False
        if column == (len(board) - 1):
            blocked = True
        else:
            for index in faller_indexes:
                if column == 0:
                    if board[column + 1][index] != '   ':
                        blocked = True
                else:
                    if board[column + 1][index] != '   ':
                        blocked = True
        if blocked == False:
            for index in faller_indexes:
                board[column + 1][index] = board[column][index]
                board[column][index] = '   '
        if column + 1 <= (len(board) - 1):
            column += 1
        return board, column

    def rotate(self, board:[[]], column: int, faller_lst: project5_faller.faller) -> [[]] and int and project5_faller.faller:
        '''Rotates faller'''
        last_faller_index = model.get_last_faller_index(board, column)
        faller_in_board_count = model.get_faller_in_board_count(board, column)
        if faller_in_board_count > 2:
            project5_faller.rotate_faller(board, last_faller_index, faller_lst, column)
        else:
            project5_faller.rotate_faller_less_than_3(board, last_faller_index, faller_lst, column)
        for element in board[column]:
            if '[' in element:
                if faller_in_board_count < 3:
                    faller_in_board_count += 1
                    break
        return board, column, faller_lst

    def match_protocal(self, board: [[]], surface) -> [[]]:
        '''Clears matching tiles and pulls above tiles down after clearing'''
        board = self.clear_match_tiles(board, surface)
        board = initialize.test_bottom(board)
        return board

    def clear_match_tiles(self, board: [[]], surface) -> [[]]:
        '''Clears mathing tiles on board'''
        self.draw_board_match(board, surface)
        for column in range(len(board)):
            for element in board[column]:
                if '*' in element:
                    board[column][board[column].index(element)] = '   '
        return board

    def draw_board_match(self, board: [[]], surface) -> None:
        '''Flashes board match before clearing'''
        rect_x = 700/3
        rect_y = 30
        rect_w = 250
        rect_len = 541 + (2/3)
        jewel_w = rect_w/6
        jewel_len = rect_len/13

        for column in range(len(board)):
            for element in range(len(board[0])):
                if '*' in board[column][element]:
                    pygame.draw.rect(surface, pygame.Color(255, 255, 255),
                                (rect_x + (jewel_w * column) + 1, rect_y + (jewel_len * element) + 0.75,
                                 (jewel_w - 0.75), (jewel_len - 0.75)), 0)

        
        
if __name__ == '__main__':
    x = ColumnsGame()
    x.run()
