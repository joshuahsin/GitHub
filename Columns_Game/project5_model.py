#project5_model
#import pygame
import random
import project5_faller
import project5_initialize_board as initialize
import project5_match_check as match
import project5 as view

def next_move(board: [[]], turn: int, column: int, faller_lst: project5_faller.faller) -> [[]] and int and project5_faller.faller and bool:
    '''Determines next move - introducing a faller or dropping - and checks if faller hits bottom'''
    bottom = False
    if turn == 0:
        board, column, faller_lst = faller1(board)
        turn += 1
    else:
        board, column, bottom = fall(board, faller_lst, column)
        if bottom == True:
            turn = 0
        elif bottom == False:
            pass
    return board, turn, column, faller_lst, bottom

def faller1(board: [[]]) -> [[]] and int and project5_faller.faller:
    '''Introduces faller in valid row - where first three elements are empty'''
    column = random.randint(0,5)
    full = True
    while full == True:
        if board[column][2] != '   ':
            full = True
            column = random.randint(0,5)
        else:
            full = False
    input1 = []
    color_columns = ['S','T','V','W','X','Y','Z']
    for x in range(0,3):
        randint = random.randint(0, 6)
        input1.append(color_columns[randint])
    faller_lst = project5_faller.faller(input1[0], input1[1], input1[2])
    board, column = project5_faller.update_faller(board, column, faller_lst._lst)
    same = True
    while same == True:
        if faller_lst._lst[0][1] == faller_lst._lst[1][1] and faller_lst._lst[0][1] == faller_lst._lst[2][1]:
            board, column, faller_lst = faller1(board)
        else:
            same = False
    return board, column, faller_lst

def fall(board: [[]], faller_lst: project5_faller.faller, column: int) -> [[]] and int and bool:
    '''Drops faller by one and checks if faller hits bottom'''
    running = True
    bottom = False
    last_faller_index = get_last_faller_index(board, column)
    faller_in_board_count = get_faller_in_board_count(board, column)
    if faller_in_board_count <= 3 and board[column][-1] == '   ':
        project5_faller.fall(board, faller_lst._lst, column, faller_in_board_count)
        full = True
    elif last_faller_index <= (len(board[column]) - 2):
        if board[column][last_faller_index + 1] == '   ':
            project5_faller.fall(board, faller_lst._lst, column, faller_in_board_count)
        else:
            if '[' in board[column][last_faller_index - 1]:
                freeze, board = project5_faller.freeze_faller(board, faller_lst)
                board = project5_faller.resolve_faller(board)
                bottom = True
    else:
        if '[' in board[column][last_faller_index - 1]:
            freeze, board = project5_faller.freeze_faller(board, faller_lst)
            board = project5_faller.resolve_faller(board)
            bottom = True
    return board, column, bottom

def get_faller_in_board_count(board: [[]], column: int) -> int:
    '''Gets amount of elements of faller in the board'''
    faller_in_board_count = 0 
    for element in board[column]:
        if '[' in element or '|' in element:
            if faller_in_board_count < 3:
                faller_in_board_count += 1
    return faller_in_board_count

def get_last_faller_index(board: [[]], column: int) -> int:
    '''Gets the index of the lowest faller element'''
    last_faller_index = 0
    for element in board[column]:
        if '[' in element:
            last_faller_index = len(board[column]) - board[column][::-1].index(element) - 1
    return last_faller_index
