# Jonathan Estrada - 33326279
# Joshua Hsin - 13651420

# File: CFInterface.py
# ICS 32 Project 2 - Connect Four
# This program is the interface for both the console and server versions of the Connect Four game

import connectfour
from itertools import chain

INVALID = 0

class CFInterfaceError(Exception):
    pass

def create_game() -> 'namedtuple':
    '''Creates a new Connect Four board '''
    new_board = connectfour.new_game()
    return new_board

def pop_attempt(columninput: str, boards: 'namedtuple') -> list:
    ''' Tests if pop is valid. If valid, update board.
        If invalid, raise and print Error. '''
    try:
        columnlist = columninput.split()
        popturn = pop_valid(boards, int(columnlist[1]) - 1)
        if popturn == True:
            boards = new_game_state_pop(boards, int(columnlist[1]) - 1)
            display_update(boards)
            display_player_turn(boards)
        else:
            raise CFInterfaceError()
    except ValueError:
        print('Column Not Given')
        raise CFInterfaceError()
    except connectfour.InvalidMoveError:
        print('Invalid Move!')
        raise CFInterfaceError()
    except IndexError:
        print('Invalid Move! (Index Error)')
        raise CFInterfaceError()

    return boards

def drop_attempt(columninput: str, boards: 'namedtuple') -> 'namedtuple':
    ''' If drop is valid, update board. If invalid, raise
        and print error. '''
    try:
        column_number = int(columninput) - 1
        boards = new_game_state(boards, column_number)
        display_update(boards)
        display_player_turn(boards)
    except ValueError:
        print(f'Not a number in between 1 and {connectfour.BOARD_COLUMNS}!')
        raise CFInterfaceError()
    except connectfour.InvalidMoveError:
        print('Invalid Move!')
        raise CFInterfaceError()
    except connectfour.GameOverError:
        print('Game Over!')
        raise CFInterfaceError()

    return boards

def display_player_turn(boards: 'namedtuple') -> None:
    ''' Print’s player turn and explanation on how to play the Connect Four game '''
    player = ''
    if boards.turn == connectfour.RED:
        player = 'RED'
    elif boards.turn == connectfour.YELLOW:
        player = 'YELLOW'
    print(f"Player {player}'s turn!")
    print()
    print(f"Drop: Type in a column number from 1-{connectfour.BOARD_COLUMNS}.")
    print("Pop: Type the letter 'p' followed by a space, followed by the")
    print("     desired column number to remove one of your own pieces")
    print("     from the bottom of the board.")
    print("Action: ", end='')

def new_game_state(game_state: 'namedtuple', column_number: int) -> 'namedtuple':
    ''' Returns a namedtuple with updated state of game given drop command '''
    new_game_state = connectfour.drop(game_state, column_number)
    return new_game_state

def new_game_state_pop(game_state: 'namedtuple', column_number: int) -> 'namedtuple':
    ''' Returns a namedtuple with updated state of game given pop command '''
    new_game_state = connectfour.pop(game_state, column_number)
    return new_game_state

def pop_valid(boards: 'namedtuple', columnnumber: int) -> bool:
    ''' Returns true if pop is valid, false if invalid '''
    try:
        turn = boards.board[int(columnnumber)][-1]
        if turn == boards.turn:
            return True
        else:
            print('Invalid Move!')
            return False
    except IndexError:
        print('Invalid Move!')

def game_draw(board: 'namedtuple') -> bool:
    ''' Tests if game is draw, return True if Draw, False if now draw '''
    draw = True
    biglist = list(chain.from_iterable(board))
    if 0 in biglist:
        draw = False
        return draw
    else:
        print('Draw')
        return draw

def determine_winner(boards: 'namedtuple') -> str:
    ''' Determines if there is a winner. If so, return
        winner. Else, return 0. '''
    winner = ''
    if connectfour.winner(boards) == 1:
        winner == 'RED'
        print("\nPlayer RED has won!")
    elif connectfour.winner(boards) == 2:
        print("\nPlayer YELLOW has won!")
        winner == 'YELLOW'    
    else:
        return 0
    return winner

def display_update(newGameState: 'namedtuple') -> None:
    ''' Given the namedtuple newGameState,
        updates columns after a player
        makes a move '''
    total_columns = range(connectfour.BOARD_COLUMNS)
    print()
    for column in total_columns:
        if (column + 1) == len(total_columns):
            print(column + 1)
        else:
            print(column + 1, end='  ')
    for row in range(connectfour.BOARD_ROWS):
        for column in range(connectfour.BOARD_COLUMNS):
            if newGameState.board[column][row] == 0:
                print('.', end = '  ')
            elif newGameState.board[column][row] == 1:
                print('R', end = '  ')
            elif newGameState.board[column][row] == 2:
                print('Y', end = '  ')
        print()
    print()











