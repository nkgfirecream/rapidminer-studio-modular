/**
 * Copyright (C) 2001-2020 by RapidMiner and the contributors
 * 
 * Complete list of developers available at our web site:
 * 
 * http://rapidminer.com
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/.
*/
package com.rapidminer.gui.plotter.mathplot;

import java.awt.Color;

import org.math.plot.Plot2DPanel;

import com.rapidminer.datatable.DataTable;
import com.rapidminer.datatable.DataTableRow;
import com.rapidminer.gui.plotter.PlotterConfigurationModel;


/**
 * This plotter can be used to create 2D bar plots.
 *
 * @author Sebastian Land, Ingo Mierswa
 * @deprecated since 9.2.0
 */
@Deprecated
public class SticksPlot2D extends JMathPlotter2D {

	private static final long serialVersionUID = -4351530035081388245L;

	public SticksPlot2D(PlotterConfigurationModel settings) {
		super(settings);
	}

	public SticksPlot2D(PlotterConfigurationModel settings, DataTable dataTable) {
		super(settings, dataTable);
	}

	@Override
	public void update() {
		if (getAxis(0) != -1) {
			getPlotPanel().removeAllPlots();
			int totalNumberOfColumns = countColumns();
			for (int currentVariable = 0; currentVariable < totalNumberOfColumns; currentVariable++) {
				if (getPlotColumn(currentVariable)) {
					DataTable table = getDataTable();
					synchronized (table) {
						int i = 0;
						double[][] data = new double[getDataTable().getNumberOfRows()][2];
						for (DataTableRow row : table) {
							data[i][0] = row.getValue(getAxis(0));
							if (Double.isNaN(data[i][0])) {
								data[i][0] = 0.0d;
							}
							data[i][1] = row.getValue(currentVariable);
							if (Double.isNaN(data[i][1])) {
								data[i][1] = 0.0d;
							}
							i++;
						}
						// PlotPanel construction
						Color color = getColorProvider().getPointColor(
								(double) (currentVariable + 1) / (double) totalNumberOfColumns);
						((Plot2DPanel) getPlotPanel())
								.addBarPlot(getDataTable().getColumnName(currentVariable), color, data);
					}
				}
			}
		} else {
			getPlotPanel().removeAllPlots();
		}
	}

	@Override
	public int getValuePlotSelectionType() {
		return MULTIPLE_SELECTION;
	}

	@Override
	public int getNumberOfAxes() {
		return 1;
	}

	@Override
	public String getAxisName(int index) {
		switch (index) {
			case 0:
				return "x-Axis";
			default:
				return "empty";
		}
	}

	@Override
	public String getPlotName() {
		return "y-Axis";
	}

	@Override
	public String getPlotterName() {
		return PlotterConfigurationModel.STICK_CHART;
	}
}
