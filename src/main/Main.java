package main;
import servidor.Receptor;
import servidor.Servidor;

/**
 * <h1>Trabalho de Sincronização de Threads</h1>
 * @author daniel nogueira
 * @author abner jordan
 * @version 0.4.1
 * */
public class Main {
	public static void main(String[] args) {
		// criando canal de comunicação cliente-servidor com limite de vagas de 4 clientes
		Canal c = new Canal(4);
		// iniciando Thread servidor
		Servidor s = new Servidor("Servidor",c);
		s.start();
		// iniciando Thread receptor
		Receptor r = new Receptor("Receptor",c);
		r.start();
	}
}
