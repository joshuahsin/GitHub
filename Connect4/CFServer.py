# Jonathan Estrada - 33326279
# Joshua Hsin - 13651420

# File: CFServer.py
# ICS 32 Project 2 - Connect Four

from collections import namedtuple
import socket
#This is the server version of the Connect Four game

#CF: Connect Four
CFConnection = namedtuple(
    'CFConnection',
    ['socket', 'socket_in', 'socket_out'])

INVALID = 0
WINNER_RED = 1
WINNER_YELLOW = 2

class CFProtocolError(Exception):
    pass

def connect(host: str, port: int) -> CFConnection:
    ''' Connects to server, and creates input and
        output for server/host. If fails, raise error '''
    try:
        CF_socket = socket.socket()
        CF_socket.connect((host, port))
        
        # 'ron-cadillac.ics.uci.edu', 4444
        
        CF_input = CF_socket.makefile('r')
        CF_output = CF_socket.makefile('w')

    except:
        raise CFProtocolError

    return CFConnection(CF_socket, CF_input, CF_output)

def close(connection: CFConnection) -> None:
    ''' Closes connection to server '''
    connection.socket_in.close()
    connection.socket_out.close()
    connection.socket.close()

def welcome(connection: CFConnection, username: str) -> bool:
    ''' Prints welcome message following successful connection to server '''
    _write_line(connection, f'I32CFSP_HELLO {username}')

    response = _read_line(connection)
    try:
        responseList = response.split()
        if responseList[0] == 'WELCOME':
            return True
        else:
            return False
    except IndexError:
        return False

def initiate_game(connection: CFConnection) -> bool:
    ''' If new game requested and server gives correct
        response, return True, else raise Error. '''
    _write_line(connection, f'AI_GAME')

    response = _read_line(connection)
    responseList = response.split()
    if responseList[0] == 'READY':
        return True
    else:
        raise CFProtocolError()

def user_turn(connection: CFConnection, move_type: str, column: int) -> int or str:
    ''' On user turn, returns column if valid input, winner if there is a winner, or raises Error '''
    _write_line(connection, f'{move_type} {column}')

    response = _read_line(connection)
    if response == 'OKAY':
        return column
    elif response == 'INVALID':
        # If the move is invalid, return 0
        return INVALID
    elif response == 'WINNER_RED' or response == 'WINNER_YELLOW':
        return response
    else:
        # In CFServer_ui, use a try/except to raise an error for invalid POPs
        raise CFProtocolError()

def server_turn(connection: CFConnection) -> [str] or int:
    ''' On server’s turn, returns user move, winner, or raises Error'''
    temp = _read_line(connection)
    server_move = temp.split()
    server_status = _read_line(connection)
    
    if server_status == 'READY':
        return [server_move, 0]
    elif server_status == 'WINNER_RED':
        return [server_move, server_status]
    elif server_status == 'WINNER_YELLOW':
        return [server_move, server_status]
    else:
        raise CFProtocolError()

#"private function": functions there to make it easier
# to write *this* module, but not intended for use in others
# their names begin with an underscore

def _write_line(connection: CFConnection, line:str) -> None:
    ''' Writes output to terminal '''
    connection.socket_out.write(line + '\r\n')
    connection.socket_out.flush()

def _read_line(connection: CFConnection) -> str:
    ''' Reads server response '''
    return connection.socket_in.readline()[:-1]





