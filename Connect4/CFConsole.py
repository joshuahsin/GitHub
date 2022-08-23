# Jonathan Estrada - 33326279
# Joshua Hsin - 13651420

# File: CFConsole.py
# ICS 32 Project 2 - Connect Four

#This is the console version of the Connect Four game

import CFInterface

def game_interface(board: 'namedtuple') -> None:
    ''' Updates the game board, tests if the
        game is a draw, takes new player input,
        and tests if a player has won'''
    CFInterface.display_update(board)
    CFInterface.display_player_turn(board)
    win_condition = CFInterface.determine_winner(board)
    
    while win_condition == 0:
        draw = CFInterface.game_draw(board.board)
        if draw == True:
            break

        player_move = input()
        board = _determine_move(player_move, board)
        win_condition = CFInterface.determine_winner(board)
 
def _determine_move(move, board) -> None:
    ''' Attempts player pop or drop based on input.
        If invalid, asks for new input '''
    while True:
        if move.startswith('p '):
            try:
                board = CFInterface.pop_attempt(move, board)
                break
            except CFInterface.CFInterfaceError:
                move = input()
        else:
            try:

                board = CFInterface.drop_attempt(move, board)
                break
            except CFInterface.CFInterfaceError:
                move = input()
                
    return board


if __name__ == '__main__':
    board = CFInterface.create_game()
    game_interface(board)
    
            




