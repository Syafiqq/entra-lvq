package com.github.syafiqq.entra.lvq.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 2:08 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatabaseApp
{
    @SuppressWarnings("NullableProblems")
    @NotNull protected Connection connection;
    @NotNull protected String path;

    public DatabaseApp(@NotNull String path) throws SQLException
    {
        this.path = path;
        this.reconnect();
    }

    public DatabaseApp(@NotNull Connection connection, @NotNull String path)
    {
        this.connection = connection;
        this.path = path;
    }

    public DatabaseApp(@NotNull DatabaseApp orm)
    {
        this(orm.connection, orm.path);
    }

    public void reconnect() throws SQLException
    {
        this.connection = DriverManager.getConnection(this.path);
    }

    public void close() throws SQLException
    {
        this.connection.close();
    }

    public boolean isClosed() throws SQLException
    {
        return this.connection.isClosed();
    }

    public void setProperties(@NotNull DatabaseApp properties)
    {
        this.connection = properties.connection;
        this.path = properties.path;
    }

    public @NotNull Connection getConnection()
    {
        return connection;
    }
}
