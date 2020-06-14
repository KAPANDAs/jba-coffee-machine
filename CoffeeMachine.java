package machine;

import java.util.Scanner;

enum MachineState {
    WAITING_ACTION, WAIT_COFFEETYPE, WAIT_WATER, WAIT_MILK, WAIT_BEANS, WAIT_CUPS, EXIT
}

class CoffeeMachineEntity {

    public static MachineState currentState = MachineState.WAITING_ACTION;
    static int[] supplies = {400, 540, 120, 550, 9};
    static int[][] costs = {
            {250, 0, 16, 4}, {350, 75, 20, 7}, {200, 100, 12, 6}
    };

    static void processInput(String arg){
        switch (currentState){
            case WAITING_ACTION:
                switch (arg){
                    case "buy":
                        currentState = MachineState.WAIT_COFFEETYPE;
                        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                        break;
                    case "fill":
                        currentState = MachineState.WAIT_WATER;
                        System.out.println("\nWrite how many ml of water do you want to add: ");
                        break;
                    case "take":
                        takeAction(supplies);
                        currentState = MachineState.WAITING_ACTION;
                        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                        break;
                    case "remaining":
                        outputCurrentState(supplies);
                        currentState = MachineState.WAITING_ACTION;
                        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                        break;
                    case "exit":
                        currentState = MachineState.EXIT;
                        break;
                    default:
                        throw new IllegalStateException();
                }
                break;
            case WAIT_COFFEETYPE:
                buyAction(supplies, arg);
                currentState = MachineState.WAITING_ACTION;
                System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                break;
            case WAIT_WATER:
                fillAction(currentState, Integer.parseInt(arg));
                currentState = MachineState.WAIT_MILK;
                System.out.println("Write how many ml of milk do you want to add: ");
                break;
            case WAIT_MILK:
                fillAction(currentState, Integer.parseInt(arg));
                currentState = MachineState.WAIT_BEANS;
                System.out.println("Write how many grams of coffee beans do you want to add: ");
                break;
            case WAIT_BEANS:
                fillAction(currentState, Integer.parseInt(arg));
                currentState = MachineState.WAIT_CUPS;
                System.out.println("Write how many disposable cups of coffee do you want to add: ");
                break;
            case WAIT_CUPS:
                fillAction(currentState, Integer.parseInt(arg));
                currentState = MachineState.WAITING_ACTION;
                System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                break;
            default:
                throw new IllegalStateException();
        }
    }

    public static MachineState getCurrentState(){
        return currentState;
    }

    private static void takeAction(int[] currentState) {
        System.out.println("I gave you $" + currentState[3]);
        currentState[3] = 0;
    }

    private static void outputCurrentState(int[] currentState) {
        System.out.println("\nThe coffee machine has:");
        System.out.println(currentState[0] + " of water");
        System.out.println(currentState[1] + " of milk");
        System.out.println(currentState[2] + " of coffee beans");
        System.out.println(currentState[4] + " of disposable cups");
        System.out.println(currentState[3] + " of money");
    }

    private static void buyAction(int[] currentState, String coffeeType) {
        int breakCode = 0;
        if (coffeeType.equals("back")) {
        } else {
            int m_coffeeType = Integer.parseInt(coffeeType);
            for (int i = 0; i < 4; i++) {
                if (m_coffeeType == 1 && i == 1) {
                } else if (currentState[i] / costs[m_coffeeType - 1][i] <= 0) {
                    if (i == 0) {
                        breakCode = i + 1;
                    } else if (i == 1) {
                        breakCode = i + 1;
                    } else if (i == 2) {
                        breakCode = i + 1;
                    } else {
                        breakCode = i + 1;
                    }
                    break;
                }
            }
            if (breakCode == 0) {
                System.out.println("I have enough resources, making you a coffee!");
                for (int j = 0; j < 3; j++) {
                    currentState[j] -= costs[m_coffeeType - 1][j];
                }
                currentState[3] += costs[m_coffeeType - 1][3];
                currentState[4] -= 1;
            } else if (breakCode == 1) {
                System.out.println("Sorry, not enough water!");
            } else if (breakCode == 2) {
                System.out.println("Sorry, not enough milk!");
            } else if (breakCode == 3) {
                System.out.println("Sorry, not enough coffee beans!");
            } else {
                System.out.println("Sorry, not enough cups!");
            }

        }
    }

    private static void fillAction(MachineState state, int amount){
        switch (state){
            case WAIT_WATER:
                supplies[0] += amount;
                break;
            case WAIT_MILK:
                supplies[1] += amount;
                break;
            case WAIT_BEANS:
                supplies[2] += amount;
                break;
            case WAIT_CUPS:
                supplies[4] += amount;
                break;
            default:
                throw new IllegalStateException();
        }
    }
}

public class CoffeeMachine {

    public static void main(String[] args) {
        System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
        while (CoffeeMachineEntity.getCurrentState() != MachineState.EXIT){
            Scanner scanner = new Scanner(System.in);
            CoffeeMachineEntity.processInput(scanner.nextLine());
        }
    }
}

