package com.king.kvcache.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
// At the top level of your kotlin file:
internal val Context.dataStoreCache: DataStore<Preferences> by preferencesDataStore(name = "dataStoreCache")