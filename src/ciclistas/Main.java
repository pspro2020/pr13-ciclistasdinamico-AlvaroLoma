package ciclistas;

import java.util.concurrent.TimeUnit;

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		BarreraPhaser phaser = new BarreraPhaser();
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Ciclista(phaser)).start();
		}
		
		new Thread(new CiclistaNoEspera(phaser)).start();
		TimeUnit.SECONDS.sleep(9);
		
		new Thread(new CiclistaLlegaTarde(phaser)).start();
		TimeUnit.SECONDS.sleep(30);
		new Thread(new CiclistaLlegaTarde(phaser)).start();
		
	}

}
