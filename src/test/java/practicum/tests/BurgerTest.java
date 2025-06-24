package practicum.tests;

import net.bytebuddy.utility.RandomString;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

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

        assertEquals("Значение name не соответствует ожидаемому", bunName, burger.bun.getName());
        assertEquals("Значение price не соответствует ожидаемому", bunPrice, burger.bun.getPrice(), 0.0f);
    }

    @Test
    public void addIngredientFillingToIngredientsSuccessTest() {
        when(ingredientFilling.getType()).thenReturn(ingredientFillingType);
        when(ingredientFilling.getName()).thenReturn(ingredientFillingName);
        when(ingredientFilling.getPrice()).thenReturn(ingredientFillingPrice);

        List<Ingredient> ingredients = burger.ingredients;
        burger.addIngredient(ingredientFilling);

        assertEquals("Размер массива ingredients не соответствует ожидаемому", 1, ingredients.size());
        assertEquals("Значение name не соответствует ожидаемому", ingredientFillingName, ingredients.get(0).getName());
        assertEquals("Значение price не соответствует ожидаемому", ingredientFillingPrice, ingredients.get(0).getPrice(), 0.0f);
        assertEquals("Значение type не соответствует ожидаемому", ingredientFillingType, ingredients.get(0).getType());
    }

    @Test
    public void removeIngredientFromIngredientsSuccessTest() {
        List<Ingredient> ingredients = burger.ingredients;
        burger.addIngredient(ingredientFilling);
        assertEquals("Ингредиент не добавился в список",1, ingredients.size());

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

        assertEquals("Первое значение в списке ingredients не соответствует ожидаемому", ingredientSauceName, burger.ingredients.get(0).getName());
        assertEquals("Второе значение в списке ingredients не соответствует ожидаемому", ingredientFillingName, burger.ingredients.get(1).getName());
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

        assertTrue("Ожидаемая строка отсутствует в чеке", receipt.contains(String.format("(==== %s ====)%n", bun.getName())));
        assertTrue("Ожидаемая строка отсутствует в чеке", receipt.contains(String.format("= %s %s =%n", ingredientFilling.getType().toString().toLowerCase(), ingredientFilling.getName())));
        assertTrue("Ожидаемая строка отсутствует в чеке", receipt.contains(String.format("= %s %s =%n", ingredientSauce.getType().toString().toLowerCase(), ingredientSauce.getName())));
        assertTrue("Ожидаемая строка отсутствует в чеке", receipt.contains(String.format("%nPrice: %f%n", bun.getPrice() * 2 + ingredientFilling.getPrice() + ingredientSauce.getPrice())));

    }

}
