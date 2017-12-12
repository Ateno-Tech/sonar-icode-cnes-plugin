package fr.cnes.sonarqube.plugins.icode.measures;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.ce.measure.MeasureComputer.MeasureComputerContext;
import org.sonar.api.ce.measure.MeasureComputer.MeasureComputerDefinition;
import org.sonar.api.ce.measure.MeasureComputer.MeasureComputerDefinitionContext;
import org.sonar.api.measures.Metric;

public class ComputeModuleF90LinesOfCodeStatisticsTest {

	@Test
	public void callDefine() {
		MeasureComputerDefinition measureComputerDefinition = Mockito.mock(MeasureComputerDefinition.class);
		MeasureComputer.MeasureComputerDefinition.Builder builder = Mockito.mock(MeasureComputer.MeasureComputerDefinition.Builder.class);
		List<Metric> allMetrics = (new ICodeMetricsF90LinesOfCode()).getMetrics();
		String[] metricsKey = new String[]{allMetrics.get(0).key(), allMetrics.get(3).key(), allMetrics.get(1).key(), allMetrics.get(2).key()};
		Mockito.when(builder.setInputMetrics(metricsKey)).thenReturn(builder);
		Mockito.when(builder.setOutputMetrics(metricsKey)).thenReturn(builder);
		Mockito.when(builder.build()).thenReturn(measureComputerDefinition);
		MeasureComputerDefinitionContext measureComputerDefinitionContext = Mockito.mock(MeasureComputerDefinitionContext.class); 
		Mockito.when(measureComputerDefinitionContext.newDefinitionBuilder()).thenReturn(builder);
		ComputeModuleF90LinesOfCodeStatistics compute = new ComputeModuleF90LinesOfCodeStatistics();
		compute.define(measureComputerDefinitionContext);
	}

	@Test
	public void given_context_when_compute_then_newComputedMeasures(){
		org.sonar.api.ce.measure.Component componentFile = Mockito.mock(org.sonar.api.ce.measure.Component.class);
		Mockito.when(componentFile.getType()).thenReturn(Component.Type.DIRECTORY);
		MeasureComputerContext context = Mockito.mock(MeasureComputerContext.class);
		Mockito.when(context.getComponent()).thenReturn(componentFile);
		ArrayList<Measure> childsMeasures = new ArrayList<Measure>();
		Measure aMeasureMin = Mockito.mock(Measure.class);
		Mockito.when(aMeasureMin.getIntValue()).thenReturn(1);
		Mockito.when(aMeasureMin.getDoubleValue()).thenReturn(1.);
		Measure aMeasureMax = Mockito.mock(Measure.class);
		Mockito.when(aMeasureMax.getIntValue()).thenReturn(5);
		Mockito.when(aMeasureMax.getDoubleValue()).thenReturn(5.);
		childsMeasures.add(aMeasureMin);
		childsMeasures.add(aMeasureMax);
		Mockito.when(context.getChildrenMeasures(ICodeMetricsF90LinesOfCode.F90_LOC.key())).thenReturn(childsMeasures);
		Mockito.when(context.getChildrenMeasures(ICodeMetricsF90LinesOfCode.F90_LOC_MEAN.key())).thenReturn(childsMeasures);
		Mockito.when(context.getChildrenMeasures(ICodeMetricsF90LinesOfCode.F90_LOC_MIN.key())).thenReturn(childsMeasures);
		Mockito.when(context.getChildrenMeasures(ICodeMetricsF90LinesOfCode.F90_LOC_MAX.key())).thenReturn(childsMeasures);
		ComputeModuleF90LinesOfCodeStatistics computeModule = new ComputeModuleF90LinesOfCodeStatistics();
		computeModule.compute(context);
		Mockito.verify(context).addMeasure(ICodeMetricsF90LinesOfCode.F90_LOC.key(), 6);
		Mockito.verify(context).addMeasure(ICodeMetricsF90LinesOfCode.F90_LOC_MEAN.key(), 3.0);
		Mockito.verify(context).addMeasure(ICodeMetricsF90LinesOfCode.F90_LOC_MIN.key(), 1);
		Mockito.verify(context).addMeasure(ICodeMetricsF90LinesOfCode.F90_LOC_MAX.key(), 5);		
	}

}
