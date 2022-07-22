/**
 * 
 */
package com.adidas.subscription.util;

import java.text.NumberFormat;

import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

/**
 * @author Telmo
 *
 */
public final class StopWatchUtils {

	
	private StopWatchUtils() {
		//utiliy class
	}
	
	//On Spring 5 prettyPrint is only present in ns :(
	public static String prettyPrintStopWatchMilis(final StopWatch sw) {
		StringBuilder sb = new StringBuilder();

		sb.append("StopWatch '" + sw.getId() + "': running time (millis) = " + sw.getTotalTimeMillis() + " ms");
		sb.append("\n-----------------------------------------\n");
		sb.append("ms     %     Task name\n");
		sb.append("-----------------------------------------\n");
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumIntegerDigits(5);
		nf.setGroupingUsed(false);
		NumberFormat pf = NumberFormat.getPercentInstance();
		pf.setMinimumIntegerDigits(3);
		pf.setGroupingUsed(false);
		for (TaskInfo task : sw.getTaskInfo()) {
			sb.append(nf.format(task.getTimeMillis())).append("  ");
			sb.append(pf.format(task.getTimeSeconds() / sw.getTotalTimeSeconds())).append("  ");
			sb.append(task.getTaskName()).append("\n");
		}
		
		return sb.toString();
	}
}
