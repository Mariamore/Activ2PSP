package es.serviciorestcliente.modelo.entidad;


public class Libro {

	private int id;
	private String titulo, editorial;
	private double nota;
	
	//Generamos los getters y setters
	public int getiD() {
		return id;
	}
	public void setiD(int id) {
		this.id = id;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
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
	
	//Generamos el constructor con todos los atributos, y vacío
	public Libro(int id, String titulo, String editorial, double nota) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.editorial = editorial;
		this.nota = nota;
	}
	
	public Libro() {
		super();
	}
	
	//Generamos el toString()
	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", editorial=" + editorial + ", nota=" + nota + "]";
	}
	
}
