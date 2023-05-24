package tres.client.uno;

import com.tres.network.packet.protocol.PlayerGameActionPacket;
import com.tres.network.packet.protocol.types.action.AccusateUnoPlayerGameAction;

public class InGamePlayerData implements com.tres.network.uno.game.InGamePlayerData<ClientPlayer, ClientGame, PlayerTurnData> {

	protected ClientPlayer player;

	protected ClientGame game;

	protected PlayerTurnData turnData;

	public InGamePlayerData(ClientPlayer player, ClientGame game) {
		this.player = player;
		this.game = game;
		this.turnData = null;
	}

	@Override
	public ClientPlayer getPlayer() {
		return this.player;
	}

	@Override
	public ClientGame getGame() {
		return this.game;
	}

	@Override
	public PlayerTurnData getTurnData() {
		return this.turnData;
	}

	@Override
	public void createTurnData() {
		if (this.turnData != null) {
			return;
		}

		this.turnData = new PlayerTurnData(this);
	}

	@Override
	public void destroyTurnData() {
		this.turnData = null;
	}


	@Override
	public boolean isMyTurn() {
		return false;
	}

	@Override
	public void accusateUno() {
		AccusateUnoPlayerGameAction action = new AccusateUnoPlayerGameAction();
		PlayerGameActionPacket packet = new PlayerGameActionPacket();
		packet.action = action;
		packet.playerRuntimeId = this.player.getId();

		this.player.getSession().sendDataPacket(packet);
	}
}
