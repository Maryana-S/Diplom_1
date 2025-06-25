package practicum.tests;

import net.bytebuddy.utility.RandomString;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    SoftAssertions softAssertions;

    Burger burger;

    String bunName;
    float bunPrice;

    IngredientType ingredientFillingType;
    String ingredientFillingName;
    float ingredientFillingPrice;

    IngredientType ingredientSauceType;
    String ingredientSauceName;
    float ingredientSaucePrice;

    @Mock
    Bun bun;

    @Mock
    Ingredient ingredientFilling;

    @Mock
    Ingredient ingredientSauce;

    @Before
    public void initParams() {
        softAssertions = new SoftAssertions();

        burger = new Burger();

        bunName = RandomString.make(5) + " bun";
        bunPrice = new Random().nextFloat() * 100;

        ingredientFillingType = IngredientType.FILLING;
        ingredientFillingName = RandomString.make(5) + " cutlet";
        ingredientFillingPrice = new Random().nextFloat() * 100;

        ingredientSauceType = IngredientType.SAUCE;
        ingredientSauceName = RandomString.make(5) + " sauce";
        ingredientSaucePrice = new Random().nextFloat() * 1000;
    }

    @Test
    public void setBunsInitBunSuccessTest() {
        when(bun.getName()).thenReturn(bunName);
        when(bun.getPrice()).thenReturn(bunPrice);

        burger.setBuns(bun);

        softAssertions.assertThat(burger.bun.getName())
                .withFailMessage("Значение name не соответствует ожидаемому '%s'", bunName)
                .isEqualTo(bunName);
        softAssertions.assertThat(burger.bun.getPrice())
                .withFailMessage("Значение price не соответствует ожидаемому '%s'", bunPrice)
                .isEqualTo(bunPrice);
        softAssertions.assertAll();
    }

    @Test
    public void addIngredientFillingToIngredientsSuccessTest() {
        when(ingredientFilling.getType()).thenReturn(ingredientFillingType);
        when(ingredientFilling.getName()).thenReturn(ingredientFillingName);
        when(ingredientFilling.getPrice()).thenReturn(ingredientFillingPrice);

        List<Ingredient> ingredients = burger.ingredients;
        burger.addIngredient(ingredientFilling);

        softAssertions.assertThat(ingredients.size())
                .withFailMessage("Размер массива ingredients не соответствует ожидаемому'")
                .isEqualTo(1);
        softAssertions.assertThat(ingredients.get(0).getName())
                .withFailMessage("Значение name не соответствует ожидаемому '%s'", ingredientFillingName)
                .isEqualTo(ingredientFillingName);
        softAssertions.assertThat(ingredients.get(0).getPrice())
                .withFailMessage("Значение price не соответствует ожидаемому '%s'", ingredientFillingPrice)
                .isEqualTo(ingredientFillingPrice);
        softAssertions.assertThat(ingredients.get(0).getType())
                .withFailMessage("Значение type не соответствует ожидаемому")
                .isEqualTo(ingredientFillingType);
        softAssertions.assertAll();
    }

    @Test
    public void removeIngredientFromIngredientsSuccessTest() {
        List<Ingredient> ingredients = burger.ingredients;
        burger.addIngredient(ingredientFilling);
        burger.removeIngredient(0);

        assertEquals("Ингредиент не удалился из списка", 0, ingredients.size());
    }

    @Test
    public void moveIngredientInIngredientsSuccessTest() {
        when(ingredientFilling.getName()).thenReturn(ingredientFillingName);
        when(ingredientSauce.getName()).thenReturn(ingredientSauceName);
        burger.addIngredient(ingredientFilling);
        burger.addIngredient(ingredientSauce);

        burger.moveIngredient(0, 1);

        softAssertions.assertThat(burger.ingredients.get(0).getName())
                .withFailMessage("Первое значение в списке ingredients не соответствует ожидаемому")
                .isEqualTo(ingredientSauceName);
        softAssertions.assertThat(burger.ingredients.get(1).getName())
                .withFailMessage("Второе значение в списке ingredients не соответствует ожидаемому")
                .isEqualTo(ingredientFillingName);
        softAssertions.assertAll();
    }

    @Test
    public void getReceiptBunAndTwoIngredientsTest() {
        when(bun.getName()).thenReturn(bunName);
        when(bun.getPrice()).thenReturn(bunPrice);

        when(ingredientFilling.getType()).thenReturn(ingredientFillingType);
        when(ingredientFilling.getName()).thenReturn(ingredientFillingName);
        when(ingredientFilling.getPrice()).thenReturn(ingredientFillingPrice);

        when(ingredientSauce.getType()).thenReturn(ingredientSauceType);
        when(ingredientSauce.getName()).thenReturn(ingredientSauceName);
        when(ingredientSauce.getPrice()).thenReturn(ingredientSaucePrice);

        burger.setBuns(bun);
        burger.addIngredient(ingredientFilling);
        burger.addIngredient(ingredientSauce);

        String receipt = burger.getReceipt();

        softAssertions.assertThat(receipt)
                .withFailMessage("Ожидаемая строка отсутствует в чеке")
                .contains(String.format("(==== %s ====)%n", bun.getName()));
        softAssertions.assertThat(receipt)
                .withFailMessage("Ожидаемая строка отсутствует в чеке")
                .contains(String.format("= %s %s =%n", ingredientFilling.getType().toString().toLowerCase(), ingredientFilling.getName()));
        softAssertions.assertThat(receipt)
                .withFailMessage("Ожидаемая строка отсутствует в чеке")
                .contains(String.format("= %s %s =%n", ingredientSauce.getType().toString().toLowerCase(), ingredientSauce.getName()));
        softAssertions.assertThat(receipt)
                .withFailMessage("Ожидаемая строка отсутствует в чеке")
                .contains(String.format("%nPrice: %f%n", bun.getPrice() * 2 + ingredientFilling.getPrice() + ingredientSauce.getPrice()));
        softAssertions.assertAll();
    }

}
