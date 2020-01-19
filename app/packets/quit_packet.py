import sys

from packets.packet import Packet

class QuitException(Exception): pass

class QuitPacket(Packet):
    id = 0x99

    def client_receive(self):
        sys.stdout.write('\r' + self.data + '\n>>> ')

    def client_send(self, node):
        node.client_socket.send(bytes([self.id]))

    def server_receive(self, clients, client):
        sys.stdout.write('[Q] ' + clients[client] + '\n')
        self.server_send([client])
        client.close()
        del clients[client]
        raise QuitException

    def server_send(self, clients):
        for sock in clients:
            sock.send(bytes([self.id]))
