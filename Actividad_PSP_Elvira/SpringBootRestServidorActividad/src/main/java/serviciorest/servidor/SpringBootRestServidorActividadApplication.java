package serviciorest.servidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRestServidorActividadApplication {

	public static void main(String[] args) {
		System.out.println("Servicio REST: Cargando el contexto de Spring...");
		
		SpringApplication.run(SpringBootRestServidorActividadApplication.class, args);
		System.out.println("Servicio REST: Contexto de Spring cargado...");
	}

}
