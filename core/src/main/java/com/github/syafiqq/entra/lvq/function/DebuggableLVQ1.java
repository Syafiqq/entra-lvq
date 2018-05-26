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
    private final List<ProcessedDatasetPojo> pTesting;
    private final List<ProcessedWeightPojo<Double>> pWeight;

    public final List<OnPostInitializationListener> postInitializeListener = new LinkedList<>();
    public final List<OnPostSatisfactionListener> postSatisfactionListeners = new LinkedList<>();
    public final List<OnDistanceCalculationListener> distanceCalculationListener = new LinkedList<>();
    public final List<OnWeightUpdateListener> weightUpdateListeners = new LinkedList<>();
    public final List<OnPostReducedLearningRateListener> reducedLearningRateListener = new LinkedList<>();
    public final List<OnPostCalculateAccuracyListener> accuracyListeners = new LinkedList<>();
    public final List<OnPostSatisfactionEvaluationListener> satisfactionEvaluationListeners = new LinkedList<>();

    public final List<OnDistanceCalculationListener> testDistanceCalculationListener = new LinkedList<>();
    public final List<OnWeightUpdateListener> testWeightUpdateListeners = new LinkedList<>();
    public final List<OnPostCalculateAccuracyListener> testAccuracyListeners = new LinkedList<>();

    public DebuggableLVQ1(double learningRate, double lrReduction, double lrThreshold, int maxIteration, List<ProcessedDatasetPojo> dataset, List<ProcessedWeightPojo<Double>> weight)
    {
        super(learningRate, lrReduction, lrThreshold, maxIteration);
        this.pDataset = new LinkedList<>();
        this.pTesting = new LinkedList<>();
        this.pWeight = new LinkedList<>();
    }

    public void setSetting(double learningRate, double lrReduction, double lrThreshold, int maxIteration)
    {
        super.learningRate = learningRate;
        super.lrReduction = lrReduction;
        super.lrThreshold = lrThreshold;
        super.maxIteration = maxIteration - 1;
    }

    public void setData(List<ProcessedDatasetPojo> dataset, List<ProcessedWeightPojo<Double>> weight, List<ProcessedDatasetPojo> testing)
    {
        this.pDataset.clear();
        this.pTesting.clear();
        this.pWeight.clear();
        this.pDataset.addAll(dataset);
        this.pWeight.addAll(weight);
        this.pTesting.addAll(testing);
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

    public void trainingAndTesting()
    {
        this.training();
        this.testing(this.pTesting);
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
                this.weightUpdateListeners.forEach(l -> l.preUpdate(data, min, min.isSameSignature(data)));
                if(min.isSameSignature(data))
                {
                    this.moveCloser(data, min);
                }
                else
                {
                    this.moveAway(data, min);
                }
                this.weightUpdateListeners.forEach(l -> l.update(min));
                this.weightUpdateListeners.forEach(l -> l.postUpdate(min));

            }
            this.reduceLearningRate();
            int same = (int) dataset.stream().filter(ProcessedDatasetPojo::isSameClass).count();
            double accuracy = same * 1.0f / dataset.size() * 100.f;
            this.accuracyListeners.forEach(l -> l.calculateAccuracy(same, super.dataset.size(), this.calculateAccuracy(this.dataset)));
            this.evaluateSatisfaction();
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override public void testing(List<ProcessedDatasetPojo> testing)
    {
        for(ProcessedDatasetPojo data : testing)
        {
            this.testDistanceCalculationListener.forEach(l -> l.preCalculated(data));
            for(ProcessedWeightPojo<Double> w : this.weight)
            {
                w.calculateDistance(data);
                this.testDistanceCalculationListener.forEach(l -> l.calculated(data, w));

            }
            this.testDistanceCalculationListener.forEach(l -> l.postCalculated(data));
            final ProcessedWeightPojo<Double> min = this.findMinimum(this.weight);
            this.testWeightUpdateListeners.forEach(l -> l.preUpdate(data, min, min.isSameSignature(data)));
            min.isSameSignature(data);
            this.testWeightUpdateListeners.forEach(l -> l.update(min));
            this.testWeightUpdateListeners.forEach(l -> l.postUpdate(min));
        }
        int same = (int) testing.stream().filter(ProcessedDatasetPojo::isSameClass).count();
        double accuracy = same * 1.0f / testing.size() * 100.f;
        this.testAccuracyListeners.forEach(l -> l.calculateAccuracy(same, testing.size(), this.calculateAccuracy(testing)));
    }

    @Override public void reduceLearningRate()
    {
        double result = (this.learningRate * this.lrReduction);
        this.reducedLearningRateListener.forEach(l -> l.reduceLearningRate(this.learningRate, this.lrReduction, result));
        this.learningRate = result;
    }

    @Override public void evaluateSatisfaction()
    {
        super.evaluateSatisfaction();
        this.satisfactionEvaluationListeners.forEach(l -> l.evaluateSatisfaction(this.counter + 1));
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

    public interface OnWeightUpdateListener
    {
        void preUpdate(ProcessedDatasetPojo data, ProcessedWeightPojo<Double> weight, boolean sameSignature);

        void update(ProcessedWeightPojo<Double> weight);

        void postUpdate(ProcessedWeightPojo<Double> weight);
    }

    public interface OnPostReducedLearningRateListener
    {
        void reduceLearningRate(double learningRate, double lrReduction, double result);
    }

    public interface OnPostCalculateAccuracyListener
    {
        void calculateAccuracy(int same, int size, double accuracy);
    }

    public interface OnPostSatisfactionEvaluationListener
    {
        void evaluateSatisfaction(int counter);
    }
}
