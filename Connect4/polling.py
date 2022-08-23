#polling.py - implementation of Polling protocal
#what has to do with sockets, servers, etc.
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

print(__name__)
