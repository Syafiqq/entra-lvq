package com.github.syafiqq.entra.lvq.function.model;

import java.util.LinkedList;
import java.util.List;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 5:59 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class LVQ<T>
{
    protected final List<ProcessedWeightPojo<T>> weight;
    protected final List<ProcessedDatasetPojo> dataset;

    public LVQ()
    {
        this.weight = new LinkedList<>();
        this.dataset = new LinkedList<>();
    }

    public abstract void initialization();

    public abstract boolean isSatisfied();

    public abstract void evaluateSatisfaction();

    public abstract void reduceLearningRate();

    public void training()
    {
        this.initialization();
        while(!this.isSatisfied())
        {
            for(ProcessedDatasetPojo data : this.dataset)
            {
                for(ProcessedWeightPojo<T> w : this.weight)
                {
                    w.calculateDistance(data);
                }
                final ProcessedWeightPojo<T> min = this.findMinimum(this.weight);
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
            System.out.printf("Accuracy = %f\n", this.calculateAccuracy(this.dataset));
            this.calculateAccuracy(this.dataset);
            this.evaluateSatisfaction();
        }
    }

    public void testing(List<ProcessedDatasetPojo> testing)
    {
        for(ProcessedDatasetPojo data : testing)
        {
            for(ProcessedWeightPojo<T> w : this.weight)
            {
                w.calculateDistance(data);
            }
            final ProcessedWeightPojo<T> min = this.findMinimum(this.weight);
            min.isSameSignature(data);
        }
    }

    protected abstract void moveAway(ProcessedDatasetPojo data, ProcessedWeightPojo<T> min);

    protected abstract void moveCloser(ProcessedDatasetPojo data, ProcessedWeightPojo<T> min);

    public abstract double calculateAccuracy(List<ProcessedDatasetPojo> dataset);

    protected abstract ProcessedWeightPojo<T> findMinimum(List<ProcessedWeightPojo<T>> weight);

    public List<ProcessedWeightPojo<T>> getWeight()
    {
        return this.weight;
    }

    public List<ProcessedDatasetPojo> getDataset()
    {
        return this.dataset;
    }
}
