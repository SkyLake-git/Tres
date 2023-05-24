package com.tres.utils;

public enum Colors {
	//Color end string, color reset

	BASE(String.valueOf((char) 27)),
	RESET("[0m"),

	// Regular Colors. Normal color, no bold, background color etc.
	BLACK("[0;30m"),    // BLACK
	RED("[0;31m"),      // RED
	GREEN("[0;32m"),    // GREEN
	YELLOW("[0;33m"),   // YELLOW
	BLUE("[0;34m"),     // BLUE
	MAGENTA("[0;35m"),  // MAGENTA
	CYAN("[0;36m"),     // CYAN
	WHITE("[0;37m"),    // WHITE

	// Bold
	BLACK_BOLD("[1;30m"),   // BLACK
	RED_BOLD("[1;31m"),     // RED
	GREEN_BOLD("[1;32m"),   // GREEN
	YELLOW_BOLD("[1;33m"),  // YELLOW
	BLUE_BOLD("[1;34m"),    // BLUE
	MAGENTA_BOLD("[1;35m"), // MAGENTA
	CYAN_BOLD("[1;36m"),    // CYAN
	WHITE_BOLD("[1;37m"),   // WHITE

	// Underline
	BLACK_UNDERLINED("[4;30m"),     // BLACK
	RED_UNDERLINED("[4;31m"),       // RED
	GREEN_UNDERLINED("[4;32m"),     // GREEN
	YELLOW_UNDERLINED("[4;33m"),    // YELLOW
	BLUE_UNDERLINED("[4;34m"),      // BLUE
	MAGENTA_UNDERLINED("[4;35m"),   // MAGENTA
	CYAN_UNDERLINED("[4;36m"),      // CYAN
	WHITE_UNDERLINED("[4;37m"),     // WHITE

	// Background
	BLACK_BACKGROUND("[40m"),   // BLACK
	RED_BACKGROUND("[41m"),     // RED
	GREEN_BACKGROUND("[42m"),   // GREEN
	YELLOW_BACKGROUND("[43m"),  // YELLOW
	BLUE_BACKGROUND("[44m"),    // BLUE
	MAGENTA_BACKGROUND("[45m"), // MAGENTA
	CYAN_BACKGROUND("[46m"),    // CYAN
	WHITE_BACKGROUND("[47m"),   // WHITE

	// High Intensity
	BLACK_BRIGHT("[0;90m"),     // BLACK
	RED_BRIGHT("[0;91m"),       // RED
	GREEN_BRIGHT("[0;92m"),     // GREEN
	YELLOW_BRIGHT("[0;93m"),    // YELLOW
	BLUE_BRIGHT("[0;94m"),      // BLUE
	MAGENTA_BRIGHT("[0;95m"),   // MAGENTA
	CYAN_BRIGHT("[0;96m"),      // CYAN
	WHITE_BRIGHT("[0;97m"),     // WHITE

	// Bold High Intensity
	BLACK_BOLD_BRIGHT("[1;90m"),    // BLACK
	RED_BOLD_BRIGHT("[1;91m"),      // RED
	GREEN_BOLD_BRIGHT("[1;92m"),    // GREEN
	YELLOW_BOLD_BRIGHT("[1;93m"),   // YELLOW
	BLUE_BOLD_BRIGHT("[1;94m"),     // BLUE
	MAGENTA_BOLD_BRIGHT("[1;95m"),  // MAGENTA
	CYAN_BOLD_BRIGHT("[1;96m"),     // CYAN
	WHITE_BOLD_BRIGHT("[1;97m"),    // WHITE

	// High Intensity backgrounds
	BLACK_BACKGROUND_BRIGHT("[0;100m"),     // BLACK
	RED_BACKGROUND_BRIGHT("[0;101m"),       // RED
	GREEN_BACKGROUND_BRIGHT("[0;102m"),     // GREEN
	YELLOW_BACKGROUND_BRIGHT("[0;103m"),    // YELLOW
	BLUE_BACKGROUND_BRIGHT("[0;104m"),      // BLUE
	MAGENTA_BACKGROUND_BRIGHT("[0;105m"),   // MAGENTA
	CYAN_BACKGROUND_BRIGHT("[0;106m"),      // CYAN
	WHITE_BACKGROUND_BRIGHT("[0;107m");     // WHITE

	private final String code;

	Colors(String code) {
		this.code = (char) 27 + code;
	}

	public static String wrap(String target, Colors color) {
		return color.toString() + target + Colors.RESET;
	}

	@Override
	public String toString() {
		return code;
	}
}
