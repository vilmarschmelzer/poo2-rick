package models;

public class Caracteristica {
	
	private int id;
	private String nome;
	private double valor;
	
	public Caracteristica(){
		
	}
	
	public Caracteristica(int id,String nome,double valor){
		this.id=id;
		this.nome=nome;
		this.valor=valor;
	}
	
	public Caracteristica(int id,String nome){
		this.id=id;
		this.nome=nome;
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
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String getTextoCombo(){
		return this.id+" - "+this.nome+" - "+this.valor;
	}
	
	public double custo(){
		return this.valor;
	}
}
