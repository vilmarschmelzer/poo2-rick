package decorator;

import models.Caracteristica;

public class CaractDecorator extends ComponentDecorator {
	
	private InstAbsDecorator inst;
	private Caracteristica caract;
	
	public CaractDecorator(InstAbsDecorator inst, Caracteristica caract){
		this.inst=inst;
		this.caract=caract;
	}
	
	@Override
	public double custo() {
		return caract.getValor() +inst.custo();
	}
}
