package br.com.vmbackup.negocio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import br.com.vmbackup.modelo.Hour;
import br.com.vmbackup.modelo.Sequence;


public class ReadFiles {

	public void setSequence(Sequence sequence) {
		URL path = getClass().getResource("sequence.txt");
		saveInFile(sequence, path.getFile());
	}

	public Sequence getSequence() {
		Sequence sequence = new Sequence();
		URL path = getClass().getResource("sequence.txt");
		try {
			sequence = (Sequence) getInFile(sequence, path.getFile());
			if (sequence.getSequence() == 0) {
				setSequence(new Sequence(1));
			}
			return sequence = (Sequence) getInFile(sequence, path.getFile());
		} catch (NullPointerException e) {
			setSequence(new Sequence(1));
			return sequence = (Sequence) getInFile(sequence, path.getFile());
		}

	}

	public void setHour(Hour hour) {
		URL path = getClass().getResource("hour.txt");
		saveInFile(hour, path.getFile());
	}

	public Hour getHour() {
		Hour hour = new Hour();
		try {
			URL path = getClass().getResource("hour.txt");
			return (Hour) getInFile(hour, path.getFile());
		} catch (NullPointerException e) {
			return hour;
		}

	}

	public void saveInFile(Object obj, String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(obj);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object getInFile(Object obj, String path) {
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			obj = (Object) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}
}