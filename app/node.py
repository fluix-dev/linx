from config import BUFFER_SIZE
from packets.manager import PACKET_LIST
from socket import AF_INET, socket, SOCK_STREAM
from threading import Thread
from packets.quit_packet import QuitPacket


import sys

kill = False

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
        try:
            while send_thread.is_alive():
                send_thread.join(1)
        except KeyboardInterrupt:
            QuitPacket('').client_send(self)
            self.client_socket.close()
            sys.exit()

    def receive(self):
        while True:
            try:
                msg = self.client_socket.recv(BUFFER_SIZE)
                packet = PACKET_LIST[msg[0]](msg[1:].decode())
                packet.client_receive()
            except OSError:
                break

    def send(self):
        while not kill:
            msg = input()
            CURSOR_UP_ONE = '\x1b[1A'
            ERASE_LINE = '\x1b[2K'
            sys.stdout.write(CURSOR_UP_ONE + ERASE_LINE)

            packet = PACKET_LIST[0x10](msg)
            packet.client_send(self)


if __name__ == "__main__":
    node = Node('localhost', 33003)
    node.begin()
