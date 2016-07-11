package com.trustedshops.androidsdk.trustbadge;

import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.DecoderException;

public class TextUtil {

    /**
     * Converts String to HEX
     * @param bytes
     * @return Hex representation of String given
     */
    public static String encodeHex(byte[] bytes)
    {
        String result = null;
        if (bytes != null && bytes.length != 0)
        {
            result = String.valueOf(Hex.encodeHex(bytes));
        }
        return result;
    }

    /**
     * Converts HEX back to String
     * @param chars
     * @return String representation of hex given
     */
    public static String decodeHex(char[] chars)
    {
        String result = null;
        if (chars != null && chars.length != 0)
        {
            try
            {
                byte[] decodedHex = Hex.decodeHex(chars);
                result = new String(decodedHex);
            } catch (DecoderException e)
            {
                Log.d("TSDEBUG", "Could not encode SKU");
            }
        }
        return result;
    }
}
