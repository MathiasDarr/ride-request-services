"""
Create & populate patients, providers tables
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



if __name__ =='__main__':
    conn = psycopg2.connect(host="localhost", port="5432", user="postgres", password="postgres", database="postgresdb")
    cur = conn.cursor()
    populate_ride_requests_table()

    print("THE POSTGRES DATABASE HAS BEEN SEEDED.")