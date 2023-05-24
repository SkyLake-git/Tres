package tres.client.uno;

import com.tres.network.packet.protocol.PlayerGameActionPacket;
import com.tres.network.packet.protocol.types.PlayerGameAction;
import com.tres.network.packet.protocol.types.action.DrawCardPlayerGameAction;
import com.tres.network.packet.protocol.types.action.PlayCardPlayerGameAction;
import com.tres.network.packet.protocol.types.action.ShoutUnoPlayerGameAction;
import com.tres.network.packet.protocol.types.action.SkipTurnPlayerGameAction;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerTurnData implements com.tres.network.uno.game.PlayerTurnData<InGamePlayerData> {

	protected int phase;

	protected InGamePlayerData data;

	protected boolean unoAccused;

	protected boolean shoutedUno;

	public PlayerTurnData(InGamePlayerData data) {
		this.data = data;
		this.shoutedUno = false;
	}

	@Override
	public void tick() {

	}

	@Override
	public int getTick() {
		return 0;
	}

	@Override
	public boolean isUnoAccused() {
		return false;
	}

	@Override
	public InGamePlayerData getInGameData() {
		return this.data;
	}

	@Override
	public void onUnoAccused(short playerBy) {
		this.unoAccused = true;
	}

	@Override
	public boolean isShoutedUno() {
		return this.shoutedUno;
	}

	@Override
	public boolean haveToShoutUno() {
		return this.data.getPlayer().getCards().getContents().size() == 1;
	}

	@Override
	public int getPhase() {
		return this.phase;
	}

	@Override
	public void setPhase(int phase) {
		this.phase = phase;
	}

	@Override
	public void drawCard() {
		DrawCardPlayerGameAction action = new DrawCardPlayerGameAction();
		this.sendGameAction(action);
	}

	@Override
	public void shoutUno() {
		ShoutUnoPlayerGameAction action = new ShoutUnoPlayerGameAction();
		this.sendGameAction(action);

		this.shoutedUno = true;
	}

	@Override
	public void skipTurn() {
		SkipTurnPlayerGameAction action = new SkipTurnPlayerGameAction();
		this.sendGameAction(action);
	}

	@Override
	public void playCard(Collection<Integer> cardRuntimeIds) {
		PlayCardPlayerGameAction action = new PlayCardPlayerGameAction();
		action.cards = new ArrayList<>(cardRuntimeIds);
		this.sendGameAction(action);
	}

	protected void sendGameAction(PlayerGameAction action) {
		PlayerGameActionPacket packet = new PlayerGameActionPacket();
		packet.playerRuntimeId = this.getInGameData().getPlayer().getId();
		packet.action = action;

		this.getInGameData().getPlayer().getSession().sendDataPacket(packet);
	}
}
