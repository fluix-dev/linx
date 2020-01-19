class Packet:
    id = None

    def __init__(self, data):
        self.data = data

    def client_receive(self):
        pass

    def client_send(self, node):
        pass

    def server_receive(self, clients, client):
        pass

    def server_send(self, clients):
        pass

    def modify_data(self, data, hub, clients, client):
        pass
