package servicioRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Actividad2AlvaroApplication {

	public static void main(String[] args) {
		System.out.println("Cargando el contexto de Spring...");
		SpringApplication.run(Actividad2AlvaroApplication.class, args);
		System.out.println("Contexto de Spring cargado correctamente");
	}

}
