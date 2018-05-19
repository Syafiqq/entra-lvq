package com.github.syafiqq.entra.lvq.database

import com.github.syafiqq.entra.lvq.model.database.dao.DatasetDao
import com.github.syafiqq.entra.lvq.util.Settings
import org.junit.Test


/*
 * This <entra-lvq> created by : 
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 3:47 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
class CreateDatasetTable: DatabaseDispatcher
{
    @Test override fun up()
    {
        val db = Settings.DB
        db.connection.prepareStatement("CREATE TABLE IF NOT EXISTS ${DatasetDao.TABLE} (  `no` INT(50) NOT NULL PRIMARY KEY,  `g1` DOUBLE NOT NULL,  `g2` DOUBLE NOT NULL,  `g3` DOUBLE NOT NULL,  `g4` DOUBLE NOT NULL,  `g5` DOUBLE NOT NULL,  `g6` DOUBLE NOT NULL,  `g7` DOUBLE NOT NULL,  `g8` DOUBLE NOT NULL,  `g9` DOUBLE NOT NULL,  `g10` DOUBLE NOT NULL,  `g11` DOUBLE NOT NULL,  `g12` DOUBLE NOT NULL,  `g13` DOUBLE NOT NULL,  `g14` DOUBLE NOT NULL,  `g15` DOUBLE NOT NULL,  `g16` DOUBLE NOT NULL,  `g17` DOUBLE NOT NULL,  `g18` DOUBLE NOT NULL,  `g19` DOUBLE NOT NULL,  `g20` DOUBLE NOT NULL,  `g21` DOUBLE NOT NULL,  `target` INT(50) NOT NULL)")
                .run {
                    execute()
                    close()
                }
    }

    @Test override fun down()
    {
        val db = Settings.DB
        db.connection.prepareStatement("DROP TABLE IF EXISTS ${DatasetDao.TABLE}").run {
            execute()
            close()
        }
    }

    @Test
    fun refresh()
    {
        this.down()
        this.up()
    }
}
