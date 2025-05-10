package com.raizunne.redstonic.Util;

import java.text.NumberFormat;
import java.util.Locale;

import net.minecraft.util.StatCollector;

/**
 * Created by Raizunne as a part of Redstonic
 * on 04/02/2015, 06:37 PM.
 */
public class Lang {

    public static String translate(String string) {
        return StatCollector.translateToLocal(string);
    }

    public static String addComas(int i) {
        return NumberFormat.getNumberInstance(Locale.US)
            .format(i);
    }
}
