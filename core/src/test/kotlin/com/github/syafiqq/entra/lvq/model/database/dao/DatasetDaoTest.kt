package com.github.syafiqq.entra.lvq.model.database.dao

import com.github.syafiqq.entra.lvq.util.Settings
import org.junit.Assert
import org.junit.Test


/*
 * This <entra-lvq> created by : 
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 9:32 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */

class DatasetDaoTest
{
    @Test
    fun `it_should_get_all_data`()
    {
        val list = DatasetDao.getAll(Settings.DB)
        Assert.assertNotNull(list)
        list.forEach(System.out::println)
    }
}
