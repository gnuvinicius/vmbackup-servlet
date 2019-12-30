package br.com.vmbackup.negocio;

import java.util.Calendar;

public class StopCloneTime {

	public static Boolean getStopCloneTime() {

		ReadFiles readFiles = new ReadFiles();
		String[] hourBegin = readFiles.getHour().getHourBegin().split(":");
		String[] hourEnd = readFiles.getHour().getHourEnd().split(":");

		if (Integer.parseInt(hourBegin[0]) > Integer.parseInt(hourEnd[0])) {
			if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < Integer.parseInt(hourBegin[0])) {
				if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(hourEnd[0])) {
					if (Calendar.getInstance().get(Calendar.MINUTE) >= Integer.parseInt(hourEnd[1])) {
						return true;
					}
				}
			}
		}

		if (Integer.parseInt(hourBegin[0]) < Integer.parseInt(hourEnd[0])) {
			if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(hourEnd[0])) {
				if (Calendar.getInstance().get(Calendar.MINUTE) >= Integer.parseInt(hourEnd[1])) {
					return true;
				}
			}
		}
		return false;
	}
}