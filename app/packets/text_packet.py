import sys

from packets.packet import Packet

class TextPacket(Packet):
    id = 0x10

    def __init__(self, data):
        self.data = data

    def client_receive(self):
        sys.stdout.write('\r' + self.data[1:] + '\n>>> ')

    def client_send(self, node):
        node.client_socket.send(bytes([self.id]) + bytes(self.data, "utf8"))

    def server_receive(self):
        sys.stdout.write('[M] ' + self.data[1:].decode('utf8') + '\n')

    def server_send(self, clients):
        for sock in clients:
            sock.send(self.data)

    def set_data(self, data):
        self.data = bytes([self.id]) + bytes(data, "utf8")
