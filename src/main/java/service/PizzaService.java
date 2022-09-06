package service;

import model.Ingredient;
import model.Pizza;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PizzaService {
    /**
     * Zwraca cenę pizzy n podstawie sumy składników
     * @param pizza
     * @return price
     */
    public double getPizzaPrice(Pizza pizza){
        return pizza.getIngredients().stream().mapToDouble(Ingredient::getPrice).sum();
    }
    /**
     * Wyszukuje i zwraca najtańszą pizze
     * @return Pizza
     */
    public Pizza findCheapest(){
        return Arrays.stream(Pizza.values())
                .min(Comparator.comparing(this::getPizzaPrice))         // optional
                .orElse(null);                                     // orElse -> jeśli optional zawiera null to zwórć dowolną warość
    }
    /**
     * Wyszukuje i zwraca najtańszą pizze ostrą
     * @return Pizza
     */
    public Pizza findCheapestSpicy() {
        return Arrays.stream(Pizza.values())
                .filter(pizza -> pizza.getIngredients().stream()
                        .anyMatch(ingredient -> ingredient.isSpicy())   // zwraca true gdy jakikolwiek składnik jest ostry
                )
                .min(Comparator.comparing(this::getPizzaPrice))         // optional
                .orElse(null);                                     // orElse -> jeśli optional zawiera null to zwórć dowolną warość
    }

    /**
     * Wyszukuje najdroższą pizze wegetariańską
     * @return Pizza
     */
    public Pizza findMostExpensiveVegetarian(){
        return Arrays.stream(Pizza.values())
                .filter(pizza -> pizza.getIngredients().stream()
                        .noneMatch(Ingredient::isMeat)
                )
                .max(Comparator.comparing(this::getPizzaPrice))         // optional
                .orElse(null);
    }
    /**
     * Wyszukuje i zwraca listę pizz mięsnych posortowanych malejąco po liczbie składników
     * @return List<Pizza>
     */
    public List<Pizza> iLikeMeat(){
        return Arrays.stream(Pizza.values())
                .filter(pizza -> pizza.getIngredients().stream()
                        .anyMatch(Ingredient::isMeat)
                )
                .sorted(Comparator.comparing(pizza -> pizza.getIngredients().stream()
                        .count(), Comparator.reverseOrder()
                ))
                .collect(Collectors.toList());
    }
    /**
     * Grupuje pizze po cenie
     * @return
     */
    public Map groupByPrice(){
        return Arrays.stream(Pizza.values()).collect(Collectors.groupingBy(this::getPizzaPrice));
    }
    /**
     * Grupowanie pizzy po ilości składników pod warunkiem, że jest ich więcej niż 4
     * @return
     */
    public TreeMap<Integer, List<Pizza>> groupByNumberOfIngredientsGreaterThan4(){
        return null;
    }
    // Zwraca menu | nazwa pizzy | ostra lub łagodna | mięsna lub wege | cena | nazwa_składnika1, ..., nazwa_składnikaN |
    public String getMenu(){
        return Arrays.stream(Pizza.values())
                .map(pizza -> String.format("| %15s | %8s | %8s | %5.2fzł | %-100s |",
                        pizza.getName(),
                        pizza.getIngredients().stream().anyMatch(Ingredient::isSpicy) ? "ostra" : "łagodna",
                        pizza.getIngredients().stream().anyMatch(Ingredient::isMeat) ? "mięsna" : "wege",
                        getPizzaPrice(pizza),
                        pizza.getIngredients().stream().map(ingredient -> ingredient.getName()).collect(Collectors.joining(","))
                        )
                )
                .collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) {
        PizzaService ps = new PizzaService();
//        System.out.println("MARGHERITA:" + ps.getPizzaPrice(Pizza.CAPRI));
//        System.out.println("CHEAPEST:" + ps.findCheapest());
//        System.out.println("CHEAPEST SPICY: " + ps.findCheapestSpicy());
//        System.out.println("EXPENSIVE VEGE: " + ps.findMostExpensiveVegetarian());
//        System.out.println(ps.iLikeMeat());
//        System.out.println(ps.groupByPrice());
        System.out.println(ps.getMenu());
    }

}
