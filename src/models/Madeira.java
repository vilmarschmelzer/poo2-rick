package models;

public class Madeira extends Caracteristica {
	public Madeira(){
		
	}
	public Madeira(int id,String nome){
		this.setId(id);
		this.setNome(nome);
	}
	public Madeira(int id,String nome,double valor){
		this.setId(id);
		this.setNome(nome);
		this.setValor(valor);
	}
}
