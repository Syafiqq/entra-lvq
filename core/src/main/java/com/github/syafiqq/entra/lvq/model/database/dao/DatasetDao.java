package com.github.syafiqq.entra.lvq.model.database.dao;

import com.github.syafiqq.entra.lvq.model.database.DatabaseApp;
import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
import com.github.syafiqq.entra.lvq.util.Settings;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 3:55 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetDao
{
    public final static String TABLE = "dataset";

    public static DatasetPojo insert(@NotNull DatabaseApp db, @NotNull DatasetPojo pojo)
    {
        pojo.id = DatasetDao._insert(db, pojo);
        return pojo;
    }

    private static int _insert(@NotNull DatabaseApp db, @NotNull DatasetPojo pojo)
    {
        db.setProperties(db);
        int id = 0;
        try
        {
            if(db.isClosed())
            {
                db.reconnect();
            }
            @NotNull final PreparedStatement statement = db.getConnection().prepareStatement("INSERT OR REPLACE INTO " + TABLE + " (`no`, `g1`, `g2`, `g3`, `g4`, `g5`, `g6`, `g7`, `g8`, `g9`, `g10`, `g11`, `g12`, `g13`, `g14`, `g15`, `g16`, `g17`, `g18`, `g19`, `g20`, `g21`, `target`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            AtomicInteger c = new AtomicInteger();
            statement.setObject(c.incrementAndGet(), pojo.id, Types.INTEGER);
            Arrays.stream(Settings.columns).forEach((idx) -> {
                // @formatter:off
                try { statement.setObject(c.incrementAndGet(), pojo.vector(idx), Types.DOUBLE); }
                catch(SQLException ignored) {}
                // @formatter:on
            });
            statement.setObject(c.incrementAndGet(), pojo.target, Types.INTEGER);
            statement.execute();
            statement.close();

            @NotNull final PreparedStatement idStmt = db.getConnection().prepareStatement("SELECT last_insert_rowid()");
            @NotNull final ResultSet callResult = idStmt.executeQuery();
            callResult.next();
            id = callResult.getInt("last_insert_rowid()");
            callResult.close();
            idStmt.close();
            db.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return id;
    }
}
