package com.mycompany.despertador;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class RelogioAlarme implements Runnable {
    
    private final LocalTime horarioAlarme;
    private final String arquivoSom;
    private final Scanner scanner;
    
    RelogioAlarme(LocalTime horarioAlarme, String arquivoSom, Scanner scanner) {
	this.horarioAlarme = horarioAlarme;
	this.arquivoSom = arquivoSom;
	this.scanner = scanner;
    }
    
    @Override
    public void run() {
	
	while (LocalTime.now().isBefore(horarioAlarme)) {
	    try {
		Thread.sleep(1000);
		
		LocalTime horaAtual = LocalTime.now();
		
		int horas = horaAtual.getHour();
		int minutos = horaAtual.getMinute();
		int segundos = horaAtual.getSecond();
		
		System.out.printf("\r%02d:%02d:%02d", horas, minutos, segundos);
	    } 
	    catch (InterruptedException ex) {
		System.out.println("Thread interrompida");
	    }
	}
	
	System.out.println("\n----------------\n! ALARME DISPARADO !\n----------------\n");
	tocarAlarme(arquivoSom);
    }
    
    private void tocarAlarme(String arquivoSom) {
	File arquivoAudio = new File(arquivoSom);
	
	try (AudioInputStream streamAudio = AudioSystem.getAudioInputStream(arquivoAudio)) {
	    Clip clip = AudioSystem.getClip();
	    clip.open(streamAudio);
	    clip.start();
	    
	    System.out.println("Pressione ENTER para desligar o alarme.");
	    scanner.nextLine();
	    clip.stop();
	    
	    scanner.close();
	}
	catch(UnsupportedAudioFileException ex) {
	    System.out.println("Formato de arquivo de áudio inválido.");
	}
	catch(LineUnavailableException ex) {
	    System.out.println("Áudio indisponível.");
	}
	catch(IOException ex) {
	    System.out.println("Erro na leitura do arquivo de áudio.");
	} 
    }
}
