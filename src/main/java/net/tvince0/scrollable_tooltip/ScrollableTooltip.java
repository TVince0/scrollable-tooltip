package net.tvince0.scrollable_tooltip;

import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ScrollableTooltip implements ModInitializer {
	public static final String MOD_ID = "scrollable_tooltip";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

	}

	public static int currentXOffset = 0;
	public static int currentYOffset = 0;

	private static List<Text> currentItem;

	private static final int scrollSize = 5;

	public static void scrollUp () {
		currentYOffset -= scrollSize;
	}

	public static void scrollDown () {
		currentYOffset += scrollSize;
	}

	public static void scrollLeft () {
		currentXOffset -= scrollSize;
	}

	public static void scrollRight () {
		currentXOffset += scrollSize;
	}

	private static void resetScroll () {
		currentXOffset = 0;
		currentYOffset = 0;
	}

	private static boolean isEqual (List<Text> item1, List<Text> item2) {
		if (item1 == null || item2 == null || item1.size() != item2.size()) {
			return false;
		}

		for (int i = 0; i < item1.size(); ++i) {
			if (!item1.get(i).getString().equals(item2.get(i).getString())) {
				return false;
			}
		}
		return true;
	}

	public static void reset () {
		resetScroll();
		currentItem = null;
	}

	public static void setItem (List<Text> item) {
		if (!isEqual(currentItem, item)) {
			resetScroll();
			currentItem = item;
		}
	}
}
