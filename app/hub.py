from config import BUFFER_SIZE, WELCOME_MESSAGE, JOIN_MESSAGE
from socket import AF_INET, socket, SO_REUSEADDR, SOL_SOCKET, SOCK_STREAM
from threading import Thread
from packets.manager import PACKET_LIST
from packets.text_packet import TextPacket
from packets.quit_packet import QuitException

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
            TextPacket("Name: ").server_send([client])
            addresses[client] = client_address
            Thread(target=self.handle_client, args=(client,)).start()


    def handle_client(self, client):
        name = client.recv(BUFFER_SIZE).decode("utf8")
        TextPacket(WELCOME_MESSAGE % name).server_send([client])
        TextPacket(JOIN_MESSAGE % name).server_send(clients)
        clients[client] = name
        try:
            while True:
                msg = client.recv(BUFFER_SIZE)
                packet = PACKET_LIST[msg[0]](msg[1:])
                packet.server_receive(clients, client)
                packet.modify_data(msg, self, clients, client)
                packet.server_send(clients)
        except QuitException:
            pass


if __name__ == "__main__":
    hub = Hub('localhost', 33003, 1)
    hub.begin()
