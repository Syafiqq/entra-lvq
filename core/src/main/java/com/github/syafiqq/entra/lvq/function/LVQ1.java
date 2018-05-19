package com.github.syafiqq.entra.lvq.function;

import com.github.syafiqq.entra.lvq.function.model.EuclideanWeightPojo;
import com.github.syafiqq.entra.lvq.function.model.LVQ;
import com.github.syafiqq.entra.lvq.model.database.dao.DatasetDao;
import com.github.syafiqq.entra.lvq.model.database.dao.WeightDao;
import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
import com.github.syafiqq.entra.lvq.model.database.pojo.WeightPojo;
import com.github.syafiqq.entra.lvq.util.Settings;
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
    @Override public void initialization()
    {
        final List<DatasetPojo> dataset = DatasetDao.getAll(Settings.DB);
        final List<WeightPojo> weight = WeightDao.getAll(Settings.DB);
        super.dataset.clear();
        super.processed.clear();
        super.dataset.addAll(dataset);
        super.processed.addAll(weight.stream().map(EuclideanWeightPojo::new).collect(Collectors.toList()));
    }

    @Override public boolean isSatisfied()
    {
        return false;
    }

    @Override public void evaluateSatisfaction()
    {

    }

    @Override public void reduceLearningRate()
    {

    }

    @Override public void train()
    {

    }
}
