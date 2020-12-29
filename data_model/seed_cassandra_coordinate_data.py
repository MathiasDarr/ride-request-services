"""
Create & populate the ride coorinates table
"""
# !/usr/bin/env python3


import os
import csv
from utilities.cassandra_utilities import createCassandraConnection, createKeySpace


def populate_coordinates_table():
    create_ride_coordiantes_table = """CREATE TABLE IF NOT EXISTS coordinates(
        rideid text, 
        time timestamp,
        latitude float,
        longitude float,
        PRIMARY KEY(rideid, time));
    """
    dbsession.execute(create_ride_coordiantes_table)

    insert_trip_data_point = """INSERT INTO coordinates(rideid, time, latitude, longitude) VALUES(%s,%s,%s,%s);"""

    TRIP_DATA_CSV_FILE = 'data/ride_coordinates/coordinates.csv'

    with open(TRIP_DATA_CSV_FILE, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            dbsession.execute(insert_trip_data_point, [ row['rideid'], row['time'], float(row['latitude']),float(row['longitude'])])


if __name__ == '__main__':
    dbsession = createCassandraConnection()
    createKeySpace("ks1", dbsession)
    try:
        dbsession.set_keyspace('ks1')
    except Exception as e:
        print(e)

    populate_coordinates_table()
    print("CASSANDRA DATABASE SEEDED")
