package main;

import java.util.ArrayList;

import cliente.Cliente;

public class Canal {
	
	// tamanho do buffer
	private int buffer_size = 0;
	// buffer de clientes
	private  ArrayList<Cliente> buffer;
	// flag para sinalizar disponibilidade do servidor
	private boolean ocupado;
	// flag para sinalizar se há cliente esperando
	private boolean tem_cliente;
	
	/**
	 *Construtor da Classe Canal que recebe um inteiro para definir o limite da ArrayList de nome buffer que 
	 *armazenará os clientes.
	 *@param bs limite maximo de vagas
	 * */
	public Canal(int bs) {
		buffer_size = bs;
		buffer = new ArrayList<>();
		ocupado = false;
		tem_cliente = false;
	}

	/**
	 * Metodo para permitir o servidor se 'comunicar' com os clientes.
	 * @return Cliente thread Cliente que será atendida pelo servidor
	 * */
	public synchronized Cliente getCliente() throws InterruptedException {
		//Um desvio condicional para resetar a flag 'tem_cliente' e a 'flag' ocupado para previnir uma possivel situação
		//na qual o servidor e o primeiro cliente que chegar acabem 'dormindo' ao mesmo tempo
		if(buffer.size()==0) {
			tem_cliente = false;
			ocupado = false;
		}
		// o servidor checa se tem clientes esperando no buffer
		if(!tem_cliente) {
			ocupado = false;
			//esperando clientes chegarem
			System.out.println("SERVIDOR: Esperando pedidos.");
			//entra em modo de 'espera' até o primeiro cliente chegar e notifica-lo(acordar)
			wait();
		}
		System.out.println("SERVIDOR: Esperando dados do cliente.");
		ocupado = true;
		//o primeiro cliente que chegou é removido do ArrayList e retornado para o servidor
		Cliente cn = buffer.get(0);
		buffer.remove(0);
		return cn;
	}
	/**
	 * Metodo para permitir o cliente se 'comunicar' com o servidor.
	 * @param c Objeto cliente para guardar no buffer
	 * @return boolean
	 * */
	public synchronized boolean conversarComServidor(Cliente c) throws InterruptedException {
		//Primeiro verifica se o servidor esta ocupado através da flag 'ocupado'
		if(ocupado) {
			//estando ocupado, o cliente verifica se há espaços no buffer
			//Se o buffer estiver cheio, o metodo retorna 'false'e a Thread cliente exibirá uma mensagem
			//de erro e finalizará sua execução.
			if(buffer.size()==buffer_size)
				return false;
		}
		// Caso o buffer não esteja cheio, o objeto Cliente 'c' é adicionado á ArrayList buffer
		buffer.add(c);
		//Se o servidor não estiver ocupado, o cliente irá notificar(acordar) o Servidor
		if(!ocupado) {
			notify();			
		}
		//Uma flag para indicar ao servidor que existem clientes esperando no buffer
		tem_cliente = true;
		System.out.println("CLIENTE "+c.getID()+": Esperando servidor me atender.");
		//cliente vai 'dormir' e aguardar até o servidor atendê-lo e notifica-lo do termino do atendimento
		wait();
		//Atendimento dado certo, o metodo retorna 'true'
		return true;
	}
	/**
	 * Metodo para acordar a primeira Thread Cliente que estiver bloqueada por esperar a Thread Servidor
	 * atendê-la.
	 * */
	public synchronized void notificar() {
		notify();
	}
}
