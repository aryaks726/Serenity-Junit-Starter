package serenityswag.inventory;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import serenityswag.authentication.LoginActions;
import serenityswag.authentication.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SerenityRunner.class)
public class WhenViewingHighlightedProducts {

    @Managed(driver = "chrome")
    WebDriver driver;

    @Steps
    LoginActions login;

    @Steps
    ViewProductDetailsActions viewProductDetails;

    ProductList productList;

    ProductDetails productDetails;

    @Test
    public void shouldDisplayHighlightedProductsOnTheWelcomePage() {
        login.as(User.STANDARD_USER);

        List<String> productsOnDisplay = productList.titles();

        assertThat(productsOnDisplay).hasSize(6)
                .contains("Sauce Labs Backpack");
    }

    @Test
    public void highlightedProductsShouldDisplayTheCorrespondingImages() {


        login.as(User.STANDARD_USER);
        List<String> productsOnDisplay = productList.titles();
//        SoftAssertions softy = new SoftAssertions()

        productsOnDisplay.forEach(
                productName -> assertThat(productList.imageTextForProduct(productName)).isEqualTo(productName)
//              productName -> softy.assertThat(productList.imageTextForProduct(productName)).isEqualTo(productName)
        );
    }

    @Test
    public void shouldDisplayCorrectProductDetailsPage() {
        login.as(User.STANDARD_USER);

        String firstItemName = productList.titles().get(0);

        // productList.openProductDetailsFor(firstItemName);

        viewProductDetails.forProductWithName(firstItemName);

        Serenity.reportThat("The product name should be correctly displayed",
                () -> assertThat(productDetails.productName()).isEqualTo(firstItemName)
        );
        //Validating Img and alt value
        Serenity.reportThat("The product image should have the correct alt text",
                () -> productDetails.productImageWithAltValueOf(firstItemName).shouldBeVisible()
        );
    }
}
