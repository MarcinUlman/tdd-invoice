package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Collection<Product> products;

	public Invoice() {
		this.products = new ArrayList<Product>();
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public void addProduct(Product product, Integer quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < quantity; i++) {
			this.addProduct(product);
		}
	}

	public BigDecimal getTotalNetValue() {
		BigDecimal sum = BigDecimal.ZERO;
		for (Product product : products) {
			sum = sum.add(product.getPrice());
		}
		return sum;
	}

	public BigDecimal getTax() {
		BigDecimal sum = BigDecimal.ZERO;
		for (Product product : products) {
			sum = sum.add(product.getPrice().multiply(product.getTaxPercent()));
		}
		return sum;
	}

	public BigDecimal getTotalGrossValue() {
		BigDecimal sum = BigDecimal.ZERO;
		sum = getTotalNetValue().add(getTax());
		return sum;
	}
}
