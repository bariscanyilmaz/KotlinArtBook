package com.ylmazbarscan.kotlinartbook

import android.graphics.Bitmap

/**
 * Created by ylmazbarscan on 26.11.2017.
 */
class Globals {
    companion object Chosen {
        var chosenImage:Bitmap?=null
        fun returnImage():Bitmap? {
            return chosenImage!!
        }
    }
}