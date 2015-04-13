package de.eah_jena.source;

import java.io.*;

public class GenerateHash {

	public void generateHash() {

		// Konsolenobjekt für Eingabe initialisieren
		BufferedReader console = new BufferedReader(new InputStreamReader(
				System.in));
		String Str = null;

		// Ausgangstext einlesen
		System.out
				.println("Bitte Text angeben, von dem Hashwert erzeugt werden soll:");
		try {
			Str = new String(console.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// erzeugten Haswert ausgeben
		System.out.println("\nHashwert für eingegebenen Text: "
				+ Str.hashCode());
	}

}