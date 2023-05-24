package tres.client.network.handler;

import com.tres.network.packet.protocol.*;

public interface PacketHandler {

	void handleText(TextPacket packet);

	void handleDisconnect(DisconnectPacket packet);

	void handleAliveSignal(AliveSignalPacket packet);

	void handleLoginStatus(LoginStatusPacket packet);

	void handlePlayerInitialized(PlayerInitializedPacket packet);

	void handleGameEvent(GameEventPacket packet);

	void handleGameLevel(GameLevelPacket packet);

	void handleServerToClientHandshake(ServerToClientHandshakePacket packet);

	void handlePlayerGameAction(PlayerGameActionPacket packet);

	void handleGameResult(GameResultPacket packet);

	void handleCardTransaction(CardTransactionPacket packet);

	void handleCardList(CardListPacket packet);

	void handleFileChunkData(FileChunkDataPacket packet);

	void handleFileChunkStream(FileChunkStreamPacket packet);

	void handleAddPlayer(AddPlayerPacket packet);

	void handleRemovePlayer(RemovePlayerPacket packet);

	void handleAvailableGames(AvailableGamesPacket packet);
}
