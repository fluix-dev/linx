from packets.text_packet import TextPacket
from packets.quit_packet import QuitPacket

PACKET_LIST = {
    TextPacket.id: TextPacket,
    QuitPacket.id: QuitPacket,
}
