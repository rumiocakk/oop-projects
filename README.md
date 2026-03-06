# Gym Meal Machine 🏋️

A Java-based vending machine simulator developed for BBM104: Introduction to Programming Laboratory II at Hacettepe University.

## About the Project

This project simulates a **Gym Meal Machine (GMM)** — a vending machine designed for athletes and fitness enthusiasts. Users can purchase meals based on their nutritional preferences (protein, carbohydrate, fat, or calorie content) or directly by slot number.

## Features

- Loads products from a file into a 6x4 slot machine
- Calculates calorie values using the Atwater system
- Accepts purchases by nutritional value (PROTEIN, CARB, FAT, CALORIE) or slot NUMBER
- Validates money (accepts 1, 5, 10, 20, 50, 100, 200 TL)
- Returns correct change after each purchase
- Handles edge cases: full machine, empty slots, insufficient funds, invalid money

## Technologies

- Java 8
- Object-Oriented Programming (Classes, Objects, Encapsulation)

## How to Run

```bash
javac *.java
java -cp . Main Product.txt Purchase.txt GMMOutput.txt
```

## Input / Output

| File | Description |
|------|-------------|
| `Product.txt` | List of products with name, price, protein, carb, fat |
| `Purchase.txt` | List of purchases with payment method and selection |
| `GMMOutput.txt` | Machine state and transaction logs |

## Course

**BBM104** - Introduction to Programming Laboratory II  
Hacettepe University, Spring 2024
