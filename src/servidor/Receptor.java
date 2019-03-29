package servidor;

import java.util.Scanner;

import cliente.Cliente;
import main.Canal;

public class Receptor extends Thread{
	// objeto Canal para passar como parâmetro na hora da criação das Threads Cliente
	private Canal c;
	//contador de IDs para as Threads
	private int ids = 1;
	
	public Receptor() {
		super();
	}
	/**
	 * Construtor da Classe Cliente que recebe umaa String para o nome da thread e um objeto da classe Cliente
	 * 'c'.
	 * @param name nome da Thread
	 * @param c objeto da classe Canal
	 * */
	public Receptor(String name, Canal c) {
		super(name);
		this.c = c;
	}
	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		//em cada repetição espera um numero ser digitado
		while(true) {
			int tempo = sc.nextInt();
			System.out.println("RECEPCAO: Cliente "+ids+" chegou e deseja pedido de "+tempo+" segundos.");
			//Cria nova Thread cliente passando como parâmetro um nome, um ID, o tempo digitado e o objeto Canal
			// para a comunicação com a Thread Servidor
			new Cliente("Cliente "+ids,ids,tempo,c).start();
			//incremento do ID para a proxima Thread cliente
			ids++;
		}
		
	}
}
