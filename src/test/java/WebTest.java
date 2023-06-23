package test;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class WebTest extends test.BaseTest {


    @DisplayName("Проверка поиска статьи на wikipedia")
    @ValueSource(strings = {"Музыка", "Танец"})
    @ParameterizedTest(name = "Проверка результата поиска для запроса {0}")
    void wikipediaSearchTest(String testData) {
        open("https://ru.wikipedia.org/");
        $("#searchInput").setValue(testData);
        $("#searchButton").click();
        $(".mw-page-title-main").shouldHave(text(testData));
    }

    static Stream<Arguments> getTopicCategories() {
        return Stream.of(
                Arguments.of("Мужское", List.of("Выбор Oskelly", "Новинки", "Бренды", "Аксессуары", "Бьюти", "Обувь", "Одежда", "Сумки","Ювелирные изделия")),
                Arguments.of("Женское", List.of("Выбор Oskelly", "Новинки", "Бренды", "Аксессуары", "Бьюти", "Обувь", "Одежда", "Сумки", "Украшения","Ювелирные изделия"))
        );
    }
    @ParameterizedTest(name = "Соответствие списка категорий заданному топику {0}")
    @MethodSource("getTopicCategories")
    public void categoryShouldBeOpenAfterClick(String categoryName, List<String> buttonName) {
        open("https://www.oskelly.ru/");
        $(".osk-header").$(byText(categoryName)).click();
        $$(".osk-header-bottom__link").filter(visible).shouldHave(CollectionCondition.texts(buttonName));
        }

    }

