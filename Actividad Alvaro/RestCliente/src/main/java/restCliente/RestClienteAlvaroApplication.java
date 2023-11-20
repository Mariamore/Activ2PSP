package restCliente;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import restCliente.controlador.ServicioProxyLibro;
import restCliente.entidad.Libro;

@SpringBootApplication
public class RestClienteAlvaroApplication implements CommandLineRunner {
	
	@Autowired 
	private ServicioProxyLibro spl;
	
	@Autowired 
	private ApplicationContext context;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		System.out.println("Cargando el contexto de Spring...");
		SpringApplication.run(RestClienteAlvaroApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Arrancando el cliente REST...");
		int opcion = menu();
		try (Scanner sc = new Scanner(System.in)) {
			while(opcion !=6) {
				switch (opcion) {
					case 1:
						Libro libro = datosAlta();
						Libro altaLibro = spl.alta(libro);
						System.out.println("Libro dado de alta: " + altaLibro);
						break;
					case 2:
						System.out.println("Introduzca el id del libro a eliminar:");
						int id = sc.nextInt();
						boolean borrado = spl.borrar(id);
						System.out.println("Libro eliminado: " + borrado);
						break;
					case 3:
						Libro l = datosModificar();
						boolean modificado = spl.modificar(l);
						System.out.println("Libro modificado: " + modificado);
						break;
					case 4:
						System.out.println("Introduzca el Id del libro a buscar:");
						int id1 = sc.nextInt();
						Libro li = spl.obtener(id1);
						System.out.println("Libro solicitado: " + li);
						break;
					case 5:
						List<Libro> listaLibros = spl.listar();
						listaLibros.forEach((v) -> System.out.println(v));
						break;
					default:
						System.out.println("Opción no válida");
				}
				opcion = menu();
			}
		}
		
		if(opcion == 6) {		
			System.out.println("Gracias por usar nuestro servicio de biblioteca. Hasta pronto.");	
			finAplicacion();
		}
	}
	
	public static int menu() {
		try (Scanner sc = new Scanner(System.in)) {
			int opcion = 0;
			
			System.out.println("********** BIBLIOTECA DE LIBROS **********");
			System.out.println("1 - Dar de alta un libro");
			System.out.println("2 - Dar de baja un libro por Id");
			System.out.println("3 - Modificar un libro por Id");
			System.out.println("4 - Buscar un libro por Id");
			System.out.println("5 - Obtener todos los libros");
			System.out.println("6 - Salir");
			System.out.println("******************************************");
			 
			while(opcion > 6 || opcion < 1) {
				System.out.println("Introduce el número de la opción elegida:");
				try {
				opcion = sc.nextInt();
				} catch (NumberFormatException e) {
					System.out.println("Error - Introduce un número");
				} catch (Exception e) {
					System.out.println("Error genérico");
				}
			}
			return opcion;
		}
	}
	
	public void finAplicacion() {	
		SpringApplication.exit(context, () -> 0);
	}
	
	public Libro datosAlta() {
		try (Scanner sc2 = new Scanner(System.in)) {
			Libro libro = new Libro();
			System.out.println("Introduzca el título del libro:");
			String titulo = sc2.nextLine();
			System.out.println("Introduzca la editorial del libro:");
			String editorial = sc2.nextLine();
			System.out.println("Introduzca la nota del libro:");
			int nota = sc2.nextInt();
			libro.setTitulo(titulo);
			libro.setEditorial(editorial);
			libro.setNota(nota);
			return libro;
		}
	}
	
	public Libro datosModificar() {
		
		Libro libro = new Libro();
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Introduzca el Id del libro a modificar:");
			int id = sc.nextInt();
			libro.setId(id);
			Libro libroModificar = spl.obtener(id);
			if(libroModificar == null) {
				libro.setId(id);
				return libro;
			}else {
				System.out.println("Introduzca el título del libro a modificar:");
				String titulo = sc.nextLine();
				libro.setTitulo(titulo);
				System.out.println("Introduzca la editorial del libro a modificar:");
				String editorial = sc.nextLine();
				libro.setEditorial(editorial);
				System.out.println("Introduzca la nota del libro a modificar:");
				int nota = sc.nextInt();
				libro.setNota(nota);
			}
		}
		return libro;
	}

}
