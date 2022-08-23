import pymysql

db = pymysql.connect(host = 'localhost', user = 'mp2', passwd = 'eecs116', db= 'flights')
cur = db.cursor()

def get_input():
    #gets user input
    print('1. Find the cheapest flight given airports and a date.')
    print('2. Find the flight and seat information for a customer.')
    print('3. Find all non-stop flights for an airline.')
    print('4. Add a new airplane.')
    print('5. Increase low-cost fares(≤ 200) by a factor.')
    print('6. Remove a seat reservation')
    a = input("Enter a task number or quit('q'): ")
    return a

def find_cheapest_flight():
    #find and return information for the cheapest flight, given departure airport, arrival airport, and date
    dept_airport = input('Please enter the airport code for the departure airport: ')
    dest_airport = input('Please enter the airport code for the destination airport: ')
    date = input('What is the date of the flight in yyyy-mm-dd? ')
    try:
        sql = '''WITH a(flight, amount) as 
                     (SELECT leg.Flight_number, fare.Amount 
                     FROM flights.Leg_instance as leg, flights.Fare as fare
                     WHERE leg.Flight_number = fare.Flight_number and leg.Departure_airport_code = \'''' + dept_airport + '''\'
                     and leg.Arrival_airport_code = \'''' + dest_airport + '''\' and leg.Leg_date = \'''' + date + '''\')
                 SELECT a.flight, a.amount
                 FROM a
                 WHERE a.amount = (SELECT min(a.amount)
                                   FROM a)'''
        cur.execute(sql)
        a = cur.fetchall()
        print()
        if(len(a) == 0):
            print("none")
        else:
            for row in a:
                print("The cheapest flight is " + str(row[0]) + ", and the cost is " + str(row[1]))
        print()
    except:
        print()
        print("error: date is in the wrong form")
        print()

def find_customer_information():
    #find the customer's infomation given customer name
    cust_name = input("Please enter the customer’s name: ")
    sql = '''SELECT seat.Flight_number, seat.Seat_number
             FROM flights.Seat_reservation as seat
             WHERE seat.Customer_name = \'''' + cust_name + '''\''''
    cur.execute(sql)
    a = cur.fetchall()
    print()
    if(len(a) == 0):
        print("none")
    else:
        for row in a:
            print("The flight number is " + str(row[0]) + ", and the seat number is " + str(row[1]))
    print()

def find_nonstop_flights():
    #find nonstop flights given airline
    airline = input("What is the name of the airline?: ")
    sql = '''SELECT flight.Flight_number
             FROM flights.Flight as flight, flights.Flight_leg as leg
             WHERE flight.Flight_number = leg.Flight_number and flight.Airline = \'''' + airline + '''\'
             GROUP BY flight.Flight_number
             HAVING count(leg.Leg_number) = 1'''
    cur.execute(sql)
    a = cur.fetchall()
    print()
    if(len(a) == 0):
        print("none")
    else:
        for row in a:
            print("The non-stop flights are ", row[0])
    print()

def add_airplane():
    #add an airline to the Airplane table given seats and plane_type
    seats = input("Please enter the total number of seats: ")
    plane_type = input("Please enter the airplane type: ")
    try:
        sql = '''INSERT INTO flights.Airplane(Airplane_id, Total_number_of_seats, Airplane_Type)
                 VALUES((SELECT max(plane.Airplane_id)
                     FROM flights.Airplane as plane) + 1, %s, %s) '''
        val = (seats, plane_type)
        cur.execute(sql, val)
        db.commit()
        sql2 = '''SELECT max(plane.Airplane_id)
                  FROM flights.Airplane as plane'''
        cur.execute(sql2)
        print()
        print("The new airplane has been added with id: ", cur.fetchone()[0])
        print()
    except:
        print()
        print("error: seats is in the wrong form")
        print()

def increase_low_cost_fares():
    #increase fairs below $200 by a factor given by user
    sql2 = '''SELECT count(fare.Flight_number)
                  FROM flights.Fare as fare
                  WHERE fare.Amount <= 200'''
    cur.execute(sql2)
    fare_count = cur.fetchone()[0]
    factor = input('Please enter a factor (e.g. 0.2 will increase all fares by 20%): ')
    try:
        sql = '''UPDATE flights.Fare
                     SET Amount = Amount *''' + str(1 + float(factor)) + '''
                     WHERE Amount <= 200'''
        cur.execute(sql)
        db.commit()
        print()
        print(fare_count, " fare(s) are affected.")
        print()
    except:
        print()
        print('error: factor is in the wrong form')
        print()

def remove_seat_reservation():
    #remove a seat reservation given flight number and customer name
    flight_num = input('Please enter the flight number: ')
    cust_name = input('Please enter the customer name: ')
    sql = '''SELECT seat.Seat_number
             FROM flights.Seat_reservation as seat
             WHERE seat.Customer_name = \'''' + cust_name + '''\' and seat.Flight_number = \'''' + flight_num + '''\''''
    cur.execute(sql)
    seat_num = -1
    a = cur.fetchone()
    if(a != None):
        seat_num = a[0]
    sql2 = '''DELETE FROM flights.Seat_reservation
              WHERE Customer_name = \'''' + cust_name + '''\' and Flight_number = \'''' + flight_num + '''\''''
    cur.execute(sql2)
    db.commit()
    print()
    if(seat_num == -1):
        print("Seat does not exist")
    else:
        print("Seat ", seat_num, " is released.")
    print()

def execute_queries():
    #execute queries until user quits
    option = get_input()
    while(option != 'q'):
        if(option == '1'):
            find_cheapest_flight()
        elif(option == '2'):
            find_customer_information()
        elif(option == '3'):
            find_nonstop_flights()
        elif(option == '4'):
            add_airplane()
        elif(option == '5'):
            increase_low_cost_fares()
        elif(option == '6'):
            remove_seat_reservation()
        else:
            print()
            print('error, try again')
            print()
        option = get_input()
    
if __name__ == '__main__':
    execute_queries()
