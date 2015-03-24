package com.joker.myfw.log;

import org.apache.log4j.Level;

public class SeriousLevel extends Level {

	private static final long serialVersionUID = -8703390514146270958L;

	protected SeriousLevel(int level, String levelStr, int syslogEquivalent) {
		super(level, levelStr, syslogEquivalent);
	}
}
