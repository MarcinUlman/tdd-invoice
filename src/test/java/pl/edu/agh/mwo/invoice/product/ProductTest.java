package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

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
	public void testProductsWithTheSameNameAndPriceAreEqual() {
		Product product1 = new DairyProduct("Kozi ser", new BigDecimal("10.56"));
		Product product2 = new DairyProduct("Kozi ser", new BigDecimal("10.56"));
		Assert.assertEquals(product1, product2);
	}

	@Test
	public void testProductsWithTheSameNameAndPriceButDifferentTypeAreNotEqual() {
		Product product1 = new OtherProduct("Kozi ser", new BigDecimal("10.56"));
		Product product2 = new TaxFreeProduct("Kozi ser", new BigDecimal("10.56"));
		Assert.assertNotEquals(product1, product2);
	}

	@Test
	public void testProductsWithDifferentNameAreNotEqual() {
		Product product1 = new DairyProduct("Kozi ser", new BigDecimal("10.56"));
		Product product2 = new DairyProduct("Krowi ser", new BigDecimal("10.56"));
		Assert.assertNotEquals(product1, product2);
	}

	@Test
	public void testProductsWithDifferentPriceAreNotEqual() {
		Product product1 = new DairyProduct("Kozi ser", new BigDecimal("10.56"));
		Product product2 = new DairyProduct("Kozi ser", new BigDecimal("10.57"));
		Assert.assertNotEquals(product1, product2);
	}

	@Test
	public void testBottleOfWineExcise() {
		BigDecimal expectedExcise = new BigDecimal("5.56");
		HasExcise wine = new BottleOfWine("Czerwone", new BigDecimal("20.99"));
		Assert.assertThat(expectedExcise, Matchers.comparesEqualTo(wine.getExcise()));
	}

	@Test
	public void testBottleOfWinePriceWithTax() {
		BigDecimal expectedPrice = new BigDecimal("128.56"); // 100 * 1.23 + 5.56
		Product wine = new BottleOfWine("Czerwone", new BigDecimal("100"));
		Assert.assertThat(expectedPrice, Matchers.comparesEqualTo(wine.getPriceWithTax()));
	}

	@Test
	public void testFuelCanisterExcise() {
		BigDecimal expectedExcise = new BigDecimal("5.56");
		HasExcise wine = new FuelCanister("PB/98", new BigDecimal("4.67"));
		Assert.assertThat(expectedExcise, Matchers.comparesEqualTo(wine.getExcise()));
	}

	@Test
	public void testFuelCanisterWinePriceWithTax() {
		BigDecimal expectedPrice = new BigDecimal("105.56"); // 100 * 1.00 + 5.56
		Product wine = new FuelCanister("ON", new BigDecimal("100"));
		Assert.assertThat(expectedPrice, Matchers.comparesEqualTo(wine.getPriceWithTax()));
	}
}
