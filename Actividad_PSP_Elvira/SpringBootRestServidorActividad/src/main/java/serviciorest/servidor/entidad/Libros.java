package serviciorest.servidor.entidad;

public class Libros {
	
	//variables
	
		private int id;
		private String titulo, editorial, nota;
		
		//Constructor
		public Libros() {
			super();
		}
		
		
		//Contructor con los atributos de la clase
		public Libros(int id, String titulo, String editorial, String nota) {
			super();
			this.id = id;
			this.titulo = titulo;
			this.editorial = editorial;
			this.nota = nota;
		}

		//getter and setters
		


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getTitulo() {
			return titulo;
		}


		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}


		public String getEditorial() {
			return editorial;
		}


		public void setEditorial(String editorial) {
			this.editorial = editorial;
		}


		public String getNota() {
			return nota;
		}


		public void setNota(String nota) {
			this.nota = nota;
		}


		@Override
		public String toString() {
			return "Libros [id=" + id + ", titulo=" + titulo + ", editorial=" + editorial + ", nota=" + nota + "]";
		}


		

}
