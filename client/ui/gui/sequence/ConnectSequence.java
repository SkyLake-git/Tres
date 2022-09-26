package client.ui.gui.sequence;

import client.Main;
import client.ui.BasicButton;
import client.ui.BasicTextField;
import client.ui.Button;
import client.ui.VisibleManager;
import client.ui.gui.MainPanel;

import java.awt.*;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ConnectSequence extends BaseSequence {

	protected VisibleManager visibleManager;

	protected BasicTextField address;

	protected BasicTextField port;

	protected Button exitButton;

	protected Button confirmButton;

	public ConnectSequence(MainPanel panel) {
		super(panel);
		this.visibleManager = new VisibleManager();
		int baseY = MainPanel.HEIGHT / 2;
		int baseX = MainPanel.WIDTH / 2;
		int x = baseX - (120 / 2);
		this.address = new BasicTextField(new Point(x, baseY - (baseY / 2)), 360, 60);
		this.address.getTextBuilder().insert(0, "127.0.0.1");
		this.port = new BasicTextField(new Point(x, baseY), 360, 60);
		this.port.getTextBuilder().insert(0, "34560");
		this.exitButton = new BasicButton(new Point(x - (MainPanel.WIDTH / 4), baseY + (baseY / 2)), 120, 60, Color.red, "Main Menu");
		this.confirmButton = new BasicButton(new Point(x, baseY + (baseY / 2)), 120, 60, Color.green, "Confirm");

		this.visibleManager.add(this.address);
		this.visibleManager.add(this.port);
		this.visibleManager.add(this.exitButton);
		this.visibleManager.add(this.confirmButton);

	}

	@Override
	public int update(ISequence parent) {
		this.visibleManager.update();
		if (this.exitButton.isClicked()) {
			this.panel.setSequence(new MainSequence(this.panel));
		}

		if (this.confirmButton.isClicked()) {
			InetAddress address = null;
			boolean reachable = false;
			try {
				address = Inet4Address.getByName(this.address.getText());
				if (address.isReachable(3000)) {
					reachable = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (reachable) {
				try {
					Main.client.start(new InetSocketAddress(address, Integer.parseInt(this.port.getText())));
				} catch (IOException e) {
					reachable = false;
				}
			}


		}
		return MainPanel.UPDATE_SUCCESS;
	}

	@Override
	public void render(Graphics g) {
		this.visibleManager.draw(g);
	}

	@Override
	public void close() {

	}
}
