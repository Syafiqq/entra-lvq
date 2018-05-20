package com.github.syafiqq.entra.lvq.function;

import com.github.syafiqq.entra.lvq.function.model.EuclideanWeightPojo;
import com.github.syafiqq.entra.lvq.function.model.LVQ;
import com.github.syafiqq.entra.lvq.function.model.ProcessedDatasetPojo;
import com.github.syafiqq.entra.lvq.function.model.ProcessedWeightPojo;
import com.github.syafiqq.entra.lvq.model.database.dao.DatasetDao;
import com.github.syafiqq.entra.lvq.model.database.dao.WeightDao;
import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
import com.github.syafiqq.entra.lvq.model.database.pojo.WeightPojo;
import com.github.syafiqq.entra.lvq.util.Settings;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 9:20 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class LVQ1 extends LVQ<Double>
{
    public int counter = 0;
    public double learningRate = 0.05;
    public double lrReduction = 0.1;
    public double lrThreshold = 1e-11;
    public double accuracy = 0;

    @Override public void initialization()
    {
        final List<DatasetPojo> dataset = DatasetDao.getAll(Settings.DB);
        final List<WeightPojo> weight = WeightDao.getAll(Settings.DB);
        super.dataset.clear();
        super.weight.clear();
        super.dataset.addAll(dataset.stream().map(ProcessedDatasetPojo::new).collect(Collectors.toList()));
        super.weight.addAll(weight.stream().map(EuclideanWeightPojo::new).collect(Collectors.toList()));
    }

    @Override public boolean isSatisfied()
    {
        return counter > 5 || this.learningRate < this.lrThreshold;
    }

    @Override public void evaluateSatisfaction()
    {
        ++counter;
    }

    @Override public void reduceLearningRate()
    {
        this.learningRate -= (this.learningRate * this.lrReduction);
    }

    @Override protected void moveAway(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> min)
    {
        min.weight.vector.forEach((idx, val) -> min.weight.vector(idx, val + (this.learningRate * data.dataset.vector(idx)) + (this.learningRate * val)));
    }

    @Override protected void moveToward(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> min)
    {
        min.weight.vector.forEach((idx, val) -> min.weight.vector(idx, val + (this.learningRate * data.dataset.vector(idx)) - (this.learningRate * val)));
    }

    @Override protected void calculateAccuracy()
    {
        this.accuracy = this.dataset.stream().filter(ProcessedDatasetPojo::isSameClass).count() * 1.0f / this.dataset.size() * 100.f;
        System.out.printf("Iteration %d = %f\n", counter + 1, this.accuracy);
    }

    @Override protected ProcessedWeightPojo<Double> findMinimum(List<ProcessedWeightPojo<Double>> weight)
    {
        return super.weight.stream().min(Comparator.comparing(ProcessedWeightPojo::getDistance)).orElse(null);
    }
}
