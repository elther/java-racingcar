package racingCar.ui;

import racingCar.domain.car.RacingCar;
import racingCar.domain.car.RacingCars;

import java.util.List;

public class ResultView {
    private static StringBuilder stringBuilder;

    public static void printRacingCars(int time, RacingCars racingCars) {
        stringBuilder = new StringBuilder();
        if (time == 0) {
            stringBuilder.append("실행 결과\n");
        }
        for (RacingCar racingCar : racingCars.getRacingCarList()) {
            stringBuilder.append(racingCar.getName()).append(" : ");
            printPosition(racingCar);
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    private static void printPosition(RacingCar racingCar) {
        for (int idx = 0; idx < racingCar.getPosition(); idx++) {
            stringBuilder.append("-");
        }
    }

    public static void printWinnerNames(List<RacingCar> winners) {
        stringBuilder = new StringBuilder();

        winners.stream().map(RacingCar::getName).forEach(value -> stringBuilder.append(value).append(", "));

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("가 최종 우승했습니다.");
        System.out.println(stringBuilder.toString());
    }
}