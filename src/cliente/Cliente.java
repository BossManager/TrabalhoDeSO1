package cliente;

import main.Canal;

public class Cliente extends Thread{
	//id do cliente
	private int ID;
	//tempo do cliente
	private int tempo;
	// objeto para se comunicar com o servidor
	private Canal c;
	
	public Cliente() {
		super();
	}
	/**
	 * Construtor da Classe Cliente que recebe uma String para o nome da thread, um inteiro para o identificador
	 * fornecido pela Thread Receptor, um inteiro para armazenar o tempo que será fornecido ao servidor na hora
	 * do atendimento  e um objeto da classe Canal para se comunicar com a Thread servidor
	 * @param name nome da Thread
	 * @param ID identificador da Thread
	 * @param tempo tempo requisitado pela Thread
	 * @param c objeto da classe Canal
	 * */
	public Cliente(String name, int ID, int tempo, Canal c) {
		super(name);
		this.ID = ID;
		this.tempo = tempo;
		this.c = c;
	}
	
	@Override
	public void run() {
		boolean b = true;
		try {
			// Cliente tenta conversar com o servidor
			b = c.conversarComServidor(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//se b for modificado para o valor 'false', significa que não havia vagas no buffer do objeto 'c'
		// e uma mensagem de erro é printada no console
		if(!b) {
			System.out.println("CLIENTE "+this.ID+": Erro: não há vagas.");
		}else {
			try {
				// A thread espera 1s antes de terminar sua execução
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Cliente "+ID+" saindo.");
		}
		
	}
	public int getID() {
		return ID;
	}
	
	public int getTempo() {
		return tempo;
	}
	
}
