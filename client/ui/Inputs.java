package client.ui;

import math.Vector2;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Inputs implements MouseInputListener, KeyListener {

	private static final Inputs instance = new Inputs();

	protected static ArrayList<String> pressedKeys;

	protected static ArrayList<String> typedKeys;
	protected static ArrayList<Integer> typedKeyCodes;

	protected static HashMap<Integer, Boolean> pressedButtons;

	protected static ArrayList<Integer> clickedButtons;

	protected static Vector2 mousePosition;

	protected static MouseEvent lastMoveEvent;

	protected static boolean mouseOnScreen;

	private Inputs() {
	}

	static {
		pressedKeys = new ArrayList<String>();
		typedKeys = new ArrayList<String>();
		typedKeyCodes = new ArrayList<Integer>();

		pressedButtons = new HashMap<Integer, Boolean>();
		clickedButtons = new ArrayList<Integer>();

		mousePosition = new Vector2(0, 0);
		mouseOnScreen = false;
	}


	public static void update() {
		clickedButtons.clear();
		typedKeys.clear();
		typedKeyCodes.clear();
	}

	public static ArrayList<String> getTypedKeys() {
		return typedKeys;
	}

	public static ArrayList<String> getPressedKeys() {
		return pressedKeys;
	}

	public static ArrayList<Integer> getTypedKeyCodes() {
		return typedKeyCodes;
	}

	public static HashMap<Integer, Boolean> getPressedButtons() {
		return pressedButtons;
	}

	public static ArrayList<Integer> getClickedButtons() {
		return clickedButtons;
	}

	public static Inputs getInstance() {
		return instance;
	}

	public static Vector2 getMousePosition() {
		return mousePosition;
	}

	public static boolean hasCursorOnScreen() {
		return mouseOnScreen;
	}

	public static boolean isButtonPressed(int button) {
		return pressedButtons.getOrDefault(button, false);
	}

	public static boolean isKeyPressed(String key) {
		return pressedKeys.contains(key);
	}

	public static boolean isButtonClicked(int button) {
		return clickedButtons.contains(button);
	}

	public static boolean isKeyTyped(String key) {
		return typedKeys.contains(key);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		typedKeys.add(String.valueOf(e.getKeyChar()));
		typedKeyCodes.add((int) e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(String.valueOf(e.getKeyChar()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(String.valueOf(e.getKeyChar()));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clickedButtons.add(e.getButton());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedButtons.put(e.getButton(), true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressedButtons.put(e.getButton(), false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOnScreen = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseOnScreen = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		MouseEvent converted = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, e.getComponent());
		Point p = converted.getPoint();
		mousePosition = new Vector2(p.x, p.y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MouseEvent converted = SwingUtilities.convertMouseEvent((Component) e.getSource(), e, e.getComponent());
		Point p = converted.getPoint();
		mousePosition = new Vector2(p.x, p.y);
		lastMoveEvent = e;
	}
}
