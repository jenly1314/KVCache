package com.king.kvcache.app.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Parcelize
data class ParcelableBean(val name: String, val i: Int, val bool: Boolean): Parcelable