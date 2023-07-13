package test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

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

    @CsvSource({
            "Танец, Та́нец — ритмичные, выразительные движения тела, обычно выстраиваемые в определённую композицию и исполняемые с музыкальным сопровождением",
            "Музыка, Му́зыка (от др.-греч. μουσική[К 1]) — вид искусства, в котором определённым образом организованные звуки используются для создания некоторого сочетания формы, гармонии, мелодии, ритма или иного выразительного содержания."
    })
    @ParameterizedTest(name = "При вводе в поиск {0} на старнице присутствует текст {1}")
    @DisplayName("Проверка поиска статьи на wikipedia")

    void successfulSearchTextTest(String testData) {
        open("https://ru.wikipedia.org/");
        $("#searchInput").setValue(testData);
        $("#searchButton").click();
        $(".mw-page-title-main").shouldHave(text(testData));
    }
}

