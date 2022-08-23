
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
