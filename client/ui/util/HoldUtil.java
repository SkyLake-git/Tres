package client.ui.util;

import client.ui.BaseVisible;

import java.awt.*;
import java.util.ArrayList;

public class HoldUtil {

	public static ArrayList<Point> getOffsets(ArrayList<BaseVisible> list, Point point, int width, int gap) {
		int singleWidth = (list.get(0)).getRect().width;
		int count = list.size();
		int singleCenter = singleWidth / 2;
		ArrayList<Point> result = new ArrayList<Point>();

		int center = point.x - singleCenter;

		int widthWill = singleWidth * count;
		int widthUse = widthWill / width;

		if (widthUse > 1.0) {
			singleWidth /= widthUse;
		}

		int finalWidth = singleWidth * count;

		int start = singleCenter - (finalWidth / 2);

		int ind = 0;
		for (BaseVisible visible : list) {
			int x = start + (finalWidth * ind);
			result.add(new Point(x, point.y));
			ind++;
		}

		return result;
	}
}
