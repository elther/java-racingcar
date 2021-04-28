import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ugemang.nextstep.domain.Car;
import ugemang.nextstep.domain.Cars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("자동차 클래스 테스트")
public class RacingCarTest {
    @Test
    @DisplayName("테스트 환경 확인")
    public void nothing(){
        System.out.println("dd");
    }

    @Nested
    @DisplayName("자동차 클래스는")
    class CarTest {
        @Nested
        @DisplayName("이름을 갖는다")
        class Context_with_name {
            @ParameterizedTest
            @CsvSource({"cargo,true", "niro,true", "malibu,false"})
            @DisplayName("이름은 5글자를 넘어갈 수 없다.")
            void it_valid_name_length_five_character(String name, boolean valid) {
                Integer maxlength = 5;
                assertThat(name.length() <= maxlength).isEqualTo(valid);
            }
        }

        @Nested
        @DisplayName("전진(Drive)과 스탑 기능을 갖는다.")
        class Context_with_drive_and_stop {
            @ParameterizedTest
            @ValueSource(ints = {4,5,6,7,8,9})
            @DisplayName("4이상의 값이 들어올 경우 전진한다.")
            void it_car_drive(int value) {
                assertThat(changeGears(value)).isGreaterThanOrEqualTo(4);
            }

            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3})
            @DisplayName("3이하의 값일때 스탑한다.")
            void it_car_stop(int value) {
                assertThat(changeGears(value)).isEqualTo(0);
            }

            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9})
            @DisplayName("0~9 사이의 값이 들어올때 기어를 변속한다.")
            void it_car_change_gear(int value) {
                assertThat(changeGears(value)).isInstanceOf(Integer.class);
            }

            int changeGears(int value){
                return (value >= 4 ? drive(value) : stop());
            }

            int drive(int value){
                return value;
            }

            int stop(){
                return 0;
            }
        }
    }

    @Nested
    @DisplayName("자동차 클래스를 담는 일급 콜렉션은")
    class Context_with_cars {
        @ParameterizedTest
        @ValueSource(strings = {"aaa,bbb,ccc,ddd,eeeeeee", "doke,strawberry,ice,throw,switch,favor"})
        @DisplayName("쉼표로 구분되는 여러대의 자동차 이름을 입력받는다.")
        void it_cars_input_names(String str){
            assertThat(str).isInstanceOf(String.class);
            assertThat(str.contains(",")).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"aaa,bbb,", ",,aa,bb,","abcdef,ababa,ee,ababababa"})
        @DisplayName("쉼표로 자동차를 분리하는 과정에서 이름 조건에 맞는 자동차들만 생성한다.")
        void it_cars_valid_names(String str){
            List<String> strings = new ArrayList<>(Arrays.asList(str.split(",")));
            strings.removeIf(i-> i.length() == 0 || i.length() > 5);
            assertThat(strings.contains("")).isFalse();
        }

        @ParameterizedTest
        @ValueSource(strings = {"aaa,bbb,ccc,ddd,eeeeeee", "doke,strawberry,ice,throw,switch,favor"})
        @DisplayName("생성이 완료된 자동차들을 일급 콜렉션에 저장한다.")
        void it_created_cars_save_first_class_collection(String str){
            List<String> strings = new ArrayList<>(Arrays.asList(str.split(",")));
            strings.removeIf(i-> i.length() == 0 || i.length() > 5);
            List<Car> cars = new ArrayList<>();
            strings.forEach(name-> cars.add(new Car(name)));
            Cars players = new Cars(cars);
            assertThat(players.getCars()).isInstanceOf(java.util.ArrayList.class);
            assertThat(players.getCars().get(0)).isInstanceOf(Car.class);
            assertThat(players.getCars().get(0).getName()).isInstanceOf(String.class);
            assertThat(players.getCars().get(0).getName().length()).isGreaterThan(0);
        }
    }
}
