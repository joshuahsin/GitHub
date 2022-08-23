# polling_ui.py - user interface for Polling client - print, read input, etc.
import polling

def run_user_interface() -> None:
     _show_welcome_banner()

     host = _read_host()
     port = _read_port()

     connection = polling.connect(host, port)

     username = _read_username()

     if polling.hello(connection, username):
         print('Welcome!')
     else:
         print('Doh!')
        

def _show_welcome_banner() -> None:
    print('Welcome to the Polling Client (Winter 2019 Version)!')
    print('----------------------------------------------------')

def _read_host() -> str:
    while True:
        host = input('Host: ').strip()
        
        if host != '':
            return host
        else:
            print('Not a valid host; try again')

def _read_port() -> int:
    while True:
        try:
            port = int(input('Port: '))

            if 0 <= port <= 65535:
                return port

        except ValueError:
            pass

if __name__ == '__main__':
    run_user_interface()


