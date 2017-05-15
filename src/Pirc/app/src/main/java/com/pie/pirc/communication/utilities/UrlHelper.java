package com.pie.pirc.communication.utilities;

/**
 * Created by pgecsenyi on 2016.03.27..
 */
public class UrlHelper
{
    /***************************************************************************************************************//**
     * Public methods.
     ******************************************************************************************************************/

    public static String buildUrl(String... parts)
    {
        if (parts == null)
            return "";
        if (parts.length <= 1)
            return parts[0];

        StringBuilder sb = new StringBuilder();
        sb.append(parts[0]);
        for (int i = 1; i < parts.length; i++)
        {
            String part = parts[i];
            if (part != null && !part.equals(""))
            {
                sb.append("/");
                sb.append(part);
            }
        }

        String url = sb.toString();

        return url;
    }
}
