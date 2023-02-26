package com.king.kvcache.cache

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.king.kvcache.KVCache

/**
 * Preferences DataStore
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
// At the top level of your kotlin file:
internal val Context.dataStoreCache by preferencesDataStore(name = KVCache.Provider.DATA_STORE_CACHE)