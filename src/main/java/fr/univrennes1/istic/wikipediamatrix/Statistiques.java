package fr.univrennes1.istic.wikipediamatrix;

import java.util.List;

public class Statistiques {

	public static double mean(List<Integer> list) {
		double res = 0;
		for (Integer integer : list) {
			res += integer;
		}
		return res / list.size();
	}

	public static double std(List<Integer> list) {
		double res = 0;
		double meanList = mean(list);
		for (Integer integer : list) {
			res += (meanList - integer) * (meanList - integer);
		}
		return res / list.size();
	}

	public static int min(List<Integer> list) {
		int minimum = list.get(0);
		for (Integer integer : list) {
			minimum = Math.min(minimum, integer);
		}
		return minimum;
	}

	public static int max(List<Integer> list) {
		int maximum = 0;
		for (Integer integer : list) {
			maximum = Math.max(maximum, integer);
		}
		return maximum;
	}

}
