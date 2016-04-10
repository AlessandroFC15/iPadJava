package ipadjava;

public class Data {
	private int day;
	private int month;
	private int year;

	Data(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	Data(Data oldData) {
		day = oldData.day;
		month = oldData.month;
		year = oldData.year;
	}

	public void setDate(int day, int month, int year) {
		this.month = month;
		this.year = year;
		this.day = checkDay(day);
	}

	private int checkDay(int day) {
		int diasPorMes[] = { 0, 31, 28, 31, 30, 30, 31, 31, 30, 31, 30, 31 };

		if (day > 0 && day <= diasPorMes[month]) {
			return day;
		}

		if (month == 2 && day == 29
				&& (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
			return day;
		}

		System.out.println("Dia inválido");

		return 1;
	}

	// Overriding

	public String toString() {
		return ">> DATE OF CREATION = " + day + "/" + month + "/" + year;
	}
}
