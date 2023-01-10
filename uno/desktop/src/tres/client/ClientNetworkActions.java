package tres.client;

import com.tres.network.packet.cipher.CommonKeyNetworkCipher;
import com.tres.network.packet.cipher.CryptoException;
import com.tres.network.packet.cipher.NetworkCipher;
import com.tres.network.packet.protocol.ClientToServerHandshakePacket;
import com.tres.network.packet.protocol.ServerToClientHandshakePacket;
import tres.client.event.packet.DataPacketReceiveEvent;
import tres.client.network.PacketResponsePromise;
import com.tres.network.packet.DataPacket;
import com.tres.network.packet.protocol.AvailableGamesPacket;
import com.tres.network.packet.protocol.RequestAvailableGamesPacket;
import com.tres.network.packet.protocol.types.AvailableGameInfo;
import com.tres.promise.Promise;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class ClientNetworkActions {

	protected ClientSession session;

	private final String eventRegistererId;

	protected ArrayList<AvailableGameInfo> availableGames;
	protected int lastFetchAvailableGames;

	protected PacketResponsePromise<RequestAvailableGamesPacket, AvailableGamesPacket> availableGamePromise;

	public ClientNetworkActions(ClientSession session) {
		this.session = session;
		this.eventRegistererId = this.session.getClient().getEventEmitter().on(DataPacketReceiveEvent.class, (channel, event) -> {
			this.onDataPacketReceive(event);
		});

		this.availableGames = new ArrayList<>();
		this.availableGamePromise = null;
		this.lastFetchAvailableGames = 0;
	}

	public void close() {
		this.session.getClient().getEventEmitter().off(this.eventRegistererId);
	}

	private void onDataPacketReceive(DataPacketReceiveEvent event) {
		DataPacket packet = event.getPacket();

		if (packet instanceof AvailableGamesPacket) {

			this.availableGames.clear();
			this.availableGames.addAll(((AvailableGamesPacket) packet).games);

		}
	}

	public void tick() {
	}

	public ArrayList<AvailableGameInfo> getCachedAvailableGames() {
		return this.availableGames;
	}

	protected Promise<AvailableGamesPacket> fetchAvailableGames() {
		if (this.availableGamePromise != null) {
			return this.availableGamePromise.getPromise();
		}

		RequestAvailableGamesPacket request = new RequestAvailableGamesPacket();
		this.availableGamePromise = new PacketResponsePromise<>(this.session, request, AvailableGamesPacket.class);
		try {
			this.availableGamePromise.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		this.session.sendDataPacket(request);

		this.availableGamePromise.getPromise().onComplete(() -> {
			this.availableGamePromise.close();
			this.availableGamePromise = null;
		});

		return this.availableGamePromise.getPromise();
	}

	public Promise<ArrayList<AvailableGameInfo>> getAvailableGames() {
		Promise<ArrayList<AvailableGameInfo>> p = new Promise<>();

		if (this.session.getTick() - this.lastFetchAvailableGames > 80) {
			Promise<AvailableGamesPacket> packetPromise = this.fetchAvailableGames();
			packetPromise.onSuccess(() -> {
				p.resolve(packetPromise.getResult().games);
			});

			packetPromise.onFailure(p::reject);

			this.lastFetchAvailableGames = this.session.getTick();
		} else {
			p.resolve(this.getCachedAvailableGames());
		}

		return p;
	}

	public void cipherHandshake(SecretKey secretKey, NetworkCipher beforeEncryption){

		byte[] key = secretKey.getEncoded();
		String algorithm = secretKey.getAlgorithm();

		try {
			key = Base64.getEncoder().encode(beforeEncryption.encrypt(key));
			algorithm = Base64.getEncoder().encodeToString(beforeEncryption.encrypt(algorithm.getBytes()));
		} catch (CryptoException e) {
			throw new RuntimeException(e);
		}

		ClientToServerHandshakePacket handshake = new ClientToServerHandshakePacket();
		handshake.spec = new SecretKeySpec(key, algorithm);

		this.session.sendDataPacket(handshake, true);
	}

}
