import java.util.Scanner;

public class Shop {

	public static String numSuffix(int i) {
		int rem = i % 10;
		switch (rem) {
		case 0:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return (i + "th");
		case 1:
			if (i % 100 != 11)
				return (i + "st");
			else 
				return (i + "th");
		case 2:
			if (i % 100 != 12) 
				return (i + "nd");
			else 
				return (i + "th");
		case 3:
			if (i % 100 != 13)
				return (i + "rd");
			else 
				return (i + "th");
		default:
			break;
		}
		return "";
	}

	public static int functionSelect(Scanner sc, int function) {
		System.out.println("This program supports 4 functions: ");
		System.out.println("     1. Set Up Shop");
		System.out.println("     2. Buy");
		System.out.println("     3. List Items");
		System.out.println("     4. Checkout");
		System.out.print("Please choose the function you want: ");
		function = sc.nextInt();
		return function;
	}

	public static int getNumItems(Scanner sc, int numItems) {
		System.out.print("Enter the number of items for the shop: ");
		numItems = sc.nextInt();
		return numItems;
	}

	public static void setupShop(Scanner sc, String[] names, double[] prices,
			int[] discountPackages) {

		for (int i = 0; i < names.length; i++) {
			System.out.print("Enter the name of the " + numSuffix(i + 1) + " product: ");
			names[i] = sc.next();
			System.out.print("Enter the per package price of " + names[i] + ": ");
			prices[i] = sc.nextDouble();
			System.out.println("Enter the number of packages ('x') to qualify for Special Discount (buy 'x' get 1 free)");
			System.out.print("for " + names[i] + ", or 0 if no Special Discount offered: ");
			discountPackages[i] = sc.nextInt();
		}
	}

	public static double addedDiscount(Scanner sc, double addedDiscount) {

		System.out.println();
		System.out.print("Enter the dollar amount to qualify for Additional "
				+ "Discount (or 0 if none offered): ");
		addedDiscount = sc.nextDouble();

		return addedDiscount;
	}

	public static double discountRate(Scanner sc, double discountRate) {
		System.out.print("Enter the Additional Discount rate (e.g., 0.1 for 10%): ");
		discountRate = sc.nextDouble();
		while (discountRate <= 0 || discountRate > 0.5) {
			System.out.print("Invalid input. Enter a value > 0 and <= 0.5: ");
			discountRate = sc.nextDouble();
		}
		return discountRate;
	}

	public static void buy(Scanner sc, int[] amounts, String[] names) {
		System.out.println();
		for (int i = 0; i < names.length; i++) {
			System.out.print("Enter the number of " + names[i] + " packages to buy: ");
			amounts[i] = sc.nextInt();
			while (amounts[i] < 0) {
				System.out.print("Invalid input. Enter a value >= 0: ");
				amounts[i] = sc.nextInt();
			}
		}

	}

	public static void listItems(String[] names, double[] prices, int[] amount, double[] amountPrices) {
		int totalAmount = 0;
		System.out.println();
		for (int i = 0; i < names.length; i++) {
			totalAmount += amount[i];
		}
		for (int i = 0; i < names.length; i++) {
			if ( amount[i] != 0 ) {
				amountPrices[i] = amount[i] * prices[i];
				System.out.println(amount[i] + " packages of " + names[i] + " @ $" + prices[i] + " per pkg = $" + amountPrices[i]);
			}
		}
		if (totalAmount == 0) {
			System.out.println("No items were purchased.");
		}
		System.out.println();
	}

	public static double calcOriginalSubTotal(double[] amountPrices, double origSubTotal) {
		for (int i = 0; i < amountPrices.length; i++) {
			origSubTotal = origSubTotal + amountPrices[i];
		}
		return origSubTotal;
	}

	public static void calcFreeAmounts(int[] amounts, int[] discountPackages, int[] freeAmounts) {
		for (int i = 0; i < amounts.length; i++) {
			if (discountPackages[i] != 0) {
				freeAmounts[i] = (amounts[i] - (amounts[i] % (discountPackages[i] + 1))) / (discountPackages[i] + 1);
			}
			else {
				freeAmounts[i] = 0;
			}
		}
	}

	public static double calcNewSubTotal(int[] amounts, double[] prices, int[] freeAmounts, double newSubTotal) {
		for (int i = 0; i < amounts.length; i++) {
			newSubTotal += (amounts[i] - freeAmounts[i]) * prices[i];
		}
		return newSubTotal;
	}

	public static double calcAmountDiscount(double[] prices, int[] freeAmounts, double amountDiscount) {
		for (int i = 0; i < prices.length; i++) {
			amountDiscount += freeAmounts[i] * prices[i];
		}
		return amountDiscount;
	}

	public static double calcPercentDiscounted(double discountRate, double newSubTotal, double addedDiscount) {
		double percentDiscounted = 0;
		if (newSubTotal > addedDiscount) {
			percentDiscounted = newSubTotal * discountRate;
		}
		return percentDiscounted;
	}

	public static double calcNewerSubTotal(double discountRate, double newSubTotal, double addedDiscount) {
		double newerSubTotal = newSubTotal;
		if (newSubTotal > addedDiscount) {
			newerSubTotal = newSubTotal * (1 - discountRate);
		}
		return newerSubTotal;
	}

