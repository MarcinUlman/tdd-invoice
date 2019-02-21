package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class BottleOfWine extends OtherProduct implements HasExcise {

	public BottleOfWine(String name, BigDecimal price) {
		super(name, price);
	}

	@Override
	public BigDecimal getExcise() {
		return new BigDecimal("5.56");
	}
	
	
}
