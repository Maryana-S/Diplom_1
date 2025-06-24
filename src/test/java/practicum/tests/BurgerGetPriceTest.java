package practicum.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static practicum.TestData.BURGER_PRICE_CREDENTIALS;

@RunWith(Parameterized.class)
public class BurgerGetPriceTest {

    Burger burger;

    @Mock
    Bun bun;

    @Mock
    Ingredient ingredientFilling;

    @Mock
    Ingredient ingredientSauce;

    float bunPrice;
    float ingredientFillingPrice;
    float ingredientSaucePrice;
    float expectedBurgerPrice;

    public BurgerGetPriceTest(float bunPrice, float ingredientFillingPrice, float ingredientSaucePrice, float expectedBurgerPrice) {
        this.bunPrice = bunPrice;
        this.ingredientFillingPrice = ingredientFillingPrice;
        this.ingredientSaucePrice = ingredientSaucePrice;
        this.expectedBurgerPrice = expectedBurgerPrice;
    }

    @Parameterized.Parameters(name = "bunPrice = {0}, ingredientFillingPrice = {1}, ingredientSaucePrice = {2}, expectedBurgerPrice = {3}")
    public static Object[][] getCredentials() {
        return BURGER_PRICE_CREDENTIALS;
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        burger = new Burger();
    }

    @Test
    public void getPriceBunAndTwoIngredientsSuccessTest() {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(ingredientFilling.getPrice()).thenReturn(ingredientFillingPrice);
        when(ingredientSauce.getPrice()).thenReturn(ingredientSaucePrice);

        burger.setBuns(bun);
        burger.addIngredient(ingredientFilling);
        burger.addIngredient(ingredientSauce);

        assertEquals("Полученная цена бургера не соответствует ожидаемой", expectedBurgerPrice, burger.getPrice(), 0.0f);
    }
}
