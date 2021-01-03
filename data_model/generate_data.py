"""
Generate some data with faker & write to csvs
"""
# !/usr/bin/env python3
from random import shuffle, seed

from faker import Faker
from faker.providers.person.en import Provider
import csv
import random
import uuid

import numpy as np

fake = Faker()
fake.random.seed(4321)


def generate_drivers_data():
    keys = ['driverid', 'first_name', 'last_name', 'email', 'password', 'phone_number', 'city', 'seats', 'average_shift_length']
    cities = ['Seattle', 'Tacoma', 'Bellevue', 'Everett']
    cities_to_area_code_map = {'Seattle': '206', 'Tacoma': '253', 'Bellevue': '425', 'Everett': '425'}
    seats = [2,3,4,5]
    with open('data/drivers/drivers.csv', 'w') as output_file:
        dict_writer = csv.DictWriter(output_file, keys)
        dict_writer.writeheader()
        drivers = []

        for i in range(20000):
            first_name = fake.first_name()
            last_name = fake.last_name()
            email = first_name + last_name + '@gmail.com'
            password = '1!ZionTF'
            city = random.choice(cities)
            area_code = cities_to_area_code_map[city]
            nseats = random.choice(seats)
            phone_number = area_code + '-' + ''.join(random.choice('123456789') for _ in range(3)) + '-' + ''.join(
                random.choice('123456789') for _ in range(3))

            ramdon_value_average_trip_length = random.randrange(40, 600)

            driver = {'driverid': str(uuid.uuid4()), 'first_name': first_name, 'last_name': last_name, 'email': email,
                    'password': password, 'city': city, 'phone_number': phone_number, 'seats':nseats, 'average_shift_length':np.random.normal(ramdon_value_average_trip_length,5)}
            drivers.append(driver)
        dict_writer.writerows(drivers)



def generate_users_data():

    keys = ['userid', 'first_name', 'last_name', 'email','password','phone_number', 'city', ]
    cities = ['Seattle', 'Tacoma', 'Bellevue', 'Everett']
    cities_to_area_code_map = {'Seattle':'206','Tacoma':'253', 'Bellevue':'425', 'Everett':'425'}


    with open('data/users/users.csv', 'w') as output_file:
        dict_writer = csv.DictWriter(output_file, keys)
        dict_writer.writeheader()

        users = []

        for i in range(200000):
            userid = str(uuid.uuid4())
            first_name = fake.first_name()
            last_name = fake.last_name()
            email = first_name + last_name + '@gmail.com'
            password = '1!ZionTF'
            city = random.choice(cities)
            area_code = cities_to_area_code_map[city]
            phone_number = area_code +'-' + ''.join(random.choice('123456789') for _ in range(3)) + '-' + ''.join(random.choice('123456789') for _ in range(3))
            user = {'userid' : str(uuid.uuid4()), 'first_name':first_name, 'last_name':last_name, 'email':email, 'password':password, 'city':city, 'phone_number':phone_number}
            users.append(user)
        dict_writer.writerows(users)


if __name__=='__main__':
    generate_users_data()
    print("DATA HAS BEEN GENERATED")
