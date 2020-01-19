from config import BUFFER_SIZE
from socket import AF_INET, socket, SO_REUSEADDR, SOL_SOCKET, SOCK_STREAM
from threading import Thread
from packets.manager import PACKET_LIST

clients = {}
addresses = {}


class Hub:
    def __init__(self, host, port, level):
        self.addr = (host, port)
        self.level = level
        self.server_socket = socket(AF_INET, SOCK_STREAM)
        self.server_socket.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
        self.server_socket.bind(self.addr)

    def begin(self):
        self.server_socket.listen(5)
        print("Waiting for connection...")

        accept_thread = Thread(target=self.accept_incoming_connections)
        accept_thread.start()
        accept_thread.join()

        self.server_socket.close()

    def accept_incoming_connections(self):
        while True:
            client, client_address = self.server_socket.accept()
            print("%s:%s has connected." % client_address)
            client.send(bytes([0x10]) + bytes("Name: ", "utf8"))
            addresses[client] = client_address
            Thread(target=self.handle_client, args=(client,)).start()


    def handle_client(self, client):
        name = client.recv(BUFFER_SIZE).decode("utf8")
        welcome = 'Welcome %s!' % name
        #packet = TextPacket
        client.send(bytes([0x10]) + bytes(welcome, "utf8"))
        #msg = "%s has joined the chat!" % name
        #broadcast(bytes(msg, "utf8"))
        clients[client] = name
        while True:
            msg = client.recv(BUFFER_SIZE)
            if msg != bytes("{quit}", "utf8"):
                packet = PACKET_LIST[msg[0]](msg)
                packet.server_receive()
                packet.set_data(name + ": " + msg[1:].decode("utf8"))
                packet.server_send(clients)
            else:
                client.send(bytes("{quit}", "utf8"))
                client.close()
                del clients[client]
                broadcast(TextPacket(bytes("%s has left the chat." % name, "utf8")))
                break


def broadcast(packet):
    packet.server_send(self)


if __name__ == "__main__":
    hub = Hub('localhost', 33003, 1)
    hub.begin()
