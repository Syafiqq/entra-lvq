package com.github.syafiqq.entra.lvq.function.model;

import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
import com.github.syafiqq.entra.lvq.model.database.pojo.WeightPojo;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 9:00 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class EuclideanWeightPojo extends ProcessedWeightPojo<Double>
{
    private final static BinaryOperator<Double> adder = (n1, n2) -> n1 + n2;

    public EuclideanWeightPojo(WeightPojo pojo)
    {
        super(pojo);
    }

    @Override public void calculateDistance(DatasetPojo pojo)
    {
        AtomicReference<Double> distance = new AtomicReference<>((double) 0);
        pojo.vector.forEach((idx, val) -> distance.accumulateAndGet(Math.pow(super.vector(idx) - val, 2), adder));
        super.distance = Math.sqrt(distance.get());
    }

    @Override public String toString()
    {
        return super.weight.toString();
    }
}
