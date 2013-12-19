package models;

public class Cor extends Caracteristica {

	public Cor(){
		
	}
	
	public Cor(int id,String nome){
		this.setId(id);
		this.setNome(nome);
	}
	
	public Cor(int id,String nome,double valor){
		this.setId(id);
		this.setNome(nome);
		this.setValor(valor);
	}
}
