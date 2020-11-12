package ciclistas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class CiclistaLlegaTarde implements Runnable {
	
	private Phaser phaser;
	private Random rng= new Random();

	public CiclistaLlegaTarde(Phaser phaser) {
		
		this.phaser=phaser;
	}

	@Override
	public void run() {//se levanta tarde y cuando sale el resto ya han salido de la gasolinera
		
		if(!phaser.isTerminated()) {
			int jointPhase = phaser.register();
			System.out.println("Llego tarde pero me uno en la fase "+jointPhase);
			
			try {
				salgoDeCasa();
			} catch (InterruptedException e) {
				System.out.printf(" me han interrumpido cuando salia de casa");
				return;
			}
			
			if(jointPhase<=BarreraPhaser.SALIR_GASOLINERA) {
				try {
					phaser.awaitAdvanceInterruptibly(phaser.arrive());
				} catch (InterruptedException e) {
					System.out.println("Nos han interrimpido mientras esperabamos");
				}
			}
			try {
				irALaVenta();
			} catch (InterruptedException e) {
				System.out.println("Me han interrumpido cuando iba a la venta");
			}
			if(jointPhase<=BarreraPhaser.IR_VENTA) {
				try {
					phaser.awaitAdvanceInterruptibly(phaser.arrive());
				} catch (InterruptedException e) {
					System.out.println("Nos han interrimpido mientras esperabamos");
				}
			}
			
			try {
				volverAGasolinera();
			} catch (InterruptedException e) {
				System.out.println("Me han interrumpido cuando volvia de la gasolinera");
			}
			if(jointPhase<=BarreraPhaser.VOLVER_GASOLINERA) {
				try {
					phaser.awaitAdvanceInterruptibly(phaser.arrive());
				} catch (InterruptedException e) {
					System.out.println("Nos han interrimpido mientras esperabamos");
				}
			}
			
			try {
				volverACasa();
			} catch (InterruptedException e) {
				System.out.println("Me han interrumpido cuando volvia a casa");
			}
		}else {
			System.out.printf("%s: me he quedado dormido y llegado muy tarde a las %s",
					Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
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
		System.out.printf("%s: He vuelto a la gasolinera y espero a mis compañeros a las  %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		
		
	}

	private void irALaVenta() throws InterruptedException {
		System.out.printf("%s: Estoy yendo a la venta a las %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		TimeUnit.SECONDS.sleep(rng.nextInt(5)+5);
		System.out.printf("%s: He llegado a la venta  %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		
		
	}

	private void salgoDeCasa() throws InterruptedException {
		System.out.printf("%s: Estoy saliendo de casaa las %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		TimeUnit.SECONDS.sleep(rng.nextInt(3)+1);
		System.out.printf("%s: Llego a la gasolinera %s\n",
				Thread.currentThread().getName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		
		
		
	}

}
