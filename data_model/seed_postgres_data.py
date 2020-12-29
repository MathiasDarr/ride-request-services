"""
Create & populate users, drivers, ride requests & rides relation data model
"""
# !/usr/bin/env python3
import psycopg2
import csv


def populate_ride_requests_table():
    create_users_table = """
            CREATE TABLE ride_requests (
                    request_id VARCHAR(50) PRIMARY KEY,
                    userid VARCHAR(30),
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
            cur.execute(insert_into_users_table, [row['request_id'], row['userid'], row["request_time"], row['riders']])
    conn.commit()


def populate_users_table():
    create_users_table = """
            CREATE TABLE users (
                    user_id VARCHAR(50) PRIMARY KEY,
                    first_name VARCHAR(30),
                    last_name VARCHAR(30),
                    email VARCHAR(30),
                    password VARCHAR(30)
            );
    """
    cur.execute(create_users_table)
    conn.commit()
    USERS_CSV_FILE = 'data/users/users.csv'
    insert_into_users_table = """INSERT INTO users(user_id, first_name, last_name, email, password) VALUES(%s,%s,%s, %s, %s);"""

    with open(USERS_CSV_FILE, newline='') as csvfile:
        reader = csv.DictReader(csvfile)

        for row in reader:
            a = [row['user_id'], row['first_name'], row["last_name"], row['email'], row['password']]
            cur.execute(insert_into_users_table, [row['user_id'], row['first_name'], row["last_name"], row['email'], row['password']])
    conn.commit()


def populate_drivers_table():
    create_drivers_table = """
            CREATE TABLE drivers (
                    driver_id VARCHAR(50) PRIMARY KEY,
                    first_name VARCHAR(30),
                    last_name VARCHAR(30),
                    email VARCHAR(30), 
                    password VARCHAR(30)
            );
    """
    cur.execute(create_drivers_table)
    conn.commit()
    DRIVERS_CSV_FILE = 'data/drivers/drivers.csv'
    insert_into_drivers_table = """INSERT INTO drivers(driver_id, first_name, last_name, email, password) VALUES(%s,%s,%s,%s,%s);"""

    with open(DRIVERS_CSV_FILE, newline='') as csvfile:
        reader = csv.DictReader(csvfile)

        for row in reader:
            cur.execute(insert_into_drivers_table, [row['driver_id'], row['first_name'], row["last_name"], row['email'], row['password']])
    conn.commit()


def populate_rides_table():
    create_rides_table = """
            CREATE TABLE rides (
                    rideid VARCHAR(50) PRIMARY KEY,
                    userid VARCHAR(50),
                    driverid VARCHAR(50),
                    ride_time timestamp,
                    riders INTEGER,
                    ride_status VARCHAR(30)
            );
    """
    cur.execute(create_rides_table)
    conn.commit()
    DRIVERS_CSV_FILE = 'data/rides/rides.csv'
    insert_into_drivers_table = """INSERT INTO rides(rideid, userid, driverid,ride_time, riders, ride_status) VALUES(%s,%s,%s,%s,%s, %s);"""

    with open(DRIVERS_CSV_FILE, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            cur.execute(insert_into_drivers_table, [row['rideid'], row['userid'], row["driverid"], row['ride_time'], row['riders'], row['ride_status']])
    conn.commit()


if __name__ =='__main__':
    conn = psycopg2.connect(host="localhost", port="5432", user="postgres", password="postgres", database="postgresdb")
    cur = conn.cursor()
    # populate_ride_requests_table()
    # populate_drivers_table()
    #
    # populate_users_table()

    print("THE POSTGRES DATABASE HAS BEEN SEEDED.")

    populate_rides_table()