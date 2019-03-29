package servidor;

import cliente.Cliente;
import main.Canal;

public class Servidor extends Thread{
	private Canal c;
	public Servidor() {
		super();
	}
	/**
	 * Construtor da Classe Servidor que recebe uma String para o nome da thread e 
	 * um objeto da classe Canal para se comunicar com as Threads Cliente
	 * @param name nome da Thread
	 * @param c objeto da classe Canal
	 * */
	public Servidor(String name, Canal c) {
		super(name);
		this.c = c;
	}
	@Override
	public void run() {
		while(true) {
			try {
				//A cada repetição, o servidor tenta pegar algum cliente armazenado no objeto 'c'
				Cliente cliente = c.getCliente();
				//Mensagem para indicar que um cliente está sendo atendido pelo servidor
				System.out.println("SERVIDOR: Atendendo cliente "+cliente.getID()+".");
				//Servidor está esperando o tempo necessario para o cliente pego
				Thread.sleep(1000*cliente.getTempo());
				//Servidor está notificando a primeira Thread cliente que estiver em modo bloqueado
				c.notificar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
