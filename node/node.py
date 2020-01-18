#!/usr/bin/env python3
"""Script for Tkinter GUI chat client."""
from socket import AF_INET, socket, SOCK_STREAM
from threading import Thread
import sys


def receive():
    """Handles receiving of messages."""
    while True:
        try:
            msg = client_socket.recv(BUFSIZ).decode("utf8")
            sys.stdout.write('\r' + msg + '\n>>> ')
        except OSError:  # Possibly client has left the chat.
            break


def send():  # event is passed by binders.
    """Handles sending of messages."""
    msg = input(">>> ")
    client_socket.send(bytes(msg, "utf8"))
    if msg == "{quit}":
        client_socket.close()


def on_closing():
    """This function is to be called when the window is closed."""
    my_msg.set("{quit}")
    send()

#----Now comes the sockets part----
HOST = input('Enter host: ')
if not HOST:
    HOST = 'localhost'

PORT = input('Enter port: ')
if not PORT:
    PORT = 33001
else:
    PORT = int(PORT)

BUFSIZ = 1024
ADDR = (HOST, PORT)

client_socket = socket(AF_INET, SOCK_STREAM)
client_socket.connect(ADDR)

receive_thread = Thread(target=receive)
receive_thread.start()

while True:
    send()
