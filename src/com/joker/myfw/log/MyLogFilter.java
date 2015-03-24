package com.joker.myfw.log;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class MyLogFilter extends Filter {

	int levelMin;
	int levelMax;

	public int getLevelMin() {
		return levelMin;
	}


	public void setLevelMin(int levelMin) {
		this.levelMin = levelMin;
	}


	public int getLevelMax() {
		return levelMax;
	}


	public void setLevelMax(int levelMax) {
		this.levelMax = levelMax;
	}

	@Override
	public int decide(LoggingEvent lgEvent) {
		int inputLevel = lgEvent.getLevel().toInt();
		if(inputLevel >= levelMin && inputLevel <= levelMax){
			return 0;
		}
		return -1;
	}
}
