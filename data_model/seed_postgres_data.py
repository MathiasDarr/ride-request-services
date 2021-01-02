"""
Create & populate users, drivers, ride requests & rides relation data model
"""
# !/usr/bin/env python3
import psycopg2
import csv


def populate_users_table():
    create_users_table = """
            CREATE TABLE users (
                    userid VARCHAR(50) PRIMARY KEY,
                    first_name VARCHAR(50),
                    last_name VARCHAR(50),
                    city VARCHAR(50),
                    phone_number VARCHAR(50),
                    email VARCHAR(50),
                    password VARCHAR(50)
            );
    """
    cur.execute(create_users_table)
    conn.commit()
    USERS_CSV_FILE = 'data/users/users.csv'
    insert_into_users_table = """INSERT INTO users(userid, first_name, last_name, email, password, phone_number, city) VALUES(%s,%s,%s, %s, %s, %s, %s);"""
    with open(USERS_CSV_FILE, newline='') as csvfile:

        reader = csv.DictReader(csvfile)
        i = 0
        for row in reader:
            i += 1
            cur.execute(insert_into_users_table, [row['userid'], row['first_name'], row["last_name"], row['email'], row['password'], row['phone_number'], row['city']])
            conn.commit()
            print(row)



def populate_drivers_table():
    create_drivers_table = """
            CREATE TABLE drivers (
                    driverid VARCHAR(50) PRIMARY KEY,
                    first_name VARCHAR(50),
                    last_name VARCHAR(50),
                    email VARCHAR(50), 
                    password VARCHAR(50),
                    phone_number VARCHAR(50),
                    city VARCHAR(50)
            );
    """
    cur.execute(create_drivers_table)
    conn.commit()
    DRIVERS_CSV_FILE = 'data/drivers/drivers.csv'
    insert_into_drivers_table = """INSERT INTO drivers(driverid, first_name, last_name, email, password, phone_number, city) VALUES(%s,%s,%s,%s,%s, %s, %s);"""

    with open(DRIVERS_CSV_FILE, newline='') as csvfile:
        reader = csv.DictReader(csvfile)

        for row in reader:
            cur.execute(insert_into_drivers_table, [row['driverid'], row['first_name'], row["last_name"], row['email'], row['password'], row['phone_number'], row['city']])
    conn.commit()


def populate_rides_table():
    create_rides_table = """
            CREATE TABLE rides (
                    rideid VARCHAR(50) PRIMARY KEY,
                    ride_time timestamp,
                    riders INTEGER,
                    ride_status VARCHAR(30),
                    driverid VARCHAR(50) REFERENCES drivers(driverid),
                    userid VARCHAR(50) REFERENCES users(userid)
            );
    """
    cur.execute(create_rides_table)
    conn.commit()
    DRIVERS_CSV_FILE = 'data/rides/rides.csv'
    insert_into_drivers_table = """INSERT INTO rides(rideid, user_id, driver_id,ride_time, riders, ride_status) VALUES(%s,%s,%s,%s,%s, %s);"""

    with open(DRIVERS_CSV_FILE, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            cur.execute(insert_into_drivers_table, [row['rideid'], row['user_id'], row["driver_id"], row['ride_time'], row['riders'], row['ride_status']])
    conn.commit()


def populate_ride_requests_table():
    create_users_table = """
            CREATE TABLE ride_requests (
                    request_id VARCHAR(50) PRIMARY KEY,
                    user_id VARCHAR(50) REFERENCES users(userid),
                    request_time VARCHAR(30),
                    riders INTEGER
            );
    """
    cur.execute(create_users_table)
    conn.commit()
    USERS_CSV_FILE = 'data/ride_requests/ride_requests.csv'
    insert_into_users_table = """INSERT INTO ride_requests(request_id, userid, request_time, riders) VALUES(%s,%s,%s, %s);"""

    with open(USERS_CSV_FILE, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            cur.execute(insert_into_users_table, [row['request_id'], row['user_id'], row["request_time"], row['riders']])
    conn.commit()


if __name__ =='__main__':
    conn = psycopg2.connect(host="localhost", port="5432", user="postgres", password="postgres", database="postgresdb")
    cur = conn.cursor()
    populate_drivers_table()
    #populate_users_table()
    # populate_ride_requests_table()
    # populate_rides_table()
    print("THE POSTGRES DATABASE HAS BEEN SEEDED.")
