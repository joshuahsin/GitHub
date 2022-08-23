# Jonathan Estrada - 33326279
# Joshua Hsin - 13651420

# File: CFServer_ui.py
# ICS 32 Project 2 - Connect Four


import CFServer
import CFInterface
import sys

def game_interface() -> None:
    ''' Connects to server and runs Connect Four game'''

    connection = 0
    while connection == False:
        connection = _connect_to_server()

    print('Successfully connected to server!\n')

    try:
        while True:
            username = _ask_username()
            
            response = CFServer.welcome(connection, username)
            if response == True:
                print(f'\nHello {username}!')
                break
            else:
                print('Server response error, ending program...')
                sys.exit()

        while True:
            server_status = CFServer.initiate_game(connection)
            if server_status:
                break
                    
        board = CFInterface.create_game()
        CFInterface.display_update(board)
        CFInterface.display_player_turn(board)
        
        while True:
            # Check if the game results in a draw
            draw = CFInterface.game_draw(board.board)
            if draw == True:
                break

            if board.turn == 1:
                player_move = input()
                board, response = _determine_player_move(connection, player_move, board)
                if _determine_winner(response) == True:
                    break
            
            elif board.turn == 2:
                print("Server's turn!")
                column_num = CFServer.server_turn(connection)
                if column_num[0][0] == 'POP':
                    if CFInterface.pop_valid(board, int(column_num[0][1]) - 1) == False:
                        print('Pop attempt by server returned an error, ending game')
                        break
                    pop_input = 'p ' + column_num[0][1]
                    board = CFInterface.pop_attempt(pop_input, board) 
                    if _determine_winner(column_num[1]) == True:
                        break
                         
                elif column_num[0][0] == 'DROP':
                    try:
                        board = CFInterface.drop_attempt(column_num[0][1], board)
                        if _determine_winner(column_num[1]) == True:
                            break
                    except CFInterface.CFInterfaceError:
                        print('Drop attempt by server returned an error, ending game')
                        break
                    except CFServer.CFProtocolError:
                        print('Drop attempt by server returned an error, ending game')
                        break
                
    finally:
        CFServer.close(connection)


def _determine_player_move(connection, move, board) -> tuple:
    ''' Determines if user (player RED) wants to drop
        or pop, then updates board '''
    while True:
        if move.startswith('p '):
            try:
                move_split = move.split()
                board = CFInterface.pop_attempt(move, board)
                response = CFServer.user_turn(connection, 'POP', move_split[1])
                if response == 'WINNER_RED' or response == 'WINNER_YELLOW':
                    return board, response
                else:
                    return board, 0
            except CFInterface.CFInterfaceError:
                move = input()
            except CFServer.CFProtocolError:
                move = input()

        else:
            try:
                board = CFInterface.drop_attempt(move, board)
                response = CFServer.user_turn(connection, 'DROP', move)
                if response == 'WINNER_RED' or response == 'WINNER_YELLOW':
                    return board, response
                else:
                    return board, 0 
            except CFInterface.CFInterfaceError:
                move = input()
            except CFServer.CFProtocolError:
                move = input()

def _determine_winner(status) -> bool:
    ''' Determines if server sends back a message stating
        if a player won '''
    if status == 'WINNER_RED':
        print('\nPlayer RED won!')
        return True

    elif status == 'WINNER_YELLOW':
        print('\nPlayer YELLOW won!')
        return True
    
    else:
        return False

def _connect_to_server() -> bool:
    ''' Asks for user input in order to connect to a server '''
    try:
        # ron-cadillac.ics.uci.edu
        print('Please input a host name:')
        host = input()
        
        # 4444
        print('Please input a port number:')
        port = int(input())
        
        connection = CFServer.connect(host, port)
        
    except CFServer.CFProtocolError:
        print('Could not connect to server, ending program...')
        sys.exit()
    
    except ValueError:
        print('Invalid port number, please re-enter info:\n')
        return False
    
    return connection
    

def _ask_username() -> str:
    ''' Asks user for a valid username '''
    while True:
        username = input('Type your name: ')

        if len(username) > 0 and (' ' not in username) and ('\t' not in username):
            return username
        else:
            print('Invalid username')


if __name__ == '__main__':
    game_interface()



