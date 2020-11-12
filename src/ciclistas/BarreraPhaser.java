package ciclistas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.Phaser;

public class BarreraPhaser extends Phaser {
	
	public static final int SALIR_GASOLINERA=0;
	public static final int IR_VENTA=1;
	public static final int VOLVER_GASOLINERA=2;
	
	

	@Override
	protected boolean onAdvance(int phase, int registeredParties) {
		switch (phase) {
		case SALIR_GASOLINERA:
					System.out.printf("%s: Todos los amigos han salido de la gasolinera a las %s\n",
							this.getClass().getSimpleName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
			break ;
		case IR_VENTA:
			System.out.printf("%s: Todos los amigos llegan a la venta a las %s\n",
					this.getClass().getSimpleName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
			break;
		case VOLVER_GASOLINERA:
			System.out.printf("%s: Todos los amigos han vuelto a la gasolinera a las %s\n",
					this.getClass().getSimpleName(),DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
			return true;
		
		}
		return super.onAdvance(phase, registeredParties);
	}
	

}
