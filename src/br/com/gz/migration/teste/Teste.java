package br.com.gz.migration.teste;

import java.text.NumberFormat;
import java.util.Locale;

public class Teste {

	public static void main(String[] args) {

		NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
		
		nf.setMaximumFractionDigits(2);
		nf.setMaximumIntegerDigits(2);
		
		System.out.println(nf.format(123456.789123));
		
	}

}
