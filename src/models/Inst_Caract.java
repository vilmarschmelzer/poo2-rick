package models;

public class Inst_Caract {
	
	private int id;
	private Instrumento instrumento;
	private Caracteristica caracteristica;
	private int qtd;
	
	public Inst_Caract(){
		
	}
	
	public Inst_Caract(Instrumento inst,Caracteristica carct,int qtd){
		this.instrumento=inst;
		this.caracteristica=carct;
		this.qtd=qtd;
	}
	
	public Inst_Caract(Caracteristica carct,int qtd){
		this.caracteristica=carct;
		this.qtd=qtd;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public Caracteristica getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
}
