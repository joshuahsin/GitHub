#Joshua Hsin
#ID 13651420
#project5_match_check.py
import project5_model as main
import copy
import project5_initialize_board as initialize
import sys


def continuous_check(board: [[]]) -> [[]]:
    '''Continuously checks board for matches until there are none'''
    new_board = run_all_checks(board)
    board_timeline = [board, new_board]
    for element in range(len(board_timeline)):
        while board_timeline[-1] != board_timeline[-2]:
            new_board = run_all_checks(new_board)
            board_timeline.append(new_board)
    return new_board
    
def run_all_checks(board: [[]]) -> [[]]:
    '''Runs all checks for matches on board'''
    new_board = horizontal_check(board)
    new_board = vertical_check(new_board)
    new_board = diagonal_right_check(new_board)
    new_board = diagonal_left_check(new_board)
    return new_board



def horizontal_check(board: [[]]) -> [[]]:
    '''Checks for horizontal matches in board'''
    board = initialize.State(board)
    board.horizontal_check()
    return board._board
    

def vertical_check(board: [[]]) -> [[]]:
    '''Checks for vertical matches in board'''
    board = initialize.State(board)
    board.vertical_check()
    return board._board

def diagonal_right_check(board: [[]]) -> [[]]:
    '''Checks for diagonal right down matches in board'''
    board = initialize.State(board)
    board.diagonal_right_check()
    return board._board

def diagonal_left_check(board: [[]]) -> [[]]:
    '''Checks for diagonal left down matches in board'''
    board = initialize.State(board)
    board.diagonal_left_check()
    return board._board

    
            



