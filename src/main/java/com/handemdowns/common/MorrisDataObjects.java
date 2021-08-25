package com.handemdowns.common;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MorrisDataObjects {
	@Data
	@AllArgsConstructor
	public static class AdGrowthChartPlot {
		private String period;
		private Long adCount;
	}

	@Data
	@AllArgsConstructor
	public static class UserAdCountChartPlot {
		private String label;
		private Long value;
	}
}