import sys

from packets.packet import Packet

class TextPacket(Packet):
    id = 0x10

    def client_receive(self):
        sys.stdout.write('\r' + self.data + '\n>>> ')

    def client_send(self, node):
        node.client_socket.send(bytes([self.id]) + bytes(self.data, "utf8"))

    def server_receive(self, clients, client):
        sys.stdout.write('[M] ' + str(self.data) + '\n')

    def server_send(self, clients):
        for sock in clients:
            sock.send(bytes([self.id]) + bytes(self.data, "utf8"))

    def modify_data(self, data, hub, clients, client):
        self.data = clients[client] + ": " + data.decode("utf8")
