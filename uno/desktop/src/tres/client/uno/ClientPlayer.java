package tres.client.uno;

import com.tres.network.packet.PlayerInfo;
import com.tres.network.uno.NetworkCard;
import com.tres.network.uno.container.FuncDifferenceContainerListener;
import org.jetbrains.annotations.Nullable;
import tres.client.ClientSession;

public class ClientPlayer extends ViewPlayer {

	protected ClientSession session;

	protected InGamePlayerData inGameData;

	public ClientPlayer(ClientSession session, short runtimeId, PlayerInfo info) {
		super(runtimeId, info);

		this.session = session;
		this.inGameData = null;

		this.initListener();
	}

	private void initListener() {
		FuncDifferenceContainerListener<NetworkCard> listener = new FuncDifferenceContainerListener<NetworkCard>(
				(slot, card) -> {
					this.session.getLogger().info("Client origin container modified");
					if (!card.hasCardInfo()) {
						this.session.disconnect("Origin container cards must be send with CardInfo");
					}
				},
				(slot, card) -> {
					if (!card.hasCardInfo()) {
						this.session.disconnect("Origin container cards must be send with CardInfo");
					}
				}
		) {
		};

		listener.listen(this.cards);
	}

	public void onJoinGame(int gameId) {
		if (this.inGameData != null) {
			return;
		}

		if (this.session.getActions().getLatestGameLevel() == null) {
			return;
		}

		this.inGameData = new InGamePlayerData(this, new ClientGame(gameId, this.session.getActions().getLatestGameLevel().rule.getUnoRule()));
	}

	public void onLeftGame() {
		this.inGameData = null;
	}

	public ClientSession getSession() {
		return session;
	}

	public @Nullable InGamePlayerData getInGameData() {
		return inGameData;
	}
}
