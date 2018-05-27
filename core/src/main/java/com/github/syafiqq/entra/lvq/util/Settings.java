package com.github.syafiqq.entra.lvq.util;

import com.github.syafiqq.entra.lvq.model.database.DatabaseApp;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import org.jetbrains.annotations.NotNull;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 2:43 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class Settings
{
    public static final String[] columns = new String[]
            {
                    "g1",
                    "g2",
                    "g3",
                    "g4",
                    "g5",
                    "g6",
                    "g7",
                    "g8",
                    "g9",
                    "g10",
                    "g11",
                    "g12",
                    "g13",
                    "g14",
                    "g15",
                    "g16",
                    "g17",
                    "g18",
                    "g19",
                    "g20",
                    "g21",
            };
    @SuppressWarnings("NullableProblems") @NotNull public static final DatabaseApp DB;
    private static final DBSource source = DBSource.JAR;
    private static final URL path;

    static
    {
        path = ClassLoader.getSystemClassLoader().getResource("db.mcrypt");

        String uPath;
        try
        {
            switch(source)
            {
                case JAR:
                {
                    uPath = java.net.URLDecoder.decode("jdbc:sqlite::resource:jar:" + path.getPath(), "UTF-8");
                }
                break;
                default:
                {

                    uPath = java.net.URLDecoder.decode("jdbc:sqlite:" + path.getPath(), "UTF-8");

                }
                break;
            }
        }
        catch(UnsupportedEncodingException e)
        {
            uPath = null;
        }

        DatabaseApp db;
        try
        {
            db = new DatabaseApp(uPath);
        }
        catch(SQLException e)
        {
            db = null;
            e.printStackTrace();
            System.exit(1);
        }
        DB = db;
    }
}
