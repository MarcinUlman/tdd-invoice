package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.Product;

public class ProductTest {
	@Test
	public void testProductNameIsCorrect() {
		Product product = new OtherProduct("buty", new BigDecimal("100.0"));
		Assert.assertEquals("buty", product.getName());
	}

	@Test
	public void testProductPriceAndTaxWithDefaultTax() {
		Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
		Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
		Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
	}

	@Test
	public void testProductPriceAndTaxWithDairyProduct() {
		Product product = new DairyProduct("Szarlotka", new BigDecimal("100.0"));
		Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
		Assert.assertThat(new BigDecimal("0.08"), Matchers.comparesEqualTo(product.getTaxPercent()));
	}

	@Test
	public void testPriceWithTax() {
		Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
		Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullName() {
		new OtherProduct(null, new BigDecimal("100.0"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithEmptyName() {
		new TaxFreeProduct("", new BigDecimal("100.0"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNullPrice() {
		new DairyProduct("Banany", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductWithNegativePrice() {
		new TaxFreeProduct("Mandarynki", new BigDecimal("-1.00"));
	}

	@Test
	public void testBottleOfWine() {
		HasExcise szampan = new BottleOfWine("Szampan", new BigDecimal("15"));
		BigDecimal expectetExceise = new BigDecimal("5.56");
		Assert.assertThat(expectetExceise, Matchers.equalTo(szampan.getExcise()));
		// invoice.addProduct(new BottleOfWine("Szampan", new BigDecimal(15)), 1);
//		Assert.assertThat(new BigDecimal("24.01"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
	}

	@Test
	public void testFuelCanister() {
		HasExcise benzynka = new FuelCanister("95", new BigDecimal("5.2"));
		BigDecimal expectetExceise = new BigDecimal("5.56");
		Assert.assertThat(expectetExceise, Matchers.comparesEqualTo(benzynka.getExcise()));
	}

	@Test
	public void testButtleOfWinePriceWithTax() {
		BigDecimal expextetPriceWithTax = new BigDecimal("128.56");
		Product wine = new BottleOfWine("wino", new BigDecimal("100"));
		Assert.assertThat(expextetPriceWithTax, Matchers.comparesEqualTo(wine.getPriceWithTax()));
	}
	
	@Test
	public void testFuelCanisterPriceWithTax() {
		BigDecimal expectedPriceWithTax = new BigDecimal("105.56");
		Product gasoline = new FuelCanister("95", new BigDecimal("100"));
		Assert.assertThat(expectedPriceWithTax, Matchers.comparesEqualTo(gasoline.getPriceWithTax()));
	}

}
