package test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Data
@AllArgsConstructor
@Builder
public class Apple {
    Color color;
    int weight;

    public static boolean isGreenApple(Apple apple){
        return apple.getColor().equals(Color.GREEN);
    }

    public static boolean isHeavyApple(Apple apple){
        return apple.getWeight() > 150 ;
    }

    public static List<Apple> filterApples(List<Apple> inventory,
                                    Predicate<Apple> p){
        List<Apple> result=new ArrayList<>();
        for(Apple apple: inventory){
            if(p.test(apple))
                result.add(apple);
        }
        return  result;
    }

    public static void prettyPrintApple(List<Apple> apples, AppleFormatter appleFormatter){
        for(Apple apple:apples){
            String output=appleFormatter.accept(apple);
            System.out.println("output = " + output);;
        }
    }
}