	public static void checkout(double origSubTotal, double newSubTotal, double amountDiscount, double percentDiscounted, double newerSubTotal, double discountRate) {
		System.out.println();
		System.out.println("Original Sub Total:            $" + origSubTotal);
		if (amountDiscount != 0) {
			System.out.println("Special Discounts:            -$" + amountDiscount);
		}
		else {
			System.out.println("No Special Discounts applied");
		}
		System.out.println("New Sub Total:                 $" + newSubTotal);
		if (percentDiscounted != 0) {
			System.out.println("Additional "  + (discountRate * 100) + "% Discount:    -$" + percentDiscounted);
		}
		else {
			System.out.println("You did not qualify for an Additional Discount");
		}
		System.out.println("Final Sub Total:               $" + newerSubTotal);
		System.out.println();
		System.out.println("Thanks for coming!");
		System.out.println();
	}

	public static void main(String[] args) {

		int function = 0;
		int numItems = 0;
		double addedDiscount = 0.0;
		double discountRate = 0.0;
		int reset = 0;
		double origSubTotal = 0.0;
		double newSubTotal = 0.0;
		double amountDiscount = 0.0;
		double percentDiscounted = 0.0;
		double newerSubTotal = 0.0;
		int function2 = 0;
		int fatReset = 1;

		Scanner sc = new Scanner(System.in);
		function = functionSelect(sc, function);
		while (fatReset == 1) {
			while (reset == 0) {

				if (function == 1) {
					numItems = getNumItems(sc, numItems);
					System.out.println();
					reset = 1;
				}
				if (function == 2 && numItems != 0) {
					reset = 1;
				}
				if (function == 2 && numItems == 0) {
					System.out.println();
					System.out.println("Shop is not set up yet!");
					System.out.println();
					reset = 0;
					function = functionSelect(sc, function);
				}
				if (function == 3 && numItems != 0) {
					reset = 1;
				}
				if (function == 3 && numItems ==0) {
					System.out.println();
					System.out.println("Shop is not set up yet!");
					System.out.println();
					reset = 0;
					function = functionSelect(sc, function);
				}
				if (function == 4 && numItems != 0) {
					reset = 1;
				}
				if (function == 4 && numItems == 0) {
					System.out.println();
					System.out.println("Shop is not set up yet!");
					System.out.println();
					reset = 0;
					function = functionSelect(sc, function);
				}

			} //while 0
			String[] names = new String[numItems];
			double[] prices = new double[numItems];
			int[] amounts = new int[numItems];
			int[] discountPackages = new int[numItems];
			double[] amountPrices = new double[numItems];
			int[] freeAmounts = new int[numItems];
			if (function == 1 ) {
				setupShop(sc, names, prices, discountPackages);
				addedDiscount = addedDiscount(sc, addedDiscount);
				if (addedDiscount != 0) {
					discountRate = discountRate(sc, discountRate);
				}
				System.out.println();
				reset = 1;
			}
			function = functionSelect(sc, function);
			while (reset == 1) {
				if (function == 1) {
					reset = 0;
				}
				if (function == 2 && numItems != 0) {
					buy(sc, amounts, names);
					System.out.println();
					function2 = 1;
					reset = 1;
					function = functionSelect(sc, function);
				}
				if (function == 2 && numItems == 0) {
					System.out.println();
					System.out.println("Shop is not set up yet!");
					System.out.println();
					reset = 1;
					function = functionSelect(sc, function);
				}

				if (function == 3 && numItems != 0 && function2 == 1) {

					listItems(names, prices, amounts, amountPrices);
					reset = 1;
					function = functionSelect(sc, function);
				}
				if (function == 3 && function2 == 0 ) {
					System.out.println();
					System.out.println("You have not bought anything!");
					System.out.println();
					reset = 1;
					function = functionSelect(sc, function);
				}

				if (function == 4 && numItems != 0 && function2 != 0) {
					origSubTotal = calcOriginalSubTotal(amountPrices, origSubTotal);
					calcFreeAmounts(amounts, discountPackages, freeAmounts);
					newSubTotal = calcNewSubTotal(amounts, prices, freeAmounts, newSubTotal);
					amountDiscount = calcAmountDiscount(prices, freeAmounts, amountDiscount);
					percentDiscounted = calcPercentDiscounted(discountRate, newSubTotal, addedDiscount);
					newerSubTotal = calcNewerSubTotal(discountRate, newSubTotal, addedDiscount);
					checkout(origSubTotal, newSubTotal, amountDiscount, percentDiscounted, newerSubTotal, discountRate);
					System.out.print("Would you like to re-run (1 for yes, 0 for no)? ");

					reset = 0;
					function = 0;
					addedDiscount = 0.0;
					discountRate = 0.0;
					origSubTotal = 0.0;
					newSubTotal = 0.0;
					amountDiscount = 0.0;
					percentDiscounted = 0.0;
					newerSubTotal = 0.0;
					function2 = 0;
					numItems = 0;
					fatReset = sc.nextInt();
					System.out.println();
					if (fatReset == 1) {
						function = functionSelect(sc, function);
					}
				}
				if (function == 4 && numItems != 0 && function2 == 0) {
					System.out.println();
					System.out.println("You have not bought anything!");
					System.out.println();
					reset = 1;
					function = functionSelect(sc, function);
				}

			} //while 1
		}//while fatReset
	}//main
}//class
