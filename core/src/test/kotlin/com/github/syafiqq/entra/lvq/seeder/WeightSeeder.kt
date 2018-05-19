package com.github.syafiqq.entra.lvq.seeder

import com.github.syafiqq.entra.lvq.model.database.dao.WeightDao
import com.github.syafiqq.entra.lvq.model.database.pojo.WeightPojo
import com.github.syafiqq.entra.lvq.util.Settings
import org.junit.Test


/*
 * This <entra-lvq> created by : 
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 4:55 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
class WeightSeeder
{
    @Test
    fun addInitial()
    {
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(2, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(3, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(4, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 4) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(5, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 5) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(6, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 6) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(7, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 7) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(8, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 8) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(9, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 9) })
    }

    @Test
    fun forDebugOnly()
    {
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(2, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(3, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 3) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(4, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(5, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 5) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(6, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 6) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(7, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 7) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(8, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 8) })
        WeightDao.insert(Settings.DB, WeightPojo().apply { set(9, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 9) })
    }
}

fun WeightPojo.set(id: Int?, g1: Number, g2: Number, g3: Number, g4: Number, g5: Number, g6: Number, g7: Number, g8: Number, g9: Number, g10: Number, g11: Number, g12: Number, g13: Number, g14: Number, g15: Number, g16: Number, g17: Number, g18: Number, g19: Number, g20: Number, g21: Number, target: Int)
{
    this.id = id
    this.vector["g1"] = g1.toDouble()
    this.vector["g2"] = g2.toDouble()
    this.vector["g3"] = g3.toDouble()
    this.vector["g4"] = g4.toDouble()
    this.vector["g5"] = g5.toDouble()
    this.vector["g6"] = g6.toDouble()
    this.vector["g7"] = g7.toDouble()
    this.vector["g8"] = g8.toDouble()
    this.vector["g9"] = g9.toDouble()
    this.vector["g10"] = g10.toDouble()
    this.vector["g11"] = g11.toDouble()
    this.vector["g12"] = g12.toDouble()
    this.vector["g13"] = g13.toDouble()
    this.vector["g14"] = g14.toDouble()
    this.vector["g15"] = g15.toDouble()
    this.vector["g16"] = g16.toDouble()
    this.vector["g17"] = g17.toDouble()
    this.vector["g18"] = g18.toDouble()
    this.vector["g19"] = g19.toDouble()
    this.vector["g20"] = g20.toDouble()
    this.vector["g21"] = g21.toDouble()
    this.target = target
}
