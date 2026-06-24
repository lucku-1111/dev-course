package stream;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    static void printProduct(Product product) {
        System.out.println(product.getName() + " (" + product.getPrice() + "원)");
    }

    static void main() {
        List<Product> list = new ArrayList<>();

        list.add(new Product("연필", 500));
        list.add(new Product("공책", 1200));
        list.add(new Product("지우개", 300));
        list.add(new Product("필통", 3000));
        list.add(new Product("볼펜", 800));

        System.out.println("===== 1. 스트림 만들고 전체 출력 (forEach) =====");
        list.forEach((p -> printProduct(p)));

        System.out.println("\n===== 2. filter: 1000원 이상만 =====");
        list.stream()
                .filter(p -> p.getPrice() >= 1000)
                .forEach(p -> printProduct(p));

        System.out.println("\n===== 3. map: 이름만 뽑기 =====");
        list.stream()
                .map(p -> p.getName())
                .forEach(name -> System.out.println(name));

        System.out.println("\n===== 4. map vs flatMap (주문 속 상품 목록) =====");
        List<Order> orders = Arrays.asList(
                new Order(1, Arrays.asList("연필", "공책")),
                new Order(2, Arrays.asList("필통", "볼펜", "공책"))
        );

        List<List<String>> byMap = orders.stream()
                .map(o -> o.getItems())
                .collect(Collectors.toList());
        List<String> byFlatMap = orders.stream()
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.toList());

        System.out.println("map     : " + byMap);
        System.out.println("flatMap : " + byFlatMap);

        System.out.println("\n===== 5. filter + map + collect: 1000원 이상 상품 이름 리스트 =====");
        List<String> result = list.stream()
                .filter(p -> p.getPrice() >= 1000)
                .map(p -> p.getName())
                .collect(Collectors.toList());
        System.out.println(result);

        System.out.println("\n===== 6. 통계 =====");
        long count = result.stream()
                .count();
        int sum = list.stream()
                .mapToInt(p -> p.getPrice())
                .sum();
        double avg = list.stream()
                .mapToInt(p -> p.getPrice())
                .average().getAsDouble();
        List<String> sortedList = list.stream()
                .sorted((a, b) -> a.getPrice() - b.getPrice())
                .map(p -> p.getName())
                .collect(Collectors.toList());

        System.out.println("1000원 이상 개수: " + count);
        System.out.println("전체 가격 합계: " + sum);
        System.out.println("전체 가격 평균: " + avg);
        System.out.println("가격 오름차순: " + sortedList);
    }
}
