package com.github.syafiqq.entra.lvq.function.model;

import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
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
    protected final List<DatasetPojo> dataset;

    public LVQ()
    {
        this.weight = new LinkedList<>();
        this.dataset = new LinkedList<>();
    }

    public abstract void initialization();

    public abstract boolean isSatisfied();

    public abstract void evaluateSatisfaction();

    public abstract void reduceLearningRate();

    public void run()
    {
        this.initialization();
        while(!this.isSatisfied())
        {
            for(DatasetPojo data : this.dataset)
            {
                for(ProcessedWeightPojo<T> w : this.weight)
                {
                    w.calculateDistance(data);
                }
                final ProcessedWeightPojo<T> min = this.findMinimum(this.weight);
                if(min.isSameSignature(data))
                {
                    min.moveToward(data);
                }
                else
                {
                    min.moveAway(data);
                }
            }
            this.reduceLearningRate();
            this.calculateAccuracy();
            this.evaluateSatisfaction();
        }
    }

    protected abstract void calculateAccuracy();

    protected abstract ProcessedWeightPojo<T> findMinimum(List<ProcessedWeightPojo<T>> weight);

    public List<ProcessedWeightPojo<T>> getWeight()
    {
        return this.weight;
    }

    public List<DatasetPojo> getDataset()
    {
        return this.dataset;
    }
}
