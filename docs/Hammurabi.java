package docs;

import java.util.Scanner;

import java.util.Random;

	public class Hammurabi {
		public int people = 100;
		public int bushels = 2800;
		public int acresOwned = 1000;
		public int landValue = 3;
		public boolean gameOn = true;
		public String name = "Hammurabi";


		Scanner scanner = new Scanner(System.in);

		public static void main(String[] args) { // required in every Java program
			new Hammurabi().playGame();

		}
		void playGame() {
			int starvedFolks = 0;
			int immigrantsGained = 5;
			int seedsPlanted = 3000;
			int year = 1;
			int ratDamage = 200;
			int bushelsToFeed =0;
			int yearsBounty = 19;
			while (gameOn == true) {
				if(year <10) {
					if (year ==1){
						this.name = rulerName();
					}
					System.out.println("               T~~\n" +
							"               |\n" +
							"              /\"\\\n" +
							"      T~~     |'| T~~\n" +
							"  T~~ |    T~ WWWW|\n" +
							"  |  /\"\\   |  |  |/\\T~~\n" +
							" /\"\\ WWW  /\"\\ |' |WW|		How's it going, " + this.name + "? \n" +
							"WWWWW/\\| /   \\|'/\\|/\"\\		You are in year " + year + " of your ten year rule.\n" +
							"|   /__\\/]WWW[\\/__\\WWWW		In the previous year " + starvedFolks +" people starved to death.\n" +
							"|\"  WWWW'|I_I|'WWWW'  |		In the previous year " + immigrantsGained + " people entered the kingdom.\n" +
							"|   |' |/  -  \\|' |'  |		The population is now "+ people + ". \n" +
							"|'  |  |LI=H=LI|' |   |		We harvested "+ yearsBounty + " bushels at " + seedsPlanted + " bushels per acre. \n" +
							"|   |' | |[_]| |  |'  |		Rats destroyed "+ ratDamage +" bushels, leaving " + bushels + " bushels in storage.\n" +
							"|   |  |_|###|_|  |   |		The city owns "+ acresOwned + " acres of land.\n" +
							"'---'--'-/___\\-'--'---'		Land is currently worth " + landValue + " bushels per acre.");
				}
				if (howManyAcresToBuy(landValue, bushels) == 0){
					askHowManyAcresTosell(acresOwned);
				}
				bushelsToFeed = askHowMuchGrainToFeedPeople(bushels);
				seedsPlanted = askHowManyAcresToPlant(acresOwned, people, bushels);
				plagueDeaths(people);
				starvedFolks = starvationDeaths(people, bushelsToFeed);
				if (starvedFolks > 0){
					if (uprising(people, starvedFolks) == true) {
						this.gameOn = true;
						break;
					}
				} else {
					immigrantsGained = immigrants(people, acresOwned, bushelsToFeed);
				}
				seedsPlanted = harvest(seedsPlanted);
				yearsBounty = seedsPlanted * seedsPlanted;
				ratDamage = grainEatenByRats(bushels);
				landValue = newCostOfLand();

				if (year < 10 && this.people <= 10){
					this.gameOn = false;
					break;
				} else if (year == 10){
					this.gameOn = false;
					break;
				} else {
					this.gameOn = true;
					year++;
				}
			}
			scanner.close();
			if (year < 10 && this.gameOn == false){
				System.out.println("Wow " + name + ", you really blew it this time, the remaining followers have \n" +
						"collectively concluded youre not fit to rule anything more than the stone" +
						" thrown at you");
			} else if (year == 10){
				System.out.println("(\uD83D\uDC4D ͡❛ ͜ʖ ͡❛)\uD83D\uDC4D" +
									"You win!");
			} else System.out.println("Wow "+ name +", you really blew it this time, the remaining followers have \n" +
					"collectively concluded youre not fit to rule anything more than the stone" +
					" thrown at you");
		}

		public int howManyAcresToBuy(int price, int bushels) {
			System.out.println("How many acres would you like to purchase? ");
			int userInput = scanner.nextInt();
			price *= userInput;
			if (bushels >= price) {
				this.bushels -= price;
			}
			else {
				System.out.println("You fool, you only have " + this.bushels + " bushels remaining, try again!");
				return howManyAcresToBuy(this.newCostOfLand(), bushels);
			}
			System.out.println("You have " + (this.bushels - userInput) + " bushels left.");
			return userInput;
		}

		public int askHowManyAcresTosell(int acresOwned){
			System.out.println("How many acres of land would you like to sell?");
			int userInpt = scanner.nextInt();
			if(userInpt <= acresOwned){
				this.acresOwned = this.acresOwned - userInpt;
				this.bushels += userInpt * landValue;
			}
			else {
				System.out.println("You doofus, you only have " + acresOwned + " acres, try again!!");
				return askHowManyAcresTosell(acresOwned);
			}
			System.out.println("You have " + this.bushels + " amount of bushels left");
			return userInpt;
		}
		public int askHowMuchGrainToFeedPeople(int bushels){
			System.out.println("How many bushels would you like to feed the people?");

			int userInput = scanner.nextInt();
			if(userInput <= bushels){
				this.bushels -= userInput;
			}else if (userInput < 100){
				System.out.println("Awfully stingy this year...");
				return 0;
			}
			else {
				System.out.println("You imbecile, you only have " + this.bushels + " bushels in storage, try again.");
				return askHowMuchGrainToFeedPeople(bushels);
			}
			System.out.println("You have fed the people " + userInput + " grains, you currently have " + this.bushels + " bushels left in storage.");
			return userInput;
		}

		public int askHowManyAcresToPlant(int acresOwned, int people, int bushels) {

				System.out.println("How many acres would you like to plant with grain?");
				int acresToPlant = scanner.nextInt();
				int amountOfBushelsNeed = acresToPlant * 2;
				int amountOfPeopleNeeded = acresToPlant / 10;
				if (acresToPlant <= acresOwned && amountOfBushelsNeed <= bushels && amountOfPeopleNeeded <= this.people) {
					System.out.println("You are currently planting " + acresToPlant + " amount of acres");
					this.bushels -= amountOfBushelsNeed;
					return acresToPlant;
				} else {
					System.out.println("You nimrod, you can't plant this amount of acres, try again!!");
					return askHowManyAcresToPlant(acresOwned, people, bushels);
				}
		}

		public int plagueDeaths(int population) {
			Random random = new Random();
			if(random.nextInt(101) <16){
				System.out.println("Wow, a plague has struck! your population got cleanly shaved in half");
				this.people = this.people /2;
				return this.people;
			}
			return 0;
		}

		public int starvationDeaths(int people, int bushelsToFeed) {
			int amountOfBushelsNeeded = people * 20;
			if (bushelsToFeed >= amountOfBushelsNeeded) {
				this.bushels += (bushelsToFeed - amountOfBushelsNeeded);
				return 0;
			}

			int bushelsShort = amountOfBushelsNeeded - bushelsToFeed;
			double amountOfPeopleStarved = (double) bushelsShort/20;
			double numberCeil = Math.ceil(amountOfPeopleStarved);
			this.people -= (int) numberCeil;
			System.out.println("Your people are STARVING, " + (int) numberCeil + " people have died from starvation!!");
			return (int) numberCeil;

		}

		public boolean uprising(int population, int howManyPeopleStarved) {

			double populationNumber = (double) population *.45;
			if(howManyPeopleStarved > populationNumber){
				System.out.println("Wow " + name +", the people have come together to overthrow your ruling! ");
				this.gameOn = false;
				return true;
			}

			return false;
		}

		public int immigrants(int population, int acresOwned, int grainInStorage) {
			if(starvationDeaths(population,grainInStorage) ==0){
				int result = (20 * acresOwned + grainInStorage) / (100 * population) + 1;
				System.out.println("Strangers from over the pond have followed the smell of your generosity \n" +
				"You've gained immigrants!!");
				this.people += result;
				return result;
			}
			return 0;
		}

		public int harvest(int seedsPlanted) {
			Random random = new Random();
			int randomNum =random.nextInt(6)+1;
			this.bushels += (seedsPlanted * randomNum);

			return randomNum;
		}

		public int grainEatenByRats(int bushels) {
			Random random = new Random();
			if (random.nextInt(101) < 40) {
				Random random2 = new Random();
				double randomNumber2 = random2.nextInt(21)+10;
				int ratChance = (int) (bushels * randomNumber2/100);
				System.out.println("Your grain storage has been touched by the rats, supplies have plummeted.");
				return ratChance;

			}
			return 0;
		}

		public int newCostOfLand() {
			Random random = new Random();
			this.landValue = random.nextInt(7)+17;

			return landValue;
		}

		public String rulerName(){
			Scanner scanner = new Scanner(System.in);
			System.out.println("Congrats!! You're the new ruler, what was your name again?");
			String newName = scanner.nextLine();
			if (newName.equals("") || newName.equals(" ")){
				System.out.println("That is not a funky enough name for a True Ruler, try again");
				return rulerName();
			} else {
				name = newName;
				return newName;
			}

		}

	}
