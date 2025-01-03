package com.mycompany.despertador;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Despertador {

    public static void main(String[] args) {
        
	Scanner scanner = new Scanner(System.in);
	DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm:ss");
	LocalTime horaDespertador = null;
	String arquivoSom = "mixkit-classic-alarm-995.wav";
	
	while (horaDespertador == null) {
	    try {
		System.out.print("Defina o horário para o alarme (HH:MM:SS): ");
		String inputHora = scanner.nextLine();
	
		horaDespertador = LocalTime.parse(inputHora, formatador);
		System.out.println("Alarme definido para " + horaDespertador);
	    }
	    catch(DateTimeParseException erro) {
		System.out.println("Formato de horário inválido!");
	    }
	}
	
	RelogioAlarme relogioAlarme = new RelogioAlarme(horaDespertador, arquivoSom, scanner);
	Thread threadAlarme = new Thread(relogioAlarme);
	threadAlarme.start();
    }
}
