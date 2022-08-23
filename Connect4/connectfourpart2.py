# File: CFServerModule.py
# ICS 32 - Connect Four

from collections import namedtuple
import socket

#CF: Connect Four
CFConnection = namedtuple(
    'CFConnection',
    ['socket', 'socket_in', 'socket_out'])

class CFProtocolError(Exception):
    pass

def connect(host: str, port: int) -> CFConnection:
    
    CF_socket = socket.socket()
    CF_socket.connect((host, port))
    
    # 'ron-cadillac.ics.uci.edu', 5501
    
    CF_input = CF_socket.makefile('r')
    CF_output = CF_socket.makefile('w')

    return CFConnection(CF_socket, CF_input, CF_output)

def close(connection: CFConnection) -> None:
    connection.socket_in.close()
    connection.socket_out.close()
    connection.socket.close()

def welcome(connection: CFConnection, username: str) -> bool:
    _write_line(connection, f'I32CFSP_HELLO {username}')

    response = _read_line(connection)
    responseList = response.split()
    if ' ' in username:
        close(connection)
        print('Not a valid username')
        return False
    else:
        if responseList[0] == 'WELCOME':
            return True
        elif responseList[0].startswith('NO_USER '):
            return False
        else:
            raise CFProtocolError()

def start_game(connection: CFConnection) -> bool:
    _write_line(connection, f'AI_GAME')

    response = _read_line(connection)
    responseList = response.split()
    if responseList[0] == 'READY':
        return True
    else:
        raise CFProtocolError()

#"private function": functions there to make it easier
# to write *this* module, but not intended for use in others
# their names begin with an underscore

def _write_line(connection: CFConnection, line:str) -> None:
    connection.socket_out.write(line + '\r\n')
    connection.socket_out.flush()

def _read_line(connection: CFConnection) -> str:
    return connection.socket_in.readline()[:-1]


# REMOVE LATER

if __name__ == '__main__':
    host = 'ron-cadillac.ics.uci.edu'
    connection = connect(host, 4444)
    welcome(connection, 'Jon')
    start_game(connection)



