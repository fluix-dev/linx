from config import BUFFER_SIZE
from packets.manager import PACKET_LIST
from socket import AF_INET, socket, SOCK_STREAM
from threading import Thread


import sys


class Node():
    def __init__(self, host, port):
        self.addr = (host, port)
        self.client_socket = socket(AF_INET, SOCK_STREAM)

    def begin(self):
        self.client_socket.connect(self.addr)

        receive_thread = Thread(target=self.receive)
        receive_thread.start()

        send_thread = Thread(target=self.send)
        send_thread.start()

    def receive(self):
        while True:
            try:
                msg = self.client_socket.recv(BUFFER_SIZE).decode("utf8")
                packet = PACKET_LIST[ord(msg[0])](msg[1:])
                packet.client_receive()
            except OSError:
                break

    def send(self):
        while True:
            msg = input()
            CURSOR_UP_ONE = '\x1b[1A'
            ERASE_LINE = '\x1b[2K'
            sys.stdout.write(CURSOR_UP_ONE + ERASE_LINE)

            packet = PACKET_LIST[0x10](msg)
            packet.client_send(self)

            if msg == "{quit}":
                self.client_socket.close()

if __name__ == "__main__":
    node = Node('localhost', 33003)
    node.begin()
