package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class FuelCanister extends TaxFreeProduct implements HasExcise {

	public FuelCanister(String name, BigDecimal price) {
		super(name, price);
	}

	@Override
	public BigDecimal getExcise() {
		return new BigDecimal("5.56");
	}
}
