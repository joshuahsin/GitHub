# Jonathan Estrada - 33326279
# Joshua Hsin - 13651420

# Connect Four - Interface Module

import connectfour
from itertools import chain

### game_interface can be removed, should be implemented in console version instead
def game_interface() -> int:
    ''' '''
    boards = create_game()
    display_update(boards)
    display_player_turn(boards)
    win_condition = determine_winner(boards)
    while win_condition == 0:
        player_move = input()
        boards = next_turn(boards, player_move)
        win_condition = determine_winner(boards)


## When taking input from server, convert 'POP' to 'p'
def next_turn(boards: 'namedtuple', column_input: str) -> 'namedtuple':
    ''' '''
    draw = game_draw(boards.board)
    if draw == True:
        print('Draw')
        return 0
    # boards = _prompt_user(column_input, boards)    # FIXME
    if column_input.startswith('p '):
        boards = pop_attempt(column_input, boards)
    else:
        boards = drop_attempt(column_input, boards)
    return boards

def create_game() -> 'namedtuple':
    new_board = connectfour.new_game()
    return new_board

def pop_attempt(columninput: str, boards: 'namedtuple') -> 'namedtuple':
    try:
        columnlist = columninput.split()
        popturn = pop_valid(boards, int(columnlist[1]) - 1)
        if popturn == True:
            boards = new_game_state_pop(boards, int(columnlist[1]) - 1)
            display_update(boards)
            display_player_turn(boards)
    except ValueError:
        print('Column Not Given')
    except connectfour.InvalidMoveError:
        print('Invalid Move!')

    return boards

def drop_attempt(columninput: str, boards: 'namedtuple') -> 'namedtuple':
    try:
        column_number = int(columninput) - 1
        # print(boards.board)
        boards = new_game_state(boards, column_number)
        display_update(boards)
        display_player_turn(boards)
    except ValueError:
        print(f'Not a number in between 1 and {connectfour.BOARD_COLUMNS}!')
    except connectfour.InvalidMoveError:
        print('Invalid Move!')
    except connectfour.GameOverError:
        print('Game Over!')

    return boards

def display_player_turn(boards: 'namedtuple'):
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
    ''' '''
    new_game_state = connectfour.drop(game_state, column_number)
    return new_game_state

def new_game_state_pop(game_state: 'namedtuple', column_number: int) -> 'namedtuple':
    ''' '''
    new_game_state = connectfour.pop(game_state, column_number)
    return new_game_state

def pop_valid(boards, columnnumber):
    ''' '''
    try:
        if boards.board[columnnumber][-1] == boards.turn:
            return True
        else:
            print('Invalid Move!')
            return False
    except IndexError:
        print('Invalid Move!')

def game_draw(board):
    ''' '''
    draw = True
    biglist = list(chain.from_iterable(board))
    if 0 in biglist:
        draw = False
    return draw

def determine_winner(boards: 'namedtuple') -> str:
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

def display_update(newGameState: 'namedtuple'):
    ''' Given the namedtuple newGameState,
        updates columnsafter a player
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


# Remove if/main and game_interface function after testing
if __name__ == '__main__':
   game_interface()









from collections import namedtuple
import socket

PollingConnection = namedtuple('PollingConnection', ['socket', 'socket_in', 'socket_out'])

# create a new type calling PollingProtocol Error that is an exception - can be raised
class PollingProtocolError(Exception):
    pass

def connect(host: str, port: int) -> PollingConnection:
    
    polling_socket = socket.socket()
    polling_socket.connect(('ron-cadillac.ics.uci.edu', 5501))
    polling_input = polling_socket.makefile('r')
    polling_output = polling_socket.makefile('w')

    return PollingConnection(polling_socket, polling_input, polling_output)

def close(connection: PollingConnection) -> None:
    connection.socket_in.close()
    connection.socket_out.close()
    connection.socket.close()



def hello(connection: PollingConnection, username: str) -> bool:
    _write_line(connection, f'POLLING_HELLO {username}')

    response = _read_line(connection)
    if response == 'HELLO':
        return True
    elif response.startswith('NO_USER '):
        return False
    else:
        raise PollingProtocolError()

#"private function": functions there to make it easier
# to write *this* module, but not intended for use in others
# their names begin with an underscore

def _write_line(connection: PollingConnection, line:str) -> None:
    connection.socket_out.write(line + '\r\n')
    connection.socket_out.flush()

def _read_line(connection: PollingConnection) -> str:
    return connection.socket_in.readline()[:-1]

