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
    public final double lrReduction;
    public final double lrThreshold;
    public final int maxIteration;
    public double learningRate;

    public LVQ1(double learningRate, double lrReduction, double lrThreshold, int maxIteration)
    {
        this.learningRate = learningRate;
        this.lrReduction = lrReduction;
        this.lrThreshold = lrThreshold;
        this.maxIteration = maxIteration - 1;
    }


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
        return counter > maxIteration || this.learningRate < this.lrThreshold;
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
        for(String idx : Settings.columns)
        {
            min.weight.vector.mergeDouble(idx, 0, (val, __) -> val - (this.learningRate * data.dataset.vector(idx)) + (this.learningRate * val));
        }
    }

    @Override protected void moveCloser(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> min)
    {
        for(String idx : Settings.columns)
        {
            min.weight.vector.mergeDouble(idx, 0, (val, __) -> val + (this.learningRate * data.dataset.vector(idx)) - (this.learningRate * val));
        }
    }

    @Override public double calculateAccuracy(List<ProcessedDatasetPojo> dataset)
    {
        return (double) (dataset.stream().filter(ProcessedDatasetPojo::isSameClass).count() * 1.0f / dataset.size() * 100.f);
    }

    @Override protected ProcessedWeightPojo<Double> findMinimum(List<ProcessedWeightPojo<Double>> weight)
    {
        return super.weight.stream().min(Comparator.comparing(ProcessedWeightPojo::getDistance)).orElse(null);
    }
}
