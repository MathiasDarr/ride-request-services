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


fake = Faker()
fake.random.seed(4321)


keys = ['userid', 'first_name', 'last_name', 'email','password','phone_number', 'city']
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


