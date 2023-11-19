package serviciorest.cliente;

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

import serviciorest.cliente.entidad.Libros;
import serviciorest.cliente.servicio.ServicioProxyLibro;
	
	/*
	 * La Clase implementara CommandLineRunner ya que el metodo main al ser una metodo estatico no
	 * podemos acceder a los métodos dinamicos de la clase 'spl',así implementaremos el metodo run 
	 */

	//Punto de arranque de nuestra aplicacion
	@SpringBootApplication
	public class SpringBootRestClienteActividadApplication implements CommandLineRunner{
		
		
		/*
		 * Inyectamos los objetos que necesitamos para acceder al ServicioRest
		 */
		
		//Inyectamos Objeto
		@Autowired
		private ServicioProxyLibro spl;
		
		//Inyectamos el propio contexto de Spring
		@Autowired
		private ApplicationContext context;
	
		/*
		 * Con el metodo damos de alta el objeto más importantes, que es el que será usado
		 * para hacer las peticiones HTTP a nuestro servicio REST.
		 */
		
		
		//Con esta notación damos de alta objetos en el contexto de Spring
	    @Bean
	    RestTemplate restTemplate(RestTemplateBuilder builder) {
			return builder.build();
		}
		
	    //Método que arranca la aplicacion
	    public static void main(String[] args) {
	    	
	    	System.out.println("Servicio cliente -> Cargando el contexto de Spring...");
			
			SpringApplication.run(SpringBootRestClienteActividadApplication.class, args);
		}
	    
	    
	    /*
	     * Este método al ser dinamico podemos acceder a  los atributos dinamicos "spl"
	     */
		@Override
		public void run(String... args) throws Exception {
			
			System.out.println("--------ARRANCAMOS EL CLIENTE REST ----------");
			System.out.println("---------------------------------------------");
			
			int opcion=0;
			
			Scanner sc= new Scanner (System.in);
			
			do {
				
			opcion=pintarMenu();
			
			//Opciones menu
			switch (opcion) {
			
			case 1: 
			
				Libros libroNuevo = new Libros();
				System.out.println("Dame id del libro:");
				int idNuevo = sc.nextInt();
				
				System.out.println("Dame el titulo el libro:");
				String tituloNuevo = sc.next();
				
				System.out.println("Dame la editoria");
				String editorialNuevo = sc.next();
				
				System.out.println("Dame la nota");
				String notaNueva = sc.next();
				
				libroNuevo.setId(idNuevo);
				libroNuevo.setTitulo(tituloNuevo);
				libroNuevo.setEditorial(editorialNuevo);
				libroNuevo.setNota(notaNueva);
				
				Libros lAlta = spl.altaLibro(libroNuevo);;
				break;
				
			case 2:
				
				System.out.println("Dame el id que quieres eliminar: ");
				int idEliminar = sc.nextInt();
				Libros lEliminar = spl.obtenerLibro(idEliminar);
				break;
				
			case 3:
				
				Libros libroModificado = new Libros();
				
				System.out.println("Dame el id del libro que desea modificar: ");
				int idModificar = sc.nextInt();
				libroModificado.setId(idModificar);
				System.out.println("Escriba el titulo modificado");
				String tituloModificado = sc.next();
				libroModificado.setTitulo(tituloModificado);
				System.out.println("Escriba la editorial modificada: ");
				String editorialModificada = sc.next();
				libroModificado.setEditorial(editorialModificada);
				System.out.println("Escriba de la nota modificada");
				String notaModificada = sc.next();
				libroModificado.setNota(notaModificada);
				
				boolean lModificar = spl.modificarLibro(libroModificado);
				break;	
				
			case 4:
				
				System.out.println("Dame el id del libro que quieres obtener");
				int idObtenerLibro = sc.nextInt();
				Libros libroObtenido = spl.obtenerLibro(idObtenerLibro);
				break;
			case 5:
				
				System.out.println("Lista de libros");
				List<Libros> listaLibros = spl.listaLibros();
				System.out.println(listaLibros);
				break;
				
				
			case 6:
				System.out.println("Fin del programa");
				System.out.println("Saliendo de la aplicacion");
				//Usaremos el método exit de la clase Spring, para mandar parar la aplicacion
				SpringApplication.exit(context, ()-> 0);//Funcion lamba
			default:
				System.out.println("opcion erronea");
			}	
		}while(opcion !=6);
			System.out.println("Error");
			pintarMenu();
		}
	 	
		//Menu
		public static int pintarMenu() {
			
			Scanner leer= new Scanner (System.in);
			int opcion;
			
			
			System.out.println("1.-Dar de alta un libro");
			System.out.println("2.-Dar de baja un libro por ID");
			System.out.println("3.-Modificar un libro por ID");
			System.out.println("4.-Obtener un libro por ID");
			System.out.println("5.-Listar todos los libros");
			System.out.println("6.-Salir");
			System.out.println("teclea una op del 1 al 6, para salir");
			opcion=leer.nextInt();
			while(opcion<1 || opcion>6) {
				System.out.println("del 1 al 6");
				opcion=leer.nextInt();
			}
			return opcion;
			
			
			}
		
		}	
		
		
		
	
