package es.serviciorestservidor.controlador;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.serviciorestservidor.modelo.entidad.Libro;
import es.serviciorestservidor.modelo.negocio.Biblioteca;

@RestController
public class ControladorBiblioteca {
	
	//Hacemos la inyección de dependencias del objeto Biblioteca
	@Autowired
	private Biblioteca biblioteca;
	
	/*
	 * POST
	 * 
	 * Vamos a dar de alta un Libro mediante el método POST, para ello configuramos que recibimos un 
	 * JSON, y devolvemos otro. No se requiere el ID del Libro porque se lo vamos a asignar nosotros, 
	 * por lo que no se lo pasamos en la URL dentro del body del HTTP request.
	 * 
	 * En caso de que se cree el objeto, devolvemos un código 200 CREATED, en caso contrario, devolvemos
	 * 400 BAD REQUEST
	 * 
	 * */
	@PostMapping(path = "biblioteca", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libro> altaLibro(@RequestBody Libro l){
		System.out.println("altaLibro -> objeto libro " + l);
		Libro libroNuevo =biblioteca.altaLibro(l);
		if(libroNuevo!= null) {
		return new ResponseEntity<Libro>(l, HttpStatus.CREATED);
		} else  {
			return new ResponseEntity<Libro> (l, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/*
	 * DELETE
	 * 
	 * Borramos un Libro en función del ID que le pasemos por el path.
	 * Al ser una búsqueda ppor clave primaria, el ID irá dentro del path de la URL.
	 * Obtendremos el ID del path mediante la anotación @PathVariable.
	 * 
	 * Si todo ha ido bien, devolvemos el código 200 OK, si no, el 404 NOT FOUND
	 * 
	 * */
	
	@DeleteMapping(path="biblioteca/{id}")
	public ResponseEntity<Libro> borrarLibro(@PathVariable("id") int iD){
		System.out.println("borrarLibro -> ID a borrar " + iD);
		Libro l = biblioteca.borrarLibro(iD);
		if (l!=null) {
			return new ResponseEntity<Libro>(l, HttpStatus.OK);
		} else {
			return new ResponseEntity<Libro> (l, HttpStatus.NOT_FOUND);
		}
	}
	
	
	/*
	 * PUT
	 * 
	 * Vamos a modificar un libro según su ID. El ID lo obtendremos del path, y los datos del JSON,
	 * del body mediante la anotación @RequestBody. Devolvemos un JSON.
	 * 
	 * En caso de que todo vaya bien, devolvemos el código 200 OK, en caso contrario, 400 BAD REQUEST
	 * 
	 * */
	@PutMapping(path ="biblioteca/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libro> modificarPorID(@RequestBody Libro l, @PathVariable("id") int iD){
		System.out.println("modificarPorID -> ID a modificar " + iD);
		System.out.println("modificarPorID -> Datos a modificar " + l);
		l.setiD(iD);
		Libro lUpdate = biblioteca.modificarPorID(l);
		if (lUpdate != null) {
			return new ResponseEntity<Libro> (lUpdate, HttpStatus.OK);
		}else {
			return new ResponseEntity<Libro> (lUpdate, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/*
	 * GET LIBRO POR ID
	 * 
	 * En este caso, vamos a buscar un libro por su ID. El ID lo obtenemos del path, y devolvemos 
	 * un JSON.
	 * 
	 * 
	 * Si todo ha ido bien, devolvemos el código 200 OK, si no, el 404 NOT FOUND
	 * 
	 * 
	 * */
	@GetMapping(path="biblioteca/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libro> buscarPorID(@PathVariable("id") int id){
		System.out.println("buscarPorID -> ID a buscar " + id);
		Libro l = biblioteca.buscarPorID(id);
		
		if (l != null) {
			return new ResponseEntity<Libro> (l, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<Libro> (l, HttpStatus.NOT_FOUND);
		}

	}
	
	
	/*
	 * GET TODOS LOS LIBROS
	 * 
	 * Como queremos obtener todos los libros, en este caso no introducimos el ID en la URL. Devolvemos un
	 * JSON.
	 * 
	 * En este caso, devolvemos el código 200 OK
	 * 
	 * */
	@GetMapping(path="biblioteca", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Libro>> buscarTodos(){
		List<Libro> listaLibros = null;
		System.out.println("buscarTodos -> Listando los libros");
		listaLibros = biblioteca.buscarTodos();
		return new ResponseEntity<List<Libro>> (listaLibros, HttpStatus.OK);
	}
}