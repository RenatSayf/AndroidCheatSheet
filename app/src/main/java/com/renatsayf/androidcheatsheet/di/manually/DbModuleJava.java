package com.renatsayf.androidcheatsheet.di.manually;

import com.renatsayf.androidcheatsheet.App;
import com.renatsayf.androidcheatsheet.data.db.room.java.AppDatabase;
import com.renatsayf.androidcheatsheet.domain.db.java.DbRepository;

//region Hint Room_Database_module
public class DbModuleJava
{
    private static final AppDatabase database = AppDatabase.getInstance(App.Companion.getINSTANCE());
    private static DbRepository repository;

    private DbModuleJava() {}

    //region Hint Room_Getting_singleton_database_repository
    public static DbRepository getDbRepository() {

        if (repository == null) {
            repository = new DbRepository(database);
        }
        return repository;
    }
    //endregion Room_Getting_singleton_database_repository
}
//endregion Room_Database_module
