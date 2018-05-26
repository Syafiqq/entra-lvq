package com.github.syafiqq.entra.lvq.function;

import com.github.syafiqq.entra.lvq.function.model.ProcessedDatasetPojo;
import com.github.syafiqq.entra.lvq.function.model.ProcessedWeightPojo;
import java.util.LinkedList;
import java.util.List;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 22 May 2018, 8:52 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DebuggableLVQ1 extends LVQ1
{
    private final List<ProcessedDatasetPojo> pDataset;
    private final List<ProcessedWeightPojo<Double>> pWeight;

    public final List<OnPostInitializationListener> postInitializeListener = new LinkedList<>();
    public final List<OnPostSatisfactionListener> postSatisfactionListeners = new LinkedList<>();
    public final List<OnDistanceCalculationListener> distanceCalculationListener = new LinkedList<>();

    public DebuggableLVQ1(double learningRate, double lrReduction, double lrThreshold, int maxIteration, List<ProcessedDatasetPojo> dataset, List<ProcessedWeightPojo<Double>> weight)
    {
        super(learningRate, lrReduction, lrThreshold, maxIteration);
        this.pDataset = new LinkedList<>();
        this.pWeight = new LinkedList<>();
    }

    public void setSetting(double learningRate, double lrReduction, double lrThreshold, int maxIteration)
    {
        super.learningRate = learningRate;
        super.lrReduction = lrReduction;
        super.lrThreshold = lrThreshold;
        super.maxIteration = maxIteration - 1;
    }

    public void setData(List<ProcessedDatasetPojo> dataset, List<ProcessedWeightPojo<Double>> weight)
    {
        this.pDataset.clear();
        this.pWeight.clear();
        this.pDataset.addAll(dataset);
        this.pWeight.addAll(weight);
    }

    @Override public void initialization()
    {
        super.dataset.clear();
        super.weight.clear();
        super.dataset.addAll(this.pDataset);
        super.weight.addAll(this.pWeight);
        this.postInitializeListener.forEach(l -> l.postInitialization(super.learningRate, super.lrReduction, super.lrThreshold, super.maxIteration + 1, super.weight, super.dataset));
    }

    @Override public boolean isSatisfied()
    {
        boolean satisfied = super.isSatisfied();
        this.postSatisfactionListeners.forEach(s -> s.postSatisfaction(super.counter + 1, super.maxIteration + 1, super.learningRate, super.lrThreshold, satisfied));
        return satisfied;
    }

    @Override public void training()
    {
        this.counter = 0;
        this.initialization();
        while(!this.isSatisfied())
        {
            for(ProcessedDatasetPojo data : this.dataset)
            {
                this.distanceCalculationListener.forEach(l -> l.preCalculated(data));
                for(ProcessedWeightPojo<Double> w : this.weight)
                {
                    w.calculateDistance(data);
                    this.distanceCalculationListener.forEach(l -> l.calculated(data, w));
                }
                this.distanceCalculationListener.forEach(l -> l.postCalculated(data));
                final ProcessedWeightPojo<Double> min = this.findMinimum(this.weight);
                if(min.isSameSignature(data))
                {
                    this.moveCloser(data, min);
                }
                else
                {
                    this.moveAway(data, min);
                }
            }
            this.reduceLearningRate();
            this.calculateAccuracy(this.dataset);
            this.evaluateSatisfaction();
        }
    }

    public interface OnPostInitializationListener
    {
        void postInitialization(double learningRate, double lrReduction, double lrThreshold, int maxIteration, List<ProcessedWeightPojo<Double>> dataset, List<ProcessedDatasetPojo> weight);
    }

    public interface OnPostSatisfactionListener
    {
        void postSatisfaction(int epoch, int maxEpoch, double learningRate, double lrThreshold, boolean result);
    }

    public interface OnDistanceCalculationListener
    {
        void preCalculated(ProcessedDatasetPojo data);

        void calculated(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight);

        void postCalculated(ProcessedDatasetPojo data);
    }
}
