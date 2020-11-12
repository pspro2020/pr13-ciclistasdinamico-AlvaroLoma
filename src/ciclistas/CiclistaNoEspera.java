package ciclistas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class CiclistaNoEspera implements Runnable {
	private Phaser phaser;
	private Random rng= new Random();

	public CiclistaNoEspera(Phaser phaser) {
		
		this.phaser=phaser;
	}

	@Override
	public void run() {
		phaser.register();
		try {
			salgoDeCasa();
		} catch (InterruptedException e) {
			System.out.printf(" me han interrumpido cuando salia de casa");
			return;
		}
		try {
			phaser.awaitAdvanceInterruptibly(phaser.arrive());
		} catch (InterruptedException e) {
			System.out.println("Nos han interrimpido mientras esperabamos");
		}
		try {
			irALaVenta();
		} catch (InterruptedException e) {
			System.out.println("Me han interrumpido cuando iba a la venta");
		}
		
		try {
			phaser.arriveAndDeregister();
			volverAGasolinera();
			volverACasa();
		} catch (InterruptedException e) {
			System.out.println("Me han interrumpido cuando esperaba");
		}
	
	}

	private void volverACasa() throws InterruptedException {
		System.out.printf("%s: Estoy volviendo a casa a las %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		TimeUnit.SECONDS.sleep(rng.nextInt(3)+1);
		System.out.printf("%s: He llegado a casa a las %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		
		
	}

	private void volverAGasolinera() throws InterruptedException {
		System.out.printf("%s: Estoy volviendo a la gasolinera a las %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		TimeUnit.SECONDS.sleep(rng.nextInt(5)+5);
		System.out.printf("%s: He vuelto a la gasolinera %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		
		
	}

	private void irALaVenta() throws InterruptedException {
		System.out.printf("%s: Estoy yendo a la venta y no espero a nadie mas a las %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		TimeUnit.SECONDS.sleep(rng.nextInt(5)+5);
		System.out.printf("%s: He llegado a la venta  %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		
		
	}

	private void salgoDeCasa() throws InterruptedException {
		System.out.printf("%s: Estoy saliendo de casaa las %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		TimeUnit.SECONDS.sleep(rng.nextInt(3)+1);
		System.out.printf("%s: Llego a la gasolinera y espero a mis compañeros %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		
		
		
	}

}
