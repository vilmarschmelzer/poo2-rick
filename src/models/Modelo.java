package models;

public class Modelo {
	
	private int id;
	private String nome;

	public Modelo(){
		
	}
	
	public Modelo(int id,String nome){
		this.setId(id);
		this.setNome(nome);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTextoCombo(){
		return this.id+" - "+this.nome;
	}
}
