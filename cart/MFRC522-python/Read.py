#!/usr/bin/env python
# -*- coding: utf8 -*-

import RPi.GPIO as GPIO
import MFRC522
import signal
import requests

continue_reading = True

# Capture SIGINT for cleanup when the script is aborted

products = {"163,119,194,4":"Cookie 3.50", "243,17,70,166":"Ruffles 7.00"}  # products
url = "http://192.168.2.104:7777/publish/topico"


def send_data(rfid):
    prod = products[rfid]
    print("Product: ", prod)
    try:
        response = requests.post(url, data=prod)
        if response.status_code == 200:
            print("Sended")
        else:
            print("Fail")
    except Exception as err:
        print(err)


def end_read(signal, frame):
    global continue_reading
    print("Ctrl+C captured, ending read.")
    continue_reading = False
    GPIO.cleanup()

# Hook the SIGINT
signal.signal(signal.SIGINT, end_read)

# Create an object of the class MFRC522
MIFAREReader = MFRC522.MFRC522()

# Welcome message
print("Welcome to the MFRC522 data read example")
print("Press Ctrl-C to stop.")

# This loop keeps checking for chips. If one is near it will get the UID and authenticate
# while continue_reading:

# Scan for cards
(status, TagType) = MIFAREReader.Request(MIFAREReader.PICC_REQIDL)

print("status ", status, " Tag ", TagType)
# If a card is found
if status == MIFAREReader.MI_OK:
    print("Card detected")

# Get the UID of the card
(status, uid) = MIFAREReader.Anticoll()

# If we have the UID, continue
if status == MIFAREReader.MI_OK:

    # Print UID
    print("Card read UID: " + str(uid[0]) + "," + str(uid[1]) + "," + str(uid[2]) + "," + str(uid[3]))
    send_data(str(uid[0]) + "," + str(uid[1]) + "," + str(uid[2]) + "," + str(uid[3]))
    # This is the default key for authentication
    key = [0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF]

    # Select the scanned tag
    MIFAREReader.SelectTag(uid)
    data = MIFAREReader.DumpClassic1K_Data(key, uid)

    MIFAREReader.StopCrypto1()

    for block in data:
        b = ""
        for byte in block:
            b += chr(byte)
        print(b)
GPIO.cleanup()

